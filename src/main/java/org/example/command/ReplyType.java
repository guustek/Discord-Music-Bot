package org.example.command;

import java.awt.*;

public enum ReplyType {
    SUCCESS(Color.GREEN),
    INFO(Color.YELLOW),
    ERROR(Color.RED);


    private final Color color;

    ReplyType(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
