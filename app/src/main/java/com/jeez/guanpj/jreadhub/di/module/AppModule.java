package com.jeez.guanpj.jreadhub.di.module;

import android.content.Context;

import com.jeez.guanpj.jreadhub.core.DataManager;
import com.jeez.guanpj.jreadhub.core.net.NetHelper;
import com.jeez.guanpj.jreadhub.core.net.NetHelperImpl;
import com.jeez.guanpj.jreadhub.db.DataBaseHelperImpl;
import com.jeez.guanpj.jreadhub.db.DatabaseHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = NetworkModule.class)
public class AppModule {
    private final Context mContext;

    public AppModule(Context context) {
        this.mContext = context;
    }

    @Provides
    @Singleton
    Context provideAppContext() {
        return mContext;
    }

    @Provides
    @Singleton
    NetHelper provideNetHelper(NetHelperImpl retrofitHelper) {
        return retrofitHelper;
    }

    @Provides
    @Singleton
    DatabaseHelper provideDbHelper(DataBaseHelperImpl dataBaseHelperImpl) {
        return dataBaseHelperImpl;
    }

    @Provides
    @Singleton
    DataManager provideDataManager(NetHelper netHelper, DatabaseHelper dbHelper) {
        return new DataManager(netHelper, dbHelper);
    }
}
