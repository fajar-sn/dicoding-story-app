package com.bangkit.intermediate.dicodingstoryapp.ui.story.story_list

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.intermediate.dicodingstoryapp.data.repository.Result
import com.bangkit.intermediate.dicodingstoryapp.databinding.FragmentStoryListBinding
import com.bangkit.intermediate.dicodingstoryapp.ui.helper.BaseFragment
import com.bangkit.intermediate.dicodingstoryapp.ui.helper.ViewModelFactory

class StoryListFragment : BaseFragment() {
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

    override fun setupViewModel() {
        val factory = ViewModelFactory.getStoryInstance()
        val viewModel: StoryListViewModel by viewModels { factory }
        this.viewModel = viewModel
    }

    override fun setupView(viewBinding: Any) {
        val binding = binding as FragmentStoryListBinding
        val progressBar = binding.progressBarStoryList
        val recyclerViewStory = binding.recyclerViewStory
        recyclerViewStory.setHasFixedSize(true)
        val orientation = requireActivity().applicationContext.resources.configuration.orientation

        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.recyclerViewStory.layoutManager = GridLayoutManager(requireActivity(), 2)
        } else {
            binding.recyclerViewStory.layoutManager = LinearLayoutManager(requireActivity())
        }

        val storyAdapter = StoryListAdapter()
        recyclerViewStory.adapter = storyAdapter
        val viewModel = viewModel as StoryListViewModel
        viewModel.getToken(requireContext())

        viewModel.getStories()?.observe(requireActivity()) { result ->
            Log.e("TAG", "DATA $result")
            if (result == null) return@observe

            when (result) {
                is Result.Loading -> progressBar.visibility = View.VISIBLE
                is Result.Error -> showError(progressBar, result.error)
                is Result.Success -> {
                    progressBar.visibility = View.GONE
                    val storiesData = result.data
                    Log.e("TAG", "DATA $storiesData")
                    storyAdapter.submitList(storiesData)
                }
            }
        }
    }

    override fun setupAction() {}
}