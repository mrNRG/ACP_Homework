package Homework.Week_3.Chat;

public interface Subject{
    void registerObserver(Observer observer, Message message);
    void removeObserver(Observer observer, Message message);
    void notifyObservers(Observer observer, Message message);
}
