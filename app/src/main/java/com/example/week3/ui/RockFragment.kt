package com.example.week3.ui

import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.week3.MusicApp
import com.example.week3.adapter.RockAdapter
import com.example.week3.adapter.RockMusicItemClick
import com.example.week3.databinding.FragmentRockBinding
import com.example.week3.model.AllRockMusic
import com.example.week3.presenter.RockPresenterContract
import com.example.week3.presenter.RockViewContract
import javax.inject.Inject

class RockFragment : Fragment(), RockViewContract {

    private val binding by lazy {
        FragmentRockBinding.inflate(layoutInflater)
    }

    @Inject
    lateinit var rockPresenter: RockPresenterContract

    override fun onAttach(context: Context) {
        super.onAttach(context)
        MusicApp.musicComponent.inject(this)
    }

    private val rocksAdapter by lazy {
        RockAdapter(object: RockMusicItemClick {
            override fun onMusicClicked(rockMusic: AllRockMusic) {
                Log.d(ContentValues.TAG, "onMusicClicked: ${rockMusic.artistName}" )
            }

        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding.rockRV.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = rocksAdapter
        }

        rockPresenter.initializePresenter(this)
        rockPresenter.registerForNetworkState()

        rockPresenter.getRockMusic()

        return binding.root
    }

    // to destroy data after closure, to prevent memory leak
    override fun onDestroyView() {
        super.onDestroyView()
        rockPresenter.destroyPresenter()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
    }

    override fun loadingState() {
        Toast.makeText(requireContext(), "Loading...", Toast.LENGTH_SHORT).show()
    }

    override fun connectionChecked() {
        TODO("Not yet implemented")
    }

    override fun allRockLoadedSuccess(allRockMusic: List<AllRockMusic>) {
        rocksAdapter.updateRockMusic(allRockMusic)
    }

    override fun onError(error: Throwable) {
        Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
    }
}