package org.example.command.pause;

import com.freya02.botcommands.api.application.slash.GuildSlashEvent;
import com.freya02.botcommands.api.application.slash.annotations.JDASlashCommand;
import org.example.command.general.BaseSlashHandler;
import org.example.command.general.Command;

public class PauseSlashHandler extends BaseSlashHandler {

    public PauseSlashHandler(Command command) {
        super(command);
    }

    @JDASlashCommand(name = PauseCommand.NAME, description = PauseCommand.DESCRIPTION)
    public void handle(GuildSlashEvent event) {
        event.deferReply().queue();
        command.execute(event);
    }
}