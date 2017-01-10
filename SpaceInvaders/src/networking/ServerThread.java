package networking;
import java.io.EOFException;
import java.io.IOException;
import java.net.SocketException;

import model.GameCharacter;

public class ServerThread implements Runnable {
	GamePacket packet;
	Server server;
	String gameId;
	
	public ServerThread(GamePacket packet, Server server) {
		this.packet = packet;
		this.server = server;
	}
	
	@Override
	public void run() {
		try {
			GameCharacter character = null;

			// Read data until no more incoming data
			while (true) {
				try {
					// If something comes in from client A, send out to clients !A
					character = (GameCharacter) packet.in.readObject();
					synchronized (server.characters) {
						server.characters.put(character.getGuid(), character);
						packet.out.reset();
						packet.out.writeObject(server.characters);
					}
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (EOFException e) {
					break;
				} catch (SocketException e) {
					break;
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
