package org.example.command.pope;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;
import org.example.audio.PlayerManager;
import org.example.audio.TrackScheduler;

import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;

public class PopeService implements Runnable {

    private final ScheduledExecutorService executorService;
    private final JDA jda;

    public PopeService(JDA jda) {
        this.jda = jda;
        executorService = new ScheduledThreadPoolExecutor(1);
        executorService.schedule(this, calculateDelay(), TimeUnit.SECONDS);
    }

    @Override
    public void run() {
        executorService.schedule(this, calculateDelay(), TimeUnit.SECONDS);
        jda.getGuilds()
                .forEach(guild -> playBareczka(guild.getIdLong()));
    }

    private void playBareczka(Long guildId) {
        Guild guild = jda.getGuildById(guildId);
        if (guild == null) {
            System.out.println("Could not find guild with id " + guildId);
            return;
        }
        List<VoiceChannel> voiceChannels = guild.getVoiceChannels();
        if (voiceChannels.isEmpty())
            return;
        VoiceChannel channelToPlay = voiceChannels.stream()
                .max(Comparator.comparingInt(o -> o.getMembers().size())).get();

        AudioManager audioManager = guild.getAudioManager();
        audioManager.openAudioConnection(channelToPlay);

        TrackScheduler scheduler = PlayerManager.getPlayerManager().getTrackManager(guild).getScheduler();

        BlockingDeque<AudioTrack> queue = new LinkedBlockingDeque<>(scheduler.getQueue());
        AudioTrack playingTrack = scheduler.getAudioPlayer().getPlayingTrack();
        if (playingTrack != null)
            queue.addFirst(playingTrack);

        scheduler.getQueue().clear();
        scheduler.getAudioPlayer().stopTrack();

        PlayerManager.getPlayerManager().searchAndLoadTrack(null, guild.getSelfMember(), "https://www.youtube.com/watch?v=1dOt_VcbgyA");

        for(AudioTrack track : queue){
            PlayerManager.getPlayerManager().searchAndLoadTrack(
                    null,
                    Objects.requireNonNull(guild.getMember((User) track.getUserData())),
                    track.getInfo().uri);
        }
        System.out.println("Bareczka jest grana na " + guild + "/" + channelToPlay.getName());
    }

    public long calculateDelay() {
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Europe/Warsaw"));
        ZonedDateTime next = now.withHour(21).withMinute(37).withSecond(0);
        if (now.compareTo(next) > 0)
            next = next.plusDays(1);
        Duration duration = Duration.between(now, next);
        return duration.getSeconds();
    }
}
