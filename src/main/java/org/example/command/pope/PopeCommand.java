package org.example.command.pope;

import com.freya02.botcommands.api.application.slash.GuildSlashEvent;
import com.freya02.botcommands.api.prefixed.BaseCommandEvent;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;
import org.example.audio.PlayerManager;
import org.example.command.general.BaseCommand;

import java.time.LocalTime;
import java.util.List;

public class PopeCommand extends BaseCommand {

    public static final String NAME = "pope";
    public static final String DESCRIPTION = "2137.";

    @Override
    public void execute(GuildSlashEvent event, Object... args) {

    }

    @Override
    public void execute(BaseCommandEvent event, Object... args) {
        System.out.println("Papieżowa komenda uruchomiona");
        var timeNow = LocalTime.now();
        System.out.println("Aktualny czas: " + timeNow);
        notify(event.getGuild());
    }

    private void notify(Guild guild) {
        List<VoiceChannel> voiceChannels = guild.getVoiceChannels();
        VoiceChannel channel = voiceChannels.stream().filter(voiceChannel -> voiceChannel.getName().equals("Ogólne")).findFirst().get();
        AudioManager audioManager = guild.getAudioManager();
        audioManager.openAudioConnection(channel);
        PlayerManager.getPlayerManager().searchAndLoadTrack(null, guild.getSelfMember(), "https://www.youtube.com/watch?v=1dOt_VcbgyA");
    }
}
