package org.example.command.resume;

import com.freya02.botcommands.api.application.ApplicationCommand;
import com.freya02.botcommands.api.application.slash.GuildSlashEvent;
import com.freya02.botcommands.api.application.slash.annotations.JDASlashCommand;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import net.dv8tion.jda.api.utils.MarkdownUtil;
import org.example.EmbedMessage;
import org.example.audio.PlayerManager;
import org.example.command.ReplyType;

public class ResumeSlashCommand extends ApplicationCommand {

    @JDASlashCommand(name = "resume", description = "Resume player")
    public void handle(GuildSlashEvent event) {
        event.deferReply().queue();
        AudioPlayer audioPlayer = PlayerManager.getPlayerManager()
                .getTrackManager(event.getGuild())
                .getAudioPlayer();
        if (! audioPlayer.isPaused()) {
            event.getHook().editOriginalEmbeds(EmbedMessage.replyEmbed(ReplyType.INFO, "Player is not paused")).queue();
            return;
        }
        audioPlayer.setPaused(false);
        event.getHook().editOriginal(MarkdownUtil.bold(":play_pause: Resumed player")).queue();
    }
}
