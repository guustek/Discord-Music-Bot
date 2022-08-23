package org.example.command.loop;

import com.freya02.botcommands.api.application.slash.GuildSlashEvent;
import com.freya02.botcommands.api.application.slash.annotations.JDASlashCommand;
import org.example.command.general.BaseSlashHandler;
import org.example.command.general.Command;

public class LoopSlashHandler extends BaseSlashHandler {
    public LoopSlashHandler(Command command) {
        super(command);
    }

    @JDASlashCommand(name = LoopCommand.NAME, description = LoopCommand.DESCRIPTION)
    public void handle(GuildSlashEvent event) {
        event.deferReply().queue();
        command.execute(event);
    }
}
