package org.example.command.resume;

import com.freya02.botcommands.api.prefixed.BaseCommandEvent;
import com.freya02.botcommands.api.prefixed.annotations.JDATextCommand;
import org.example.command.general.BaseTextHandler;
import org.example.command.general.Command;

public class ResumeTextHandler extends BaseTextHandler {
    public ResumeTextHandler(Command command) {
        super(command);
    }

    @JDATextCommand(name = ResumeCommand.NAME, description = ResumeCommand.DESCRIPTION)
    public void handle(BaseCommandEvent event) {
        command.execute(event);
    }
}
