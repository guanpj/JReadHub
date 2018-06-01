package com.jeez.guanpj.jreadhub.ui.topic.detail;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jeez.guanpj.jreadhub.R;
import com.jeez.guanpj.jreadhub.base.BaseAdapter;
import com.jeez.guanpj.jreadhub.base.BaseViewHolder;
import com.jeez.guanpj.jreadhub.bean.EntityEventTopicBean;
import com.jeez.guanpj.jreadhub.bean.RelateTopicBean;
import com.jeez.guanpj.jreadhub.bean.TopicBean;
import com.jeez.guanpj.jreadhub.bean.TopicNewsBean;
import com.jeez.guanpj.jreadhub.mvpframe.view.fragment.AbsBaseMvpFragment;
import com.jeez.guanpj.jreadhub.ui.common.article.CommonArticleFragment;
import com.jeez.guanpj.jreadhub.ui.topic.detail.relate.RelateTopicWindow;
import com.jeez.guanpj.jreadhub.util.Constants;
import com.jeez.guanpj.jreadhub.widget.RelativePopupWindow;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import butterknife.BindView;

public class TopicDetailFragment extends AbsBaseMvpFragment<TopicDetailPresenter> implements TopicDetailContract.View, Toolbar.OnMenuItemClickListener {

    public static final int VIEW_TYPE_TOP = 99, VIEW_TYPE_BOTTOM = 98;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.txt_topic_detail_title)
    TextView mTxtTopicTitle;
    @BindView(R.id.txt_topic_time)
    TextView mTxtTopicTime;
    @BindView(R.id.txt_topic_description)
    TextView mTxtTopicDescription;
    @BindView(R.id.ll_web_title_container)
    LinearLayout mTitleContainer;
    @BindView(R.id.ll_topic_trace_container)
    LinearLayout mTimelineContainer;
    @BindView(R.id.recycler_topic_trace)
    RecyclerView mRecyclerTimeline;
    @BindView(R.id.ll_relative_topic_container)
    LinearLayout mRelativeTopicContainer;
    @BindView(R.id.tfl_relative_topic)
    TagFlowLayout mRelativeTopic;
    @BindView(R.id.scroll_view)
    NestedScrollView mScrollView;

    private TopicBean mTopic;
    private BaseAdapter<RelateTopicBean> mTimelineAdapter = new BaseAdapter<RelateTopicBean>() {
        @Override
        public BaseViewHolder<RelateTopicBean> onCreateViewHolder(ViewGroup parent, int viewType) {
            return new TopicTraceViewHolder(getContext(), parent);
        }

        @Override
        public int getItemViewType(int position) {
            if (position == 0) {
                return VIEW_TYPE_TOP;
            }
            if (position == getItemCount() - 1) {
                return VIEW_TYPE_BOTTOM;
            }
            return super.getItemViewType(position);
        }
    };

    public static TopicDetailFragment newInstance(String topicId) {
        TopicDetailFragment fragment = new TopicDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.BUNDLE_TOPIC_ID, topicId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String topicId = getArguments().getString(Constants.BUNDLE_TOPIC_ID);
        mPresenter.getTopicDetail(topicId);
    }

    @Override
    protected void performInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_topic_detail;
    }

    @Override
    public void initView() {
        TypedValue navIcon = new TypedValue();
        Resources.Theme theme = getActivity().getTheme();
        theme.resolveAttribute(R.attr.navBackIcon, navIcon, true);

        mToolbar.setNavigationIcon(navIcon.resourceId);
        mToolbar.setNavigationOnClickListener(v -> pop());
        mToolbar.setOnMenuItemClickListener(this);
    }

    @Override
    public void initDataAndEvent() {
        if (mTopic == null) {
            return;
        }
        mTxtTopicTitle.setText(mTopic.getTitle());
        mTxtTopicTime.setText(mTopic.getPublishDate().toLocalDate().toString() + "  " +
                mTopic.getPublishDate().toLocalTime().toString().substring(0, 8));
        mTxtTopicDescription.setText(mTopic.getSummary());
        mTxtTopicDescription.setVisibility(TextUtils.isEmpty(mTopic.getSummary()) ? View.GONE : View.VISIBLE);
        mTitleContainer.removeAllViews();
        for (final TopicNewsBean topic : mTopic.getNewsArray()) {
            TextView textView = new TextView(getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            textView.setLayoutParams(params);
            textView.setPadding(10, 16, 10, 16);
            textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_news, 0, 0, 0);
            textView.setCompoundDrawablePadding(15);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            textView.setTextColor(getResources().getColor(R.color.text_topic_detail_news_title));
            textView.setBackgroundResource(R.drawable.selector_btn_background);
            if (TextUtils.isEmpty(topic.getSiteName())) {
                textView.setText(topic.getTitle());
            } else {
                SpannableString spannableTitle = SpannableString.valueOf(topic.getTitle() + " " + topic.getSiteName());
                spannableTitle.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.text_topic_detail_news_author)),
                        topic.getTitle().length() + 1,
                        topic.getTitle().length() + topic.getSiteName().length() + 1,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                textView.setText(spannableTitle);
            }
            textView.setOnClickListener(v -> start(CommonArticleFragment.newInstance(topic)));
            mTitleContainer.addView(textView);
        }
        mRecyclerTimeline.setAdapter(mTimelineAdapter);
        mRecyclerTimeline.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerTimeline.setNestedScrollingEnabled(false);
        if (null != mTopic.getTimeline() && null != mTopic.getTimeline().getTopics() && 0 < mTopic.getTimeline().getTopics().size()) {
            mTimelineAdapter.addItems(mTopic.getTimeline().getTopics());
            mTimelineContainer.setVisibility(View.VISIBLE);
        } else {
            mTimelineContainer.setVisibility(View.GONE);
        }

        if (!mTopic.getEntityEventTopics().isEmpty()) {
            mRelativeTopicContainer.setVisibility(View.VISIBLE);
            ArrayList<EntityEventTopicBean> entityEventTopics = mTopic.getEntityEventTopics();
            mRelativeTopic.setAdapter(new TagAdapter<EntityEventTopicBean>(mTopic.getEntityEventTopics()) {
                @Override
                public View getView(FlowLayout parent, int position, EntityEventTopicBean entityEventTopicBean) {
                    TextView item = (TextView) getLayoutInflater().inflate(R.layout.item_relate_topic, mRelativeTopic, false);
                    item.setText(entityEventTopicBean.getEntityName() + entityEventTopicBean.getEventTypeLabel());
                    return item;
                }
            });
            mRelativeTopic.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
                @Override
                public void onSelected(Set<Integer> selectPosSet) {
                    if (!selectPosSet.isEmpty()) {
                        Iterator<Integer> iterator = selectPosSet.iterator();
                        if (iterator.hasNext()) {

                        }
                    }
                }
            });
            mRelativeTopic.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                @Override
                public boolean onTagClick(View view, int position, FlowLayout parent) {
                    String topicId = String.valueOf(entityEventTopics.get(position).getEntityId());
                    long order = mTopic.getOrder();
                    RelateTopicWindow window = new RelateTopicWindow(getActivity(), topicId, order);
                    window.showOnAnchor(view, RelativePopupWindow.VerticalPosition.ABOVE, RelativePopupWindow.HorizontalPosition.CENTER, false);
                    return true;
                }
            });
        } else {
            mRelativeTopicContainer.setVisibility(View.GONE);
        }

        mScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            /*mTxtToolbarTitle.setVisibility(
                    scrollY > mTxtTopicTime.getBottom() ? View.VISIBLE : View.GONE);
            mImgToolbar.setVisibility(scrollY > mTxtTopicTime.getBottom() ? View.GONE : View.VISIBLE);*/
        });
    }

    @Override
    public void onRequestStart() {

    }

    @Override
    public void onRequestTopicEnd(TopicBean bean) {
        mTopic = bean;
        initDataAndEvent();
    }

    @Override
    public void onRequestError() {
        showShortToast("请求错误");
    }

    @Override
    public void onFabClick() {

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }
}
