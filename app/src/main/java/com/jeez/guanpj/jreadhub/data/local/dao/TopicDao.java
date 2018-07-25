package com.jeez.guanpj.jreadhub.data.local.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.jeez.guanpj.jreadhub.bean.TopicBean;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;

@Dao
public interface TopicDao {
    @Query("SELECT * FROM topic")
    public Flowable<List<TopicBean>> getAllTopic();

    @Query("SELECT * FROM topic")
    public Maybe<List<TopicBean>> getAllTopic1();

    @Query("SELECT * FROM topic")
    public Single<List<TopicBean>> getAllTopic2();

    @Query("SELECT * FROM topic WHERE id=:topicId")
    public Flowable<List<TopicBean>> getTopicById(String topicId);

    @Query("SELECT * FROM topic WHERE id=:topicId")
    public Maybe<TopicBean> getTopicById1(String topicId);

    @Query("SELECT * FROM topic WHERE id=:topicId")
    public Single<TopicBean> getTopicById2(String topicId);

    @Insert
    public void insertTopic(TopicBean... topicBeans);

    @Delete
    public void deleteTopic(TopicBean topicBean);
}
