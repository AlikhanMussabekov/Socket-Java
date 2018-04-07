

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static final int PORT = 1488;

    public static void main(String[] args) throws ClassNotFoundException {

        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(PORT);

            System.out.println("Waiting for connection...");

            Socket socket = serverSocket.accept();

            System.out.println("Connected: " + socket.getInetAddress());

            try(ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                //ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())
            ){

                while (true) {

                    /*byte[] buf = new byte[32 * 1024];
                    int readBytes = in.read(buf);

                    String line = new String(buf, 0, readBytes);
                    System.out.printf("Client >> %s", line);

                    out.write(line.getBytes());
                    out.flush();*/

                    Citizens citizen = (Citizens) in.readObject();
                    System.out.println(citizen.getName());

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }

    }
}
