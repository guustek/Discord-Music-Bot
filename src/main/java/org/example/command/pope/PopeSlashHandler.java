package org.example.command.pope;

import com.freya02.botcommands.api.application.slash.GuildSlashEvent;
import com.freya02.botcommands.api.application.slash.annotations.JDASlashCommand;
import org.example.command.general.BaseSlashHandler;
import org.example.command.general.Command;

public class PopeSlashHandler extends BaseSlashHandler {
    public PopeSlashHandler(Command command) {
        super(command);
    }

    @JDASlashCommand(name = PopeCommand.NAME, description = PopeCommand.DESCRIPTION)
    public void handle(GuildSlashEvent event) {
        command.execute(event);
    }
}
