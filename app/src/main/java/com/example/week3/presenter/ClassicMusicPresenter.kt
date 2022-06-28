package com.example.week3.presenter

import com.example.week3.model.classic.AllClassicMusic
import com.example.week3.network.MusicRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ClassicMusicPresenter @Inject constructor(
    private val musicRepository: MusicRepository,
    private val compositeDisposable: CompositeDisposable
        ): ClassicPresenterContract{

    private var classicViewContract: ClassicViewContract? = null

    override fun initializePresenter(viewContract: ClassicViewContract) {
        classicViewContract = viewContract
    }

    override fun registerForNetworkState() {
        musicRepository.checkNetworkAvailability()
    }

    override fun getClassicMusic() {
        classicViewContract?.loadingState()

        // to monitor network state
        musicRepository.networkState
            .subscribe{ netState ->
                if (netState){
                    musicRepository.getClassicMusic()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                            // for success
                            { classicViewContract?.allClassicLoadedSuccess(it) },
                            // for error
                            { classicViewContract?.onError(it) }
                        )
                        .also { compositeDisposable.add(it) }
                } else {
                    classicViewContract?.onError(Throwable("No Internet Connection"))
                }
            }
            .also { compositeDisposable.add(it) }
    }

    override fun destroyPresenter() {
        classicViewContract = null
        compositeDisposable.dispose()
    }


}

interface ClassicViewContract {
    fun loadingState()
    fun connectionChecked()
    fun allClassicLoadedSuccess(allClassicMusic: List<AllClassicMusic>)
    fun onError(error: Throwable)
}

interface ClassicPresenterContract {
    fun initializePresenter(viewContract: ClassicViewContract)
    fun registerForNetworkState()
    fun getClassicMusic()
    fun destroyPresenter()

}