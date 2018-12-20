package com.dbs.sg.fx.rate.ema.config;

import java.io.Serializable;

import com.google.inject.Singleton;
import com.netflix.governator.annotations.Configuration;

@Singleton
public class KafkaBrokerConfig implements Serializable {

	private static final long serialVersionUID = 1L;

	@Configuration("kafka.brokers.servers")
	private String servers;

	@Configuration("kafka.brokers.applicationId")
	private String applicationId;

	@Configuration("kafka.brokers.offsetConfig")
	private String offsetConfig;

	@Configuration("kafka.brokers.topic")
	private String topic;

	@Configuration("kafka.brokers.topic.prefix.out")
	private String topicPrefixOut;

	@Configuration("kafka.brokers.trustStoreLocation")
	private String trustStoreLocation;

	@Configuration("kafka.brokers.trustStorePassword")
	private String trustStorePassword;

	@Configuration("kafka.brokers.keyStoreLocation")
	private String keyStoreLocation;

	@Configuration("kafka.brokers.keyStorePassword")
	private String keyStorePassword;

	@Configuration("kafka.brokers.keyPassword")
	private String keyPassword;

	@Configuration("kafka.brokers.sslEnabled")
	private boolean isSSLEnabled;

	public String getServers() {
		return servers;
	}

	public String getApplicationId() {
		return applicationId;
	}

	public String getOffsetConfig() {
		return offsetConfig;
	}

	public String getTopic() {
		return topic;
	}

	public String getTopicPrefixOut() {
		return topicPrefixOut;
	}

	public String getTrustStoreLocation() {
		return trustStoreLocation;
	}

	public String getTrustStorePassword() {
		return trustStorePassword;
	}

	public String getKeyStoreLocation() {
		return keyStoreLocation;
	}

	public String getKeyStorePassword() {
		return keyStorePassword;
	}

	public String getKeyPassword() {
		return keyPassword;
	}

	public boolean isSSLEnabled() {
		return isSSLEnabled;
	}

}
