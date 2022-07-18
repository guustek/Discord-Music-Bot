package org.example.command.clear;

import com.freya02.botcommands.api.prefixed.BaseCommandEvent;
import com.freya02.botcommands.api.prefixed.TextCommand;
import com.freya02.botcommands.api.prefixed.annotations.JDATextCommand;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import org.example.EmbedMessage;
import org.example.audio.PlayerManager;
import org.example.command.ReplyType;

import java.util.concurrent.BlockingDeque;

public class ClearTextCommand extends TextCommand {
    @JDATextCommand(name = "clear", description = "Clears the track queue. This command does not remove currently playing track")
    public void handle(BaseCommandEvent event) {
        BlockingDeque<AudioTrack> queue = PlayerManager.getPlayerManager()
                .getTrackManager(event.getGuild())
                .getScheduler()
                .getQueue();
        var removedTracksCount = queue.size();
        var message = "Removed " + removedTracksCount + " tracks from queue.";
        if (removedTracksCount == 1)
            message = "Removed 1 track from queue.";
        queue.clear();
        EmbedMessage.replyWithEmbed(event, EmbedMessage.buildBasicEmbed(ReplyType.SUCCESS, message));
    }
}
