package com.dbs.sg.fx.rate.ema.subscribers;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

abstract class AbstractSubscriber<T> implements Subscriber<T> {

	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractSubscriber.class);

	@Override
	public void onSubscribe(final Subscription s) {
		s.request(Long.MAX_VALUE);
	}

	@Override
	public void onNext(final T t) {
		try {
			processItem(t);
		} catch (final Exception ex) {
			onError(ex);
		}
	}

	@Override
	public void onError(final Throwable t) {
		LOGGER.error("Error in processing item " + t, t);
	}

	@Override
	public void onComplete() {
	}

	public abstract void processItem(T t);

}
