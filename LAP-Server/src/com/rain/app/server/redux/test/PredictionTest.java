/**
 * 
 */
package com.rain.app.server.redux.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
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
		ServerRedux.setChampionTranslationList(ServerRedux.setupChampionDataList());
		
		Executors.newCachedThreadPool().submit(() -> {
			MainWindow gui = new MainWindow();
			gui.begin(new String[0]);
		});
		
		new PredictionTest().begin();
	}
	
	public void begin(){
		try{
			ResponseDTO data = collectData();
			writeToDisk(data);
			List<PredictionMatch> matches = new ArrayList<>();
			for(int i = 0; i < data.getMatch().getMatches().size(); i++){
				matches.add(new PredictionMatch(data.getMatch().getMatches().get(i), data.getMatch().getMatchReferences().get(i)));
			}
			
			for(int i = 0; i<matches.size(); i++){
				System.out.println("i: " + i + " " + matches.get(i) + "\n");
			}
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
//private a / m
	private ResponseDTO collectData() throws Exception{
		ResponseDTO response = new ResponseDTO(KEY, rHandler.getSummonerData(KEY).getSummonerId());
		Request request = new Request(RequestType.GET)
							.setRequestStart(0)
								.setRequestStop(320)
									.setSummonerName(KEY);
		LOGGER.log(Level.INFO, "PredictionTest: [" + Thread.currentThread().getName() + "] Sending request to RiotApiHandler now...");
		rHandler.prepareRequest(request);
		
		//response.setAnalysis(rHandler.getSummonerData(KEY).getAnalysis(request));
		response.setMatch(rHandler.getSummonerData(KEY).getMatchHistory(request));
		
		return response;
	}
	
	private void writeToDisk(ResponseDTO data) throws FileNotFoundException, IOException{
		File out = new File(ServerRedux.getDATA_DIR());
		out.mkdirs();
		ObjectOutputStream o_out = new ObjectOutputStream(new FileOutputStream(ServerRedux.getDATA_DIR() + KEY + ".data"));
		o_out.writeObject(data);
		o_out.close();
	}
	
//non-private a / m

}
