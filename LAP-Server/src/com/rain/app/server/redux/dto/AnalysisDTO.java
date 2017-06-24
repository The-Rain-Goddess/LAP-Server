package com.rain.app.server.redux.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.TreeMap;

import com.rain.app.server.redux.AggregatedChampionData;

public class AnalysisDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4882364436013608070L;
	private TreeMap<Integer, ArrayList<AggregatedChampionData>> championData;

	public AnalysisDTO(TreeMap<Integer, ArrayList<AggregatedChampionData>> championData) {
		this.championData = championData;
	}

	/**
	 * @return the championData
	 */
	public TreeMap<Integer, ArrayList<AggregatedChampionData>> getChampionData() {
		return championData;
	}

	/**
	 * @param championData the championData to set
	 */
	public void setChampionData(TreeMap<Integer, ArrayList<AggregatedChampionData>> championData) {
		this.championData = championData;
	}

}
