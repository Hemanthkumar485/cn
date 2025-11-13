import java.net.*;
import java.io.*;

public class SlidReceiver {
    public static void main(String[] args) throws Exception {
        Socket s = new Socket(InetAddress.getLocalHost(), 5000);
        DataInputStream in = new DataInputStream(s.getInputStream());
        PrintStream p = new PrintStream(s.getOutputStream());

        int i, rptr = -1, nf, rws = 8;
        String rbuf[] = new String[8];
        String ch;

        System.out.println("Receiver started...");

        do {
            nf = Integer.parseInt(in.readLine());
            if (nf <= rws - 1) {
                for (i = 1; i <= nf; i++) {
                    rptr = ++rptr % 8;
                    rbuf[rptr] = in.readLine();
                    System.out.println("Received Frame " + rptr + ": " + rbuf[rptr]);
                }

                rws -= nf;
                System.out.println("\nAcknowledgment sent for frame " + (rptr + 1) + "\n");
                p.println(rptr + 1);
                rws += nf;
            } else {
                System.out.println("The number of frames exceeds the receiver window size.");
                break;
            }

            ch = in.readLine();
        } while (ch.equalsIgnoreCase("yes"));

        s.close();
        System.out.println("Connection closed.");
    }
}
