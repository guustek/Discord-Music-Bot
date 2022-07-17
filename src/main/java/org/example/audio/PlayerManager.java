package org.example.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import net.dv8tion.jda.api.entities.AudioChannel;
import net.dv8tion.jda.api.entities.Guild;

import java.util.HashMap;
import java.util.Map;

public class PlayerManager {

    private static PlayerManager playerManager;

    public static PlayerManager getPlayerManager() {
        if (playerManager == null)
            playerManager = new PlayerManager();
        return playerManager;
    }

    private final Map<Long, AudioManager> audioManagers;
    private final AudioPlayerManager audioPlayerManager;

    private PlayerManager() {
        this.audioManagers = new HashMap<>();
        this.audioPlayerManager = new DefaultAudioPlayerManager();

        AudioSourceManagers.registerRemoteSources(this.audioPlayerManager);
        AudioSourceManagers.registerLocalSource(this.audioPlayerManager);
    }

    public AudioManager getAudioManager(Guild guild) {
        return this.audioManagers.computeIfAbsent(guild.getIdLong(), guildId -> {
            final AudioManager manager = new AudioManager(this.audioPlayerManager);
            guild.getAudioManager().setSendingHandler(manager.getSendHandler());
            return manager;
        });
    }

    public void loadAndPlay(AudioChannel channel, String audioUrl) {
        AudioManager manager = this.getAudioManager(channel.getGuild());
        this.audioPlayerManager.loadItemOrdered(
                manager,
                audioUrl,
                new AudioLoadResultHandlerImpl(manager.getScheduler())
        );
    }
}
