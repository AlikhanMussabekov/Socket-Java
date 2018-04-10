import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class udpServer {

    public static final int PORT = 8000;

    public static void main(String[] args){

        try(DatagramSocket ds = new DatagramSocket(PORT)){
            while(true){
                DatagramPacket pack = new DatagramPacket(new byte[1024], 1024);
                ds.receive(pack);
                String message = new String(pack.getData());

                System.out.println(message);

            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
