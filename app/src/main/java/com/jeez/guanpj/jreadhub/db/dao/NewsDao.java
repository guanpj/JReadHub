package com.jeez.guanpj.jreadhub.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.jeez.guanpj.jreadhub.bean.NewsBean;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface NewsDao {
    @Query("SELECT * FROM news")
    public Flowable<List<NewsBean>> getAllNews();

    @Query("SELECT * FROM news WHERE id=:newsId")
    public Flowable<NewsBean> getNewsById(String newsId);

    @Insert
    public void insertNews(NewsBean... newsBeans);

    @Delete
    public void deleteNews(NewsBean newsBean);
}
