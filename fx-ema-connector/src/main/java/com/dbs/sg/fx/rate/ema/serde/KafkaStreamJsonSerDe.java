package com.dbs.sg.fx.rate.ema.serde;

import java.util.Map;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

import com.dbs.sg.fx.rate.model.json.FxRateModel;
import com.google.inject.Singleton;

@Singleton
public class KafkaStreamJsonSerDe implements Serde<FxRateModel> {

	@Override
	public void configure(final Map<String, ?> configs, final boolean isKey) {
	}

	@Override
	public void close() {
	}

	@Override
	public Serializer<FxRateModel> serializer() {
		return new KafkaJsonSerDe();
	}

	@Override
	public Deserializer<FxRateModel> deserializer() {
		return new KafkaJsonSerDe();
	}

}
