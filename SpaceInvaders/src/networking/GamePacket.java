package networking;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class GamePacket {
	Socket socket;
	ObjectOutputStream out;
	ObjectInputStream in;

	public GamePacket(Socket socket, ObjectOutputStream out, ObjectInputStream in) {
		this.socket = socket;
		this.out = out;
		this.in = in;
	}
}
