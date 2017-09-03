package com.rain.app.server.redux.test;

import com.rain.app.service.riot.api.endpoints.match.dto.Participant;

public class RankedPerformanceScore {
	
	private long score;

	public RankedPerformanceScore(Participant p) {
		score = 0; // = 
		calculateRankedPerformanceScore(p);
	}
	
	
//private a/m	
	private long calculateRankedPerformanceScore(Participant p){
		return 0;
	}

//non-private a/m	
	public long getValue(){
		return score;
	}

}
