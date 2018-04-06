

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static final int PORT = 1488;

    public static void main(String[] args){

        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(PORT);

            System.out.println("Waiting for connection...");

            Socket socket = serverSocket.accept();

            System.out.println("Connected: " + socket.getInetAddress());

            try(InputStream in = socket.getInputStream();
                OutputStream out = socket.getOutputStream()){

                while (true) {
                    byte[] buf = new byte[32 * 1024];
                    int readBytes = in.read(buf);
                    String line = new String(buf, 0, readBytes);
                    System.out.printf("Client >> %s", line);

                    out.write(line.getBytes());
                    out.flush();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }

    }
}
