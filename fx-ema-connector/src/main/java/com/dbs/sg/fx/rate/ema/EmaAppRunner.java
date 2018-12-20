package com.dbs.sg.fx.rate.ema;

import java.util.Properties;

import com.dbs.sg.fx.rate.ema.client.EMAConsumer;
import com.dbs.sg.fx.rate.ema.util.EmaAppUtil;
import com.google.inject.Injector;

public class EmaAppRunner {

	public static final String CORE_PROPERTIES = "/guice.properties";
	private static final String LOCAL_PROFILE = "dev";

	public static void main(String[] args) throws Exception {
		final Properties properties = EmaAppUtil.loadPropsFromClasspath(LOCAL_PROFILE + CORE_PROPERTIES);
		startApplication(properties);
	}

	private static void startApplication(final Properties properties) throws Exception { 
		System.setProperty("APP_NAME", properties.getProperty("app.name"));
		System.setProperty("LOG_DIR", properties.getProperty("log.dir"));

		final EmaAppInjector emaApp = new EmaAppInjector(properties);

		final Injector injector = emaApp.getInjector();
		final EMAConsumer consumer = injector.getInstance(EMAConsumer.class);
		consumer.startConsumer();

		System.out.println("EmaAppRunner.startApplication()");

	}
}
