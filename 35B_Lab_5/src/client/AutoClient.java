package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

import model.Automobile;
import model.Properties;
import util.FileIO;

public class AutoClient extends DefaultSocketClient  implements SocketClientInterface {

	/////////////////////////////////////////
	// INSTANCE VARIABLES
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private Scanner stdInput;
	private String input, output;
	private Properties props;
	private Automobile auto;
	private CarModelOptionsIO autoTool;
	private boolean connected = false;

	/////////////////////////////////////////
	// CONSTRUCTOR
	public AutoClient(String strHost, int iPort) {
		super(strHost, iPort);
		autoTool = new CarModelOptionsIO();
	}

	/////////////////////////////////////////
	// GETTERS / SETTERS

	/////////////////////////////////////////
	// METHODS
	public void run() {
		if (openConnection()) {
			handleSession();
			closeSession();
		}
	}

	public boolean openConnection() {
		if (DEBUG)
			System.out.println("C: Inside openConnection");
		
		try {
			sock = new Socket(strHost, iPort);
			System.out.println("C: created socket");
		} catch (IOException socketError) {
			System.out.println("C: Unable to connect to " + strHost);
			return false;
		}

		connected = true;

		try {
			out = new ObjectOutputStream(sock.getOutputStream());
			in = new ObjectInputStream(sock.getInputStream());
			
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Unable to obtain stream to/from " + strHost);
			return false;
		}
		
		return true;
	}

	public void sendOutput(Properties prop) {

	}

	public void handleSession() {
		if (DEBUG)
			System.out.println("C: Inside handleSession");
		
		stdInput = new Scanner(System.in);
		input = "";
		System.out.println("C: Creating session with " + strHost + " : " + iPort);
		try {
			drawMenu();
			input = stdInput.nextLine();
			
			while (connected) {
		
				if (input.equals("1")) {
					System.out.print("C: Please provide path to Properties file\n     ----> ");
					input = stdInput.nextLine();
					
					try {
						props = new FileIO().readProperties(input);
					} catch (Exception e) {}
					
					if (props != null) {
						System.out.println("C: Sending properties file");
						out.writeObject("inputProps");
						out.flush();
						out.writeObject(props);
						out.flush();
						input = (String) in.readObject();		// receives status about server's construction of auto from props
						System.out.println("C: " + input);
					} else {
						System.out.println("C: Error creating Properties object from file path.");
						System.out.println("C: Try again. \n\n");
					}
				}
				
				
				else if (input.equals("2")) {
					out.writeObject("getList");
					out.flush();
					input = (String) in.readObject();
					String[] carArray = input.split("#");
					for(int i = 0; i < carArray.length; i++) {
						System.out.println("\nC:====================================");
						System.out.println("C:\t" + carArray[i]);
					}
					System.out.println("\nC: Please copy and paste your selected choice (ignore price)");
					System.out.print("C: EX) make model year \n     ----> ");
					output = stdInput.nextLine();
					out.writeObject(output);
					out.flush();
					
					auto = (Automobile) in.readObject();
					System.out.println("C: Successfully received " + auto.getMakeModelYear());

					autoTool.selectAutoChoices(auto);
					
					auto.printChoices();
						
					
				}
				
				else if (input.equals("3")) {
					this.disconnect();
					break;
				}
				
				else {
					System.out.println("C: Invalid input. Try again \n\n");
				}
				
				drawMenu();
				input = stdInput.nextLine();
				
			}
			
		} catch (Exception e) {
			System.out.println("C: Error handling session");

		}
	}

	public void handleInput(Automobile auto) {

	}

	public void closeSession() {
		try {
			toServer = null;
			fromServer = null;
			out.close();
			sock.close();
		} catch (IOException e) {
			if (DEBUG)
				System.err.println("Error closing socket to " + strHost);
		}
	}
	
	
	public void drawMenu() {
		System.out.println("\nC: //////////////////////////////////////////////");
		System.out.println("C: ==============================================");
		System.out.println("C:            Please select an option ");
		System.out.println("C: ");
		System.out.println("C:      1) Upload Properties file  ");
		System.out.println("C:      2) Browse Server Catalogue  ");
		System.out.println("C:      3) Quit");
		System.out.println("C: ");
		System.out.print  ("C:      ----> ");
	}

	public void disconnect() {
		output = "quit";
		
		try {
			out.writeObject(output);
			out.flush();
			input = (String) in.readObject();
			System.out.println("C: Goodbye!");
//			out.writeObject("bye");
//			out.flush();
			input = null;
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
			
		connected = false;
	}
	
	
	public static void main(String[] args) {		

		/////////////////////////////////////////
		// SERVER
		int PORT = 8010;
		
		AutoClient client = new AutoClient("localhost", PORT);
		Thread z2 = new Thread(client);
		z2.start();
	}
}
