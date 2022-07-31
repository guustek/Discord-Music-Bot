package org.example.command.np;

import com.freya02.botcommands.api.application.ApplicationCommand;
import com.freya02.botcommands.api.application.slash.GuildSlashEvent;
import com.freya02.botcommands.api.application.slash.annotations.JDASlashCommand;
import org.example.MessageUtils;
import org.example.audio.PlayerManager;

public class NpSlashCommand extends ApplicationCommand {

    @JDASlashCommand(name = "np", description = "Display currently playing track")
    public void handle(GuildSlashEvent event) {
        event.deferReply().queue();
        var playingTrack = PlayerManager.getPlayerManager()
                .getTrackManager(event.getGuild())
                .getAudioPlayer()
                .getPlayingTrack();
        if (playingTrack == null)
            MessageUtils.replyWithEmbed(event, MessageUtils.buildBasicEmbed("No track playing now"));
        else
            MessageUtils.replyWithEmbed(event, MessageUtils.buildTrackInfoEmbed("Currently playing ", playingTrack));
    }
}
