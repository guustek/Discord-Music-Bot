package org.example.command.clear;

import com.freya02.botcommands.api.application.slash.GuildSlashEvent;
import com.freya02.botcommands.api.application.slash.annotations.JDASlashCommand;
import org.example.command.general.BaseSlashHandler;
import org.example.command.general.Command;

public class ClearSlashHandler extends BaseSlashHandler {

    public ClearSlashHandler(Command command) {
        super(command);
    }

    @JDASlashCommand(name = ClearCommand.NAME, description = ClearCommand.DESCRIPTION)
    public void handle(GuildSlashEvent event) {
        event.deferReply().queue();
        command.execute(event);
    }
}
