package edu.uel.cw.simplechatclient.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClientSocket {
	
	private static final String HOST_NAME = "localhost";
	private static final int PORT = 7070;
	private Socket sc;
	private PrintWriter pw;
	BufferedReader bf;
	
	public ChatClientSocket() throws Exception {
		this(HOST_NAME, PORT);
	}
	
	public ChatClientSocket(String hostName, int portNumber) throws Exception {
		sc = new Socket(hostName, portNumber);
		pw = new PrintWriter(sc.getOutputStream(), true);
		bf = new BufferedReader(new InputStreamReader(sc.getInputStream()));
	}
	
	public void sendMessage(String message) {
		pw.println(message);
	}
	
	public String getMessage() throws IOException {
		return bf.readLine();
	}
}
