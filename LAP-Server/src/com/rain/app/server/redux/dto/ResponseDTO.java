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

	public ResponseDTO() {
		
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
