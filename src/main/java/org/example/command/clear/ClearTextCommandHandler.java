package org.example.command.clear;

import com.freya02.botcommands.api.prefixed.BaseCommandEvent;
import com.freya02.botcommands.api.prefixed.annotations.JDATextCommand;
import org.example.command.general.BaseTextCommand;
import org.example.command.general.CommandExecutor;

public class ClearTextCommandHandler extends BaseTextCommand {

    public ClearTextCommandHandler(CommandExecutor commandExecutor) {
        super(commandExecutor);
    }

    @JDATextCommand(name = "clear", description = "Clears the track queue. This command does not remove currently playing track")
    public void handle(BaseCommandEvent event) {
        commandExecutor.execute(event);
    }
}
