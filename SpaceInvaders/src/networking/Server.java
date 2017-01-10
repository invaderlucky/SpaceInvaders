package networking;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import model.GameCharacter;

public class Server implements Runnable {
	ServerSocket serverSocket;
	HashMap<String, GameCharacter> characters;
	public ArrayList<GamePacket> packets;

	public static final int SERVER_PORT = 7779;

	@Override
	public void run() {
		try {
			serverSocket = new ServerSocket(SERVER_PORT);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		packets = new ArrayList<GamePacket>();
		characters = new HashMap<String, GameCharacter>();

		// Continuously connect to clients
		while (true) {
			try {
				// Accept the new connection
				Socket socket = serverSocket.accept();
				// Set up in and out streams
				ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
				ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

				// Create a packet to hold all connection infol
				GamePacket packet = new GamePacket(socket, out, in);
				// Add the new connection to the list of connections
				packets.add(packet);

				new Thread(new ServerThread(packet, this)).start();
			} catch (IOException e) {

			}
		}
	}
}
