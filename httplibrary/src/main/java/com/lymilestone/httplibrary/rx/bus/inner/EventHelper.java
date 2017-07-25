package com.lymilestone.httplibrary.rx.bus.inner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * @Description: 事件帮助类
 * @author: <a href="http://www.xiaoyaoyou1212.com">DAWI</a>
 * @date: 2016-12-29 19:08
 */
public class EventHelper {
    protected final static Map<Class<?>, Object> STICKY_EVENT_MAP;
    protected final static Subject<Object> SUBJECT;

    static {
        SUBJECT = PublishSubject.create().toSerialized();
        STICKY_EVENT_MAP = new HashMap<>();
    }

    protected EventHelper() {
    }

    static <T> Flowable<T> toFlowable(Class<T> eventType) {
        return SUBJECT.ofType(eventType).toFlowable(BackpressureStrategy.BUFFER);
    }

    static synchronized void dellSticky(Object event) {
        if (!STICKY_EVENT_MAP.isEmpty()) {
            List<Class> classes = new ArrayList<>();
            for (Map.Entry<Class<?>, Object> objectEntry : STICKY_EVENT_MAP.entrySet()) {
                if (objectEntry.getKey() == event.getClass()) {
                    classes.add(event.getClass());
                }
            }
            stickyEventMapRemove(classes);
        }
    }

    static void stickyEventMapRemove(List<Class> classes) {
        for (Class aClass : classes) STICKY_EVENT_MAP.remove(aClass);
    }
}
