package Homework.Week_3.Chat;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Message implements Serializable{
    private String content;
    private Date date;

    public Message(String content) {
        this.content = content;
        this.date = new Date();
    }

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        return "[" + dateFormat.format(date)+ "]\t\t" + content;
    }
}
