package com.rain.app.server.redux.dto;

import java.io.Serializable;

public class ResponseDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 813743032122433941L;
	private MatchDTO match;
	private ProfileDTO profile;
	private AnalysisDTO analysis;
	private String summonerName;
	private long summonerId;

	public ResponseDTO(String summonerName, long id) {
		this.summonerName = summonerName;
		this.summonerId = id;
	}

	/**
	 * @return the match
	 */
	public MatchDTO getMatch() {
		return match;
	}

	/**
	 * @return the profile
	 */
	public ProfileDTO getProfile() {
		return profile;
	}

	/**
	 * @return the analysis
	 */
	public AnalysisDTO getAnalysis() {
		return analysis;
	}

	/**
	 * @return the summonerName
	 */
	public String getSummonerName() {
		return summonerName;
	}

	/**
	 * @return the summonerId
	 */
	public long getSummonerId() {
		return summonerId;
	}

	/**
	 * @param summonerName the summonerName to set
	 */
	public void setSummonerName(String summonerName) {
		this.summonerName = summonerName;
	}

	/**
	 * @param summonerId the summonerId to set
	 */
	public void setSummonerId(long summonerId) {
		this.summonerId = summonerId;
	}

	/**
	 * @param match the match to set
	 */
	public void setMatch(MatchDTO match) {
		this.match = match;
	}

	/**
	 * @param profile the profile to set
	 */
	public void setProfile(ProfileDTO profile) {
		this.profile = profile;
	}

	/**
	 * @param analysis the analysis to set
	 */
	public void setAnalysis(AnalysisDTO analysis) {
		this.analysis = analysis;
	}

}
