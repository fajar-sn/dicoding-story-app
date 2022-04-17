package com.bangkit.intermediate.dicodingstoryapp.ui.story_list

import android.content.res.Configuration
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.intermediate.dicodingstoryapp.R
import com.bangkit.intermediate.dicodingstoryapp.databinding.FragmentStoryListBinding
import com.bangkit.intermediate.dicodingstoryapp.ui.helper.BaseFragment

class StoryListFragment : BaseFragment() {
    private lateinit var recyclerViewStory: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        viewBinding = FragmentStoryListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupView(binding)
        setupAction()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }

    override fun setupView(viewBinding: Any) {
//        recyclerViewStory = binding.recyclerViewStory
//        recyclerViewStory.setHasFixedSize(true)
//        val orientation = requireActivity().applicationContext.resources.configuration.orientation
//
//        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            binding.recyclerViewStory.layoutManager = GridLayoutManager(requireActivity(), 2)
//        } else {
//            binding.recyclerViewStory.layoutManager = LinearLayoutManager(requireActivity())
//        }
//
//        val adapter = StoryListAdapter()
    }

    override fun setupViewModel() {

    }

    override fun setupAction() {

    }
}