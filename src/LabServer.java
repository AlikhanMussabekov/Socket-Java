import org.json.simple.parser.ParseException;
import sun.security.krb5.internal.TGSRep;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class LabServer {
    public static final int PORT = 1488;

    public static void main(String[] args) throws ClassNotFoundException {

        ConcurrentSkipListSetCollection curSet = new ConcurrentSkipListSetCollection();
        JsonCommandParser parser = new JsonCommandParser();
        Scanner cmdScanner;

        Thread mainThread = Thread.currentThread();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Stoped...");
            /*try {
                curSet.save();
            } catch (IOException e) {
                e.printStackTrace();
            }*/
            //System.out.println(11111111);
            try {
                mainThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }));

        Scanner path = new Scanner(System.getenv("PATH"));

        path.useDelimiter(":");

        String pathStr = null;
        String check = null;

        while(path.hasNext()){

            pathStr = path.next();

            if (pathStr.substring(pathStr.length()-4,pathStr.length()).equals("lab5"))
                check = pathStr;

        }
        path.close();

        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(PORT);

            System.out.println("Waiting for connection...");

            Socket socket = serverSocket.accept();

            System.out.println("Connected: " + socket.getInetAddress());

            try(InputStream in = socket.getInputStream();
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())
            ){

                curSet.setPath(check+"/str.csv");
                curSet.readElements();
                curSet.writeElements();

                //new Thread(new CommandThread(in,out)).start();

                while (true) {

                    System.out.println("Server is running...");

                    try {

                        byte[] buf = new byte[32 * 1024];
                        int readBytes = in.read(buf);

                        try {
                            String line = new String(buf, 0, readBytes);
                            cmdScanner = new Scanner(line);
                            cmdScanner.useDelimiter(" ");
                        }catch (StringIndexOutOfBoundsException e){
                            continue;
                        }


                        //System.out.println(1);

                        String curCmd = cmdScanner.next();

                        try{

                            String stringJson = cmdScanner.next();

                            try {
                                parser.nextCommand(curCmd, curSet, stringJson);
                                curSet.writeElements();


                                out.writeObject(curSet.returnObjects());
                                out.flush();

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                        }catch(NoSuchElementException e){
                            if (curCmd.equals("stop_app")){
                                curSet.save();
                                System.out.println("Application stopped...");
                                break;
                            }else{
                                continue;
                            }
                        }

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
