package Homework.Week_3.Chat.server;

import Homework.Week_3.Chat.Message;
import Homework.Week_3.Chat.Observer;

import java.io.*;

public class LogObserver implements Observer {
    private BufferedWriter out;

    public LogObserver(String path) throws FileNotFoundException, UnsupportedEncodingException {
        try {
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path, true)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Message message) {
        try {
            out.write(String.valueOf(message));
            out.newLine();
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
