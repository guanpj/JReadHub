package com.jeez.guanpj.jreadhub.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.jeez.guanpj.jreadhub.bean.NewsBean;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;

@Dao
public interface NewsDao {
    @Query("SELECT * FROM news")
    public Flowable<List<NewsBean>> getAllNews();

    @Query("SELECT * FROM news WHERE id=:newsId")
    public Flowable<NewsBean> getNewsById(String newsId);

    @Query("SELECT * FROM news WHERE id=:newsId")
    public Maybe<NewsBean> getNewsById1(String newsId);

    @Query("SELECT * FROM news WHERE id=:newsId")
    public Single<NewsBean> getNewsById2(String newsId);

    @Insert
    public void insertNews(NewsBean... newsBeans);

    @Delete
    public void deleteNews(NewsBean newsBean);
}
