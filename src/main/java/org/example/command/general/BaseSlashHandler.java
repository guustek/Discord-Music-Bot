package org.example.command.general;

import com.freya02.botcommands.api.application.ApplicationCommand;

public abstract class BaseSlashHandler extends ApplicationCommand {

    protected final Command command;

    public BaseSlashHandler(Command command) {
        this.command = command;
    }
}
