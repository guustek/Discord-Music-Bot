package org.example.command.skip;

import com.freya02.botcommands.api.annotations.CommandMarker;
import com.freya02.botcommands.api.application.slash.GuildSlashEvent;
import com.freya02.botcommands.api.application.slash.annotations.JDASlashCommand;
import org.example.command.general.BaseSlashHandler;
import org.example.command.general.Command;

@CommandMarker
public class SkipSlashHandler extends BaseSlashHandler {

    public SkipSlashHandler(Command command) {
        super(command);
    }

    @JDASlashCommand(name = SkipCommand.NAME, description = SkipCommand.DESCRIPTION)
    public void handle(GuildSlashEvent event) {
        event.deferReply().queue();
        command.execute(event);
    }
}
