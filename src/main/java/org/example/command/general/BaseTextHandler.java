package org.example.command.general;

import com.freya02.botcommands.api.prefixed.TextCommand;

public abstract class BaseTextHandler extends TextCommand {
    protected final Command command;

    public BaseTextHandler(Command command) {
        this.command = command;
    }
}
