package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import adapter.*;
import model.Automobile;
import model.Properties;

import adapter.BuildAuto;
import debug.Debuggable;

public class Server implements Debuggable, Runnable {

	/////////////////////////////////////////
	// INSTANCE VARIABLES
	private Socket inSock;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private String input, output;
	private Automobile auto;
	private BuildCarModelOptions build = new BuildCarModelOptions();
	private boolean connected;

	/////////////////////////////////////////
	// CONSTRUCTOR
	public Server(Socket clientSocket) {
		inSock = clientSocket;
	}
	

	/////////////////////////////////////////
	// METHODS
	public void run() {
		createConnection();
		handleConnection();
		//closeSession();
	}

	public void createConnection() {
		if (DEBUG)
			System.out.println("S: inside createConnection");
		
		connected = true;
		
		
		try {
			this.in = new ObjectInputStream(inSock.getInputStream());
			this.out = new ObjectOutputStream(inSock.getOutputStream());
			
		} catch (IOException e) {
			System.out.println("S: Could not communicate with client");
		}
		

		System.out.println("S: Client accepted");
	}

	public void handleConnection() {
		
		if (DEBUG)
			System.out.println("S: Inside handleConnection");
		
		if (DEBUG)
			System.out.println("S: Handling session");

		try {
			input = (String) in.readObject();
			System.out.println("S: Client response: " + input);

			
			while (connected) {
				System.out.println("inside while loop ");
				
				System.out.println("S: Client response: " + input);
				
				if (input.equals("inputProps")) {

					// Receives and handles processing of Properties object

					System.out.println("S: Receiving properties file from client");
					Properties props = (Properties) in.readObject();
					
					
					if (build.buildAutoFromProperties(props)) {
						output = (props.getMakeModelYear() + " successfully processed");
						System.out.println("S: " + output);
						out.writeObject(output);
						out.flush();
					} else {
						output = ("Failed to build from " + props.getMakeModelYear());
						System.out.println("S: " + output);
						out.writeObject(output);
						out.flush();
					}
				}

				else if (input.equalsIgnoreCase("getList")) {

					// Sends list of Automobiles and facilitates sending chosen Automobile
					System.out.println("Inside getlist");
					
					
					output = (build.getModelList());
					
					out.writeObject(output);
					out.flush();
					System.out.println("S: Databse sent to client. Waiting for choice");
					input = (String) in.readObject(); // holds name of selected aut0
					
					DataBaseAuto lhm = new BuildAuto();
					auto = lhm.retrieveAuto(input); // expects MMY format, or wont work

					if (auto != null) {
						out.writeObject(auto);
						out.flush();
						System.out.println("S: Sent");
					} else {
						System.out.println("S: Not found in database");
					}
				}

				else if (input.equalsIgnoreCase("quit")) {
					output = "Goodbye!";
					out.writeObject(output);
					out.flush();
					System.out.println("S: Disconnecting client.");
					closeSession();
					connected = false;
					input = null;
					break;
				}

				input = (String) in.readObject();
				System.out.println("S: Client response: " + input);

			}
		} catch (Exception e) {
			System.out.println("S: Error handling session");
			e.printStackTrace();
		}
	}

	public void closeSession() {
		try {
			out.close();
			in.close();
			inSock.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	

}
