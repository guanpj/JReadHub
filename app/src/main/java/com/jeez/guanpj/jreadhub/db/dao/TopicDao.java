package com.jeez.guanpj.jreadhub.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.jeez.guanpj.jreadhub.bean.TopicBean;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface TopicDao {
    @Query("SELECT * FROM topic")
    public Flowable<List<TopicBean>> getAllTopic();

    @Query("SELECT * FROM topic WHERE id=:topicId")
    public Flowable<TopicBean> getTopicById(String topicId);

    @Insert
    public void insertTopic(TopicBean... topicBeans);

    @Delete
    public void deleteTopic(TopicBean topicBean);
}
