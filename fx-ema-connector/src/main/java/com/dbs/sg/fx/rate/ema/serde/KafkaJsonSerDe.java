package com.dbs.sg.fx.rate.ema.serde;

import java.util.Map;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;

import com.dbs.sg.fx.rate.ema.util.EmaAppUtil;
import com.dbs.sg.fx.rate.model.json.FxRateModel;
import com.google.inject.Singleton;

@Singleton
public class KafkaJsonSerDe implements Serializer<FxRateModel>, Deserializer<FxRateModel> {

	@Override
	public FxRateModel deserialize(final String topic, final byte[] data) {
		return EmaAppUtil.readData(data, FxRateModel.class);
	}

	@Override
	public void configure(final Map<String, ?> configs, final boolean isKey) {

	}

	@Override
	public byte[] serialize(final String topic, final FxRateModel data) {
		return EmaAppUtil.writeToJson(data).getBytes();
	}

	@Override
	public void close() {
	}

}
