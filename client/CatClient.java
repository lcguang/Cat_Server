import java.net.Socket;
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
    private final String hostName = "CatServer";

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
        while (fileScanner.hasNextLine()) {
            fileLines.add(fileScanner.nextLine().toUpperCase());
        }
        fileScanner.close();
        int totalLines = fileLines.size();

        try {
            Socket clientSocket = new Socket(hostName, portNumber);

            PrintWriter outMessage = new PrintWriter(clientMessage.getOutputStream(), true);
            BufferReader inMessage = new BufferReader(new InputStreamReader(clientSocket.getInputStream()));

            for (int t = 0; t < 10; t++) {
                Thread.sleep(3000);

                outMessage.println("LINE");
                String line = inMessage.readLine();
                System.out.println(line);

                int currentLine = 0;
                while (currentLine++ < totalLines) {
                    if (fileLines.get(i).equals(line)) {
                        break;
                    }
                }
                if (currentLine == totalLines) {
                    System.out.println("MISSING");
                } else {
                    System.out.println("OK");
                }
            }

            clientSocket.close();
        } catch (UnknownHostException e) {
            System.err.println("Unknown host " + hostName);
            e.printStackTrace();
            System.exit(1);
        } catch (IOException e) {
            System.out.println("Exception was caught on port: " + portNumber);
            e.printStackTrace();
            System.exit(1);
        }
    }
}
