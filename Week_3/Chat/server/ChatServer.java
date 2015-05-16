package Homework.Week_3.Chat.server;

import Homework.Week_3.Chat.Observer;
import Homework.Week_3.Chat.Subject;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class ChatServer implements Subject {

    private ServerSocket serverSocket = null;
    private boolean connected = false;
    private int port = 0;
    private List<Observer> clients = null;

    public ChatServer(int port) {
        this.port = port;
        clients = new LinkedList<>();
    }

    public void connect() {
        if (connected) {
            return;
        }
        try {
            serverSocket = new ServerSocket(port);
            connected = true;
            while (serverSocket.isBound() && connected) {
                Socket clientSocket = serverSocket.accept();
                new ClientThread(this, clientSocket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        if (connected) {
            connected = false;
            for (Observer observer : clients) {
                ClientThread client = (ClientThread) observer;
                client.disconnect();
                clients.remove(observer);
            }
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void registerObserver(Observer observer, String string) {
        clients.add(observer);
        observer.update(string);
    }

    @Override
    public void removeObserver(Observer observer, String string) {
        ClientThread client = (ClientThread) observer;
        observer.update("Good bye, " + client.name + ", have a nice day!!!");
        clients.remove(observer);
        for (Observer ob : clients) {
            ob.update(string);
        }

    }

    @Override
    public void notifyObservers(Observer observer, String string) {
        for (Observer ob : clients) {
            if (ob != observer) {
                ob.update(string);
            }
        }

    }
}
