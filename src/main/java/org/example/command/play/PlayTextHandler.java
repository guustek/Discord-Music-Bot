package org.example.command.play;

import com.freya02.botcommands.api.annotations.Optional;
import com.freya02.botcommands.api.prefixed.BaseCommandEvent;
import com.freya02.botcommands.api.prefixed.annotations.JDATextCommand;
import com.freya02.botcommands.api.prefixed.annotations.TextOption;
import org.example.command.general.BaseTextHandler;
import org.example.command.general.Command;

public class PlayTextHandler extends BaseTextHandler {

    public PlayTextHandler(Command command) {
        super(command);
    }

    @JDATextCommand(name = PlayCommand.NAME, description = PlayCommand.DESCRIPTION)
    public void handle(BaseCommandEvent event, @Optional @TextOption String search) {
        command.execute(event, search);
    }
}
