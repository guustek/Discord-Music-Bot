package org.example.command.general;

import com.freya02.botcommands.api.application.ApplicationCommand;

public abstract class BaseSlashCommand extends ApplicationCommand {

    protected final CommandExecutor commandExecutor;

    public BaseSlashCommand(CommandExecutor commandExecutor) {
        this.commandExecutor = commandExecutor;
    }
}
