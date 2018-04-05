import java.net.Socket;
import java.net.UnknownHostException;
import java.util.*;
import java.io.*;
import java.lang.*;

/**
 * CatClient queries CatServer every 3 seconds for 30 seconds. Upon
 * receiving each response, it should check to see if the response
 * is in a text file. If it is, it should echo ”OK”. If it isn’t, it
 * should echo ”MISSING”, in either case followed by a newline. The
 * path to the text file should be the first argument. The server’s
 * port number should be the second.
 */
public class CatClient {
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
        int totalLines = fileLines.size();

        String hostName = "0.0.0.0";
        try {
            System.out.println("Connecting to server...");
            Socket clientSocket = new Socket(hostName, portNumber);
            System.out.println("Client socket created.");

            PrintWriter outMessage = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader inMessage = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            for (int t = 0; t < 10; t++) {
                try {
                    Thread.sleep(3000);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                System.out.println("Sending message to Server...");
                outMessage.println("LINE");
                System.out.println("Done.");
                String line = inMessage.readLine();
                System.out.println(line);

                int currentLine = 0;
                while (currentLine < totalLines) {
                    if (fileLines.get(currentLine).equals(line)) {
                        break;
                    }
                    currentLine++;
                }
                if (currentLine == totalLines) {
                    System.out.println("MISSING");
                } else {
                    System.out.println("OK");
                }
            }

            clientSocket.close();
        } catch (UnknownHostException e) {
            System.out.println("Unknown host " + hostName);
            e.printStackTrace();
            System.exit(1);
        } catch (IOException e) {
            System.out.println("Exception was caught on port: " + portNumber);
            e.printStackTrace();
            System.exit(1);
        }
    }
}
