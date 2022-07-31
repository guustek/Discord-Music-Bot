package org.example.command.clear;

import com.freya02.botcommands.api.application.slash.GuildSlashEvent;
import com.freya02.botcommands.api.prefixed.BaseCommandEvent;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.Event;
import org.example.MessageUtils;
import org.example.audio.PlayerManager;
import org.example.command.general.BaseCommand;

import java.util.concurrent.BlockingDeque;

public class ClearCommand extends BaseCommand {

    public static final String NAME = "clear";
    public static final String DESCRIPTION = "Clears the track queue. This command does not remove currently playing track.";

    @Override
    public void execute(GuildSlashEvent event, Object... args) {
        execute(event, event.getGuild());
    }

    @Override
    public void execute(BaseCommandEvent event, Object... args) {
        execute(event, event.getGuild());
    }

    private void execute(Event event, Guild guild) {
        BlockingDeque<AudioTrack> queue = PlayerManager.getPlayerManager()
                .getTrackManager(guild)
                .getScheduler()
                .getQueue();
        var removedTracksCount = queue.size();
        var message = "Removed " + removedTracksCount + " tracks from queue.";
        if (removedTracksCount == 1)
            message = "Removed 1 track from queue.";
        queue.clear();
        MessageUtils.replyWithEmbed(event, MessageUtils.buildBasicEmbed(message));
    }
}
