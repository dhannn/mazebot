package utils;

public interface Observer {
    public void link(Observable observable);
    public void update();
}
