import java.net.ServerSocket;
import java.net.Socket;
import java.net.InetAddress;
import java.util.*;
import java.io.*;
import java.lang.*;

/**
 * When it receives ”LINE” followed by a new line, it should
 * reply with the next line of the file, followed by a new line
 * – except that the reply should be in all UPPERCASE. With each
 * call, it should work its way down the file. If it gets to the
 * bottom, it should start over at the top. The path to the text
 * file should be the first command-line argument. The port number
 * should be the second, e.g. ”catserver /var/datavol/sample.txt 2000”.
 */
public class CatServer {
    public static void main(String args[]) throws IOException {
        if (args.length != 2) {
            System.out.println("Wrong number of arguments!");
            System.exit(1);
        }

        // Retrieve the file name and port number.
        String file = args[0];
        int portNumber = Integer.parseInt(args[1]);

        // Read all the lines and store them in an arraylist.
        Scanner fileScanner = new Scanner(new File(file));
        ArrayList<String> fileLines = new ArrayList<>();
        System.out.println("Reading file...");
        while (fileScanner.hasNextLine()) {
            fileLines.add(fileScanner.nextLine().toUpperCase());
        }
        System.out.println("Done.");
        fileScanner.close();
        int currentLine = 0;
        int totalLines = fileLines.size();

        try {
            ServerSocket serverSocket = new ServerSocket(portNumber);
            System.out.println("Server started.");
            InetAddress serverAddress = serverSocket.getInetAddress();
            System.out.println("Server host address: " + serverAddress.getHostName());

            while (true) {
                Socket clientSocket = serverSocket.accept();
                new Thread(new CatWorker(clientSocket, serverSocket, portNumber, fileLines)).start();
            }
        } catch (IOException e) {
            System.out.println("Exception was caught on port: " + portNumber);
            e.printStackTrace();
            System.exit(1);
        }
    }
}
