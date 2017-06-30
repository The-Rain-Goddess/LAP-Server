/**
 * 
 */
package com.rain.app.server.redux.client;

import java.io.Serializable;

/**
 * @author Ryan May
 *
 */
public class Request implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9180467265309047782L;
	private String summonerName;
	private int requestStart;
	private int requestStop;
	private RequestType type;
	
	/**
	 * generic constructor
	 */
	public Request(RequestType type) {
		this.type = type;
	}

	/**
	 * @return the summonerName
	 */
	public String getSummonerName() {
		return summonerName;
	}

	/**
	 * @return the requestStart
	 */
	public int getRequestStart() {
		return requestStart;
	}

	/**
	 * @return the requestStop
	 */
	public int getRequestStop() {
		return requestStop;
	}

	/**
	 * @return the type
	 */
	public RequestType getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public Request setType(RequestType type) {
		this.type = type;
		return this;
	}

	/**
	 * @param summonerName the summonerName to set
	 */
	public Request setSummonerName(String summonerName) {
		this.summonerName = summonerName;
		return this;
	}

	/**
	 * @param requestStart the requestStart to set
	 */
	public Request setRequestStart(int requestStart) {
		this.requestStart = requestStart;
		return this;
	}

	/**
	 * @param requestStop the requestStop to set
	 */
	public Request setRequestStop(int requestStop) {
		this.requestStop = requestStop;
		return this;
	}
	
	@Override
	public String toString(){
		return "Name: " + summonerName + ", Start: " + requestStart + ", Stop: " + requestStop;
	}
}
