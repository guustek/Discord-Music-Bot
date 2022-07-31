package org.example.command.np;

import com.freya02.botcommands.api.application.slash.GuildSlashEvent;
import com.freya02.botcommands.api.application.slash.annotations.JDASlashCommand;
import org.example.command.general.BaseSlashHandler;
import org.example.command.general.Command;

public class NpSlashHandler extends BaseSlashHandler {

    public NpSlashHandler(Command command) {
        super(command);
    }

    @JDASlashCommand(name = NpCommand.NAME, description = NpCommand.DESCRIPTION)
    public void handle(GuildSlashEvent event) {
        event.deferReply().queue();
        command.execute(event);
    }
}
