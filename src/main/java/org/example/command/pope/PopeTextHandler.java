package org.example.command.pope;

import com.freya02.botcommands.api.prefixed.BaseCommandEvent;
import com.freya02.botcommands.api.prefixed.annotations.JDATextCommand;
import org.example.command.general.BaseTextHandler;
import org.example.command.general.Command;

public class PopeTextHandler extends BaseTextHandler {
    public PopeTextHandler(Command command) {
        super(command);
    }

    @JDATextCommand(name = PopeCommand.NAME, description = PopeCommand.DESCRIPTION)
    public void handle(BaseCommandEvent event) {
        command.execute(event);
    }
}
