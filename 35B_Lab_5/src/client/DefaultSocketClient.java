package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import debug.Debuggable;

public class DefaultSocketClient implements Debuggable, Runnable, SocketClientInterface, SocketClientConstants {

	/////////////////////////////////////////
	// INSTANCE VARIABLES
	protected Socket sock;
	protected BufferedReader fromServer;
	protected BufferedWriter toServer;
	protected String strHost;
	protected int iPort;

	/////////////////////////////////////////
	// CONSTRUCTOR
	public DefaultSocketClient(String strHost, int iPort) {
		setPort(iPort);
		setHost(strHost);
	}

	/////////////////////////////////////////
	// GETTERS / SETTERS
	public void setHost(String strHost) {
		this.strHost = strHost;
	}

	public void setPort(int iPort) {
		this.iPort = iPort;
	}

	/////////////////////////////////////////
	// METHODS
	public void run() {
		if (openConnection()) {
			handleSession();
			closeSession();
		}
	}

	public boolean openConnection() {

		try {
			sock = new Socket(strHost, iPort);
		} catch (IOException socketError) {
			if (DEBUG)
				System.err.println("Unable to connect to " + strHost);
			return false;
		}
		try {
			fromServer = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			toServer = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
		} catch (Exception e) {
			if (DEBUG)
				System.err.println("Unable to obtain stream to/from " + strHost);
			return false;
		}
		return true;
	}

	public void handleSession() {
		String strInput = "";
		if (DEBUG)
			System.out.println("Handling session with " + strHost + ":" + iPort);
		try {
			while ((strInput = fromServer.readLine()) != null)
				handleInput(strInput);
		} catch (IOException e) {
			if (DEBUG)
				System.out.println("Handling session with " + strHost + ":" + iPort);
		}
	}

	public void sendOutput(String strOutput) {
		try {
			toServer.write(strOutput, 0, strOutput.length());
		} catch (IOException e) {
			if (DEBUG)
				System.out.println("Error writing to " + strHost);
		}
	}

	public void handleInput(String strInput) {
		System.out.println(strInput);
	}

	public void closeSession() {
		try {
			toServer.close();
			fromServer.close();
			sock.close();
		} catch (IOException e) {
			if (DEBUG)
				System.err.println("Error closing socket to " + strHost);
		}
	}
}
