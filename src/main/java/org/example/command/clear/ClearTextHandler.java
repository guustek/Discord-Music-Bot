package org.example.command.clear;

import com.freya02.botcommands.api.prefixed.BaseCommandEvent;
import com.freya02.botcommands.api.prefixed.annotations.JDATextCommand;
import org.example.command.general.BaseTextHandler;
import org.example.command.general.Command;

public class ClearTextHandler extends BaseTextHandler {

    public ClearTextHandler(Command command) {
        super(command);
    }

    @JDATextCommand(name = ClearCommand.NAME, description = ClearCommand.DESCRIPTION)
    public void handle(BaseCommandEvent event) {
        command.execute(event);
    }
}
