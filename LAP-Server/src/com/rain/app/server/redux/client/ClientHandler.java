package com.rain.app.server.redux.client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.rain.app.server.redux.RiotApiHandler;
import com.rain.app.server.redux.SummonerData;
import com.rain.app.server.redux.dto.AnalysisDTO;
import com.rain.app.server.redux.dto.MatchDTO;
import com.rain.app.server.redux.dto.ProfileDTO;
import com.rain.app.server.redux.dto.ResponseDTO;
import com.rain.app.service.riot.constant.Platform;

public class ClientHandler extends Thread {
	private Socket s;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private RiotApiHandler riotApiHandler;
	private long timeStamp;
	private String requestedName;
	private Request requestFromClient;
	
	protected SummonerData mSummoner;
	
	private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
//public constructor	
	public ClientHandler(Socket s) throws IOException{
		log("ClientHandler: Client accepted");
		this.s = 	s;
		this.in = 	new ObjectInputStream( 	new BufferedInputStream(	s.getInputStream()));
		this.out = new ObjectOutputStream(	new BufferedOutputStream(	s.getOutputStream()));
		this.timeStamp = System.currentTimeMillis();
		this.riotApiHandler = null;
		this.requestFromClient = null;
	}
	
	@Override
	public void run(){
		beginLog();
		try{
			getRequestFromClient();
			setupRiotApiHandler();
			ResponseDTO responseForClient = processRequest();
			respondToClient(responseForClient);
			in.close(); out.close(); s.close();
		} catch(ClassNotFoundException e){
			e.printStackTrace();
		} catch(SocketException e){
			e.printStackTrace();
		} catch(IOException e){
			e.printStackTrace();
		} finally{
			endLog();
		}
	}
	
//private accessors/mutators
	private void getRequestFromClient() throws ClassNotFoundException, SocketException, IOException{
		requestFromClient = (Request) in.readObject();
		log("ClientHandler: " + requestFromClient);
		requestedName = requestFromClient.getSummonerName();
		requestFromClient.setSummonerName(requestedName.trim().replaceAll(" ", "").toLowerCase());
	}
	
	private void setupRiotApiHandler(){
		riotApiHandler = new RiotApiHandler(requestedName, Platform.NA);
	}
	
	private void beginLog(){
		log(Level.FINE, getDate() + " ClientHandler: " + Thread.currentThread().getName() + " is started as ClientHandler.");
	}
	
	private void endLog(){
		log("ClientHandler: Time elapsed -> " + getTime());
		log(Level.FINE, getDate() + " ClientHandler: " + Thread.currentThread().getName() + ", Data Exchange Finished and thread closing." );
	}
	
	private String getTime(){
		return String.format("%,.2f sec.", (((double)System.currentTimeMillis() - (double) timeStamp)/1000.0));
	}
	
	private ResponseDTO processRequest(){
		log("ClientHandler: Processing client request...");
		ResponseDTO responseToClient = null;
		if(requestFromClient.getType() == RequestType.GET){
			responseToClient = new ResponseDTO();
			responseToClient.setMatch(getMatchHistory());
			responseToClient.setAnalysis(getAnalysis());
			responseToClient.setProfile(getProfile());
		} else if(requestFromClient.getType() == RequestType.POST){
			sendFeedback();
		} return responseToClient;	
	}
	
	private MatchDTO getMatchHistory(){
		log("ClientHandler: Attempting to get match history...");
		return riotApiHandler.getSummonerData(requestFromClient.getSummonerName()).getMatchHistory(requestFromClient);
	}
	
	private AnalysisDTO getAnalysis(){
		log("ClientHandler: Attempting to get analysis...");
		return riotApiHandler.getSummonerData(requestFromClient.getSummonerName()).getAnalysis(requestFromClient);
	}

	private ProfileDTO getProfile(){
		log("ClientHandler: Attempting to get profile data...");
		return riotApiHandler.getSummonerData(requestFromClient.getSummonerName()).getProfile(requestFromClient);
	}
	
	private List<String> sendFeedback(){
		try{
			String limit = in.readUTF();
			int numberOfLines = Integer.parseInt(limit);
			List<String> feedback = new ArrayList<>(20);
			for(int i = 0; i<numberOfLines; i++){
				String line = in.readUTF();
				feedback.add(line);
			} Path feedbackFilePath = Paths.get("feedback\\" + getDate().replace(":", "-").replace(" ", "-")+".txt");
			Files.write(feedbackFilePath, feedback, Charset.forName("UTF-8"));
			log("ClientHandler: Feedback has been successfully written to file.");
		} catch(IOException e){
			e.printStackTrace();
		} return null;
	}

	private void respondToClient(ResponseDTO response) throws IOException {
		log("ClientHandler: Succesfully retrieved data for response.");
		log("ClientHandler: Responding to client with response...");
		//log("ClientHandler: \n" + ServerUtilities.printList(response));
		out.writeObject(response);
		log("ClientHandler: Response to client succesfully transmitted.");
	}
	
	private String getDate(){
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();					
	    String strDate = sdfDate.format(now);	
		strDate = "[" + strDate + "]";
		return strDate;
	}
	
	private static void log(String msg){
		LOGGER.log(Level.INFO, msg);
	}
	
	private static void log(Level level, String msg){
		LOGGER.log(level, msg);
	}
	
	@SuppressWarnings("unused")
	private static void broadcast(String msg){
		System.out.println("ClientHandler: " + Thread.currentThread().getName() + ", MSG: " + msg);
	}
}
