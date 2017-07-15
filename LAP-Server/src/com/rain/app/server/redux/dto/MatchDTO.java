package com.rain.app.server.redux.dto;

import java.io.Serializable;
import java.util.ArrayList;

import com.rain.app.service.riot.api.endpoints.match.dto.Match;
import com.rain.app.service.riot.api.endpoints.match.dto.MatchReferenceList;

public class MatchDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1932973421869961217L;
	private ArrayList<Match> matches;
	private MatchReferenceList matchReferences;

	public MatchDTO(ArrayList<Match> matches, MatchReferenceList matchReferences) {
		this.matches = matches;
		this.matchReferences = matchReferences;
	}

	/**
	 * @return the matches
	 */
	public ArrayList<Match> getMatches() {
		return matches;
	}

	/**
	 * @return the matchReferences
	 */
	public MatchReferenceList getMatchReferences() {
		return matchReferences;
	}

	/**
	 * @param matches the matches to set
	 */
	public void setMatches(ArrayList<Match> matches) {
		this.matches = matches;
	}

	/**
	 * @param matchReferences the matchReferences to set
	 */
	public void setMatchReferences(MatchReferenceList matchReferences) {
		this.matchReferences = matchReferences;
	}

}
