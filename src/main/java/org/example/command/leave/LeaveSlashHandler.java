package org.example.command.leave;

import com.freya02.botcommands.api.annotations.CommandMarker;
import com.freya02.botcommands.api.application.slash.GuildSlashEvent;
import com.freya02.botcommands.api.application.slash.annotations.JDASlashCommand;
import org.example.command.general.BaseSlashHandler;
import org.example.command.general.Command;

@CommandMarker
public class LeaveSlashHandler extends BaseSlashHandler {

    public LeaveSlashHandler(Command command) {
        super(command);
    }

    @JDASlashCommand(name = LeaveCommand.NAME, description = LeaveCommand.DESCRIPTION)
    public void handle(GuildSlashEvent event) {
        event.deferReply().queue();
        command.execute(event);
    }
}
