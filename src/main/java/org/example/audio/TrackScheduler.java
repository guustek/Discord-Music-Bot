package org.example.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import lombok.Getter;
import net.dv8tion.jda.api.entities.User;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

@Getter
public class TrackScheduler extends AudioEventAdapter {

    private final AudioPlayer audioPlayer;
    private final BlockingDeque<AudioTrack> queue;

    public TrackScheduler(AudioPlayer audioPlayer) {
        this.audioPlayer = audioPlayer;
        this.queue = new LinkedBlockingDeque<>();
    }

    public boolean queue(AudioTrack track, User user) {
        track.setUserData(user);
        var startedPlaying = this.audioPlayer.startTrack(track, true);
        if (! startedPlaying) {
            queue.add(track);
        }
        return startedPlaying;
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        if (endReason.mayStartNext || endReason == AudioTrackEndReason.STOPPED) {
            if (! this.queue.isEmpty())
                nextTrack();
        }
    }

    private void nextTrack() {
        this.audioPlayer.startTrack(this.queue.poll(), false);
    }
}
