package com.jeez.guanpj.jreadhub.mvpframe.rx;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

public class OldRxBus {
    private static OldRxBus sInstance;

    public static synchronized OldRxBus getInstance() {
        if (null == sInstance) {
            sInstance = new OldRxBus();
        }
        return sInstance;
    }

    private OldRxBus() {}

    private ConcurrentHashMap<Object, List<Subject>> subjectMapper = new ConcurrentHashMap<>();

    public <T> Observable<T> register(Object tag) {
        List<Subject> subjectList = subjectMapper.get(tag);
        if (null == subjectList) {
            subjectList = new ArrayList<>();
            subjectMapper.put(tag, subjectList);
        }
        Subject<T> subject;
        subjectList.add(subject = PublishSubject.<T>create());
        return  subject;
    }

    public void unRegister(Object tag) {
        List<Subject> subjectList = subjectMapper.get(tag);
        if (!isEmpty(subjectList)) {
            subjectList.remove(tag);
        }
    }

    public OldRxBus unRegister(Object tag, Observable<?> observable) {
        if (null == observable) {
            return getInstance();
        }
        List<Subject> subjectList = subjectMapper.get(tag);
        if (null != subjectList) {
            subjectList.remove(tag);
            if (isEmpty(subjectList)) {
                subjectMapper.remove(observable);
            }
        }
        return getInstance();
    }

    public void post(Object tag, Object content) {
        List<Subject> subjectList = subjectMapper.get(tag);
        if (!isEmpty(subjectList)) {
            for (Subject subject : subjectList) {
                subject.onNext(content);
            }
        }
    }

    public void post(Object content) {
        post(content.getClass().getName(), content);
    }

    public static boolean isEmpty(Collection<Subject> collection) {
        return null == collection || collection.isEmpty();
    }
}
