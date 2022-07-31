package org.example.command.leave;

import com.freya02.botcommands.api.annotations.CommandMarker;
import com.freya02.botcommands.api.prefixed.BaseCommandEvent;
import com.freya02.botcommands.api.prefixed.annotations.JDATextCommand;
import org.example.command.general.BaseTextCommand;
import org.example.command.general.CommandExecutor;

@CommandMarker
public class LeaveTextCommandHandler extends BaseTextCommand {

    public LeaveTextCommandHandler(CommandExecutor commandExecutor) {
        super(commandExecutor);
    }

    @JDATextCommand(name = "leave", description = "Leave audio channel")
    public void handle(BaseCommandEvent event) {
        commandExecutor.execute(event);
    }
}
