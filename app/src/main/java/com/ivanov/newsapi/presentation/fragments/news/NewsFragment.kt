package com.ivanov.newsapi.presentation.fragments.news

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import by.kirich1409.viewbindingdelegate.viewBinding
import com.ivanov.newsapi.R
import com.ivanov.newsapi.databinding.NewsFragmentBinding
import com.ivanov.newsapi.data.room.entity.News
import com.ivanov.newsapi.presentation.activities.MainActivity
import com.ivanov.newsapi.presentation.fragments.news.recycleview.adapters.LoaderStateAdapter
import com.ivanov.newsapi.presentation.fragments.news.recycleview.adapters.NewsAdapter
import com.ivanov.newsapi.presentation.fragments.news.recycleview.adapters.OnItemClickListener
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewsFragment : Fragment(R.layout.news_fragment) {

    private val mBinding by viewBinding(NewsFragmentBinding::bind)
    @ExperimentalPagingApi
    private val viewModel: NewsViewModel by viewModel()
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: NewsAdapter
    private lateinit var mSwipeRefresh: SwipeRefreshLayout
    private lateinit var mLoaderStateAdapter: LoaderStateAdapter

    @ExperimentalPagingApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapters(savedInstanceState)
        initRecyclerView()
        initViewModel()
        initRefresh()
    }

    private fun initRefresh() {
        mSwipeRefresh = mBinding.swipeRefresh
        mSwipeRefresh.setOnRefreshListener { mAdapter.refresh() }

        lifecycleScope.launch {
            mAdapter.loadStateFlow.collectLatest {
                mSwipeRefresh.isRefreshing = it.refresh is LoadState.Loading
            }
        }
    }

    @ExperimentalPagingApi
    private fun initViewModel() {
        lifecycleScope.launch {
            viewModel.fetchNews().collectLatest {
                mAdapter.submitData(it)
            }
        }
    }

    private fun initAdapters(savedInstanceState: Bundle?) {
        mAdapter = NewsAdapter(context)
        mLoaderStateAdapter = LoaderStateAdapter(context) { mAdapter.retry() }

        mAdapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(news: News, position: Int) {
                val bundle = Bundle()
                bundle.putString("URL", news.url)
                (activity as MainActivity).navController.navigate(R.id.action_newsFragment_to_webViewFragment, bundle)
            }
        })
    }

    private fun initRecyclerView() {
        mRecyclerView = mBinding.recycleNews
        mRecyclerView.adapter = mAdapter.withLoadStateFooter(mLoaderStateAdapter)
    }

    override fun onDestroyView() {
        mRecyclerView.adapter = null
        super.onDestroyView()
    }
}