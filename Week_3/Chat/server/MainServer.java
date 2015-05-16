package Homework.Week_3.Chat.server;

public class MainServer {
    public static final int PORT = 8888;

    public static void main(String[] args) {

        ChatServer chatServer = new ChatServer(PORT);
        chatServer.connect();
    }
}
