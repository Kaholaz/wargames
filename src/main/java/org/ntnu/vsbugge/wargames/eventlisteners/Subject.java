package org.ntnu.vsbugge.wargames.eventlisteners;

import java.util.ArrayList;
import java.util.List;

public abstract class Subject {

    List<EventListener> eventListeners;

    public Subject() {
        eventListeners = new ArrayList<>();
    }

    public void attach(EventListener observer) {
        eventListeners.add(observer);
    }

    public void detach(EventListener observer) {
        eventListeners.remove(observer);
    }

    public void notifyObservers(EventType eventType) {
        eventListeners.forEach(listener -> listener.onEvent(eventType));
    }
}