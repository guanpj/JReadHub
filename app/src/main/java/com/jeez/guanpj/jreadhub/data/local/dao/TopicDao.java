package com.jeez.guanpj.jreadhub.data.local.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.support.annotation.NonNull;

import com.jeez.guanpj.jreadhub.bean.TopicBean;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface TopicDao {
    @Query("SELECT * FROM topic")
    Flowable<List<TopicBean>> getAllTopic();

    @Query("SELECT * FROM topic WHERE id = :topicId")
    Flowable<List<TopicBean>> getTopicById(@NonNull String topicId);

    @Query("SELECT * FROM topic WHERE id = :topicId")
    Single<TopicBean> getSingleTopicById(@NonNull  String topicId);

    @Query("SELECT * FROM topic WHERE title LIKE '%' || :keyWord || '%' OR summary LIKE '%' || :keyWord || '%'")
    Flowable<List<TopicBean>> getTopicsByKeyword(@NonNull String keyWord);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTopic(TopicBean... topicBeans);

    @Update
    int  updateTopic(@NonNull TopicBean topicBean);

    @Query("DELETE FROM topic WHERE id = :topicId")
    void deleteTopicById(@NonNull String topicId);

    @Delete
    void deleteTopic(TopicBean topicBean);

    @Query("DELETE FROM topic")
    void deleteAllTopic();
}
