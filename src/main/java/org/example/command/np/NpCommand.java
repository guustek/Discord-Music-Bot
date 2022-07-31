package org.example.command.np;

import com.freya02.botcommands.api.application.slash.GuildSlashEvent;
import com.freya02.botcommands.api.prefixed.BaseCommandEvent;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.Event;
import org.example.MessageUtils;
import org.example.audio.PlayerManager;
import org.example.command.general.BaseCommand;

public class NpCommand extends BaseCommand {

    public static final String NAME = "np";
    public static final String DESCRIPTION = "Display currently playing track.";

    @Override
    public void execute(GuildSlashEvent event, Object... args) {
        displayPlayingTrack(event, event.getGuild());
    }

    @Override
    public void execute(BaseCommandEvent event, Object... args) {
        displayPlayingTrack(event, event.getGuild());
    }

    private void displayPlayingTrack(Event event, Guild guild) {
        var playingTrack = PlayerManager.getPlayerManager()
                .getTrackManager(guild)
                .getAudioPlayer()
                .getPlayingTrack();
        if (playingTrack == null)
            MessageUtils.replyWithEmbed(event, MessageUtils.buildBasicEmbed("No track playing now"));
        else
            MessageUtils.replyWithEmbed(event, MessageUtils.buildTrackInfoEmbed("Currently playing ", playingTrack));
    }
}
