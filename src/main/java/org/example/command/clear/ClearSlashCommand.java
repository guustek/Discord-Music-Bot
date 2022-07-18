package org.example.command.clear;

import com.freya02.botcommands.api.application.ApplicationCommand;
import com.freya02.botcommands.api.application.slash.GuildSlashEvent;
import com.freya02.botcommands.api.application.slash.annotations.JDASlashCommand;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import org.example.EmbedMessage;
import org.example.audio.PlayerManager;
import org.example.command.ReplyType;

import java.util.concurrent.BlockingDeque;

public class ClearSlashCommand extends ApplicationCommand {

    @JDASlashCommand(name = "clear", description = "Clears the track queue. This command does not remove currently playing track")
    public void handle(GuildSlashEvent event) {
        event.deferReply().queue();
        BlockingDeque<AudioTrack> queue = PlayerManager.getPlayerManager()
                .getTrackManager(event.getGuild())
                .getScheduler()
                .getQueue();
        var removedTracksCount = queue.size();
        var message = "Removed " + removedTracksCount + " tracks from queue.";
        if (removedTracksCount == 1)
            message = "Removed 1 track from queue.";
        queue.clear();
        event.getHook().editOriginalEmbeds(EmbedMessage.replyEmbed(ReplyType.SUCCESS, message)).queue();
    }
}
