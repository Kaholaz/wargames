package org.ntnu.vsbugge.wargames.utils.eventlisteners;

/**
 * An interface for creating event listeners to attach to subjects. This interface can either be used functionally or
 * through inheritance.
 *
 * @author Atle Olsø / vsbugge (<a href="https://gitlab.com/atleolso/observer">https://gitlab.com/atleolso/observer</a>)
 */
public interface EventListener {
    /**
     * Specifies an action that should be taken when a subject notifies its event listeners.
     *
     * @param eventType
     *            The type of the event.
     */
    void onEvent(EventType eventType);
}
