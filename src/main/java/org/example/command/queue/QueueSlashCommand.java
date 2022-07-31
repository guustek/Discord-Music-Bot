package org.example.command.queue;

import com.freya02.botcommands.api.annotations.CommandMarker;
import com.freya02.botcommands.api.application.ApplicationCommand;
import com.freya02.botcommands.api.application.slash.GuildSlashEvent;
import com.freya02.botcommands.api.application.slash.annotations.JDASlashCommand;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.example.MessageUtils;
import org.example.audio.PlayerManager;
import org.example.audio.TrackManager;

import java.util.ArrayList;
import java.util.List;

@CommandMarker
public class QueueSlashCommand extends ApplicationCommand {

    @JDASlashCommand(name = "queue", description = "List songs in a queue")
    public void handle(GuildSlashEvent event) {
        event.deferReply().queue();
        PlayerManager playerManager = PlayerManager.getPlayerManager();
        TrackManager trackManager = playerManager.getTrackManager(event.getGuild());
        AudioTrack playingTrack = trackManager.getAudioPlayer().getPlayingTrack();
        var queue = trackManager
                .getScheduler()
                .getQueue();
        List<MessageEmbed> embedList = new ArrayList<>();
        if (playingTrack != null)
            embedList.add(MessageUtils.buildTrackInfoEmbed("Currently playing", playingTrack));
        else {
            if (queue.isEmpty()) {
                MessageUtils.replyWithEmbed(event, MessageUtils.buildBasicEmbed("Queue is empty"));
                return;
            }
        }
        embedList.addAll(queue.stream()
                .map(audioTrack -> MessageUtils.buildTrackInfoEmbed("Waiting", audioTrack))
                .toList());
        embedList = MessageUtils.limitTracksEmbeds(embedList);
        MessageUtils.replyWithEmbed(event, embedList);
    }
}