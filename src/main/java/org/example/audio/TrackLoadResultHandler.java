package org.example.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.Event;
import org.example.MessageUtils;

import java.util.ArrayList;
import java.util.List;

public class TrackLoadResultHandler implements AudioLoadResultHandler {

    private final TrackScheduler trackScheduler;
    private final Event event;
    private final User user;

    public TrackLoadResultHandler(TrackScheduler trackScheduler, Event event, User user) {
        this.trackScheduler = trackScheduler;
        this.event = event;
        this.user = user;
    }

    @Override
    public void trackLoaded(AudioTrack audioTrack) {
        var startedPlaying = trackScheduler.queue(audioTrack, user);
        if (startedPlaying)
            MessageUtils.replyWithEmbed(event, MessageUtils.buildTrackInfoEmbed("Currently playing", audioTrack));
        else
            MessageUtils.replyWithEmbed(event, MessageUtils.buildTrackInfoEmbed("Added to queue", audioTrack));

    }

    @Override
    public void playlistLoaded(AudioPlaylist audioPlaylist) {
        var firstTrack = audioPlaylist.getTracks().get(0);
        AudioTrack playingTrack = trackScheduler.getAudioPlayer().getPlayingTrack();
        if (audioPlaylist.isSearchResult()) {
            var startedPlaying = trackScheduler.queue(firstTrack, user);
            if (startedPlaying)
                MessageUtils.replyWithEmbed(event, MessageUtils.buildTrackInfoEmbed("Currently playing", firstTrack));
            else
                MessageUtils.replyWithEmbed(event, MessageUtils.buildTrackInfoEmbed("Added to queue", firstTrack));
        }
        else {
            List<MessageEmbed> embedList = new ArrayList<>();
            if (playingTrack == null) {
                trackScheduler.queue(firstTrack, user);
                embedList.add(MessageUtils.buildTrackInfoEmbed("Currently playing", firstTrack));
            }
            audioPlaylist.getTracks().stream().skip(1).forEach(track -> trackScheduler.queue(track, user));

            embedList.addAll(trackScheduler.getQueue().stream()
                    .map(audioTrack -> MessageUtils.buildTrackInfoEmbed("Added to queue", audioTrack))
                    .toList());
            embedList = MessageUtils.limitTracksEmbeds(embedList);
            MessageUtils.replyWithEmbed(event, embedList);
        }
    }

    @Override
    public void noMatches() {
        MessageUtils.replyWithEmbed(event, MessageUtils.buildBasicEmbed("Not found"));
    }

    @Override
    public void loadFailed(FriendlyException e) {
        MessageUtils.replyWithEmbed(event, MessageUtils.buildBasicEmbed("Load failed"));
    }
}
