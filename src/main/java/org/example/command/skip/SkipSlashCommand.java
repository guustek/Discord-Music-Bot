package org.example.command.skip;

import com.freya02.botcommands.api.annotations.CommandMarker;
import com.freya02.botcommands.api.application.ApplicationCommand;
import com.freya02.botcommands.api.application.slash.GuildSlashEvent;
import com.freya02.botcommands.api.application.slash.annotations.JDASlashCommand;
import org.example.audio.PlayerManager;

@CommandMarker
public class SkipSlashCommand extends ApplicationCommand {

    @JDASlashCommand(name = "skip", description = "Skip currently playing track")
    public void handle(GuildSlashEvent event) {
        event.deferReply().queue();
        PlayerManager.getPlayerManager().skip(event);
    }
}
