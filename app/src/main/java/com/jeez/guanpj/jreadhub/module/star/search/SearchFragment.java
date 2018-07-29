package com.jeez.guanpj.jreadhub.module.star.search;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding2.support.v7.widget.RxSearchView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jeez.guanpj.jreadhub.R;
import com.jeez.guanpj.jreadhub.bean.SearchHistoryBean;
import com.jeez.guanpj.jreadhub.event.FabClickEvent;
import com.jeez.guanpj.jreadhub.event.SearchByKeywordEvent;
import com.jeez.guanpj.jreadhub.module.adpter.FragmentAdapter;
import com.jeez.guanpj.jreadhub.module.adpter.SearchHistoryAdapterWithThirdLib;
import com.jeez.guanpj.jreadhub.module.star.news.StarCommonListFragment;
import com.jeez.guanpj.jreadhub.module.star.topic.StarTopicFragment;
import com.jeez.guanpj.jreadhub.mvpframe.rx.RxBus;
import com.jeez.guanpj.jreadhub.mvpframe.view.fragment.AbsBaseMvpSwipeBackFragment;
import com.jeez.guanpj.jreadhub.util.ResourceUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;

import static android.content.Context.SEARCH_SERVICE;

public class SearchFragment extends AbsBaseMvpSwipeBackFragment<SearchPresenter> implements SearchContract.View {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tabs)
    TabLayout mTabLayout;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;
    @BindView(R.id.ll_history)
    LinearLayout mHistoryLayout;
    @BindView(R.id.rv_history)
    RecyclerView mHistoryRecyclerView;
    @BindView(R.id.rl_list_header)
    RelativeLayout mListHeaderView;

    SearchView mSearchView;
    private FragmentAdapter mFragmentAdapter;
    private SearchHistoryAdapterWithThirdLib mHistoryAdapter;
    private View mEmptyView;
    private TextView mEmptyTipsView;

    public static SearchFragment newInstance() {
        Bundle args = new Bundle();
        SearchFragment fragment = new SearchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void performInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_search_container;
    }

    @Override
    public void initView() {
        mToolbar.inflateMenu(R.menu.menu_search);
        mToolbar.setNavigationIcon(ResourceUtil.getResource(getActivity(), R.attr.navBackIcon));
        mToolbar.setNavigationOnClickListener(v -> pop());

        MenuItem item = mToolbar.getMenu().findItem(R.id.action_search);
        mSearchView = (SearchView) item.getActionView();
        mSearchView.setQueryHint("输入关键字");
        mSearchView.onActionViewExpanded();
        /*SearchManager searchManager = (SearchManager)getActivity().getSystemService(SEARCH_SERVICE);
        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));*/

        mHistoryAdapter = new SearchHistoryAdapterWithThirdLib();
        mHistoryRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mHistoryRecyclerView.setAdapter(mHistoryAdapter);
        mEmptyView = getLayoutInflater().inflate(R.layout.view_empty, (ViewGroup) mHistoryRecyclerView.getParent(), false);
        mEmptyTipsView = mEmptyView.findViewById(R.id.txt_tips);
        mEmptyTipsView.setText("暂无搜索历史数据");
    }

    @Override
    public void initDataAndEvent() {
        List<String> pageTitles = new ArrayList<>();
        pageTitles.add("热门话题");
        pageTitles.add("资讯");

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(StarTopicFragment.newInstance());
        fragments.add(StarCommonListFragment.newInstance());

        mFragmentAdapter = new FragmentAdapter(getChildFragmentManager(), pageTitles);
        mFragmentAdapter.setFragments(fragments);
        mViewPager.setAdapter(mFragmentAdapter);

        for (int i = 0; i < pageTitles.size(); i++) {
            mTabLayout.addTab(mTabLayout.newTab().setText(pageTitles.get(i)));
        }
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);

        mToolbar.setNavigationOnClickListener(v -> pop());
        mHistoryAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            mPresenter.deleteHistory((SearchHistoryBean) adapter.getData().get(position));
        });
        mHistoryAdapter.setOnItemClickListener((adapter, view, position) -> {
            SearchHistoryBean searchHistoryBean = (SearchHistoryBean) adapter.getData().get(position);
            mSearchView.setQuery(searchHistoryBean.getKeyWord(), true);
            mSearchView.clearFocus();
        });
        initSearchView();
    }

    @SuppressLint("CheckResult")
    private void initSearchView() {
        RxSearchView.queryTextChangeEvents(mSearchView)
                .throttleLast(100, TimeUnit.MILLISECONDS)
                .debounce(300, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(searchViewQueryTextEvent -> {
                    String keyWord = searchViewQueryTextEvent.queryText().toString();
                    if (searchViewQueryTextEvent.isSubmitted()) {
                        mSearchView.clearFocus();

                        RxBus.getInstance().post(new SearchByKeywordEvent(keyWord));
                        List<Fragment> fragments = new ArrayList<>();
                        fragments.add(StarTopicFragment.newInstance(keyWord));
                        fragments.add(StarCommonListFragment.newInstance(keyWord));
                        mFragmentAdapter.setFragments(fragments);

                        showSearchResult();

                        SearchHistoryBean searchHistoryBean = new SearchHistoryBean();
                        searchHistoryBean.setKeyWord(keyWord);
                        searchHistoryBean.setTime(System.currentTimeMillis());
                        mPresenter.addHistory(searchHistoryBean);
                    }
                    if (TextUtils.isEmpty(keyWord)) {
                        showHistory();
                    }
                });
    }

    private void showSearchResult() {
        mViewPager.setVisibility(View.VISIBLE);
        mTabLayout.setVisibility(View.VISIBLE);
        mHistoryLayout.setVisibility(View.GONE);
    }

    private void showHistory() {
        mViewPager.setVisibility(View.GONE);
        mTabLayout.setVisibility(View.GONE);
        mHistoryLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.loadAllHistory();
    }

    @Override
    public void bindData(List<SearchHistoryBean> data) {
        mListHeaderView.setVisibility(View.VISIBLE);
        mHistoryAdapter.setNewData(data);
    }

    @Override
    public void showEmpty() {
        mListHeaderView.setVisibility(View.GONE);
        mHistoryAdapter.setEmptyView(mEmptyView);
    }

    @Override
    public void onPause() {
        super.onPause();
        mSearchView.clearFocus();
    }

    @OnClick(R.id.fab)
    void onFabClick() {
        if (mHistoryLayout.getVisibility() == View.VISIBLE) {
            mHistoryRecyclerView.scrollToPosition(0);
        } else {
            RxBus.getInstance().post(new FabClickEvent(mViewPager.getCurrentItem()));
        }
    }
}
