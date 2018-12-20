package com.dbs.sg.fx.rate.ema.transformer;

import org.slf4j.Logger;

import com.dbs.sg.fx.rate.ema.logging.InjectLogger;
import com.dbs.sg.fx.rate.model.json.FxRateModel;
import com.google.inject.Singleton;
import com.thomsonreuters.ema.access.Data;
import com.thomsonreuters.ema.access.DataType;
import com.thomsonreuters.ema.access.DataType.DataTypes;
import com.thomsonreuters.ema.access.FieldEntry;
import com.thomsonreuters.ema.access.FieldList;
import com.thomsonreuters.ema.access.Msg;

@Singleton
public class ElektronMsgTransformer {

	@InjectLogger
	private Logger LOGGER;

	public FxRateModel transformElektronMsg(final Msg elektronMsg) {
		LOGGER.info("Elektron message data-type :[{}]", elektronMsg.payload().dataType());
		FxRateModel model = null;
		if (DataType.DataTypes.FIELD_LIST == elektronMsg.payload().dataType()) {
			model = transform(elektronMsg.payload().fieldList());
		}
		return model;
	}

	private FxRateModel transform(final FieldList fieldList) {
		final FxRateModel model = new FxRateModel();
		for (final FieldEntry fieldEntry : fieldList) {
			LOGGER.info("Fid:[{}], Name:[{}], DataType:[{}] " + fieldEntry.fieldId(), fieldEntry.name(),
					DataType.asString(fieldEntry.load().dataType()));

			if (Data.DataCode.BLANK == fieldEntry.code()) {
				LOGGER.info("Message is blank...");
			} else {
				switch (fieldEntry.loadType()) {
				case DataTypes.REAL:
					LOGGER.info("Name:[{}], real-value:[{}]", fieldEntry.name(), fieldEntry.real().asDouble());
					break;
				case DataTypes.DATE:
					LOGGER.info("Name:[{}], date-value:[{}]", fieldEntry.name(), fieldEntry.date().day() + " / "
							+ fieldEntry.date().month() + " / " + fieldEntry.date().year());
					break;
				case DataTypes.TIME:
					LOGGER.info("Name:[{}], time-value:[{}]", fieldEntry.name(),
							fieldEntry.time().hour() + ":" + fieldEntry.time().minute() + ":"
									+ fieldEntry.time().second() + ":" + fieldEntry.time().millisecond());
					break;
				case DataTypes.DATETIME:
					LOGGER.info("Name:[{}], date-time-value:[{}]", fieldEntry.name(),
							fieldEntry.dateTime().day() + " / " + fieldEntry.dateTime().month() + " / "
									+ fieldEntry.dateTime().year() + "." + fieldEntry.dateTime().hour() + ":"
									+ fieldEntry.dateTime().minute() + ":" + fieldEntry.dateTime().second() + ":"
									+ fieldEntry.dateTime().millisecond() + ":" + fieldEntry.dateTime().microsecond()
									+ ":" + fieldEntry.dateTime().nanosecond());
					break;
				case DataTypes.INT:
					LOGGER.info("Name:[{}], int-value:[{}]", fieldEntry.name(), fieldEntry.intValue());
					break;
				case DataTypes.UINT:
					LOGGER.info("Name:[{}], uint-value:[{}]", fieldEntry.name(), fieldEntry.uintValue());
					break;
				case DataTypes.ASCII:
					LOGGER.info("Name:[{}], ascii-value:[{}]", fieldEntry.name(), fieldEntry.ascii());
					break;
				case DataTypes.ENUM:
					LOGGER.info("Name:[{}], enum-value:[{}]", fieldEntry.name(),
							fieldEntry.hasEnumDisplay() ? fieldEntry.enumDisplay() : fieldEntry.enumValue());
					break;
				case DataTypes.RMTES:
					LOGGER.info("Name:[{}], rmtes-value:[{}]", fieldEntry.name(), fieldEntry.rmtes());
					break;
				case DataTypes.ERROR:
					LOGGER.error("Name:[{}], error-value:[{}]", fieldEntry.name(),
							fieldEntry.error().errorCodeAsString());
					break;
				default:
					LOGGER.info("===>");
					break;
				}
			}
		}
		return model;
	}
}
