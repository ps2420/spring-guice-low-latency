package com.dbs.sg.fx.rate.ema.logging;

import java.lang.reflect.Field;

import org.slf4j.Logger;

import com.google.inject.TypeLiteral;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;

public class SLF4JTypeListener implements TypeListener {

	public <T> void hear(final TypeLiteral<T> typeLiteral, final TypeEncounter<T> typeEncounter) {
		for (final Field field : typeLiteral.getRawType().getDeclaredFields()) {
			if (field.getType() == Logger.class && field.isAnnotationPresent(InjectLogger.class)) {
				typeEncounter.register(new SLF4JMembersInjector<T>(field));
			}
		}
	}

}