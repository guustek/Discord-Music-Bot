package org.example.audio;

import com.freya02.botcommands.api.application.slash.GuildSlashEvent;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.example.EmbedMessage;

import java.util.ArrayList;
import java.util.List;

public class TrackLoadResultHandler implements AudioLoadResultHandler {

    private final TrackScheduler trackScheduler;
    private final GuildSlashEvent event;

    public TrackLoadResultHandler(TrackScheduler trackScheduler, GuildSlashEvent event) {
        this.trackScheduler = trackScheduler;
        this.event = event;
    }

    @Override
    public void trackLoaded(AudioTrack audioTrack) {
        var startedPlaying = trackScheduler.queue(audioTrack, event);
        if (startedPlaying)
            event.getHook().editOriginalEmbeds(EmbedMessage.audioInfoEmbed("Currently playing", audioTrack)).queue();
        else
            event.getHook().editOriginalEmbeds(EmbedMessage.audioInfoEmbed("Added to queue", audioTrack)).queue();

    }

    @Override
    public void playlistLoaded(AudioPlaylist audioPlaylist) {
        var firstTrack = audioPlaylist.getTracks().get(0);
        AudioTrack playingTrack = trackScheduler.getAudioPlayer().getPlayingTrack();
        if (audioPlaylist.isSearchResult()) {
            var startedPlaying = trackScheduler.queue(firstTrack, event);
            if (startedPlaying)
                event.getHook().editOriginalEmbeds(EmbedMessage.audioInfoEmbed("Currently playing", firstTrack)).queue();
            else
                event.getHook().editOriginalEmbeds(EmbedMessage.audioInfoEmbed("Added to queue", firstTrack)).queue();
        }
        else {
            List<MessageEmbed> embedList = new ArrayList<>();
            if (playingTrack == null) {
                trackScheduler.queue(firstTrack, event);
                embedList.add(EmbedMessage.audioInfoEmbed("Currently playing", firstTrack));
            }
            audioPlaylist.getTracks().stream().skip(1).forEach(track -> trackScheduler.queue(track, event));

            embedList.addAll(trackScheduler.getQueue().stream()
                    .map(audioTrack -> EmbedMessage.audioInfoEmbed("Added to queue", audioTrack))
                    .toList());
            embedList = EmbedMessage.limitTracksEmbeds(embedList);
            event.getHook().editOriginalEmbeds(embedList).queue();
        }
    }

    @Override
    public void noMatches() {
        event.getHook().editOriginal("Not found").queue();
    }

    @Override
    public void loadFailed(FriendlyException e) {
        event.getHook().editOriginal("Load failed").queue();
    }
}
