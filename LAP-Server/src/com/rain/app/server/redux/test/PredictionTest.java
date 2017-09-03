/**
 * 
 */
package com.rain.app.server.redux.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.rain.app.server.redux.RiotApiHandler;
import com.rain.app.server.redux.ServerRedux;
import com.rain.app.server.redux.client.Request;
import com.rain.app.server.redux.client.RequestType;
import com.rain.app.server.redux.dto.ResponseDTO;
import com.rain.app.server.redux.gui.MainWindow;
import com.rain.app.server.redux.logger.MyLogger;
import com.rain.app.service.riot.constant.Platform;

/**
 * @author Ryan May
 *
 */
public class PredictionTest {
	
	RiotApiHandler rHandler = null;
	static final String KEY = "theraingoddess";
	List<PredictionMatch> matches = new ArrayList<>();
	
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	/**
	 * 
	 */
	public PredictionTest() {
		rHandler = new RiotApiHandler("theraingoddess", Platform.NA);
	}

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		LOGGER.log(Level.INFO, "Begining Prediction Test...");
		MyLogger.setup();
		promptApiKey();
		ServerRedux.setChampionTranslationList(ServerRedux.setupChampionDataList());
		
		Executors.newCachedThreadPool().submit(() -> {
			MainWindow gui = new MainWindow();
			gui.begin(new String[0]);
		});
		
		new PredictionTest().begin();
	}
	
	private static void promptApiKey(){
		System.out.println("Please Input Api Key for this session...");
		Scanner kb = new Scanner(System.in);
		ServerRedux.API_KEY = kb.nextLine().trim();
		System.out.println(ServerRedux.API_KEY);
		kb.close();
		System.out.println("Thank you Hooman for your input.");
	}
	
	public void begin(){
		try{
			ResponseDTO data = collectData();
			writeToDisk(data);
			setupPredictionMatches(data);
			runSimplePrediction();
			setupRankedPerformanceScores();
			
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
//private a / m
	private ResponseDTO collectData() throws Exception{
		ResponseDTO response = new ResponseDTO(KEY, rHandler.getSummonerData(KEY).getSummonerId());
		Request request = new Request(RequestType.GET)
				.setRequestStart(0)
					.setRequestStop(20)
						.setSummonerName(KEY);
		if(!ServerRedux.getSummonerDataStorage().containsKey(KEY)){
			
			LOGGER.log(Level.INFO, "PredictionTest: [" + Thread.currentThread().getName() + "] Sending request to RiotApiHandler now...");
			rHandler.prepareRequest(request);
			
			//response.setAnalysis(rHandler.getSummonerData(KEY).getAnalysis(request));
			response.setMatch(rHandler.getSummonerData(KEY).getMatchHistory(request));
			return response;
		} else{
			rHandler.prepareRequest(request);
			response.setMatch(ServerRedux.getSummonerDataStorage().get(KEY).getMatchHistory(request));
			return response;
		}
		
		
	}
	
	private void writeToDisk(ResponseDTO data) throws FileNotFoundException, IOException{
		File out = new File(ServerRedux.getDATA_DIR());
		if(!new File(ServerRedux.getDATA_DIR() + KEY + ".data").exists()){
			out.mkdirs();
			ObjectOutputStream o_out = new ObjectOutputStream(new FileOutputStream(ServerRedux.getDATA_DIR() + KEY + ".data"));
			o_out.writeObject(data);
			o_out.close();
		}
	}
	
	private void setupPredictionMatches(ResponseDTO data){
		for(int i = 0; i < data.getMatch().getMatches().size(); i++){
			matches.add(new PredictionMatch(data.getMatch().getMatches().get(i), data.getMatch().getMatchReferences().getMatches().get(i)));
		}
	}
	
	private void runSimplePrediction(){
		int predictionCorrectCount = 0;
		for(int i = 0; i < matches.size(); i++){
			//System.out.println("i: " + i + " " + matches.get(i) + "\n");
			if(matches.get(i).getVictor() == matches.get(i).getPredictedVictor())
				predictionCorrectCount++;
		} System.out.println(String.format("SimplePrediction was correct %.2f%% of the time.", ((double) predictionCorrectCount / matches.size() * 100.0)));
	}
	
	private void setupRankedPerformanceScores(){
		List<RankedPerformanceScore> scores = calculateRankedPerformanceScores();
		record(scores);
	}
	
	private List<RankedPerformanceScore> calculateRankedPerformanceScores(){
		List<RankedPerformanceScore> scores = new ArrayList<>();
		for(int i = 0; i < matches.size(); i++){
			scores.addAll(matches.get(i).calculateRankedPerformanceScores());
		} return scores;
	}
	
	private void record(List<RankedPerformanceScore> scores){
		PrintWriter pw = null;
		try{
			System.out.println("Recording results...");
			File logFile = new File("test\\logFile.log");
			if(!logFile.exists())
				logFile.createNewFile();
			
			pw = new PrintWriter(new BufferedWriter( new FileWriter(logFile) ));
			pw.println("[" + new Date().toString() + "]" + " Ranked Performance Scores:");
			for(int i = 0; i < scores.size(); i++){
				pw.println(scores.get(i).getValue());
			}
			pw.close();
			System.out.println("Done.");
		} catch(IOException e){
			e.printStackTrace();
		} pw.close();
	}
	
//non-private a / m

}
