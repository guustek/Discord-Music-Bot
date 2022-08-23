package org.example.command.loop;

import com.freya02.botcommands.api.prefixed.BaseCommandEvent;
import com.freya02.botcommands.api.prefixed.annotations.JDATextCommand;
import org.example.command.bass.BassTextHandler;
import org.example.command.general.Command;

public class LoopTextHandler extends BassTextHandler {
    public LoopTextHandler(Command command) {
        super(command);
    }

    @JDATextCommand(name = LoopCommand.NAME, description = LoopCommand.DESCRIPTION)
    public void handle(BaseCommandEvent event) {
        command.execute(event);
    }
}
