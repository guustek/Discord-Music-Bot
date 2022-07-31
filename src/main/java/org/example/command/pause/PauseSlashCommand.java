package org.example.command.pause;

import com.freya02.botcommands.api.application.ApplicationCommand;
import com.freya02.botcommands.api.application.slash.GuildSlashEvent;
import com.freya02.botcommands.api.application.slash.annotations.JDASlashCommand;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import net.dv8tion.jda.api.utils.MarkdownUtil;
import org.example.MessageUtils;
import org.example.audio.PlayerManager;

public class PauseSlashCommand extends ApplicationCommand {

    @JDASlashCommand(name = "pause", description = "Pause player")
    public void handle(GuildSlashEvent event) {
        event.deferReply().queue();
        AudioPlayer audioPlayer = PlayerManager.getPlayerManager()
                .getTrackManager(event.getGuild())
                .getAudioPlayer();
        var playingTrack = audioPlayer.getPlayingTrack();
        if (playingTrack == null) {
            MessageUtils.replyWithEmbed(event, MessageUtils.buildBasicEmbed("No track is playing"));
            return;
        }
        audioPlayer.setPaused(true);
        MessageUtils.replyWithText(event, MarkdownUtil.bold(":pause_button: Paused. Use /resume to resume"));
    }
}