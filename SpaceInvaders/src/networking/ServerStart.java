package networking;

public class ServerStart {
	public static void main(String [] args) {
		Server server = new Server();
		new Thread(server).start();
	}
}
