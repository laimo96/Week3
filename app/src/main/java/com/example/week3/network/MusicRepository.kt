package com.example.week3.network

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkCapabilities.*
import android.net.NetworkRequest
import com.example.week3.model.AllRockMusic
import com.example.week3.model.classic.AllClassicMusic
import com.example.week3.model.pop.AllPopMusic
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

interface MusicRepository {
    val networkState: BehaviorSubject<Boolean>
    fun checkNetworkAvailability() // : BehaviorSubject<Boolean>
    fun getRockMusic(): Single<List<AllRockMusic>>
    fun getPopMusic(): Single<List<AllPopMusic>>
    fun getClassicMusic(): Single<List<AllClassicMusic>>
}

class MusicRepositoryImpl @Inject constructor(
    private val connectivityManager: ConnectivityManager,
    private val musicService: MusicService,
    private val networkRequest: NetworkRequest
    ) : MusicRepository{



    override val networkState: BehaviorSubject<Boolean> = BehaviorSubject.create()

    override fun checkNetworkAvailability() {

        connectivityManager.requestNetwork(networkRequest, object :
            ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                networkState.onNext(true)
            }

            override fun onUnavailable() {
                super.onUnavailable()
                networkState.onNext(false)
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                networkState.onNext(false)
            }
        })
    }

    override fun getClassicMusic(): Single<List<AllClassicMusic>> =
        musicService.getClassicMusic()

    override fun getRockMusic(): Single<List<AllRockMusic>> =
        musicService.getRockMusic()


    override fun getPopMusic(): Single<List<AllPopMusic>> =
        musicService.getPopMusic()


    private fun checkCapabilities(): NetworkCapabilities? {
        connectivityManager.activeNetwork?.let{
            return connectivityManager.getNetworkCapabilities(it)
        }?: return null
    }

    fun checkNetwork(): Boolean {
        return checkCapabilities()?.hasCapability(NET_CAPABILITY_INTERNET) ?: false &&
                checkCapabilities()?.hasCapability(NET_CAPABILITY_VALIDATED)
                ?: false
    }
}