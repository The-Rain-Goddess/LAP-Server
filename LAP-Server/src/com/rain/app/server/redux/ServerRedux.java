package com.rain.app.server.redux;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.rain.app.server.redux.gui.MainWindow;
import com.rain.app.server.redux.logger.MyLogger;
import com.rain.app.service.riot.api.ApiConfig;
import com.rain.app.service.riot.api.RiotApi;
import com.rain.app.service.riot.api.RiotApiException;
import com.rain.app.service.riot.api.endpoints.constants.dto.Champion;
import com.rain.app.service.riot.api.endpoints.constants.dto.ChampionList;
import com.rain.app.service.riot.constant.Platform;

public class ServerRedux {
//Package Varriables	
	private static final Map<String, SummonerData> summonerDataStorage = Collections.synchronizedSortedMap(new TreeMap<String, SummonerData>());
	private static final ExecutorService dataRetrievalPool = new ThreadPoolExecutor(1, 100, 10000L, TimeUnit.MILLISECONDS, new RateLimitingQueue(5000, 8, 10000L, 1));
	private static ChampionList championTranslationList;
	private static boolean running = true;
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private final int PORT_NUMBER = 48868;
	private static ServerSocket ss = null;
	
//MAIN	
	public static void main(String[] args) throws IOException{
		MyLogger.setup();
		new ServerRedux();
		
		
	}

//constructor	
	public ServerRedux() throws IOException{
		championTranslationList = getChampionDataList();
		log("Server: Champion translation list succesfully retrieved.");
		log("Server: Server Started");
		ExecutorService executor = null;
		
		try{
			log("Server: [" + Thread.currentThread().getName() + "] is started.");
			executor = Executors.newCachedThreadPool();
			
			executor.submit(() -> {
				new MainWindow().begin(new String[0]);
			});	
			
			ss = new ServerSocket(PORT_NUMBER);
			System.out.println("Server Started");
			while(running){
				Socket serviceSocket = ss.accept();
				log("Server: Accepted from " + serviceSocket.getInetAddress());
				ClientHandler c = new ClientHandler(serviceSocket);
				executor.submit(c);
			} 
			
			executor.shutdown();
			Thread.sleep(5000L);
		} catch(SocketException e){
			System.out.println("Server Socket was closed.");
		} catch(IOException e){
			e.printStackTrace();
		} catch(InterruptedException e){
			System.out.println("Thread " + Thread.currentThread().getName() + " was interrupted.");
			e.printStackTrace();
		} finally{
			log("Server: Server shutting down");
			executor.shutdownNow();
			System.out.println("Server Ended");
			javafx.application.Platform.exit();
			//System.exit(0);
		}
	}

//private a/m	
	private static ChampionList getChampionDataList(){
		try {
			log("Server: Retrieving champion translation list...");
			return (new RiotApi(new ApiConfig().setKey("RGAPI-1E616A6D-EC4A-46F1-A9BE-E1C620EABB05"))).getDataChampionList(Platform.NA);
		} catch (RiotApiException e1) {
			e1.printStackTrace();
			log(Level.SEVERE, "Server: Riot Service unavailable, attempting to get Champion Data List.", e1);
			return getChampionDataList();
		}
	}
	
	private static void log(String msg){
		LOGGER.log(Level.INFO, msg);
	}
	
	@SuppressWarnings("unused")
	private static void log(Level level, String msg){
		LOGGER.log(level, msg);
	}
	
	private static void log(Level level, String msg, Exception e){
		LOGGER.log(level, msg, e);
	}	
	
//non-private a/m
	public static String getChampionNameFromId(long champId){
		for(Map.Entry<String, Champion> entry : new ArrayList<>(championTranslationList.getData().entrySet()))
			if(entry.getValue().getId() == champId)
				return entry.getValue().getName();
		return null;
	}
	
	public static List<SummonerData> getSummonerDataStorageAsList(){
		List<SummonerData> data = new ArrayList<>();
		for(Map.Entry<String, SummonerData> entry : summonerDataStorage.entrySet()){
			data.add(entry.getValue());
		} return data;
	}
	
	public static ChampionList getChampionTranslationList(){ return championTranslationList; }
	
	public static Map<String, SummonerData> getSummonerDataStorage(){ return summonerDataStorage; }
	
	public static ExecutorService getDataRetrievalPool(){ return dataRetrievalPool; }
	
	public static boolean isRunning(){ return running; }
	
	public static void stopRunning(){ 
		running = false;
		try {
			ss.close();
		} catch(SocketException e){
			System.out.println("Socket Closed.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}