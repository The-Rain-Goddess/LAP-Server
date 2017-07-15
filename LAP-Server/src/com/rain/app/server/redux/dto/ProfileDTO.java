package com.rain.app.server.redux.dto;

import java.io.Serializable;
import java.util.ArrayList;

import com.rain.app.service.riot.api.endpoints.champion_mastery.dto.ChampionMasteryList;
import com.rain.app.service.riot.api.endpoints.league.dto.LeagueList;

public class ProfileDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1690086234753482393L;
	private ArrayList<LeagueList> leagues;
	private ChampionMasteryList masteries;

	public ProfileDTO(ArrayList<LeagueList> leagues, ChampionMasteryList masteries) {
		this.leagues = leagues;
		this.masteries = masteries;
	}

	/**
	 * @return the leagues
	 */
	public ArrayList<LeagueList> getLeagues() {
		return leagues;
	}

	/**
	 * @return the masteries
	 */
	public ChampionMasteryList getMasteries() {
		return masteries;
	}

	/**
	 * @param leagues the leagues to set
	 */
	public void setLeagues(ArrayList<LeagueList> leagues) {
		this.leagues = leagues;
	}

	/**
	 * @param masteries the masteries to set
	 */
	public void setMasteries(ChampionMasteryList masteries) {
		this.masteries = masteries;
	}

}
