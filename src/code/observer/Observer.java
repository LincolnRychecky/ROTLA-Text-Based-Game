package code.observer;

import java.util.HashMap;

public interface Observer {
    public void update(HashMap<String, String> message);
}