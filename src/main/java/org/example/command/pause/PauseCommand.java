package org.example.command.pause;

import com.freya02.botcommands.api.application.slash.GuildSlashEvent;
import com.freya02.botcommands.api.prefixed.BaseCommandEvent;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.utils.MarkdownUtil;
import org.example.MessageUtils;
import org.example.audio.PlayerManager;
import org.example.command.general.BaseCommand;

public class PauseCommand extends BaseCommand {

    public static final String NAME = "pause";
    public static final String DESCRIPTION = "Pause player.";
    @Override
    public void execute(GuildSlashEvent event, Object... args) {
        pausePlayingTrack(event, event.getGuild());
    }

    @Override
    public void execute(BaseCommandEvent event, Object... args) {
        pausePlayingTrack(event, event.getGuild());
    }

    private void pausePlayingTrack(Event event, Guild guild){
        AudioPlayer audioPlayer = PlayerManager.getPlayerManager()
                .getTrackManager(guild)
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
