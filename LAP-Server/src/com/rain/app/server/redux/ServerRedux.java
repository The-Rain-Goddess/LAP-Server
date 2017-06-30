package com.rain.app.server.redux;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.rain.app.server.redux.client.ClientHandler;
import com.rain.app.server.redux.dto.ResponseDTO;
import com.rain.app.server.redux.gui.GUIEvent;
import com.rain.app.server.redux.gui.MainWindow;
import com.rain.app.server.redux.logger.MyLogger;
import com.rain.app.server.redux.test.PredictionTest;
import com.rain.app.service.riot.api.ApiConfig;
import com.rain.app.service.riot.api.RiotApi;
import com.rain.app.service.riot.api.RiotApiException;
import com.rain.app.service.riot.api.endpoints.constants.dto.Champion;
import com.rain.app.service.riot.api.endpoints.constants.dto.ChampionList;
import com.rain.app.service.riot.constant.Platform;

public class ServerRedux {
//Package Varriables	
	private static final Map<String, SummonerData> summonerDataStorage = new MapListener<String, SummonerData>();
	private static final ExecutorService dataRetrievalPool = new ThreadPoolExecutor(1, 100, 10000L, TimeUnit.MILLISECONDS, new RateLimitingQueue(5000, 8, 10000L, 1));
	private static ExecutorService clientHandlerService;
	private static ChampionList championTranslationList;
	private static boolean running = true;
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private final int PORT_NUMBER = 48868;
	private static ServerSocket ss = null;
	private static MainWindow gui = null;
	private static String DATA_DIR = "C:\\LAP-Server\\";
	
//MAIN	
	public static void main(String[] args) throws IOException{
		MyLogger.setup();
		new ServerRedux().start();
	}

//constructor	
	public ServerRedux() throws IOException{
		log("Server: " + threadName() + " Server Started");
		clientHandlerService = null;
	}
	
	private void start(){
		try{
			log("Server: " + threadName() + " is started.");
			setupExecutors();
			setupData();
			setupServerSocket();
			
			setupTestThread();
			while(running){
				handleClients();
			} shutdownExecutors();
			suspend(5000L);
		} catch(SocketException e){
			System.out.println("Server Socket was closed.");
		} catch(FileNotFoundException e){
			e.printStackTrace();
		} catch(IOException e){
			e.printStackTrace();
		} catch(ClassNotFoundException e){
			e.printStackTrace();
		} catch(Exception e){
			e.printStackTrace();
		} finally{
			shutdownServer();
		}
	}
	
	private void setupExecutors(){
		clientHandlerService = Executors.newCachedThreadPool();
		
		clientHandlerService.submit(() -> {
			gui = new MainWindow();
			gui.begin(new String[0]);
		});
	}
	
	private void setupData() throws FileNotFoundException, ClassNotFoundException, IOException{
		setupChampionData();
		loadSummonerData();
	}
	
	private void setupChampionData(){
		log("Server: " + threadName() + " Loading Champion translation list...");
		championTranslationList = setupChampionDataList();
		log("Server: " + threadName() + " Champion translation list succesfully retrieved.");
	}
	
	private void loadSummonerData() throws FileNotFoundException, IOException, ClassNotFoundException{
		File home = new File(DATA_DIR);
		if(!home.exists())
			home.mkdirs();
		List<File> data = getDataFilesFromDataDir();
		if(data!=null){
			for(File file : data){
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
				SummonerData summoner = new SummonerData((ResponseDTO) ois.readObject());
				System.out.println("New media read from disk: \n" + summoner);
				log("Server: " + threadName() + " New data read from disk: \n" + summoner);
				summonerDataStorage.put(summoner.getSummonerName(), summoner);
				ois.close();
			}
		}
	}
	
	private List<File> getDataFilesFromDataDir(){
		File mainDirectory = new File(DATA_DIR);
		File[] files = mainDirectory.listFiles();
		List<File> dataDir = Arrays.asList(files);
		List<File> allDataFiles = new ArrayList<>();
		for (int i = 0; i < dataDir.size(); i++) {
			if(dataDir.get(i).getAbsolutePath().contains(".data"))
				allDataFiles.add(dataDir.get(i));
		} System.out.println("Files to be read into ram : \n" + allDataFiles); 
		log("Server: " + threadName() + " Files to be read into ram : \n" + allDataFiles);
		return allDataFiles;
	}
	
	private void setupServerSocket() throws IOException{
		ss = new ServerSocket(PORT_NUMBER);
		System.out.println("Server Started");
		log("Server: " + threadName() + " Server Started");
	}
	
	private void setupTestThread(){
		clientHandlerService.submit(() -> {
			new PredictionTest().begin();
		});
	}
	
	private void handleClients() throws SocketException, IOException{
		Socket serviceSocket = ss.accept();
		log("Server: " + threadName() + " Accepted from " + serviceSocket.getInetAddress());
		ClientHandler c = new ClientHandler(serviceSocket);
		clientHandlerService.submit(c);
	}
	
	private void shutdownExecutors(){
		clientHandlerService.shutdown();
	}
	
	private void suspend(long millis){
		try{
			Thread.sleep(millis);
		} catch(InterruptedException e){
			e.printStackTrace();
		}
	}
	
	private void shutdownServer(){
		log("Server: " + threadName() + " Server shutting down");
		clientHandlerService.shutdownNow();
		System.out.println("Server Ended");
		javafx.application.Platform.exit();
		System.exit(0);
	}

//private a/m	
	private String threadName(){
		return "[" + Thread.currentThread().getName() + "]";
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
	public static ChampionList setupChampionDataList(){
		try {
			log("Server: " + "[" + Thread.currentThread().getName() + "]" + " Retrieving champion translation list...");
			return (new RiotApi(new ApiConfig().setKey("RGAPI-1E616A6D-EC4A-46F1-A9BE-E1C620EABB05"))).getDataChampionList(Platform.NA);
		} catch (RiotApiException e1) {
			e1.printStackTrace();
			log(Level.SEVERE, "Server: " + "[" + Thread.currentThread().getName() + "]" + " Riot Service unavailable, attempting to get Champion Data List...", e1);
			return setupChampionDataList();
		}
	}
	
	public static void addEventToGUI(GUIEvent e){
		try{
			clientHandlerService.submit(()-> {
				e.execute();
			});
		} catch(Exception x){
			x.printStackTrace();
		}
	}
	
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
	
	public static MainWindow getGUI(){ return gui; }
	
	public static ChampionList getChampionTranslationList(){ return championTranslationList; }
	
	public static Map<String, SummonerData> getSummonerDataStorage(){ return summonerDataStorage; }
	
	public static ExecutorService getDataRetrievalPool(){ return dataRetrievalPool; }
	
	public static void setChampionTranslationList(ChampionList c){ championTranslationList = c; } 
	
	public static boolean isRunning(){ return running; }
	
	public static void stopRunning(){ 
		running = false;
		try {
			if(ss!=null){
				ss.close();
			}
		} catch(SocketException e){
			System.out.println("Socket Closed.");
			log("Server: " + "[" + Thread.currentThread().getName() + "]" + " ServerSocket forcibly closed.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return the dATA_DIR
	 */
	public static String getDATA_DIR() {
		return DATA_DIR;
	}

	/**
	 * @param dATA_DIR the dATA_DIR to set
	 */
	public static void setDATA_DIR(String dATA_DIR) {
		DATA_DIR = dATA_DIR;
	}
}