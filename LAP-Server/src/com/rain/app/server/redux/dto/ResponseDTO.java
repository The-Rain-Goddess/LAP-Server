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
	public ResponseDTO setSummonerName(String summonerName) {
		this.summonerName = summonerName;
		return this;
	}

	/**
	 * @param summonerId the summonerId to set
	 */
	public ResponseDTO setSummonerId(long summonerId) {
		this.summonerId = summonerId;
		return this;
	}

	/**
	 * @param match the match to set
	 */
	public ResponseDTO setMatch(MatchDTO match) {
		this.match = match;
		return this;
	}

	/**
	 * @param profile the profile to set
	 */
	public ResponseDTO setProfile(ProfileDTO profile) {
		this.profile = profile;
		return this;
	}

	/**
	 * @param analysis the analysis to set
	 */
	public ResponseDTO setAnalysis(AnalysisDTO analysis) {
		this.analysis = analysis;
		return this;
	}

}
