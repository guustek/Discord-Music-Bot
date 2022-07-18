package org.example.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import lombok.Getter;

@Getter
public class TrackManager {

    private final AudioPlayer audioPlayer;

    private final TrackScheduler scheduler;

    private final TrackSendHandler sendHandler;

    public TrackManager(AudioPlayerManager audioPlayerManager) {
        this.audioPlayer = audioPlayerManager.createPlayer();
        this.scheduler = new TrackScheduler(audioPlayer);
        this.audioPlayer.addListener(this.scheduler);
        this.sendHandler = new TrackSendHandler(this.audioPlayer);
    }
}
