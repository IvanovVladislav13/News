package com.ivanov.newsapi.presentation.fragments.news

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import by.kirich1409.viewbindingdelegate.viewBinding
import com.ivanov.newsapi.R
import com.ivanov.newsapi.data.room.entity.News
import com.ivanov.newsapi.databinding.NewsFragmentBinding
import com.ivanov.newsapi.presentation.activities.MainActivity
import com.ivanov.newsapi.presentation.fragments.news.adapters.LoaderStateAdapter
import com.ivanov.newsapi.presentation.fragments.news.adapters.NewsAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewsFragment : Fragment(R.layout.news_fragment) {

    private val binding by viewBinding(NewsFragmentBinding::bind)

    @ExperimentalPagingApi
    private val viewModel: NewsViewModel by viewModel()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NewsAdapter
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var loaderStateAdapter: LoaderStateAdapter

    @ExperimentalPagingApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapters()
        initRecyclerView()
        initViewModel()
        initRefresh()
    }

    private fun initRefresh() {
        swipeRefresh = binding.swipeRefresh
        swipeRefresh.setOnRefreshListener {
            adapter.refresh()
        }

        lifecycleScope.launch {
            adapter.loadStateFlow.collectLatest {
                swipeRefresh.isRefreshing = it.refresh is LoadState.Loading
            }
        }
    }

    @ExperimentalPagingApi
    private fun initViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.newsState.collectLatest {
                adapter.submitData(viewLifecycleOwner.lifecycle, it)
            }
        }
        if (viewModel.newsPosition > 0) {
            recyclerView.scrollToPosition(viewModel.newsPosition)
            viewModel.newsPosition = 0
        }
    }

    @ExperimentalPagingApi
    private fun initAdapters() {
        adapter = NewsAdapter(context)
        loaderStateAdapter = LoaderStateAdapter(context) { adapter.retry() }

        adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                if (positionStart == 0 && positionStart == recyclerView.paddingStart) {
                    recyclerView.scrollToPosition(0)
                }
            }
        })

        adapter.setOnItemClickListener(object : NewsAdapter.OnItemClickListener {
            override fun onItemClick(news: News, position: Int) {
                val bundle = Bundle()
                bundle.putString("URL", news.url)

                (activity as MainActivity).navController.navigate(
                    R.id.action_newsFragment_to_webViewFragment,
                    bundle
                ).also {
                    viewModel.newsPosition = position
                }
            }
        })
    }

    private fun initRecyclerView() {
        recyclerView = binding.recyclerNews
        recyclerView.adapter = adapter.withLoadStateFooter(loaderStateAdapter)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        recyclerView.adapter = null
    }
}