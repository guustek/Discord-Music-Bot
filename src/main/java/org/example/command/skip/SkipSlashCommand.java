package org.example.command.skip;

import com.freya02.botcommands.api.annotations.CommandMarker;
import com.freya02.botcommands.api.application.ApplicationCommand;
import com.freya02.botcommands.api.application.slash.GuildSlashEvent;
import com.freya02.botcommands.api.application.slash.annotations.JDASlashCommand;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import org.example.MessageUtils;
import org.example.audio.PlayerManager;

@CommandMarker
public class SkipSlashCommand extends ApplicationCommand {

    @JDASlashCommand(name = "skip", description = "Skip currently playing track")
    public void handle(GuildSlashEvent event) {
        event.deferReply().queue();
        PlayerManager playerManager = PlayerManager.getPlayerManager();
        AudioTrack playingTrack = playerManager.getTrackManager(event.getGuild()).getAudioPlayer().getPlayingTrack();
        if (playingTrack == null) {
            MessageUtils.replyWithEmbed(event, MessageUtils.buildBasicEmbed("Nothing is playing"));
            return;
        }
        playerManager.getTrackManager(event.getGuild()).getAudioPlayer().stopTrack();
        MessageUtils.replyWithEmbed(event, MessageUtils.buildTrackInfoEmbed("Skipped", playingTrack));
    }
}
