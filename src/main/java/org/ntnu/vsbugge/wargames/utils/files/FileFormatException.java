package org.ntnu.vsbugge.wargames.utils.files;

import java.io.IOException;

/**
 * An exception used to signal that something was wrong with the format of a read file.
 *
 * @author vsbugge
 */
public class FileFormatException extends IOException {
    /**
     * Constructor for FileFormatException.
     *
     * @param message
     *            The exception message.
     */
    public FileFormatException(String message) {
        super(message);
    }
}
