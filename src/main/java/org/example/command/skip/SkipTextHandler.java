package org.example.command.skip;

import com.freya02.botcommands.api.annotations.CommandMarker;
import com.freya02.botcommands.api.prefixed.BaseCommandEvent;
import com.freya02.botcommands.api.prefixed.annotations.JDATextCommand;
import org.example.command.general.BaseTextHandler;
import org.example.command.general.Command;

@CommandMarker
public class SkipTextHandler extends BaseTextHandler {

    public SkipTextHandler(Command command) {
        super(command);
    }

    @JDATextCommand(name = SkipCommand.NAME, description = SkipCommand.DESCRIPTION)
    public void handle(BaseCommandEvent event) {
        command.execute(event);
    }
}
