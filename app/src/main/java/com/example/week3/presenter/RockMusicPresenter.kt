package com.example.week3.presenter

import com.example.week3.model.AllRockMusic
import com.example.week3.network.MusicRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RockMusicPresenter @Inject constructor(
    private val musicRepository: MusicRepository,
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
): RockPresenterContract{

    private var rockViewContract: RockViewContract? = null

    override fun initializePresenter(viewContract: RockViewContract) {
        rockViewContract = viewContract
    }

    override fun registerForNetworkState() {
        musicRepository.checkNetworkAvailability()
    }

    override fun getRockMusic() {
        rockViewContract?.loadingState()

        // to monitor network state
        musicRepository.networkState
            .subscribe{ netState ->
                if (netState){
                    musicRepository.getRockMusic()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                            // for success
                            { rockViewContract?.allRockLoadedSuccess(it) },
                            // for error
                            { rockViewContract?.onError(it) }
                        )
                        .also { compositeDisposable.add(it) }
                } else {
                    rockViewContract?.onError(Throwable("No Internet Connection"))
                }
            }
            .also { compositeDisposable.add(it) }
    }

    override fun destroyPresenter() {
        rockViewContract = null
        compositeDisposable.dispose()
    }


}

interface RockViewContract {
    fun loadingState()
    fun connectionChecked()
    fun allRockLoadedSuccess(allRockMusic: List<AllRockMusic>)
    fun onError(error: Throwable)
}

interface RockPresenterContract {
    fun initializePresenter(viewContract: RockViewContract)
    fun registerForNetworkState()
    fun getRockMusic()
    fun destroyPresenter()

}