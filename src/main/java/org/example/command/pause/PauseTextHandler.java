package org.example.command.pause;

import com.freya02.botcommands.api.prefixed.BaseCommandEvent;
import com.freya02.botcommands.api.prefixed.annotations.JDATextCommand;
import org.example.command.general.BaseTextHandler;
import org.example.command.general.Command;

public class PauseTextHandler extends BaseTextHandler {

    public PauseTextHandler(Command command) {
        super(command);
    }

    @JDATextCommand(name = PauseCommand.NAME, description = PauseCommand.DESCRIPTION)
    public void handle(BaseCommandEvent event) {
        command.execute(event);
    }
}
