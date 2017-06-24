package com.rain.app.server.redux.dto;

import java.io.Serializable;
import java.util.ArrayList;

import com.rain.app.service.riot.api.endpoints.match.dto.Match;
import com.rain.app.service.riot.api.endpoints.match.dto.MatchReference;

public class MatchDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1932973421869961217L;
	private ArrayList<Match> matches;
	private ArrayList<MatchReference> matchReferences;

	public MatchDTO(ArrayList<Match> matches, ArrayList<MatchReference> matchReferences) {
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
	public ArrayList<MatchReference> getMatchReferences() {
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
	public void setMatchReferences(ArrayList<MatchReference> matchReferences) {
		this.matchReferences = matchReferences;
	}

}
