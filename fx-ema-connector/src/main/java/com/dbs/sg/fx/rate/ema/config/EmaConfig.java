package com.dbs.sg.fx.rate.ema.config;

import java.io.Serializable;

import com.google.inject.Singleton;
import com.netflix.governator.annotations.Configuration;

@Singleton
public class EmaConfig implements Serializable {

	private static final long serialVersionUID = 1L;

	@Configuration("ema.config.serviceHost")
	private String serviceHost;

	@Configuration("ema.config.servicePort")
	private String servicePort;

	@Configuration("ema.config.serviceName")
	private String serviceName;

	@Configuration("ema.config.symbolList")
	private String symbolList;

	@Configuration("ema.config.user")
	private String user;

	public String getServiceHost() {
		return serviceHost;
	}

	public String getServicePort() {
		return servicePort;
	}

	public String getServiceName() {
		return serviceName;
	}

	public String getSymbolList() {
		return symbolList;
	}

	public String getUser() {
		return user;
	}

}
