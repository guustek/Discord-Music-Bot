package org.example.command.leave;

import com.freya02.botcommands.api.annotations.CommandMarker;
import com.freya02.botcommands.api.prefixed.BaseCommandEvent;
import com.freya02.botcommands.api.prefixed.annotations.JDATextCommand;
import org.example.command.general.BaseTextHandler;
import org.example.command.general.Command;

@CommandMarker
public class LeaveTextHandler extends BaseTextHandler {

    public LeaveTextHandler(Command command) {
        super(command);
    }

    @JDATextCommand(name = LeaveCommand.NAME, description = LeaveCommand.DESCRIPTION)
    public void handle(BaseCommandEvent event) {
        command.execute(event);
    }
}
