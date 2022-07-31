package org.example.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.Event;

import java.util.HashMap;
import java.util.Map;

public class PlayerManager {

    private static PlayerManager playerManager;

    public static PlayerManager getPlayerManager() {
        if (playerManager == null)
            playerManager = new PlayerManager();
        return playerManager;
    }

    private final Map<Long, TrackManager> trackManagers;

    private final AudioPlayerManager audioPlayerManager;

    private PlayerManager() {
        this.trackManagers = new HashMap<>();
        this.audioPlayerManager = new DefaultAudioPlayerManager();
        this.audioPlayerManager.getConfiguration().setFilterHotSwapEnabled(true);

        AudioSourceManagers.registerRemoteSources(this.audioPlayerManager);
        AudioSourceManagers.registerLocalSource(this.audioPlayerManager);
    }

    public TrackManager getTrackManager(Guild guild) {
        return this.trackManagers.computeIfAbsent(guild.getIdLong(), guildId -> {
            final TrackManager manager = new TrackManager(this.audioPlayerManager);
            guild.getAudioManager().setSendingHandler(manager.getSendHandler());
            return manager;
        });
    }

    public void searchAndLoadTrack(Event event, Member author, String audioUrl) {
        TrackManager manager = this.getTrackManager(author.getGuild());
        this.audioPlayerManager.loadItemOrdered(
                manager,
                audioUrl,
                new TrackLoadResultHandler(manager.getScheduler(), event, author.getUser())
        );
    }

}
