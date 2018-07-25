package com.jeez.guanpj.jreadhub.di.module;

import android.content.Context;

import com.jeez.guanpj.jreadhub.core.DataManager;
import com.jeez.guanpj.jreadhub.core.db.DatabaseHelper;
import com.jeez.guanpj.jreadhub.core.db.DatabaseHelperImpl;
import com.jeez.guanpj.jreadhub.core.net.NetHelper;
import com.jeez.guanpj.jreadhub.core.net.NetHelperImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = {NetworkModule.class, DatabaseModule.class})
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
    NetHelper provideNetHelper(NetHelperImpl netHelperImpl) {
        return netHelperImpl;
    }

    @Provides
    @Singleton
    DatabaseHelper provideDbHelper(DatabaseHelperImpl dataBaseHelperImpl) {
        return dataBaseHelperImpl;
    }

    @Provides
    @Singleton
    DataManager provideDataManager(NetHelper netHelper, DatabaseHelper dbHelper) {
        return new DataManager(netHelper, dbHelper);
    }
}
