package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Driver {

	
	public static void main(String[] args) {		

		/////////////////////////////////////////
		// SERVER
		int PORT = 8010;
		
	//	BuildCarModelOptions build = new BuildCarModelOptions();
		
	//	build = new BuildCarModelOptions();
	//	build.loadAutos();
	
		System.out.println("S: Starting server");
		
		try {
			ServerSocket serverSocket = new ServerSocket(PORT);
			while (true) {
				Socket socket = serverSocket.accept();
				System.out.println("S: Serversocket successfully created");
				new Thread(new Server(socket)).start();
			}
		} catch(IOException e) {
			System.out.println("Could not listen on port: " + PORT);
			e.printStackTrace();
		}
		
		
	}

}
