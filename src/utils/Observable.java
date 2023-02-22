package utils;

public interface Observable {
    public void attach(Observer observer);
    public void notifyObservers();
}
