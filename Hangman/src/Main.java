import java.util.Scanner;

public class Main {
	
	public static void main(String[] args) {
		Scanner reader = new Scanner(System.in);
		System.out.println("(c)lient or (s)erver?");
		String choice = reader.nextLine();
		if (!choice.isEmpty() && choice.toLowerCase().charAt(0) == 's')
			new Server();
		else
			new Client();
		reader.close();
	}
	
}
