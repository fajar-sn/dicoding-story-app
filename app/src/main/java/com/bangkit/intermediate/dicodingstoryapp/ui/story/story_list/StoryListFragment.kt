package com.bangkit.intermediate.dicodingstoryapp.ui.story.story_list

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.intermediate.dicodingstoryapp.databinding.FragmentStoryListBinding
import com.bangkit.intermediate.dicodingstoryapp.ui.helper.BaseFragment
import com.bangkit.intermediate.dicodingstoryapp.ui.helper.ViewModelFactory
import com.bangkit.intermediate.dicodingstoryapp.ui.story.StoryListViewModel
import com.bangkit.intermediate.dicodingstoryapp.ui.story.add_story.AddStoryActivity

class StoryListFragment : BaseFragment() {
    private val launcherIntentAddStory =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode != ADD_STORY_RESULT) return@registerForActivityResult
            val isSubmitted = it.data?.getBooleanExtra("isSubmitted", false) as Boolean
            if (isSubmitted) setupView(binding)
        }

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
        val recyclerViewStory = binding.recyclerViewStory
        recyclerViewStory.setHasFixedSize(true)
        val orientation = requireActivity().applicationContext.resources.configuration.orientation

        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.recyclerViewStory.layoutManager = GridLayoutManager(requireActivity(), 2)
        } else {
            binding.recyclerViewStory.layoutManager = LinearLayoutManager(requireActivity())
        }

        val storyAdapter = StoryListAdapter()

        recyclerViewStory.adapter = storyAdapter.withLoadStateFooter(
            footer = LoadingStateAdapter { storyAdapter.retry() }
        )

        val viewModel = viewModel as StoryListViewModel
        viewModel.getToken(requireContext())

        viewModel.getStories()
            ?.observe(requireActivity()) { storyAdapter.submitData(lifecycle, it) }
    }

    override fun setupAction() =
        (binding as FragmentStoryListBinding).floatingActionButton.setOnClickListener {
            val intent = Intent(requireContext(), AddStoryActivity::class.java)
            launcherIntentAddStory.launch(intent)
        }

    companion object {
        const val ADD_STORY_RESULT = 200
    }
}