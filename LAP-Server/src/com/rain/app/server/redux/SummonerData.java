package com.rain.app.server.redux;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.rain.app.server.redux.client.Request;
import com.rain.app.server.redux.dto.AnalysisDTO;
import com.rain.app.server.redux.dto.MatchDTO;
import com.rain.app.server.redux.dto.ProfileDTO;
import com.rain.app.server.redux.dto.ResponseDTO;
import com.rain.app.service.riot.api.endpoints.champion_mastery.dto.ChampionMastery;
import com.rain.app.service.riot.api.endpoints.champion_mastery.dto.ChampionMasteryList;
import com.rain.app.service.riot.api.endpoints.league.dto.LeagueList;
import com.rain.app.service.riot.api.endpoints.match.dto.Match;
import com.rain.app.service.riot.api.endpoints.match.dto.MatchReference;
import com.rain.app.service.riot.api.endpoints.match.dto.MatchReferenceList;
import com.rain.app.service.riot.api.endpoints.match.dto.Participant;
import com.rain.app.service.riot.api.endpoints.match.dto.ParticipantStats;
import com.rain.app.service.riot.api.endpoints.match.dto.Player;
import com.rain.app.service.riot.api.endpoints.summoner.dto.Summoner;



public class SummonerData implements Serializable{
	
	private static final long serialVersionUID = 8387323305835454512L;
	private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private static final int WIN_DATA_INDEX = 1, LOSS_DATA_INDEX = 2, ALL_DATA_INDEX = 0;
	
	private ArrayList<Match> matchList;
	private MatchReferenceList matchReferenceList;
	private ArrayList<String> masteryProfileData;
	private ChampionMasteryList championMasteryList;
	private TreeMap<String, ArrayList<LeagueList>> leagueMap;
	private TreeMap<Integer, ArrayList<AggregatedChampionData>> rankedChampionDataMap;
	private String summonerName;
	private long summonerId;

//constructors	
	public SummonerData(String name, Summoner summoner){
		log("SummonerData: " + threadName() + " " + "Constructor called.");
		this.summonerId = summoner.getId();
		this.summonerName = name;
		this.matchList = new ArrayList<>();
		log("SummonerData: " + threadName() + " " + summonerName + " was created.");
	}
	
	public SummonerData(ResponseDTO dataDTO){
		log("SummonerData: " + threadName() + " " + "Constructor called.");
		this.summonerName = dataDTO.getSummonerName();
		this.summonerId = dataDTO.getSummonerId();
		if (dataDTO.getMatch()!=null){
			this.matchList = dataDTO.getMatch().getMatches();
			this.matchReferenceList = dataDTO.getMatch().getMatchReferences();
		} else {
			this.matchList = new ArrayList<Match>();
		}
		
		if(dataDTO.getAnalysis()!=null){
			this.rankedChampionDataMap = dataDTO.getAnalysis().getChampionData();
		}
		
		if(dataDTO.getProfile()!=null){
			this.championMasteryList = dataDTO.getProfile().getMasteries();
		}
		
		log("SummonerData: " + threadName() + " " + summonerName + " was created.");
	}

//private a/m
	//statics
	private static void log(Level level, String msg, Exception e){
		LOGGER.log(level, msg, e);
	}
	
	private static void log(String msg){
		LOGGER.log(Level.INFO, msg);
	}
	
	//non-statics
	private String aggregateMatchData(Match match, int matchIndex){
		String aggregatedData = null;
		List<String> unorderedPlayerList = new ArrayList<>(10);
		for(int i = 0; i < 10; i++){ //aggregates player data to list
			Participant player = match.getParticipants().get(i);
			Player pIdentity = match.getParticipantIdentities().get(i).getPlayer();
			String stats = 	getMatchDetails(player.getStats(), matchReferenceList.getMatches().get(matchIndex)) +
							"champion:" + ServerRedux.getChampionNameFromId(player.getChampionId()) + "/" + 
							getSummonerSpellIds(player) + 
							getMatchPeripherals(match) + 
							"summonerId: " + pIdentity.getSummonerId() + "/";;
			if(pIdentity.getSummonerId()==summonerId)
				stats = stats + getTeamStats(match.getParticipants(), player.getTeamId());
			unorderedPlayerList.add(stats);
		} 
		aggregatedData = toOutputFormat(sortPlayerList(unorderedPlayerList)); //sorts list and formats it		
		return aggregatedData;
	}
	
	private String getTeamStats(List<Participant> players, int teamId){
		double teamDmg = 0, enemyTeamDmg = 0;
		for(int k = 0; k<10; k++){ //this loop is to get total team dmg and total enemy team dmg
			if(players.get(k).getTeamId() == teamId )
				teamDmg+= (players.get(k).getStats().getTotalDamageDealtToChampions());
			else
				enemyTeamDmg += (players.get(k).getStats().getTotalDamageDealtToChampions());
		} return "totalTeamDmg:" + teamDmg + "/" + 
				 "totalEnemyDmg:" + enemyTeamDmg + "/";
	}
	
	private List<String> sortPlayerList(List<String> unorderedPlayerList){
		List<String> orderedPlayerList = new ArrayList<>(10);
		for(String playerData : unorderedPlayerList){
			if(playerData.contains("summonerId:" + summonerId))
				orderedPlayerList.add(0, playerData);
			else
				orderedPlayerList.add(playerData);
		} return orderedPlayerList;
	}
	
	private String toOutputFormat(List<String> list){
		String out = "";
		for(int i = 0; i<list.size(); i++)
			out = (i==list.size()-1) ? (out + list.get(i)) : (out + list.get(i) + "PLAYERS");
		return out;
	}
	
	private String getMatchPeripherals(Match match){
				return 	"matchLength:" + match.getGameDuration() + "/" +	//60
						"matchId:" + match.getGameId() + "/" +				//61
						"matchMode:" + match.getGameMode() + "/" +			//62
						"matchType:" + match.getGameType() + "/" + 			//63
						"matchStartTime:" + match.getGameCreation() + "/" + //64
						"queueType:" + match.getQueueId() + "/";			//65
	}
	
	private String getSummonerSpellIds(Participant player){
				 return "sspell1:" + player.getSpell1Id() +  "/" +		//58
						"sspell2:" + player.getSpell2Id() + "/" +		//59 
						"teamId:" +  player.getTeamId() + "/"; 
	}
	
	private String getMatchDetails(ParticipantStats ps, MatchReference matchReference){
		return 	aggregatePlayerStats(ps) ;		
	}
	
	private String aggregatePlayerStats(ParticipantStats ps){
		String returnString = 
						"assists:" + ps.getAssists() + "/" + 
						
						"champLevel:" + ps.getChampLevel() + "/" + 
						"cs:" + (ps.getTotalMinionsKilled() + ps.getNeutralMinionsKilledEnemyJungle() + ps.getNeutralMinionsKilledTeamJungle()) + "/" +
						"deaths:" + ps.getDeaths() + "/" + 		  
						"dmgToChamp:" + ps.getTotalDamageDealtToChampions() + "/" + 
						"dmgTaken:" + ps.getTotalDamageTaken() + "/" + 
						"doubleKills:" + ps.getDoubleKills() + "/" + 
						"firstBloodAssist:" + ps.isFirstBloodAssist() + "/" + 
						"firstBloodKill:" + ps.isFirstBloodKill() + "/" + 
						"goldEarned:" + ps.getGoldEarned() + "/" +
						"item0:" + ps.getItem0() + "/" + 
						"item1:" + ps.getItem1() + "/" + 
						"item2:" + ps.getItem2() + "/" +
						"item3:" + ps.getItem3() + "/" + 
						"item4:" + ps.getItem4() + "/" + 
						"item5:" + ps.getItem5() + "/" +
						"item6:" + ps.getItem6() + "/" + 
						"kills:" + ps.getKills() + "/" + 
						"largestKillSpree:" + ps.getLargestKillingSpree() + "/" + 
						"inhibKills:" + ps.getInhibitorKills() + "/" + 
						"largestCritStrike:" + ps.getLargestCriticalStrike() +"/" + 
						"magicDmgDealt:" + ps.getMagicDamageDealt() + "/" + 
						"magicDmgDealtChamps:" + ps.getMagicDamageDealtToChampions() +"/" + 
						"magicDmgTaken:" + ps.getMagicalDamageTaken() +"/" + 
						"minionsKilled:" + ps.getTotalMinionsKilled() + "/" + 
						"neutralMinionsKilled:" + ps.getNeutralMinionsKilled() + "/" + 
						"neutralMinionsKilledEnemyJngl:" + ps.getNeutralMinionsKilledEnemyJungle() +"/" + 
						"neutralMinionsKilledTeamJngl:" + ps.getNeutralMinionsKilledTeamJungle() + "/" + 
						"nodeCaptures:" + ps.getNodeCapture() + "/" + 
						"nodeCaptureAssist:" + ps.getNodeCaptureAssist() + "/" + 
						"nodeNeutralized:" + ps.getNodeNeutralize() + "/" + 
						"nodeNeutralizedAssist:" + ps.getNodeNeutralizeAssist() + "/" + 
						"objectivePlayerScore:" + ps.getObjectivePlayerScore() + "/" + 
						"pentaKills:" + ps.getPentaKills() + "/" + 
						"physicalDmgDealt:" + ps.getPhysicalDamageDealt() + "/" + 
						"PhysicalDmgDealtToChampions:" + ps.getPhysicalDamageDealtToChampions() +"/" + 
						"physicalDmgTaken:" + ps.getPhysicalDamageTaken() + "/" + 
						"quadraKills:" + ps.getQuadraKills() + "/" + 
						"sightWardsBoughtInGame:" + ps.getSightWardsBoughtInGame() + "/" + 
						"teamObjective:" + ps.getTeamObjective() + "/" + 
						"totalDmgDealt:" + ps.getTotalDamageDealt() + "/" + 
						"totalDmgDealtToChampions:" + ps.getTotalDamageDealtToChampions() + "/" + 
						"totalDmgTaken:" + ps.getTotalDamageTaken() + "/" + 
						"totalHeal:" + ps.getTotalHeal() + "/" + 
						"totalPlayerScore:" + ps.getTotalPlayerScore() + "/" + 
						"totalScoreRank:" + ps.getTotalScoreRank() + "/" + 
						"totalTimeCrowdControlDealt:" + ps.getTimeCCingOthers() + "/" + 
						"totalUnitHealed:" + ps.getTotalUnitsHealed() + "/" + 
						"towerKills:"  +ps.getTurretKills() + "/" + 
						"tripleKills:" + ps.getTripleKills() + "/" + 
						"trueDmgTaken:" + ps.getTrueDamageTaken() + "/" + 
						"trueDmgDealt:" + ps.getTrueDamageDealt() + "/" + 
						"trueDmgDealtChamps:" + ps.getTrueDamageDealtToChampions() + "/" + 
						"unrealKills:" + ps.getUnrealKills() + "/" + 
						"visionWardsBoughtInGame:" + ps.getVisionWardsBoughtInGame() + "/" + 
						"wardsKilled:" + ps.getWardsKilled() + "/" + 
						"wardsPlaced:" + ps.getWardsPlaced() +"/" + 
						"winner:" + ps.isWin() + "/";
		return returnString;
	}
	
	private ProfileDTO getProfileSummary(){
		log("SummonerData: " + threadName() + " Aggregating profile summary.");
		ArrayList<LeagueList> leagues = new ArrayList<>();
		for(Map.Entry<String, ArrayList<LeagueList>> entry : leagueMap.entrySet()){
			for(LeagueList league : entry.getValue()){
				leagues.add(league);
			} 
		} log("SummonerData: " + threadName() + " Profile summary aggregated successfully."); 
		ProfileDTO profile = new ProfileDTO(leagues, this.championMasteryList);
		log("SummonerData: " + threadName() + " " + profile);
		return profile;
	}
	
	@SuppressWarnings("unused")
	private ArrayList<ChampionMastery> getChampionMasterySummary(int numberOfMasteriesToRetrieve){
		log("SummonerData: " + threadName() + " Aggregating mastery summary.");
		ArrayList<ChampionMastery> championMasterySummary = new ArrayList<>();
		for(int i = 0; i < numberOfMasteriesToRetrieve && i < championMasteryList.getChampionMasteries().size(); i++){
			championMasterySummary.add(championMasteryList.getChampionMasteries().get(i));
		} log("SummonerData: " + threadName() + " Mastery summary aggregated successfully.");  
		return championMasterySummary;
	}
	
	private TreeMap<Integer, ArrayList<AggregatedChampionData>> updateRankedChampionDataMap(){
		TreeMap<Integer, ArrayList<AggregatedChampionData>> updatedChampionDataMap = new TreeMap<>();
		for(int i = 0; i < matchList.size(); i++){
			for(int j = 0; j < matchList.get(i).getParticipantIdentities().size(); j++){
				if(matchList.get(i).getParticipantIdentities().get(j).getPlayer().getSummonerId() == summonerId){
					Participant player = matchList.get(i).getParticipants().get(j);
					if(updatedChampionDataMap.containsKey(player.getChampionId())){
						updatedChampionDataMap.get(player.getChampionId()).get(ALL_DATA_INDEX).addNewMatchStats(player.getStats());
						if(player.getStats().isWin()){
							updatedChampionDataMap.get(player.getChampionId()).get(WIN_DATA_INDEX).addNewMatchStats(player.getStats());
							//updatedChampionDataMap.get(player.getChampionId()).get(LOSS_DATA_INDEX).incrementMatchNumber()
						} else{
							updatedChampionDataMap.get(player.getChampionId()).get(LOSS_DATA_INDEX).addNewMatchStats(player.getStats());
							//updatedChampionDataMap.get(player.getChampionId()).get(WIN_DATA_INDEX).incrementMatchNumber()
						}
					} else {
						ArrayList<AggregatedChampionData> newData = new ArrayList<>();
						newData.add(new AggregatedChampionData(player.getStats(), player.getChampionId())); //all data
						if(player.getStats().isWin()){ // win/loss data
							newData.add(new AggregatedChampionData(player.getStats(), player.getChampionId()));
							newData.add(new AggregatedChampionData(player.getChampionId(), true)); //could throw an error later if adding to uninstanciated fields
						} else{
							newData.add(new AggregatedChampionData(player.getChampionId(), false));
							newData.add(new AggregatedChampionData(player.getStats(), player.getChampionId()));
						} updatedChampionDataMap.put(player.getChampionId(), newData);
					}
				}
			}
		} rankedChampionDataMap = updatedChampionDataMap;
		return rankedChampionDataMap;
	}
	
	private String threadName(){
		return "[" + Thread.currentThread().getName() + "]";
	}
	
//non-private accessors/mutators
	public SummonerData updateAnalysis(){
		log("SummonerData: " + threadName() + " Updating analysis data for " + summonerName + "...");
		rankedChampionDataMap = updateRankedChampionDataMap();
		log("SummonerData: " + threadName() + " Succesfully updated analysis data.");
		return this;
	}
	
	public SummonerData setMatchReferenceList(MatchReferenceList matches){
		matchReferenceList = matches;
		return this;
	}
	
	public SummonerData addMatch(Match match, boolean addNewMatch){
		if(addNewMatch) //if false, adding old match
			matchList.add(0, match);
		else
			matchList.add(match);
		return this;
	}
	
	public SummonerData addMatch(Match match){
		matchList.add(match);
		return this;
	}
	
	public SummonerData setLeagueMap(TreeMap<String, ArrayList<LeagueList>> league){
		this.leagueMap = league;
		return this;
	}
	
	public ChampionMasteryList setChampionMasteryList(ChampionMasteryList mastery){
		this.championMasteryList = mastery;
		return championMasteryList;
	}
	
//accessors
	//dtos
	public MatchDTO getMatchHistory(Request request){
		log("SummonerData: " + threadName() + " Retrieving match history for " + summonerName);
		log("SummonerData: " + threadName() + " Retrieving match data [" + request.getRequestStart() + " , " + request.getRequestStop() + ")");
		
		int numberOfMatches = request.getRequestStop() - request.getRequestStart(), i = 0;
		ArrayList<Match> matches = new ArrayList<>(numberOfMatches * 10);
		//ArrayList<MatchReference> matchReferences = new ArrayList<>(numberOfMatches * 10);
		try{
			
			for(i = request.getRequestStart(); i < request.getRequestStop()-1; i++){
				log("SummonerData: " + threadName() + " Match requested -> \n" + matchList.get(i).getGameId() + "\n\n" + Arrays.asList(aggregateMatchData(matchList.get(i), i).replaceAll("PLAYERS", "PLAYERS\n").split("PLAYERS")));
				matches.add(matchList.get(i));
				//matchReferences.add(matchReferenceList.getMatches().get(i));
			} return new MatchDTO(matches, matchReferenceList);
			
		} catch(IndexOutOfBoundsException e){
			e.printStackTrace();
			log(Level.WARNING, "SummonerData: " + threadName() + " Match " + i + " not found.", e);
			return null;
		}
	}
	
	public ProfileDTO getProfile(Request request){ 
		log("SummonerData: " + threadName() + " Retrieving profile data for " + summonerName + "...");
		ProfileDTO profile = getProfileSummary();
		log("SummonerData: " + threadName() + " Profile -> \n" + profile);
		return profile;
	} 
	
	public AnalysisDTO getAnalysis(Request request){
		log("SummonerData: " + threadName() + " Retrieving analysis data for " + summonerName);
		log("SummonerData: " + threadName() + " Analysis -> \n" + ServerUtilities.mapOfListsToString(rankedChampionDataMap));
		return new AnalysisDTO(rankedChampionDataMap);
	}
	
	//regular getters
	public List<String> getMasteryProfileData(){ return masteryProfileData; }
	
	public String getSummonerName(){ return summonerName; }
	
	public long getSummonerId(){ return summonerId; }
	
	public MatchReferenceList getMatchReferenceList(){ return matchReferenceList; }
	
	public long getMostRecentMatchId(){ return matchList.get(0).getGameId(); }
	
	public Map<String, ArrayList<LeagueList>> getLeagueMap(){ return this.leagueMap; }
	
	public Map<Integer, ArrayList<AggregatedChampionData>> getRankedAnalysis(){ return this.rankedChampionDataMap; }
	
	public List<Match> getMatchList(){ return matchList; }
}
