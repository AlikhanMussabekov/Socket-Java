

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentSkipListSet;

public class Server {

    public static final int PORT = 1488;

    public static void main(String[] args) throws ClassNotFoundException {

        Thread mainThread = Thread.currentThread();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Stoped...");
            //System.out.println(11111111);
            try {
                mainThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }));

        ConcurrentSkipListSet<Citizens> citizenSet = new ConcurrentSkipListSet(new CitizenNameComporator());

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

                    System.out.println(1);

                    try {
                        Citizens citizen = (Citizens) in.readObject();
                        citizenSet.add(citizen);

                        for(Citizens curCitizen: citizenSet){
                            curCitizen.printInfo();
                        }

                        System.out.println(citizen.getName());
                    }catch(EOFException e){
                        System.out.println("EOFException");
                        break;
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }

    }
}
