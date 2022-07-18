package org.example.command.np;

import com.freya02.botcommands.api.application.ApplicationCommand;
import com.freya02.botcommands.api.application.slash.GuildSlashEvent;
import com.freya02.botcommands.api.application.slash.annotations.JDASlashCommand;
import org.example.EmbedMessage;
import org.example.audio.PlayerManager;
import org.example.command.ReplyType;

public class NpSlashCommand extends ApplicationCommand {

    @JDASlashCommand(name = "np", description = "Display currently playing track")
    public void handle(GuildSlashEvent event) {
        event.deferReply().queue();
        var playingTrack = PlayerManager.getPlayerManager()
                .getTrackManager(event.getGuild())
                .getAudioPlayer()
                .getPlayingTrack();
        if (playingTrack == null)
            event.getHook()
                    .editOriginalEmbeds(EmbedMessage.replyEmbed(ReplyType.INFO, "No track playing now"))
                    .queue();
        else
            event.getHook()
                    .editOriginalEmbeds(EmbedMessage.audioInfoEmbed("Currently playing ", playingTrack))
                    .queue();
    }
}
