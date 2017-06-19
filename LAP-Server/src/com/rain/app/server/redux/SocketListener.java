package com.rain.app.server.redux;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketListener implements Runnable {
	private ServerSocket ss;
	private Socket serviceSocket = null;
	
	public SocketListener(int portNumber) throws IOException {
		ss = new ServerSocket(portNumber);
	}

	@Override
	public void run() {
		while(true){
			try {
				serviceSocket = ss.accept();
				this.notifyAll();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void close() throws IOException{ ss.close();}
	
	public Socket getServiceSocket(){ return serviceSocket; }
	
	public void resetServiceSocket(){ serviceSocket = null; }

}
