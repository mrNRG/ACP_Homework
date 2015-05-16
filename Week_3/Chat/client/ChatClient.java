package Homework.Week_3.Chat.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {

    private Socket socket = null;
    private Scanner console = null;
    private PrintWriter out = null;
    private Scanner input = null;

    public ChatClient(String host, int port) {
        try {
            socket = new Socket(host,port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // initialized input and output
        // client write message
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    out = new PrintWriter(socket.getOutputStream(), true);
                    console = new Scanner(System.in);
                    while (true) {
                        String message = console.nextLine();
                        out.println(message);
                    }
                } catch (IOException e) {

                    e.printStackTrace();
                }
            }

        }).start();

        // client read message
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    input = new Scanner(socket.getInputStream());
                    while(true){
                        if(input.hasNextLine()){
                            System.out.println(input.nextLine());
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
