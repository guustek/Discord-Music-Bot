package org.example.command.resume;

import com.freya02.botcommands.api.application.ApplicationCommand;
import com.freya02.botcommands.api.application.slash.GuildSlashEvent;
import com.freya02.botcommands.api.application.slash.annotations.JDASlashCommand;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import net.dv8tion.jda.api.utils.MarkdownUtil;
import org.example.MessageUtils;
import org.example.audio.PlayerManager;

public class ResumeSlashCommand extends ApplicationCommand {

    @JDASlashCommand(name = "resume", description = "Resume player")
    public void handle(GuildSlashEvent event) {
        event.deferReply().queue();
        AudioPlayer audioPlayer = PlayerManager.getPlayerManager()
                .getTrackManager(event.getGuild())
                .getAudioPlayer();
        if (! audioPlayer.isPaused()) {
            MessageUtils.replyWithEmbed(event, MessageUtils.buildBasicEmbed("Player is not paused"));
            return;
        }
        audioPlayer.setPaused(false);
        MessageUtils.replyWithText(event, MarkdownUtil.bold(":play_pause: Resumed player"));
    }
}
