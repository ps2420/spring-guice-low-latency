package com.dbs.sg.fx.rate.model.json;

import java.io.Serializable;
import java.util.Date;

public class FxRateModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private String uuid;
	private Date timestamp;
	private String sourceSystem;
	private String takeCompID;
	private String symbol;
	private String tenor;

	private Double bid;
	private Double bidSize;
	private Double ask;
	private Double askSize;
	private boolean tradeable;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getSourceSystem() {
		return sourceSystem;
	}

	public void setSourceSystem(String sourceSystem) {
		this.sourceSystem = sourceSystem;
	}

	public String getTakeCompID() {
		return takeCompID;
	}

	public void setTakeCompID(String takeCompID) {
		this.takeCompID = takeCompID;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getTenor() {
		return tenor;
	}

	public void setTenor(String tenor) {
		this.tenor = tenor;
	}

	public Double getBid() {
		return bid;
	}

	public void setBid(Double bid) {
		this.bid = bid;
	}

	public Double getBidSize() {
		return bidSize;
	}

	public void setBidSize(Double bidSize) {
		this.bidSize = bidSize;
	}

	public Double getAsk() {
		return ask;
	}

	public void setAsk(Double ask) {
		this.ask = ask;
	}

	public Double getAskSize() {
		return askSize;
	}

	public void setAskSize(Double askSize) {
		this.askSize = askSize;
	}

	public boolean isTradeable() {
		return tradeable;
	}

	public void setTradeable(boolean tradeable) {
		this.tradeable = tradeable;
	}

}
