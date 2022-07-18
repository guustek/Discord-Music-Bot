package org.example.command.queue;

import com.freya02.botcommands.api.annotations.CommandMarker;
import com.freya02.botcommands.api.application.ApplicationCommand;
import com.freya02.botcommands.api.application.slash.GuildSlashEvent;
import com.freya02.botcommands.api.application.slash.annotations.JDASlashCommand;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.example.EmbedMessage;
import org.example.audio.PlayerManager;
import org.example.audio.TrackManager;
import org.example.command.ReplyType;

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
            embedList.add(EmbedMessage.buildTrackInfoEmbed("Currently playing", playingTrack));
        else {
            if (queue.isEmpty()) {
                EmbedMessage.replyWithEmbed(event, EmbedMessage.buildBasicEmbed(ReplyType.SUCCESS, "Queue is empty"));
                return;
            }
        }
        embedList.addAll(queue.stream()
                .map(audioTrack -> EmbedMessage.buildTrackInfoEmbed("Waiting", audioTrack))
                .toList());
        embedList = EmbedMessage.limitTracksEmbeds(embedList);
        EmbedMessage.replyWithEmbed(event, embedList);
    }
}