import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class udpClient {

    public static final int PORT = 8000;
    public static final String HOST = "localhost";


    public static void main(String[] args){
        Scanner in = new Scanner(System.in);

        while(true) {
            String message = in.next();

            try {
                byte[] data = message.getBytes();

                InetAddress addr = InetAddress.getByName(HOST);
                DatagramPacket pack = new DatagramPacket(data, data.length, addr, PORT);

                try (DatagramSocket ds = new DatagramSocket()) {
                    ds.send(pack);
                }
            } catch (IOException e) {
                System.out.println(e.getStackTrace());
            }
        }
    }
}
