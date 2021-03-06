package com.rain.app.server.redux;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.rain.app.server.redux.client.Request;
import com.rain.app.service.riot.api.ApiConfig;
import com.rain.app.service.riot.api.RiotApi;
import com.rain.app.service.riot.api.endpoints.champion_mastery.dto.ChampionMasteryList;
import com.rain.app.service.riot.api.endpoints.league.dto.LeagueList;
import com.rain.app.service.riot.api.endpoints.match.dto.Match;
import com.rain.app.service.riot.api.endpoints.match.dto.MatchReference;
import com.rain.app.service.riot.api.endpoints.match.dto.MatchReferenceList;
import com.rain.app.service.riot.api.endpoints.summoner.dto.Summoner;
import com.rain.app.service.riot.constant.Platform;

public class RiotApiHandler {
	private String summonerName;
	private SummonerData summonerData;
	private Summoner summoner;
	private Platform platform;
	private RiotApi api;
	private long summonerId;
	private long summonerAccountId;
	//private final String apiKey  = "fb22315c-06cd-4f26-91ed-f0912a72a78d"; //api-key
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	//private RiotApi api_backup;
	//private final String apiKey2 = "RGAPI-1E616A6D-EC4A-46F1-A9BE-E1C620EABB05"; //api-key2
	
//constructor	
	public RiotApiHandler(String summonerName, Platform platform){
		try{
			log(Level.INFO, "RiotApiHandler: " + threadName() + " Constructor called for summoner " + summonerName + "...");
			this.summonerName = summonerName;
			this.platform = platform;
			this.api = new RiotApi(new ApiConfig().setKey(ServerRedux.API_KEY));
			this.summoner = (Summoner) evaluateFromFuture(api.getClass().getMethod("getSummonerByName", Platform.class, String.class), platform, summonerName);
			this.summonerId = this.summoner.getId();
			this.summonerAccountId = this.summoner.getAccountId();
			this.summonerData = (isSummonerExistent()) ? (isSummonerCurrent()) ? getSummonerDataFromStorage(summonerName) : updateSummoner() : createSummoner();
			log(Level.INFO, "RiotApiHandler: " + threadName() + " Constructor call finished for summoner " + summonerName + ".");
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}
	
//private mutators	
	private Object evaluateFromFuture(Method method, Object... args){
		try {
			Future<Object> future = ServerRedux.getDataRetrievalPool().submit(new FutureTask<Object>(api, method, args));
			return future.get();
		} catch (ExecutionException e) {
			e.printStackTrace();
			System.err.println("Riot Api Exception!!!");
			suspend(1000L);
			return evaluateFromFuture(method, args);
		} catch(InterruptedException e){
			e.printStackTrace();
			return null;
		} catch(Exception e){
			e.printStackTrace();
		} return null;
	}
	
	private void suspend(long timeout){
		try{
			Thread.sleep(timeout);
		} catch(InterruptedException e){
			e.printStackTrace();
		}
	}
	
	private boolean isSummonerExistent(){
		return (ServerRedux.getSummonerDataStorage().containsKey(summonerName));
	}
	
	private boolean isSummonerCurrent() throws InterruptedException, ExecutionException, NoSuchMethodException, SecurityException{
		return ( mostRecentMatchInMemoryId()== getMatchReferenceList().getMatches().get(0).getGameId());
	}
	
	private static void log(Level info, String string) {
		LOGGER.log(info, string);
	}

	private long mostRecentMatchInMemoryId(){
		return ServerRedux.getSummonerDataStorage().get(summonerName).getMostRecentMatchId();
	}
	
	@SuppressWarnings("unused")
	private MatchReferenceList getMostRecentMatchReferenceList() throws InterruptedException, ExecutionException, NoSuchMethodException, SecurityException{
		return (MatchReferenceList) evaluateFromFuture(api.getClass().getMethod("getRecentMatchListByAccountId", Platform.class, long.class), platform, summonerId);
	}
	
	private MatchReferenceList getMatchReferenceList(){
		try {
			MatchReferenceList firstCentenial = (MatchReferenceList) evaluateFromFuture(api.getClass().getMethod("getMatchReferenceListByAccountId", Platform.class, long.class, Set.class, Set.class, Set.class, long.class, long.class, int.class, int.class), platform, summonerAccountId, null, null, null, -1, -1, -1, -1);
			MatchReferenceList secondCentenial = (MatchReferenceList) evaluateFromFuture(api.getClass().getMethod("getMatchReferenceListByAccountId", Platform.class, long.class, Set.class, Set.class, Set.class, long.class, long.class, int.class, int.class), platform, summonerAccountId, null, null, null, -1, -1, 100, 200);
			MatchReferenceList thirdCentenial = (MatchReferenceList) evaluateFromFuture(api.getClass().getMethod("getMatchReferenceListByAccountId", Platform.class, long.class, Set.class, Set.class, Set.class, long.class, long.class, int.class, int.class), platform, summonerAccountId, null, null, null, -1, -1, 200, 300);
			firstCentenial.getMatches().addAll(secondCentenial.getMatches());
			firstCentenial.getMatches().addAll(thirdCentenial.getMatches());
			return firstCentenial;
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} return null;
	}

	private SummonerData getSummonerDataFromStorage(String keyName){
		log("RiotApiHandler: " + threadName() + " Retrieving old summoner data...");
		return ServerRedux.getSummonerDataStorage().get(keyName);
	}
	
	private int getNumberOfNewMatches(List<MatchReference> matchReferences){
		int count = 0;
		long mostRecentMatchInMemory = mostRecentMatchInMemoryId();
		long matchToAddId = matchReferences.get(count).getGameId();
		while(matchToAddId != mostRecentMatchInMemory){
			matchToAddId = matchReferences.get(count).getGameId();
			count++;
		} return count;
	}
	
	private Match getMatch(long matchId) throws NoSuchMethodException, SecurityException{
		return (Match) evaluateFromFuture(api.getClass().getMethod("getMatch", Platform.class, long.class), platform, matchId);
	}
	
	private void updateMatchReferences(MatchReferenceList mrl){
		ServerRedux.getSummonerDataStorage().get(summonerName).setMatchReferenceList(mrl);
	}
	
	private void updateMatches(MatchReferenceList mrl) throws NoSuchMethodException, SecurityException{
		List<MatchReference> matchReferences = mrl.getMatches();
		int numberOfNewMatchesToAdd = getNumberOfNewMatches(matchReferences);
		for(int i = numberOfNewMatchesToAdd; i > 0 ; i--){
			log("RiotApiHandler: " + threadName() + " Retrieving match: " + matchReferences.get(i).getGameId() + "...");
			ServerRedux.getSummonerDataStorage().get(summonerName).addMatch(getMatch(matchReferences.get(i).getGameId()), true);
			log("RiotApiHandler: " + threadName() + " Successfully retrieved match: " + matchReferences.get(i).getGameId() + ".");
		}
	}
	
	private void updateProfileData() throws NoSuchMethodException, SecurityException{
		ServerRedux.getSummonerDataStorage().get(summonerName).setLeagueMap(getLeagueMap());
		ServerRedux.getSummonerDataStorage().get(summonerName).setChampionMasteryList(getChampionMastery());
	}
	
	private void updateRankedData(){
		log("RiotApiHandler: " + threadName() + " Updating ranked data...");
		ServerRedux.getSummonerDataStorage().get(summonerName).updateAnalysis();
		log("RiotApiHandler: " + threadName() + " Successfully updated ranked data.");
	}
	
	@SuppressWarnings("unchecked")
	private TreeMap<String, ArrayList<LeagueList>> getLeagueMap() throws NoSuchMethodException, SecurityException{
		log("RiotApiHandler: " + threadName() + " Attempting to retrieve league map...");
		TreeMap<String, ArrayList<LeagueList>> map = new TreeMap<String, ArrayList<LeagueList>>();
		map.put(summonerName, (ArrayList<LeagueList>)evaluateFromFuture(api.getClass().getMethod("getLeagueBySummonerId", Platform.class, long.class), platform, summonerId));
		//log("RiotApiHandler: Successfully retrieved league map.");
		return map; 
	}
	
	private ChampionMasteryList getChampionMastery() throws NoSuchMethodException, SecurityException{
		log("RiotApiHandler: " + threadName() + " Attempting to retrieve Champion Mastery...");
		return (ChampionMasteryList) evaluateFromFuture(api.getClass().getMethod("getChampionMasteriesBySummoner", Platform.class, long.class), platform, summonerId);
	}
	
	private List<Match> getMatchList(MatchReferenceList newMatchReferences) throws NoSuchMethodException, SecurityException{
		log("RiotApiHandler: " + threadName() + " Attempting to retrieve MatchReferenceList...");
		List<Match> matchList = Arrays.asList(	this.getMatch(newMatchReferences.getMatches().get(0).getGameId()),
				this.getMatch(newMatchReferences.getMatches().get(1).getGameId()),
				this.getMatch(newMatchReferences.getMatches().get(2).getGameId()),
				this.getMatch(newMatchReferences.getMatches().get(3).getGameId()),
				this.getMatch(newMatchReferences.getMatches().get(4).getGameId()),
				this.getMatch(newMatchReferences.getMatches().get(5).getGameId()),
				this.getMatch(newMatchReferences.getMatches().get(6).getGameId()),
				this.getMatch(newMatchReferences.getMatches().get(7).getGameId()));
		log("RiotApiHandler: " + threadName() + " Successfully retrieved MatchReferenceList.");
		return matchList;
	}
	
	private SummonerData updateSummoner(){ 
		log("RiotApiHandler: " + threadName() + " Updating old summoner data..." + " for " + summonerName);
		try {
			MatchReferenceList updatedMatchReferenceList = getMatchReferenceList();
			
			log("RiotApiHandler: " + threadName() + " Updating MatchReferenceList...");
			updateMatchReferences(updatedMatchReferenceList);
			log("RiotApiHandler: " + threadName() + " Updated MatchReferenceList.");
			
			log("RiotApiHandler: " + threadName() + " Updated Matches...");
			updateMatches(updatedMatchReferenceList);
			log("RiotApiHandler: " + threadName() + " Updated Matches.");
			
			log("RiotApiHandler: " + threadName() + " Updated Profile Data...");
			updateProfileData();
			log("RiotApiHandler: " + threadName() + " Updated Profile Data.");
			
			log("RiotApiHandler: " + threadName() + " Updated Ranked Data...");
			updateRankedData();
			log("RiotApiHandler: " + threadName() + " Updated Ranked Data.");
			
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		log("RiotApiHandler: " + threadName() + " Successfully updated old summoner data.");
		return ServerRedux.getSummonerDataStorage().get(summonerName);
	}
	
	private SummonerData createSummoner(){
		log("RiotApiHandler: " + threadName() + " Creating new SummonerData for " + summonerName + "...");
		SummonerData newSummoner = null;
		try {
			MatchReferenceList newMatchReferences = getMatchReferenceList();
			newSummoner = new SummonerData(summonerName, summoner);
			newSummoner.setMatchReferenceList(newMatchReferences);
			getMatchList(newMatchReferences).forEach(newSummoner::addMatch);
			log("RiotApiHandler: " + threadName() + " Mastery list: \n" + ServerUtilities.listToString(newSummoner.setChampionMasteryList(getChampionMastery()).getChampionMasteries()));
			log("RiotApiHandler: " + threadName() + " League map: \n" + ServerUtilities.mapToString(newSummoner.setLeagueMap(getLeagueMap()).getLeagueMap()));
			log("RiotApiHandler: " + threadName() + " Attempting to retrieve updated analysis data...");
			log("RiotApiHandler: " + threadName() + " Ranked Analysis: \n" + ServerUtilities.mapOfListsToString(newSummoner.updateAnalysis().getRankedAnalysis()));
			ServerRedux.getSummonerDataStorage().put(summonerName, newSummoner);
			log("RiotApiHandler: " + threadName() + " SummonerData->" + summonerName + " was successfully added to storage.");
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} return newSummoner;
	}
	
	private void log(String msg){
		LOGGER.log(Level.INFO, msg);
	}

//non-private accessors/mutators
	public void prepareRequest(Request request){
		MatchReferenceList matchReferences = getMatchReferenceList();
		log("RiotApiHandler: " + threadName() + " Recieved request for data, executing now...");
			while(getSummonerData(summonerName).getMatchList().size() <= request.getRequestStop()){
				try{
					getSummonerData(summonerName)
						.addMatch(
							getMatch(
								matchReferences
									.getMatches()
										.get(
											getSummonerData(summonerName)
												.getMatchList()
													.size()+1)			
															.getGameId() ) );
					log("RiotApiHandler: [" + Thread.currentThread().getName() 
											+ "] loaded new match to summoner " 
											+ summonerName + "-> " 
											+ getSummonerData(summonerName).getMatchList().get(getSummonerData(summonerName).getMatchList().size()-1));
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch(Exception e){
					e.printStackTrace();
				}
			}
	}
	
	private String threadName(){
		return "[" + Thread.currentThread().getName() + "]";
	}
	
	public SummonerData getSummonerData(String key){
		return ServerRedux.getSummonerDataStorage().get(key);
	}
	
	public SummonerData getLocalSummonerData(){ return summonerData; }
}