import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

    public static final int PORT = 1488;
    public static final String HOST = "localhost";

    public static void main(String[] args){

        Socket socket = null;

        try {
            socket = new Socket(HOST,PORT);

            System.out.println(1);

            try(//ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())){

                System.out.println(2);

                String line = "";
                String name = "";
                int age;

                Citizens citizen = null;

                while(true) {

                    Scanner consoleIn = new Scanner(System.in);

                    name = consoleIn.next();
                    age = consoleIn.nextInt();

                    System.out.println("3");

                    citizen = new Citizens(name, age);
                    //line = consoleIn.next();

                    System.out.printf("Name: %s    Age: %d \n", citizen.getName(), citizen.getAge());

                    out.writeObject(citizen);
                    out.flush();


                    /*byte[] data = new byte[32 * 1024];
                    int readBytes = in.read(data);

                    System.out.printf("Server >> %s \n", new String(data, 0, readBytes));*/
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
