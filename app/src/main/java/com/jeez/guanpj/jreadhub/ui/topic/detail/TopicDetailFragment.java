package com.jeez.guanpj.jreadhub.ui.topic.detail;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jeez.guanpj.jreadhub.R;
import com.jeez.guanpj.jreadhub.bean.EntityEventTopicBean;
import com.jeez.guanpj.jreadhub.bean.TopicBean;
import com.jeez.guanpj.jreadhub.bean.TopicNewsBean;
import com.jeez.guanpj.jreadhub.event.OpenWebSiteEvent;
import com.jeez.guanpj.jreadhub.mvpframe.rx.RxBus;
import com.jeez.guanpj.jreadhub.mvpframe.view.fragment.AbsBaseMvpSwipeBackFragment;
import com.jeez.guanpj.jreadhub.ui.adpter.TopicTimelineAdapter;
import com.jeez.guanpj.jreadhub.ui.topic.detail.relate.RelevantTopicWindow;
import com.jeez.guanpj.jreadhub.util.Constants;
import com.jeez.guanpj.jreadhub.util.ResourceUtil;
import com.jeez.guanpj.jreadhub.widget.RelativePopupWindow;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;
import com.zhy.view.flowlayout.TagView;

import java.util.ArrayList;

import butterknife.BindView;

public class TopicDetailFragment extends AbsBaseMvpSwipeBackFragment<TopicDetailPresenter> implements TopicDetailContract.View {

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
    @BindView(R.id.txt_toolbar_header)
    TextView mToolbarHeader;

    private TopicBean mTopic;
    private String mTitle;
    private TopicTimelineAdapter mTimelineAdapter;

    public static TopicDetailFragment newInstance(String topicId, String title) {
        TopicDetailFragment fragment = new TopicDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.BUNDLE_TOPIC_ID, topicId);
        bundle.putString(Constants.EXTRA_TOPIC_TITLE, title);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String topicId = getArguments().getString(Constants.BUNDLE_TOPIC_ID);
        mTitle = getArguments().getString(Constants.EXTRA_TOPIC_TITLE);
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
        mToolbar.setNavigationIcon(ResourceUtil.getResource(getActivity(), R.attr.navBackIcon));
        mToolbar.inflateMenu(R.menu.menu_topic_detail);
        mToolbar.setTitle(getText(R.string.topic_detail));
        mToolbar.setNavigationOnClickListener(v -> pop());
        mToolbarHeader.setText(mTitle);
        mToolbarHeader.setVisibility(View.GONE);
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
            textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_ring, 0, 0, 0);
            textView.setCompoundDrawablePadding(15);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            textView.setTextColor(ContextCompat.getColor(getContext(), R.color.text_topic_detail_news_title));
            textView.setBackgroundResource(R.drawable.selector_btn_background);
            if (TextUtils.isEmpty(topic.getSiteName())) {
                textView.setText(topic.getTitle());
            } else {
                SpannableString spannableTitle = SpannableString.valueOf(getContext().getString(R.string.title___site_name, topic.getTitle(), topic.getSiteName()));
                spannableTitle.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(), R.color.text_topic_detail_news_author)),
                        topic.getTitle().length(),
                        topic.getTitle().length() + topic.getSiteName().length() + 2,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                textView.setText(spannableTitle);
            }
            textView.setOnClickListener(v -> {
                String url = null;
                if (!TextUtils.isEmpty(topic.getMobileUrl())) {
                    url = topic.getMobileUrl();
                } else {
                    url = topic.getUrl();
                }
                if (!TextUtils.isEmpty(url)) {
                    RxBus.getInstance().post(new OpenWebSiteEvent(url, topic.getTitle()));
                }
            });
            mTitleContainer.addView(textView);
        }
        mTimelineAdapter = new TopicTimelineAdapter(getContext());
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
                    TextView item = (TextView) getLayoutInflater().inflate(R.layout.item_relevant_topic, mRelativeTopic, false);
                    item.setText(entityEventTopicBean.getEntityName() + entityEventTopicBean.getEventTypeLabel());
                    return item;
                }
            });
            mRelativeTopic.setOnTagClickListener((view, position, parent) -> {
                String topicId = String.valueOf(entityEventTopics.get(position).getEntityId());
                long order = mTopic.getOrder();

                RelevantTopicWindow window = new RelevantTopicWindow(getActivity(), topicId, order);
                window.showOnAnchor(view, RelativePopupWindow.VerticalPosition.ABOVE, RelativePopupWindow.HorizontalPosition.CENTER, true);
                setBackgroundAlpha(0.7f);
                window.setOnDismissListener(() -> {
                    setBackgroundAlpha(1f);
                    if (((TagView) view).isChecked()) {
                        ((TagView) view).setChecked(false);
                    }
                });
                return true;
            });
        } else {
            mRelativeTopicContainer.setVisibility(View.GONE);
        }

        mScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (scrollY > mTxtTopicTime.getBottom()) {
                mToolbarHeader.setVisibility(View.VISIBLE);
                mToolbar.setTitle("");
            } else {
                mToolbarHeader.setVisibility(View.GONE);
                mToolbar.setTitle(getText(R.string.topic_detail));
            }
        });
    }

    private void setBackgroundAlpha(float v) {
        WindowManager.LayoutParams lp = ((Activity) getContext()).getWindow().getAttributes();
        lp.alpha = v;
        ((Activity) getContext()).getWindow().setAttributes(lp);
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
}
