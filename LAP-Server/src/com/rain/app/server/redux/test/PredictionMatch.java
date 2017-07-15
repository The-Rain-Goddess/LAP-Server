/**
 * 
 */
package com.rain.app.server.redux.test;

import java.util.ArrayList;
import java.util.List;

import com.rain.app.service.riot.api.endpoints.match.dto.Match;
import com.rain.app.service.riot.api.endpoints.match.dto.MatchReference;
import com.rain.app.service.riot.api.endpoints.match.dto.ParticipantStats;

/**
 * @author Ryan May
 *
 */
public class PredictionMatch {
	
	public enum Team{
		BLUE("BLUE"), RED("RED"), EQUIVALENT("EQUIVALENT");
		
		String team;
		Team(String team){
			this.team = team;
		}
	}
	
	private Match matchDetails;
	@SuppressWarnings("unused")
	private MatchReference matchReference;
	private Team victor, predictedVictor;
	
	/**
	 * generic constructor
	 */
	public PredictionMatch(Match md, MatchReference mr) {
		this.matchDetails = md;
		this.matchReference = mr;
		this.victor = (md.getTeams().get(0).isWin()) ? Team.BLUE : Team.RED;
		this.predictedVictor = simpleWinPrediction();
	}
	
//non-private a / m
	public Team simpleWinPrediction(){
		double predictiveValue = simpleRedPrediction() / simpleBluePrediction();
		return (predictiveValue != 1.0) ? (predictiveValue > 1) ? Team.RED : Team.BLUE : Team.EQUIVALENT;
	}
	
	public Team getPredictedVictor(){
		return this.predictedVictor;
	}
	
	public Team getVictor(){
		return victor;
	}
	
	public List<RankedPerformanceScore> calculateRankedPerformanceScores(){
		List<RankedPerformanceScore> scores = new ArrayList<>();
		for(int i = 0; i < matchDetails.getParticipants().size(); i++){
			scores.add(new RankedPerformanceScore(matchDetails.getParticipants().get(i)));
		} return scores;
	}
	
	
//private a / m
	private double simpleRedPrediction(){
		double score = 0;
		for(int i = 0; i < matchDetails.getParticipants().size(); i++){
			if(matchDetails.getParticipants().get(i).getTeamId()== 100)
				score += simplePlayerScore(matchDetails.getParticipants().get(i).getStats());
		} return score; 
	}
	
	private double simpleBluePrediction(){
		double score = 0;
		for(int i = 0; i < matchDetails.getParticipants().size(); i++){
			if(matchDetails.getParticipants().get(i).getTeamId()== 200)
			score += simplePlayerScore(matchDetails.getParticipants().get(i).getStats());
		} return score; 
	}
	
	private double simplePlayerScore(ParticipantStats p){
		return 100.0 * p.getTotalSessionsPlayed() * getTotalPlayerWinRate(p);
	}
	
	private double getTotalPlayerWinRate(ParticipantStats p){
		return (double)p.getWins() / (double) p.getTotalSessionsPlayed();
	}
	
//Overloaded
	@Override
	public String toString(){
		return "Victor: " + this.victor.team 
				+ ", Predicted: " + this.predictedVictor.team 
				+ ", RED Score: " + simpleRedPrediction()
				+ ", BLUE Score: " + simpleBluePrediction();
	}

}
