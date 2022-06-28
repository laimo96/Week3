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
import com.example.week3.adapter.PopAdapter
import com.example.week3.adapter.PopMusicItemClick
import com.example.week3.databinding.FragmentPopBinding
import com.example.week3.model.pop.AllPopMusic
import com.example.week3.presenter.PopPresenterContract
import com.example.week3.presenter.PopViewContract
import javax.inject.Inject

class PopFragment : Fragment(), PopViewContract {

    private val binding by lazy {
        FragmentPopBinding.inflate(layoutInflater)
    }

    @Inject
    lateinit var popPresenter: PopPresenterContract

    override fun onAttach(context: Context) {
        super.onAttach(context)
        MusicApp.musicComponent.inject(this)
    }

    private val popsAdapter by lazy {
        PopAdapter(object: PopMusicItemClick {
            override fun onMusicClicked(popMusic: AllPopMusic) {
                Log.d(ContentValues.TAG, "onMusicClicked: ${popMusic.artistName}" )
            }

        })
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding.popRV.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = popsAdapter
        }

        popPresenter.initializePresenter(this)
        popPresenter.registerForNetworkState()

        popPresenter.getPopMusic()

        return binding.root
    }

    // to destroy data after closure, to prevent memory leak
    override fun onDestroyView() {
        super.onDestroyView()
        popPresenter.destroyPresenter()
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

    override fun allPopLoadedSuccess(allPopMusic: List<AllPopMusic>) {
        popsAdapter.updatePopMusic(allPopMusic)
    }

    override fun onError(error: Throwable) {
        Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
    }
}