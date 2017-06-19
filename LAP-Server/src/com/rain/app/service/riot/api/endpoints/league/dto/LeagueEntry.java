package com.rain.app.service.riot.api.endpoints.league.dto;

import java.io.Serializable;

import com.rain.app.service.riot.api.Dto;

public class LeagueEntry extends Dto implements Serializable {

	private static final long serialVersionUID = 3987113536371700279L;

	private boolean freshBlood;
	private boolean hotStreak;
	private boolean inactive;
	private int leaguePoints;
	private int losses;
	private MiniSeries miniSeries;
	private String playerOrTeamId;
	private String playerOrTeamName;
	private String rank;
	private boolean veteran;
	private int wins;

	public int getLeaguePoints() {
		return leaguePoints;
	}

	public int getLosses() {
		return losses;
	}

	public MiniSeries getMiniSeries() {
		return miniSeries;
	}

	public String getPlayerOrTeamId() {
		return playerOrTeamId;
	}

	public String getPlayerOrTeamName() {
		return playerOrTeamName;
	}

	public String getRank() {
		return rank;
	}

	public int getWins() {
		return wins;
	}

	public boolean isFreshBlood() {
		return freshBlood;
	}

	public boolean isHotStreak() {
		return hotStreak;
	}

	public boolean isInactive() {
		return inactive;
	}

	public boolean isVeteran() {
		return veteran;
	}

	@Override
	public String toString() {
		return getPlayerOrTeamName();
	}
}
