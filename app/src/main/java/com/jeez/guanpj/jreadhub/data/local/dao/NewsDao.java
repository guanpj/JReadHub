package com.jeez.guanpj.jreadhub.data.local.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.support.annotation.NonNull;

import com.jeez.guanpj.jreadhub.bean.NewsBean;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface NewsDao {
    @Query("SELECT * FROM news")
    Flowable<List<NewsBean>> getAllNews();

    @Query("SELECT * FROM news WHERE id = :newsId")
    Flowable<List<NewsBean>> getNewsById(@NonNull String newsId);

    @Query("SELECT * FROM news WHERE id = :newsId")
    Single<NewsBean> getSingleNewsById(@NonNull String newsId);

    @Query("SELECT * FROM news WHERE title LIKE '%' || :keyWord || '%' OR summary LIKE '%' || :keyWord || '%'")
    Flowable<List<NewsBean>> getNewsByKeyword(@NonNull String keyWord);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNews(NewsBean... newsBeans);

    @Update
    int updateNews(NewsBean newsBean);

    @Query("DELETE FROM news Where id = :newsId")
    void deleteNewsById(@NonNull String newsId);

    @Delete
    void deleteNews(NewsBean newsBean);

    @Query("DELETE FROM news")
    void deleteAllNews();
}
