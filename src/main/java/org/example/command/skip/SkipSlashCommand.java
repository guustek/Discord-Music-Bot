package org.example.command.skip;

import com.freya02.botcommands.api.annotations.CommandMarker;
import com.freya02.botcommands.api.application.ApplicationCommand;
import com.freya02.botcommands.api.application.slash.GuildSlashEvent;
import com.freya02.botcommands.api.application.slash.annotations.JDASlashCommand;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import org.example.EmbedMessage;
import org.example.audio.PlayerManager;
import org.example.command.ReplyType;

@CommandMarker
public class SkipSlashCommand extends ApplicationCommand {

    @JDASlashCommand(name = "skip", description = "Skip currently playing track")
    public void handle(GuildSlashEvent event) {
        event.deferReply().queue();
        PlayerManager playerManager = PlayerManager.getPlayerManager();
        AudioTrack playingTrack = playerManager.getTrackManager(event.getGuild()).getAudioPlayer().getPlayingTrack();
        if (playingTrack == null) {
            EmbedMessage.replyWithEmbed(event, EmbedMessage.buildBasicEmbed(ReplyType.INFO, "Nothing is playing"));
            return;
        }
        playerManager.getTrackManager(event.getGuild()).getAudioPlayer().stopTrack();
        EmbedMessage.replyWithEmbed(event, EmbedMessage.buildTrackInfoEmbed("Skipped", playingTrack));
    }
}
