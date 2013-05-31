package Main;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.*;

import Controller.Controller;
import Controller.ControllerClient;
import GUI.FrameMain;

public class Client {
	
	private static final class CloseWindowListnerImpl extends WindowAdapter {
		public void windowClosing(WindowEvent e) {
			try {
				controller.closeAll();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			System.exit(0);
		}
	}

	private static ControllerClient controller;
	public static void main(String[] args) throws IOException {
//		try {
//			clientSocket = new Socket("192.168.1.3", 4444);
//			out = new ObjectOutputStream(clientSocket.getOutputStream());
//			in = new ObjectInputStream(clientSocket.getInputStream());
//		} catch (UnknownHostException e) {
//			System.err.println("Don't know about host: taranis.");
//			System.exit(1);
//		} catch (IOException e) {
//			System.err
//					.println("Couldn't get I/O for the connection to: taranis.");
//			System.exit(1);
//		}

		controller = new ControllerClient();
		FrameMain mainFrame = new FrameMain((Controller)controller);
		mainFrame.setTitle("CLIENT");
		mainFrame.setVisible(true);
		controller.setFrame(mainFrame);
		mainFrame.addWindowListener(new CloseWindowListnerImpl());

		/*
		 * while ((fromServer = in.readLine()) != null) {
		 * System.out.println("Server: " + fromServer); if
		 * (fromServer.equals("Bye.")) break;
		 * 
		 * fromUser = buffClient.readLine(); if (fromUser != null) {
		 * System.out.println("Client: " + fromUser); out.println(fromUser); } }
		 */
	}
}