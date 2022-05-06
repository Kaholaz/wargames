package org.ntnu.vsbugge.wargames.utils.eventlisteners;

import java.util.ArrayList;
import java.util.List;

/**
 * Subject is an abstract class that facilitates eventListeners. Children of this class can be attached event listeners that trigger certain actions.
 */
public abstract class Subject {

    private List<EventListener> eventListeners = new ArrayList<>();

    /**
     * Attaches an event listener to the instance.
     * @param eventListener The event listener.
     */
    public void attach(EventListener eventListener) {
        eventListeners.add(eventListener);
    }

    /**
     * Removes an event listener from the instance.
     * @param eventListener The event listener.
     */
    public void detach(EventListener eventListener) {
        eventListeners.remove(eventListener);
    }

    /**
     * Notifies all event listeners of an event of the supplied event type.
     * @param eventType The type of event.
     */
    public void notifyEventListeners(EventType eventType) {
        eventListeners.forEach(listener -> listener.onEvent(eventType));
    }
}