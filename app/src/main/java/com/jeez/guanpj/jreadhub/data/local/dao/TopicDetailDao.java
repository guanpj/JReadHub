package com.jeez.guanpj.jreadhub.data.local.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.support.annotation.NonNull;

import com.jeez.guanpj.jreadhub.bean.TopicDetailBean;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface TopicDetailDao {
    @Query("SELECT * FROM topic_detail")
    Flowable<List<TopicDetailBean>> getAllTopic();

    @Query("SELECT * FROM topic_detail WHERE id = :topicId")
    Flowable<List<TopicDetailBean>> getTopicById(@NonNull String topicId);

    @Query("SELECT * FROM topic_detail WHERE id = :topicId")
    Single<TopicDetailBean> getSingleTopicById(@NonNull  String topicId);

    @Query("SELECT * FROM topic_detail WHERE title LIKE '%' || :keyWord || '%' OR summary LIKE '%' || :keyWord || '%'")
    Flowable<List<TopicDetailBean>> getTopicsByKeyword(@NonNull String keyWord);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTopic(TopicDetailBean... topicBeans);

    @Update
    int  updateTopic(@NonNull TopicDetailBean topicBean);

    @Query("DELETE FROM topic_detail WHERE id = :topicId")
    void deleteTopicById(@NonNull String topicId);

    @Delete
    void deleteTopic(TopicDetailBean topicBean);

    @Query("DELETE FROM topic_detail")
    void deleteAllTopic();
}
