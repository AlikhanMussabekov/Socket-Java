import org.json.simple.parser.ParseException;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class UserThread extends Thread {

    public static final int PORT = 1488;

    ConcurrentSkipListSetCollection curSet = null;
    JsonCommandParser parser = new JsonCommandParser();
    Scanner cmdScanner;

    Socket socket = null;

    public UserThread(ConcurrentSkipListSetCollection curSet, Socket socket) {
        this.curSet = curSet;
        this.socket = socket;
    }

    @Override
    public void run() {
        System.out.println("new Thread");

        try {

            try(InputStream in = socket.getInputStream();
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())
            ){

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


                        String curCmd = cmdScanner.next();

                        try{

                            String stringJson = cmdScanner.next();

                            try {
                                parser.nextCommand(curCmd, curSet, stringJson);
                                curSet.writeElements();

                                //new Thread (new CommandThread(curSet.returnObjects(),out)).start();

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
