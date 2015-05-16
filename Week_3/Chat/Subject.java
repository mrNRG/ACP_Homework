package Homework.Week_3.Chat;

public interface Subject {
    void registerObserver(Observer observer, String string);
    void removeObserver(Observer observer, String string);
    void notifyObservers(Observer observer, String string);
}
