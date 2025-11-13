import java.net.*;
import java.io.*;

public class SlidSender {
    public static void main(String[] args) throws Exception {
        // Use a higher port number (like 5000) instead of 10 to avoid permission errors
        ServerSocket ser = new ServerSocket(5000);
        System.out.println("Sender started, waiting for receiver...");
        Socket s = ser.accept();
        System.out.println("Receiver connected.");

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        DataInputStream in1 = new DataInputStream(s.getInputStream());
        PrintStream p = new PrintStream(s.getOutputStream());

        String sbuff[] = new String[8];
        int sptr = 0, sws = 8, nf, ano, i;
        String ch;

        do {
            System.out.println("\n--- Sender ---");
            System.out.print("Enter the number of frames to send: ");
            nf = Integer.parseInt(in.readLine());
            p.println(nf);

            if (nf <= sws - 1) {
                System.out.println("Enter " + nf + " messages to send:\n");
                for (i = 1; i <= nf; i++) {
                    sbuff[sptr] = in.readLine();
                    p.println(sbuff[sptr]);
                    sptr = ++sptr % 8;
                }
            } else {
                System.out.println("The number of frames exceeds window size!");
                break;
            }

            sws -= nf;
            System.out.print("Waiting for acknowledgment... ");
            ano = Integer.parseInt(in1.readLine());
            System.out.println("Received acknowledgment for " + ano + " frames.");
            sws += nf;

            System.out.print("\nDo you want to send more frames? (yes/no): ");
            ch = in.readLine();
            p.println(ch);

        } while (ch.equalsIgnoreCase("yes"));

        s.close();
        ser.close();
        System.out.println("Connection closed.");
    }
}
