package com.dbs.sg.fx.rate.ema.logging;

import com.google.inject.MembersInjector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

public class SLF4JMembersInjector<T> implements MembersInjector<T> {
	private final Field field;
	private final Logger logger;

	public SLF4JMembersInjector(final Field field) {
		this.field = field;
		this.logger = LoggerFactory.getLogger(field.getDeclaringClass());
		field.setAccessible(true);
	}

	public void injectMembers(final T t) {
		try {
			field.set(t, logger);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
}