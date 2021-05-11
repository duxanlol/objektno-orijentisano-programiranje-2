import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Server {
	
	private ServerSocket serverSocket;
	private int CONNECTION_PORT = 4000;
	private Socket connectionSocket;
	private final String pathToWordlist =  "wordlist.txt";
	private Scanner reader;
	private ArrayList<String> wordlist;
	private Random random;
	public Server() {
		initServer();
	}
	
	private String getRandomWord() {
		return wordlist.get(random.nextInt(wordlist.size()));
	}
	
	private boolean initWordlist() {
		Scanner sc = null;
		random = new Random();
		try {
			sc = new Scanner(new File(pathToWordlist));
			while (sc.hasNextLine()) {
				String[] line = sc.nextLine().split(",");
				wordlist =  new ArrayList<String>(Arrays.asList(line));
			}
			sc.close();
			return true;
		} catch (FileNotFoundException e1) {
			System.out.println("Error loading wordlist, exiting application.");
			e1.printStackTrace();
			System.exit(1);
			return false; //Nema smisla jer je pre toga exit ali drugacije nece da kompajlira.
		}
	}
	private boolean initServer() {
		reader = new Scanner(System.in);
		System.out.println("Which port to listen on? [enter] default");
		String port = reader.nextLine();
		if (!port.isEmpty())
			CONNECTION_PORT = Integer.parseInt(port);
		
		try {
			serverSocket = new ServerSocket(CONNECTION_PORT);
		} catch (IOException e) {
			System.out.println("Couldnt initialize server socket. Aborting program.");
			e.printStackTrace();
			System.exit(1);
		}
		System.out.println("Server up and running on port: " + this.CONNECTION_PORT);
		initWordlist();

		while (true) {
			try {
				connectionSocket = serverSocket.accept();
			} catch (IOException e) {

			}
			System.out.println("Connection successful: [" + connectionSocket.getInetAddress() + ":"
					+ connectionSocket.getLocalPort() + "]");
			try {
				new Thread(new ServerThread(connectionSocket, new State(getRandomWord()))).start();
				System.out.println("Thread initialized.");
			} catch (Exception e) {
				System.out.println("Couldn't open a thread. Aborting program.");
				e.printStackTrace();
				System.exit(1);
			}
			
		}
	}
}
