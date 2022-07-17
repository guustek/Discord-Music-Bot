package org.example.command.play;

import com.freya02.botcommands.api.annotations.CommandMarker;
import com.freya02.botcommands.api.application.annotations.AppOption;
import com.freya02.botcommands.api.application.slash.GuildSlashEvent;
import com.freya02.botcommands.api.application.slash.annotations.JDASlashCommand;
import org.example.audio.PlayerManager;
import org.example.command.AbstractSlashCommand;

@CommandMarker
public class PlaySlashCommand extends AbstractSlashCommand {

    @JDASlashCommand(name = "play", description = "Plays specified video audio")
    public void handle(
            GuildSlashEvent event,
            @AppOption(name = "video", description = "Video url") String url) {
        System.out.println(url);
        var author = event.getMember();
        var channel = author.getVoiceState().getChannel();
        event.getGuild().getAudioManager().openAudioConnection(channel);
        PlayerManager.getPlayerManager().loadAndPlay(channel,url);
    }
}
