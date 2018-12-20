package com.dbs.sg.fx.rate.ema.config;

import java.io.Serializable;

import com.google.inject.Singleton;
import com.netflix.governator.annotations.Configuration;

@Singleton
public class ReactorConfig implements Serializable {

	private static final long serialVersionUID = 1L;

	@Configuration("reactor.core.consumerLimit")
	private int consumerLimit = 5;

	public int getConsumerLimit() {
		return consumerLimit;
	}

}
