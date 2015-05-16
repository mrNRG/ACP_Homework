package Homework.Week_3.Chat.server;

import Homework.Week_3.Chat.Message;
import Homework.Week_3.Chat.Observer;
import Homework.Week_3.Chat.Subject;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class ChatServer implements Subject {

    private ServerSocket serverSocket = null;
    private LogObserver log;
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
            log = new LogObserver("log.txt");
            while (serverSocket.isBound() && connected) {
                Socket clientSocket = serverSocket.accept();
                new ClientThread(this, clientSocket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void registerObserver(Observer observer, Message message) {
        clients.add(observer);
        observer.update(message);
    }

    @Override
    public void removeObserver(Observer observer, Message message) {
        log.update(message);
        ClientThread client = (ClientThread) observer;
        observer.update(new Message("Good bye, " + client.name + ", have a nice day!!!"));
        clients.remove(observer);
        for (Observer ob : clients) {
            ob.update(message);
        }

    }

    @Override
    public void notifyObservers(Observer observer, Message message) {
        log.update(message);
        for (Observer ob : clients) {
            if (ob != observer) {
                ob.update(message);
            }
        }
    }
}
