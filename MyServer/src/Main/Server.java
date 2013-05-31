package Main;

import Controller.Controller;
import Controller.ControllerServer;
import Controller.KKMultiServerThread;
import Controller.Protocol;
import GUI.FrameMain;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.*;
import java.sql.SQLException;
import java.io.*;

public class Server {

	private static ControllerServer controller;
	private static Socket clientSocket = null;
	private static ObjectOutputStream out = null;
	private static ObjectInputStream in = null;

	public static void main(String[] args) throws SQLException, Exception {
		controller = new ControllerServer();
		FrameMain mainFrame = new FrameMain((Controller)controller);
		mainFrame.setTitle("SERVER");
		mainFrame.setVisible(true);
		controller.setFrame(mainFrame);
		mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				try {
					//out.close();
					//in.close();
					//clientSocket.close();
					//serverSocket.close();
					controller.closeAll();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				System.exit(0);
			}
		});
		
		ServerSocket serverSocket = null;
		boolean listening = true;
		
		InetAddress ownIP = InetAddress.getLocalHost();
		System.out.println(ownIP.getHostAddress());
		try {
			serverSocket = new ServerSocket(4444);
		} catch (IOException e) {
			System.err.println("Could not listen on port: 4444.");
			System.exit(1);	
		}
		
		
//		try {
//			clientSocket = serverSocket.accept();
//		} catch (IOException e) {
//			System.err.println("Accept failed.");
//			System.exit(1);
//		}
//		out = new ObjectOutputStream(clientSocket.getOutputStream());
//		in = new ObjectInputStream(clientSocket.getInputStream());
		//Protocol protocol = new Protocol(controller);
		while (listening){
	        new KKMultiServerThread(controller, serverSocket.accept()).start();
		}
		
		serverSocket.close();
//		while (true){
//			out.writeObject(protocol.execute(in.readObject()));
//		}
	}
}