
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ConcurrentSkipListSet;

public class LabClient {

    private static final int PORT = 1488;
    private static final String HOST = "localhost";

    public static void main(String[] args){

        ArrayList<Citizens> mainSet;

        Socket socket;

        Scanner consoleInput = new Scanner(System.in);

        try{
            socket = new Socket(HOST,PORT);

            try(OutputStream out = socket.getOutputStream();
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream())){

                String line;

                while(true){

                    //System.out.println(1);

                    line = consoleInput.nextLine();

                    //System.out.println(2);

                    out.write(line.getBytes());
                    out.flush();

                    try{
                        mainSet = (ArrayList<Citizens>) in.readObject();

                        mainSet.stream().forEach(Citizens -> Citizens.printInfo());

                        /*for(Citizens curCitizen: mainSet){
                            curCitizen.printInfo();
                        }*/

                    } catch (ClassNotFoundException | EOFException e) {
                        //e.printStackTrace();
                        System.out.println("Application stopped...");
                        break;
                    }

                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }



    }
}
