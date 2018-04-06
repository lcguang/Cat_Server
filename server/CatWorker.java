import java.net.ServerSocket;
import java.net.Socket;
import java.net.InetAddress;
import java.util.*;
import java.io.*;
import java.lang.*;

public class CatWorker implements Runnable {
    private Socket clientSocket;
    private ServerSocket serverSocket;
    private int portNumber;
    private List<String> fileLines;
    private int currentLine;
    private int totalLines;

    public CatWorker(Socket clientSocket, ServerSocket serverSocket, int portNumber, List<String> fileLines) {
        this.clientSocket = clientSocket;
        this.serverSocket = serverSocket;
        this.portNumber = portNumber;
        this.fileLines = fileLines;
        this.currentLine = 0;
        this.totalLines = fileLines.size();
    }

    public void run() {
        try {
            // TODO:
            // Check if client is still connected.
            while (true) {
                PrintWriter outMessage = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader inMessage = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                if (inMessage.readLine().equals("LINE")) {
                    System.out.println("Responding to client's request...");
                    outMessage.println(fileLines.get(currentLine));
                    System.out.println("Done.");
                    if (++currentLine == totalLines) {
                        currentLine = 0;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Exception was caught on port: " + portNumber);
            e.printStackTrace();
            System.exit(1);
        }
    }
}
