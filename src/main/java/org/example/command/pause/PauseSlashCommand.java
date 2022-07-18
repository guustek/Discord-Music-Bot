package org.example.command.pause;

import com.freya02.botcommands.api.application.ApplicationCommand;
import com.freya02.botcommands.api.application.slash.GuildSlashEvent;
import com.freya02.botcommands.api.application.slash.annotations.JDASlashCommand;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import net.dv8tion.jda.api.utils.MarkdownUtil;
import org.example.EmbedMessage;
import org.example.audio.PlayerManager;
import org.example.command.ReplyType;

public class PauseSlashCommand extends ApplicationCommand {

    @JDASlashCommand(name = "pause", description = "Pause player")
    public void handle(GuildSlashEvent event) {
        event.deferReply().queue();
        AudioPlayer audioPlayer = PlayerManager.getPlayerManager()
                .getTrackManager(event.getGuild())
                .getAudioPlayer();
        var playingTrack = audioPlayer.getPlayingTrack();
        if (playingTrack == null) {
            EmbedMessage.replyWithEmbed(event, EmbedMessage.buildBasicEmbed(ReplyType.INFO, "No track is playing"));
            return;
        }
        audioPlayer.setPaused(true);
        EmbedMessage.replyWithText(event, MarkdownUtil.bold(":pause_button: Paused. Use /resume to resume"));
    }
}