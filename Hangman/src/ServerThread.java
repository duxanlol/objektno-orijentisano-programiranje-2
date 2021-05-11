import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerThread implements Runnable {

	private Socket socket;
	private DataInputStream in_socket;
	private ObjectOutputStream out_socket;
	private State state;

	public ServerThread(Socket socket, State state) throws Exception {
		this.socket = socket;
		this.in_socket = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
		this.out_socket = new ObjectOutputStream(socket.getOutputStream());
		this.state = state;
	}

	public void cleanUp() {
		try {
			in_socket.close();
			out_socket.close();
			socket.close();
		} catch (Exception e) {

		}
		System.out.println("Closed thread.");
		return;
	}

	@Override
	public void run() {
		try {
			System.out.println("SeverThread Connection successful from " + socket.getInetAddress().getHostAddress());
			System.out.println(state.getSolution());
			state.solve();
			out_socket.writeObject(state);
			while (!socket.isClosed()) {
				char guess = (char) in_socket.read();
				state.addGuess(guess);
				state.solve();
				if (state.getLives() != 0 && !state.isVictory()) 
					out_socket.writeUnshared(state);
				else {
					state.setCurrentWord("Game over");
					if (state.isVictory())
						state.setCurrentWord("Victory!");
					out_socket.writeUnshared(state);
					cleanUp();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
