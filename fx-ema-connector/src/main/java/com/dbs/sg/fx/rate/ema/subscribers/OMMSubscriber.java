package com.dbs.sg.fx.rate.ema.subscribers;

import javax.annotation.PostConstruct;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;

import com.dbs.sg.fx.rate.ema.config.KafkaBrokerConfig;
import com.dbs.sg.fx.rate.ema.configuration.KafkaStreamConfiguration;
import com.dbs.sg.fx.rate.ema.logging.InjectLogger;
import com.dbs.sg.fx.rate.ema.transformer.ElektronMsgTransformer;
import com.dbs.sg.fx.rate.ema.util.EmaAppUtil;
import com.dbs.sg.fx.rate.model.json.FxRateModel;
import com.google.inject.Inject;
import com.thomsonreuters.ema.access.Msg;

public class OMMSubscriber extends AbstractSubscriber<Msg> {

	@InjectLogger
	private Logger LOGGER;

	@Inject
	private KafkaStreamConfiguration kafkaStreamConfiguration;

	private KafkaProducer<String, FxRateModel> kafkaProducer;

	@Inject
	private KafkaBrokerConfig kafkaBrokerConfig;

	@Inject
	private ElektronMsgTransformer msgTransformer;

	@PostConstruct
	public void setup() {
		this.kafkaProducer = this.kafkaStreamConfiguration.getKafkaProducer();
	}

	@Override
	public void processItem(final Msg elektronMsg) {
		final FxRateModel model = this.msgTransformer.transformElektronMsg(elektronMsg);
		if (model != null) {
			final ProducerRecord<String, FxRateModel> producerRecord = generateMessage(model, this.kafkaBrokerConfig);
			this.kafkaProducer.send(producerRecord);
		}
	}

	public ProducerRecord<String, FxRateModel> generateMessage(final FxRateModel model,
			final KafkaBrokerConfig _kafkaBrokerConfig) {
		final String genTopic = generateTopic(model, _kafkaBrokerConfig);
		LOGGER.debug("Topic:[{}], message being sent:[{}]", genTopic, EmaAppUtil.writeToJson(model));
		return new ProducerRecord<String, FxRateModel>(genTopic, model.getUuid(), model);
	}

	public String generateTopic(final FxRateModel model, final KafkaBrokerConfig _kafkaBrokerConfig) {
		final String topicName = _kafkaBrokerConfig.getTopicPrefixOut() + model.getSymbol();
		return topicName;
	}

}
