package com.dbs.sg.fx.rate.ema.util;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.governator.configuration.PropertiesConfigurationProvider;

public class EmaAppUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(EmaAppUtil.class);

	public static final String CORE_PROPERTIES = "guice.properties";

	private static ObjectMapper objectMapper;

	static {
		objectMapper = new ObjectMapper();
		objectMapper.setSerializationInclusion(Include.NON_NULL);
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	public static ObjectMapper objectMapper() {
		return objectMapper;
	}

	public static String writeToJson(final Object data) {
		String jsonData = "";
		if (data != null) {
			try {
				jsonData = EmaAppUtil.objectMapper.writeValueAsString(data);
			} catch (final Exception ex) {
				LOGGER.warn("Failed to write data into json format for class: [{}] " + data.getClass());
			}
		}
		return jsonData;
	}

	public static <T> T readData(final byte[] data, final Class<T> clazz) {
		T value = null;
		try {
			value = EmaAppUtil.objectMapper.readValue(data, clazz);
		} catch (final Exception ex) {
			LOGGER.error("Error in converting to class:[{}]", clazz);
		}
		return value;
	}

	public static Properties mergeProperties(final Properties from, final Properties to) {
		from.keySet().stream().forEach(key -> {
			to.put(key, from.get(key));
		});
		return to;
	}

	public static Properties loadPropsFromClasspath(final String filePath) throws IOException {
		final Properties properties = new Properties();
		try (final InputStream ios = EmaAppUtil.class.getClassLoader().getResourceAsStream(filePath);) {
			properties.load(ios);
		}
		return properties;
	}

	public static Properties loadPropsFromSystemDir(final String filePath) throws IOException {
		final Properties properties = new Properties();
		properties.load(new FileReader(filePath));
		return properties;
	}

	public static PropertiesConfigurationProvider buildConfigProvider(final Properties properties) {
		final PropertiesConfigurationProvider provider = new PropertiesConfigurationProvider(properties);
		properties.keySet().stream().forEach(key -> {
			provider.setVariable((String) key, (String) properties.get(key));
		});
		return provider;
	}
}
