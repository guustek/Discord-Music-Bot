package org.example.command.play;

import com.freya02.botcommands.api.annotations.CommandMarker;
import com.freya02.botcommands.api.application.annotations.AppOption;
import com.freya02.botcommands.api.application.slash.GuildSlashEvent;
import com.freya02.botcommands.api.application.slash.annotations.JDASlashCommand;
import org.example.command.general.BaseSlashHandler;
import org.example.command.general.Command;

@CommandMarker
public class PlaySlashHandler extends BaseSlashHandler {

    public PlaySlashHandler(Command command) {
        super(command);
    }

    @JDASlashCommand(name = PlayCommand.NAME, description = PlayCommand.DESCRIPTION)
    public void play(
            GuildSlashEvent event,
            @AppOption(name = "search", description = "Url or name") String search) {
        event.deferReply().queue();
        command.execute(event, search);
    }
}
