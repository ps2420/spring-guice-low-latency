package com.dbs.sg.fx.rate.ema.client;

import java.util.Arrays;

import org.slf4j.Logger;

import com.dbs.sg.fx.rate.ema.config.EmaConfig;
import com.dbs.sg.fx.rate.ema.logging.InjectLogger;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.thomsonreuters.ema.access.ElementList;
import com.thomsonreuters.ema.access.EmaFactory;
import com.thomsonreuters.ema.access.OmmArray;
import com.thomsonreuters.ema.access.OmmConsumer;
import com.thomsonreuters.ema.rdm.EmaRdm;

@Singleton
public class EMAConsumer {

	@InjectLogger
	private Logger LOGGER;

	@Inject
	private Injector injector;

	@Inject
	private EmaConfig emaConfig;

	public void startConsumer() {
		LOGGER.info("logging test message ");
		try {
			final OMMMsgEvtListener evtListener = injector.getInstance(OMMMsgEvtListener.class);
			final ElementList batch = buildBatchRequest(this.emaConfig);

			LOGGER.info("Building OmmConsumer successfully..");
			final OmmConsumer consumer = ommConsumer(this.emaConfig);

			LOGGER.info("Registering OmmMessageListener for currencies : " + this.emaConfig.getSymbolList());
			consumer.registerClient(
					EmaFactory.createReqMsg().serviceName(this.emaConfig.getServiceName()).payload(batch), evtListener);
		} catch (final Exception ex) {
			throw new RuntimeException("Error in Starting OMM-Consumer " + ex, ex);
		}
	}

	private OmmConsumer ommConsumer(final EmaConfig _emaConfig) {
		final String connectionUrl = _emaConfig.getServiceHost() + ":" + _emaConfig.getServicePort();
		final OmmConsumer consumer = EmaFactory.createOmmConsumer(
				EmaFactory.createOmmConsumerConfig().host(connectionUrl).username(_emaConfig.getUser()));
		return consumer;
	}

	private ElementList buildBatchRequest(final EmaConfig _emaConfig) {
		final OmmArray array = currencyArray(_emaConfig);

		final ElementList batch = EmaFactory.createElementList();
		batch.add(EmaFactory.createElementEntry().array(EmaRdm.ENAME_BATCH_ITEM_LIST, array));
		return batch;
	}

	private OmmArray currencyArray(final EmaConfig _emaConfig) {
		final String[] currArray = _emaConfig.getSymbolList().trim().split(",");

		final OmmArray array = EmaFactory.createOmmArray();
		Arrays.asList(currArray).stream().forEach(currency -> {
			array.add(EmaFactory.createOmmArrayEntry().ascii(currency + "="));
		});
		return array;
	}

}
