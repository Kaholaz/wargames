package org.ntnu.vsbugge.wargames.utils.eventlisteners;

import java.util.ArrayList;
import java.util.List;

/**
 * Subject is an abstract class that facilitates eventListeners. Children of this class can be attached event listeners
 * that trigger certain actions.
 *
 * @author Atle Olsø / vsbugge (<a href="https://gitlab.com/atleolso/observer">https://gitlab.com/atleolso/observer</a>)
 */
public interface Subject {
    List<EventListener> eventListeners = new ArrayList<>();

    /**
     * Attaches an event listener to the instance.
     *
     * @param eventListener
     *            The event listener.
     */
    default void attach(EventListener eventListener) {
        eventListeners.add(eventListener);
    }

    /**
     * Removes an event listener from the instance.
     *
     * @param eventListener
     *            The event listener.
     */
    default void detach(EventListener eventListener) {
        eventListeners.remove(eventListener);
    }

    /**
     * Notifies all event listeners of an event of the supplied event type.
     *
     * @param eventType
     *            The type of event.
     */
    default void notifyEventListeners(EventType eventType) {
        eventListeners.forEach(listener -> listener.onEvent(eventType));
    }
}
