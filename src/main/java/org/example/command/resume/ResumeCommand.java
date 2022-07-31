package org.example.command.resume;

import com.freya02.botcommands.api.application.slash.GuildSlashEvent;
import com.freya02.botcommands.api.prefixed.BaseCommandEvent;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.utils.MarkdownUtil;
import org.example.MessageUtils;
import org.example.audio.PlayerManager;
import org.example.command.general.BaseCommand;

public class ResumeCommand extends BaseCommand {

    public static final String NAME = "resume";
    public static final String DESCRIPTION = "Resume player.";

    @Override
    public void execute(GuildSlashEvent event, Object... args) {
        resumeTrack(event, event.getGuild());
    }

    @Override
    public void execute(BaseCommandEvent event, Object... args) {
        resumeTrack(event, event.getGuild());
    }

    private void resumeTrack(Event event, Guild guild) {
        AudioPlayer audioPlayer = PlayerManager.getPlayerManager()
                .getTrackManager(guild)
                .getAudioPlayer();
        if (! audioPlayer.isPaused()) {
            MessageUtils.replyWithEmbed(event, MessageUtils.buildBasicEmbed("Player is not paused"));
            return;
        }
        audioPlayer.setPaused(false);
        MessageUtils.replyWithText(event, MarkdownUtil.bold(":play_pause: Resumed player"));
    }
}
