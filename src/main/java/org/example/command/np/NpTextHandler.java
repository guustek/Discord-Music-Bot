package org.example.command.np;

import com.freya02.botcommands.api.prefixed.BaseCommandEvent;
import com.freya02.botcommands.api.prefixed.annotations.JDATextCommand;
import org.example.command.general.BaseTextHandler;
import org.example.command.general.Command;

public class NpTextHandler extends BaseTextHandler {
    public NpTextHandler(Command commandExecutor) {
        super(commandExecutor);
    }

    @JDATextCommand(name = NpCommand.NAME, description = NpCommand.DESCRIPTION)
    public void handle(BaseCommandEvent event) {
        command.execute(event);
    }
}
