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
	public void setType(RequestType type) {
		this.type = type;
	}

	/**
	 * @param summonerName the summonerName to set
	 */
	public void setSummonerName(String summonerName) {
		this.summonerName = summonerName;
	}

	/**
	 * @param requestStart the requestStart to set
	 */
	public void setRequestStart(int requestStart) {
		this.requestStart = requestStart;
	}

	/**
	 * @param requestStop the requestStop to set
	 */
	public void setRequestStop(int requestStop) {
		this.requestStop = requestStop;
	}
	
	@Override
	public String toString(){
		return "Name: " + summonerName + ", Start: " + requestStart + ", Stop: " + requestStop;
	}
}
