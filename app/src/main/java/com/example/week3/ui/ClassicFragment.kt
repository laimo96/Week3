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
import com.example.week3.adapter.ClassicAdapter
import com.example.week3.adapter.ClassicMusicItemClick
import com.example.week3.databinding.FragmentClassicBinding
import com.example.week3.model.classic.AllClassicMusic
import com.example.week3.presenter.*
import javax.inject.Inject

class ClassicFragment : Fragment(), ClassicViewContract {

    private val binding by lazy {
        FragmentClassicBinding.inflate(layoutInflater)
    }


    @Inject
    lateinit var classicPresenter: ClassicPresenterContract

    override fun onAttach(context: Context) {
        super.onAttach(context)
        MusicApp.musicComponent.inject(this)
    }


    private val classicsAdapter by lazy {
        ClassicAdapter(object: ClassicMusicItemClick {
            override fun onMusicClicked(classicMusic: AllClassicMusic) {
                Log.d(ContentValues.TAG, "onMusicClicked: ${classicMusic.artistName}" )
            }

        })
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding.classicRV.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = classicsAdapter
        }

        classicPresenter.initializePresenter(this)
        classicPresenter.registerForNetworkState()

        classicPresenter.getClassicMusic()

        return binding.root
    }

    // to destroy data after closure, to prevent memory leak
    override fun onDestroyView() {
        super.onDestroyView()
        classicPresenter.destroyPresenter()
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


    override fun allClassicLoadedSuccess(allClassicMusic: List<AllClassicMusic>) {
        classicsAdapter.updateClassicMusic(allClassicMusic)
    }

    override fun onError(error: Throwable) {
        Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
    }
}