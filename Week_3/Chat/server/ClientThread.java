package Homework.Week_3.Chat.server;


import Homework.Week_3.Chat.Observer;
import Homework.Week_3.Chat.Subject;

import java.io.*;
import java.net.Socket;

public class ClientThread implements Observer, Runnable {
    private Subject subject = null;
    private Socket clientSocket = null;
    private Thread thread = null;
    private PrintWriter out = null;
    private BufferedReader in = null;
    public String name = null;

    public ClientThread(Subject subject, Socket clientSocket) {
        this.subject = subject;
        this.clientSocket = clientSocket;
        try {
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        thread = new Thread(this);
        thread.start();
    }

    public void disconnect() {
        try {
            out.close();
            in.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(String string) {
        out.println(string);
    }

    @Override
    public void run() {
        try {
            String defaultName = clientSocket.getInetAddress().toString() +":"+ clientSocket.getPort();
            String s;
            out.println("Please type your name: ");
            while ((s = in.readLine()) != null) {
                if (s.equals("!QUIT")) {
                    subject.removeObserver(this, name + " just left the chat.");
                    String message = String.format("Client ip %s, port %s is disconnected\n",
                            clientSocket.getInetAddress(),
                            clientSocket.getPort());
                    System.out.println(message);
                    break;
                }
                if (name == null) {
                    if (s.equals("")) {
                        name = defaultName;
                    } else {
                        name = s;
                    }
                    subject.notifyObservers(this, name + " just joined the chat");
                    subject.registerObserver(this, "Welcome, " + name);
                    String message = String.format("New client connection ip %s, port %s\n",
                            clientSocket.getInetAddress(),
                            clientSocket.getPort());
                    System.out.println(message);
                } else {
                    subject.notifyObservers(this, name + ": " + s);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            this.disconnect();
        }
    }
}
