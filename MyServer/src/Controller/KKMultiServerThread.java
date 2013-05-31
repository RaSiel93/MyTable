package Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;

public class KKMultiServerThread extends Thread {
	private Socket socket = null;
	private ControllerServer controller;

	public KKMultiServerThread(ControllerServer controller, Socket socket) {
		super("KKMultiServerThread");
		this.controller = controller;
		this.socket = socket;
	}

	public void run() {

		try {
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
		
//			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
//			BufferedReader in = new BufferedReader(new InputStreamReader(
//					socket.getInputStream()));

			//Object inputLine = null;
			//Object outputLine = null;
			Protocol protocol = new Protocol(controller);
			//outputLine = protocol.processInput(null);
			//out.println(outputLine);

			while (true) {
				//outputLine = inputLine);
				out.writeObject(protocol.execute(in.readObject()));
				//out.close();
				//in.close();
				//socket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}