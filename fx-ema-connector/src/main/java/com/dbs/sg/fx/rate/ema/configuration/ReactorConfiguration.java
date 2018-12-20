package com.dbs.sg.fx.rate.ema.configuration;

import java.util.stream.IntStream;

import javax.annotation.PostConstruct;

import com.dbs.sg.fx.rate.ema.config.ReactorConfig;
import com.dbs.sg.fx.rate.ema.subscribers.OMMSubscriber;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.thomsonreuters.ema.access.Msg;

import reactor.core.publisher.WorkQueueProcessor;
import reactor.util.concurrent.Queues;

@Singleton
public class ReactorConfiguration {

	private final String PUBLISHER_NAME = "ema-connector";
	private WorkQueueProcessor<Msg> workQueueProcessor;

	@Inject
	private ReactorConfig reactorConfig;

	@Inject
	private Injector injector;

	@PostConstruct
	public void setup() {
		this.setupWorkQueueProcessor(reactorConfig);
	}

	public WorkQueueProcessor<Msg> getWorkQueueProcessor() {
		return workQueueProcessor;
	}

	private WorkQueueProcessor<Msg> setupWorkQueueProcessor(final ReactorConfig _reactorConfig) {
		final WorkQueueProcessor<Msg> processor = WorkQueueProcessor.share(PUBLISHER_NAME,
				Queues.SMALL_BUFFER_SIZE * 2);
		IntStream.range(0, _reactorConfig.getConsumerLimit()).forEach((index) -> {
			processor.subscribe(this.injector.getInstance(OMMSubscriber.class));
		});
		return processor;
	}
}
