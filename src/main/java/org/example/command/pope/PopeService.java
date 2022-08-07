package org.example.command.pope;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;
import org.example.audio.PlayerManager;
import org.example.audio.TrackScheduler;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.*;

public class PopeService extends ListenerAdapter implements Runnable {

    private final static String SERIALIZATION_FILE_NAME = "PopeServiceGuilds.bin";

    private final Map<Long, Boolean> guildEnabled;
    private final ScheduledExecutorService executorService;
    private final JDA jda;

    public PopeService(JDA jda) {
        this.jda = jda;
        guildEnabled = deserializeEnabledOnGuildMap();
        Runtime.getRuntime().addShutdownHook(new Thread(this :: serializeEnabledOnGuildMap));
        executorService = new ScheduledThreadPoolExecutor(1);
        executorService.schedule(this, calculateDelay(), TimeUnit.SECONDS);
    }

    @Override
    public void run() {
        executorService.schedule(this, calculateDelay(), TimeUnit.SECONDS);
        guildEnabled.entrySet().stream()
                .filter(Map.Entry :: getValue)
                .forEach(entry -> playBareczka(entry.getKey()));
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

    private long calculateDelay() {
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Europe/Warsaw"));
        ZonedDateTime next = now.withHour(21).withMinute(37).withSecond(0);
        if (now.compareTo(next) >= 0)
            next = next.plusDays(1);
        Duration duration = Duration.between(now, next);
        return duration.getSeconds();
    }

    private boolean stop() {
        executorService.shutdown();
        try {
            return executorService.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean toggleEnabledOnGuild(long guildId) {
        boolean enabled = guildEnabled.getOrDefault(guildId, false);
        guildEnabled.put(guildId, ! enabled);
        return ! enabled;
    }

    @SuppressWarnings("unchecked")
    private Map<Long, Boolean> deserializeEnabledOnGuildMap() {
        System.out.println("Deserializing " + SERIALIZATION_FILE_NAME);
        Map<Long, Boolean> deserializedMap = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(SERIALIZATION_FILE_NAME);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            deserializedMap = (Map<Long, Boolean>) objectInputStream.readObject();

            objectInputStream.close();
            fileInputStream.close();
        } catch (ClassNotFoundException | IOException e) {
            System.out.println("Failed to deserialize " + SERIALIZATION_FILE_NAME);
            e.printStackTrace();
        }
        return deserializedMap != null ? deserializedMap : new HashMap<>();
    }

    private void serializeEnabledOnGuildMap() {
        System.out.println("Serializing " + guildEnabled.toString() + " to " + SERIALIZATION_FILE_NAME);
        try {
            FileOutputStream fileOutStream = new FileOutputStream(SERIALIZATION_FILE_NAME);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutStream);
            objectOutputStream.writeObject(guildEnabled);
            objectOutputStream.close();
            fileOutStream.close();
        } catch (IOException e) {
            System.out.println("Failed to serialize " + guildEnabled);
            e.printStackTrace();
        }
    }

    @Override
    public void onGuildLeave(@NotNull GuildLeaveEvent event) {
        guildEnabled.remove(event.getGuild().getIdLong());
    }
}
