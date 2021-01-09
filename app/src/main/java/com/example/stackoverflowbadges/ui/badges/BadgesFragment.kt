package com.example.stackoverflowbadges.ui.badges

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.stackoverflowbadges.databinding.BadgesFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalPagingApi
@AndroidEntryPoint
class BadgesFragment : Fragment() {

    companion object {
        fun newInstance() =
            BadgesFragment()
    }

    private var postsJob: Job? = null
    private lateinit var binding: BadgesFragmentBinding
    private val viewModel: BadgesViewModel by activityViewModels()
    @Inject
    lateinit var adapter: BadgesAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = BadgesFragmentBinding.inflate(inflater, container, false)
        initList()
        initFilters()
        initAdapter()
        getPosts()
        return binding.root
    }

    /**
     * Init the list with dividers and swipe to refresh.
     */
    private fun initList() {
        val decoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        binding.list.addItemDecoration(decoration)
        binding.swipeToRefresh.setOnRefreshListener { adapter.refresh() }
    }

    /**
     * Get the posts from the view model and submit them to the adapter.
     */
    private fun getPosts() {
        // Make sure we cancel the previous job before creating a new one
        postsJob?.cancel()
        postsJob = viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getBadges().collectLatest {
                adapter.submitData(it)
            }
        }
    }

    /**
     * Init the adapter, show a toast if the request fail with the load state listener and
     * replace the fragment if we are in landscape mode or navigate to a new one if we are in portrait.
     */
    private fun initAdapter() {
        binding.list.adapter = adapter.withLoadStateFooter(footer = BadgesLoadStateAdapter { adapter.retry() })
        adapter.addLoadStateListener { loadState ->
            if (loadState.append.endOfPaginationReached && adapter.itemCount == 0) {
                binding.emptyState?.visibility = View.VISIBLE
            } else {
                binding.emptyState?.visibility = View.GONE
            }
            binding.swipeToRefresh.isRefreshing = loadState.refresh is LoadState.Loading
            context?.apply {
                val errorState = loadState.refresh as? LoadState.Error
                errorState?.let {
                    Toast.makeText(
                        this,
                        "\uD83D\uDE28 Wooops ${it.error.localizedMessage}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun initFilters() {
        binding.radioGroup?.setOnCheckedChangeListener { group, checkedId ->
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.setFilterByRank(checkedId)
                adapter.refresh()
            }
        }
        binding.toggle?.setOnCheckedChangeListener { buttonView, isChecked ->
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.setFilterOrder(isChecked)
                adapter.refresh()
            }
        }
    }

}