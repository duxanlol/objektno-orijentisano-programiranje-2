import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import java.net.Socket;

import java.util.Scanner;

public class Client {

	private Scanner reader;
	private int CONNECTION_PORT = 4000;
	private String HOSTNAME = "localhost";
	private ObjectInputStream in_socket;
	private DataOutputStream out_socket;
	private Socket socket;

	public Client() {
		initClient();
	}

	private void cleanUp() {
		try {
			in_socket.close();
			out_socket.close();
			socket.close();
		} catch (Exception e) {
			
		}
		return;
	}

	private void initClient() {
		reader = new Scanner(System.in);
		System.out.println("Which port to send to? [enter] default");
		String port = reader.nextLine();
		if (!port.isEmpty())
			CONNECTION_PORT = Integer.parseInt(port);
		
		try {
			socket = new Socket(HOSTNAME, CONNECTION_PORT);
			if (socket != null)
				System.out.println("Connection successful to: " + socket.getInetAddress());
			in_socket = new ObjectInputStream(socket.getInputStream());
			out_socket = new DataOutputStream(socket.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		while (!socket.isClosed()) {
			try {
				State state = (State) in_socket.readObject();
				if (state.getLives() != 0 && !state.isVictory()) {
					System.out.println(state);
					System.out.print("Guess a character: ");
					String guess = "";
					while(guess.isEmpty())
						guess = reader.nextLine();
					out_socket.write(guess.charAt(0));
				} else {
					if (state.isVictory())
						System.out.println("Victory!!!");
					else
						System.out.println("Game over.");
					cleanUp();
				}

			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}
}
