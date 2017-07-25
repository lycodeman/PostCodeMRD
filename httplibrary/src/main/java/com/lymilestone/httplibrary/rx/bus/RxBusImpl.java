package com.lymilestone.httplibrary.rx.bus;



import com.lymilestone.httplibrary.rx.bus.inner.EventComposite;
import com.lymilestone.httplibrary.rx.bus.inner.EventFind;
import com.lymilestone.httplibrary.rx.bus.inner.EventHelper;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @Description: RxBus事件管理
 * @author: <a href="http://www.xiaoyaoyou1212.com">DAWI</a>
 * @date: 2016-12-19 15:07
 */
public class RxBusImpl extends EventHelper implements IBus {

    private ConcurrentMap<Object, EventComposite> mEventCompositeMap = new ConcurrentHashMap<>();

    @Override
    public void register(Object object) {
        if (object == null) {
            throw new NullPointerException("Object to register must not be null.");
        }
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        EventComposite subscriberMethods = EventFind.findAnnotatedSubscriberMethods(object, compositeDisposable);
        mEventCompositeMap.put(object, subscriberMethods);

        if (!STICKY_EVENT_MAP.isEmpty()) {
            subscriberMethods.subscriberSticky(STICKY_EVENT_MAP);
        }
    }

    @Override
    public void unregister(Object object) {
        if (object == null) {
            throw new NullPointerException("Object to register must not be null.");
        }
        EventComposite subscriberMethods = mEventCompositeMap.get(object);
        if (subscriberMethods != null) {
            subscriberMethods.getCompositeDisposable().dispose();
        }
        mEventCompositeMap.remove(object);
    }

    @Override
    public void post(Object event) {
        SUBJECT.onNext(event);
    }

    @Override
    public void postSticky(Object event) {
        STICKY_EVENT_MAP.put(event.getClass(), event);
        post(event);
    }
}
