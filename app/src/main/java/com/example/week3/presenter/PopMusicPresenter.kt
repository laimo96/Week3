package com.example.week3.presenter

import com.example.week3.model.pop.AllPopMusic
import com.example.week3.network.MusicRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PopMusicPresenter @Inject constructor(
    private val musicRepository: MusicRepository,
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
): PopPresenterContract{

    private var popViewContract: PopViewContract? = null

    override fun initializePresenter(viewContract: PopViewContract) {
        popViewContract = viewContract
    }

    override fun registerForNetworkState() {
        musicRepository.checkNetworkAvailability()
    }

    override fun getPopMusic() {
        popViewContract?.loadingState()

        // to monitor network state
        musicRepository.networkState
            .subscribe{ netState ->
                if (netState){
                    musicRepository.getPopMusic()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                            // for success
                            { popViewContract?.allPopLoadedSuccess(it) },
                            // for error
                            { popViewContract?.onError(it) }
                        )
                        .also { compositeDisposable.add(it) }
                } else {
                    popViewContract?.onError(Throwable("No Internet Connection"))
                }
            }
            .also { compositeDisposable.add(it) }
    }

    override fun destroyPresenter() {
        popViewContract = null
        compositeDisposable.dispose()
    }


}

interface PopViewContract {
    fun loadingState()
    fun connectionChecked()
    fun allPopLoadedSuccess(allPopMusic: List<AllPopMusic>)
    fun onError(error: Throwable)
}

interface PopPresenterContract {
    fun initializePresenter(viewContract: PopViewContract)
    fun registerForNetworkState()
    fun getPopMusic()
    fun destroyPresenter()

}