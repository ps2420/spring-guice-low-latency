package com.dbs.sg.fx.rate.ema.client;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;

import com.dbs.sg.fx.rate.ema.configuration.ReactorConfiguration;
import com.dbs.sg.fx.rate.ema.logging.InjectLogger;
import com.google.inject.Inject;
import com.thomsonreuters.ema.access.AckMsg;
import com.thomsonreuters.ema.access.DataType;
import com.thomsonreuters.ema.access.GenericMsg;
import com.thomsonreuters.ema.access.Msg;
import com.thomsonreuters.ema.access.OmmConsumerClient;
import com.thomsonreuters.ema.access.OmmConsumerEvent;
import com.thomsonreuters.ema.access.RefreshMsg;
import com.thomsonreuters.ema.access.StatusMsg;
import com.thomsonreuters.ema.access.UpdateMsg;

import reactor.core.publisher.WorkQueueProcessor;

public class OMMMsgEvtListener implements OmmConsumerClient {

	@InjectLogger
	private Logger LOGGER;

	@Inject
	private ReactorConfiguration reactorConfiguration;

	private WorkQueueProcessor<Msg> workQueueProcessor;

	private final String NOT_SET = "<not set>";

	@PostConstruct
	public void setup() {
		this.workQueueProcessor = this.reactorConfiguration.getWorkQueueProcessor();
	}

	private void processMessage(final Msg message) {
		LOGGER.debug("UpdateMsg: item-name:[{}] ", (message.hasName() ? message.name() : NOT_SET));
		if (DataType.DataTypes.FIELD_LIST == message.payload().dataType()) {
			this.workQueueProcessor.onNext(message);
		}
	}

	@Override
	public void onRefreshMsg(final RefreshMsg refrMsg, final OmmConsumerEvent consumerEvent) {
		LOGGER.debug("RefreshMsg: service-name:[{}] ", (refrMsg.hasServiceName() ? refrMsg.serviceName() : NOT_SET));
		processMessage(refrMsg);
	}

	@Override
	public void onUpdateMsg(final UpdateMsg updateMsg, final OmmConsumerEvent consumerEvent) {
		LOGGER.debug("UpdateMsg: service-name:[{}] ", (updateMsg.hasServiceName() ? updateMsg.serviceName() : NOT_SET));
		processMessage(updateMsg);
	}

	@Override
	public void onStatusMsg(final StatusMsg statusMsg, final OmmConsumerEvent consumerEvent) {
		LOGGER.debug("StatusMsg: item-name:[{}] ", (statusMsg.hasName() ? statusMsg.name() : NOT_SET));
		LOGGER.debug("StatusMsg: service-name:[{}] ", (statusMsg.hasServiceName() ? statusMsg.serviceName() : NOT_SET));
		if (statusMsg.hasState()) {
			LOGGER.debug("StatusMsg: item-state:[{}] ", statusMsg.state());
		}
	}

	@Override
	public void onGenericMsg(final GenericMsg genericMsg, final OmmConsumerEvent consumerEvent) {

	}

	@Override
	public void onAckMsg(final AckMsg ackMsg, final OmmConsumerEvent consumerEvent) {

	}

	@Override
	public void onAllMsg(final Msg msg, final OmmConsumerEvent consumerEvent) {

	}

}
