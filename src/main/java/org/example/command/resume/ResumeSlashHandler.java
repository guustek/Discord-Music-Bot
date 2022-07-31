package org.example.command.resume;

import com.freya02.botcommands.api.application.slash.GuildSlashEvent;
import com.freya02.botcommands.api.application.slash.annotations.JDASlashCommand;
import org.example.command.general.BaseSlashHandler;
import org.example.command.general.Command;

public class ResumeSlashHandler extends BaseSlashHandler {

    public ResumeSlashHandler(Command command) {
        super(command);
    }

    @JDASlashCommand(name = ResumeCommand.NAME, description = ResumeCommand.DESCRIPTION)
    public void handle(GuildSlashEvent event) {
        event.deferReply().queue();
        command.execute(event);
    }
}
