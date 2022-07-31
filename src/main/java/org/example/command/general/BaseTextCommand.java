package org.example.command.general;

import com.freya02.botcommands.api.prefixed.TextCommand;

public abstract class BaseTextCommand extends TextCommand {
    protected final CommandExecutor commandExecutor;

    public BaseTextCommand(CommandExecutor commandExecutor) {
        this.commandExecutor = commandExecutor;
    }
}
