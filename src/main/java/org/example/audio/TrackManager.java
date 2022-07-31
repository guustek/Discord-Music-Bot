package org.example.audio;

import com.sedmelluq.discord.lavaplayer.filter.equalizer.EqualizerFactory;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import lombok.Getter;

@Getter
public class TrackManager {

    private static final float[] BASS_BOOST = {
            0.2f,
            0.15f,
            0.1f,
            0.05f,
            0.0f,
            - 0.05f,
            - 0.1f,
            - 0.1f,
            - 0.1f,
            - 0.1f,
            - 0.1f,
            - 0.1f,
            - 0.1f,
            - 0.1f,
            - 0.1f
    };

    private final AudioPlayer audioPlayer;

    private final TrackScheduler scheduler;

    private final TrackSendHandler sendHandler;

    public TrackManager(AudioPlayerManager audioPlayerManager) {
        this.audioPlayer = audioPlayerManager.createPlayer();
        this.scheduler = new TrackScheduler(audioPlayer);
        this.audioPlayer.addListener(this.scheduler);
        this.audioPlayer.setFrameBufferDuration(500);
        this.sendHandler = new TrackSendHandler(this.audioPlayer);
    }

    public void bassBoost(float percentage) {
        if (percentage == 0) {
            this.audioPlayer.setFilterFactory(null);
            return;
        }
        var equalizerFactory = new EqualizerFactory();
        this.audioPlayer.setFilterFactory(equalizerFactory);
        final float multiplier = percentage / 100.00f;

        for (int i = 0; i < BASS_BOOST.length; i++) {
            equalizerFactory.setGain(i, BASS_BOOST[i] * multiplier);
        }
    }
}
