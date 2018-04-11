import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class CommandThread extends Thread{

    ArrayList<Citizens> citizens;
    ObjectOutputStream out;

    public CommandThread(ArrayList<Citizens> citizens,ObjectOutputStream out) {
        this.citizens = citizens;
        this.out = out;
    }

    @Override
    public void run() {
        System.out.println("TTTHHRREEAADDD");
        try {
            out.writeObject(citizens);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
