package com.dbs.sg.fx.rate.ema;

import java.util.Properties;

import com.dbs.sg.fx.rate.ema.logging.SLF4JTypeListener;
import com.dbs.sg.fx.rate.ema.util.EmaAppUtil;
import com.google.inject.Injector;
import com.google.inject.matcher.Matchers;
import com.netflix.governator.configuration.AbstractObjectConfigurationProvider;
import com.netflix.governator.configuration.PropertiesConfigurationProvider;
import com.netflix.governator.guice.BootstrapBinder;
import com.netflix.governator.guice.BootstrapModule;
import com.netflix.governator.guice.LifecycleInjector;
import com.netflix.governator.lifecycle.LifecycleManager;

class EmaAppInjector {

	public static final String CORE_PROPERTIES = "guice.properties";
	public static final String BASE_PACKAGE = "com.dbs.sg.fx.rate.ema";

	private LifecycleManager lifecycleManager;
	private Injector injector;
	private PropertiesConfigurationProvider configurationProvider;

	public EmaAppInjector(final Properties properties) {
		this.configurationProvider = EmaAppUtil.buildConfigProvider(properties);
		this.initialize();
	}

	public LifecycleManager getLifecycleManager() {
		return lifecycleManager;
	}

	public PropertiesConfigurationProvider getConfigurationProvider() {
		return configurationProvider;
	}

	public Injector getInjector() {
		return injector;
	}

	public void initialize() {
		try {
			this.injector = loadModuleInjector(this.configurationProvider);
			this.lifecycleManager = injector.getInstance(LifecycleManager.class);
			this.lifecycleManager.start();
		} catch (final Exception ex) {
			throw new RuntimeException("Failed to load Guice application context ", ex);
		}
	}

	public Injector loadModuleInjector(final AbstractObjectConfigurationProvider provider) {
		final Injector injector = LifecycleInjector.builder().usingBasePackages(BASE_PACKAGE)
				.withBootstrapModule(new BootstrapModule() {
					@Override
					public void configure(final BootstrapBinder binder) {
						binder.bindConfigurationProvider().toInstance(provider);
						binder.bindListener(Matchers.any(), new SLF4JTypeListener());
					}
				}).build().createInjector();
		return injector;
	}
}
