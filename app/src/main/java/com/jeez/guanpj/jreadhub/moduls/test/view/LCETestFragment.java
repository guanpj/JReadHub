package com.jeez.guanpj.jreadhub.moduls.test.view;

import com.jeez.guanpj.jreadhub.R;
import com.jeez.guanpj.jreadhub.moduls.test.base.view.BaseRefreshLceFragment;
import com.jeez.guanpj.jreadhub.moduls.test.base.view.BaseRefreshViewAdapter;
import com.jeez.guanpj.jreadhub.moduls.test.bean.PostModel;
import com.jeez.guanpj.jreadhub.moduls.test.presenter.LCETestPresenter;

public class LCETestFragment extends BaseRefreshLceFragment<PostModel, LCETestView, LCETestPresenter>
        implements LCETestView {

    @Override
    public LCETestPresenter createPresenter() {
        return new LCETestPresenter(getActivity());
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_essence;
    }

    @Override
    public void initData() {
        loadData(false);
        setLceAnimator(new LoadingAnimator());
    }

    @Override
    public BaseRefreshViewAdapter bindAdapter() {
        return new TestAdapter(getActivity());
    }

    @Override
    public void refreshData(boolean isDownRefresh) {
        super.refreshData(isDownRefresh);
        loadData(true);
    }

    @Override
    public void bindData(PostModel model) {
        super.bindData(model);
        getAdapter().refreshAdapter(isDownRefresh(),model.getList(),model);
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        super.loadData(pullToRefresh);
        getPresenter().getLCETestList(TestAdapter.Type.Text.type, pullToRefresh);
    }

}
