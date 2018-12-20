package com.dbs.sg.fx.rate.ema.configuration;

import java.util.Properties;

import javax.annotation.PostConstruct;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.SslConfigs;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.streams.StreamsConfig;

import com.dbs.sg.fx.rate.ema.config.KafkaBrokerConfig;
import com.dbs.sg.fx.rate.ema.serde.KafkaJsonSerDe;
import com.dbs.sg.fx.rate.ema.serde.KafkaStreamJsonSerDe;
import com.dbs.sg.fx.rate.ema.util.EmaAppUtil;
import com.dbs.sg.fx.rate.model.json.FxRateModel;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.Singleton;

@Singleton
public class KafkaStreamConfiguration {

	private KafkaBrokerConfig brokerConfig;
	
	private Properties kConsumerProperties;
	
	private Properties kProducerProperties;

	private KafkaProducer<String, FxRateModel> kafkaProducer;

	@Inject
	public void setBrokerConfig(final KafkaBrokerConfig brokerConfig) {
		this.brokerConfig = brokerConfig;
	}

	@PostConstruct
	public void setup() {
		this.kProducerProperties = kafkaProducerProperties(brokerConfig);
		this.kConsumerProperties = kafkaConsumerProperties(brokerConfig);
		this.kafkaProducer = new KafkaProducer<>(kProducerProperties);
	}

	public Properties getkConsumerProperties() {
		return this.kConsumerProperties;
	}

	public Properties getkProducerProperties() {
		return this.kProducerProperties;
	}

	@Provides
	public KafkaProducer<String, FxRateModel> getKafkaProducer() {
		return this.kafkaProducer;
	}

	private Properties kafkaProducerProperties(final KafkaBrokerConfig kafkaBrokerConfig) {
		final Properties props = producerProperties(kafkaBrokerConfig);
		if (kafkaBrokerConfig.isSSLEnabled()) {
			EmaAppUtil.mergeProperties(buildSSLProperties(kafkaBrokerConfig), props);
		}
		return props;
	}

	private Properties kafkaConsumerProperties(final KafkaBrokerConfig kafkaBrokerConfig) {
		final Properties props = consumerProperties(kafkaBrokerConfig);
		if (kafkaBrokerConfig.isSSLEnabled()) {
			EmaAppUtil.mergeProperties(buildSSLProperties(kafkaBrokerConfig), props);
		}
		return props;
	}

	private Properties producerProperties(final KafkaBrokerConfig kafkaBrokerConfig) {
		final Properties props = new Properties();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBrokerConfig.getServers());
		props.put(ProducerConfig.CLIENT_ID_CONFIG, kafkaBrokerConfig.getApplicationId() + "_CONSUMER");
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaJsonSerDe.class.getName());
		return props;
	}

	private Properties consumerProperties(final KafkaBrokerConfig kafkaBrokerConfig) {
		final Properties props = new Properties();
		props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBrokerConfig.getServers());
		props.put(StreamsConfig.APPLICATION_ID_CONFIG, kafkaBrokerConfig.getApplicationId() + "_PRODUCER");

		props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
		props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, KafkaStreamJsonSerDe.class.getName());
		props.put(StreamsConfig.NUM_STREAM_THREADS_CONFIG, 3);

		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, kafkaBrokerConfig.getOffsetConfig());
		return props;
	}

	private Properties buildSSLProperties(final KafkaBrokerConfig kafkaBrokerConfig) {
		final Properties props = new Properties();
		props.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SSL");
		props.put(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, kafkaBrokerConfig.getTrustStoreLocation());
		props.put(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG, kafkaBrokerConfig.getTrustStorePassword());
		props.put(SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG, kafkaBrokerConfig.getKeyStoreLocation());
		props.put(SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG, kafkaBrokerConfig.getKeyPassword());
		props.put(SslConfigs.SSL_KEY_PASSWORD_CONFIG, kafkaBrokerConfig.getKeyPassword());
		return props;
	}

}
