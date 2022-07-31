package org.example.command.queue;

import com.freya02.botcommands.api.application.slash.GuildSlashEvent;
import com.freya02.botcommands.api.prefixed.BaseCommandEvent;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.Event;
import org.example.MessageUtils;
import org.example.audio.PlayerManager;
import org.example.audio.TrackManager;
import org.example.command.general.BaseCommand;

import java.util.ArrayList;
import java.util.List;

public class QueueCommand extends BaseCommand {

    public static final String NAME = "queue";
    public static final String DESCRIPTION = "List tracks in a queue.";

    @Override
    public void execute(GuildSlashEvent event, Object... args) {
        displayQueue(event, event.getGuild());
    }

    @Override
    public void execute(BaseCommandEvent event, Object... args) {
        displayQueue(event, event.getGuild());
    }

    public void displayQueue(Event event, Guild guild) {
        PlayerManager playerManager = PlayerManager.getPlayerManager();
        TrackManager trackManager = playerManager.getTrackManager(guild);
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
