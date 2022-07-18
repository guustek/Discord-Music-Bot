package org.example.audio;

import com.freya02.botcommands.api.application.slash.GuildSlashEvent;
import com.freya02.botcommands.api.prefixed.BaseCommandEvent;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.Guild;
import org.example.EmbedMessage;
import org.example.command.ReplyType;

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

    public void searchAndLoadTrack(GuildSlashEvent event, String audioUrl) {
        TrackManager manager = this.getTrackManager(event.getGuild());
        this.audioPlayerManager.loadItemOrdered(
                manager,
                audioUrl,
                new TrackLoadResultHandler(manager.getScheduler(), event)
        );
    }

    public void skip(GuildSlashEvent event) {
        AudioTrack playingTrack = this.getTrackManager(event.getGuild()).getAudioPlayer().getPlayingTrack();
        if (playingTrack == null) {
            event.getHook().editOriginalEmbeds(EmbedMessage.replyEmbed(ReplyType.INFO, "Nothing is playing")).queue();
            return;
        }
        this.getTrackManager(event.getGuild()).getAudioPlayer().stopTrack();
        event.getHook().editOriginalEmbeds(EmbedMessage.audioInfoEmbed("Skipped", playingTrack)).queue();
    }

    public void skip(BaseCommandEvent event) {
        AudioTrack playingTrack = this.getTrackManager(event.getGuild()).getAudioPlayer().getPlayingTrack();
        if (playingTrack == null) {
            event.reply(EmbedMessage.replyEmbed(ReplyType.INFO, "Nothing is playing")).queue();
            return;
        }
        this.getTrackManager(event.getGuild()).getAudioPlayer().stopTrack();
        event.reply(EmbedMessage.audioInfoEmbed("Skipped", playingTrack)).queue();
    }
}
