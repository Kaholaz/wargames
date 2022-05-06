package org.ntnu.vsbugge.wargames.utils.files;

import java.io.IOException;

public class FileFormatException extends IOException {
    public FileFormatException(String message) {
        super(message);
    }
}
