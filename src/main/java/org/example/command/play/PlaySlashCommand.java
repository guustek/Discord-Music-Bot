package org.example.command.play;

import com.freya02.botcommands.api.annotations.CommandMarker;
import com.freya02.botcommands.api.application.ApplicationCommand;
import com.freya02.botcommands.api.application.annotations.AppOption;
import com.freya02.botcommands.api.application.slash.GuildSlashEvent;
import com.freya02.botcommands.api.application.slash.annotations.JDASlashCommand;
import org.example.MessageUtils;
import org.example.audio.PlayerManager;

import java.net.MalformedURLException;
import java.net.URL;

@CommandMarker
public class PlaySlashCommand extends ApplicationCommand {

    @JDASlashCommand(name = "play", description = "Play track or playlist")
    public void play(
            GuildSlashEvent event,
            @AppOption(name = "search", description = "Url or name") String search) {
        event.deferReply().queue();
        if (! event.getGuild().getAudioManager().isConnected()) {
            var success = joinAuthorsChannel(event);
            if (! success)
                return;
        }
        if (!isUrl(search))
            search = "ytsearch:" + search;
        PlayerManager.getPlayerManager().searchAndLoadTrack(event, search);
    }

    private boolean joinAuthorsChannel(GuildSlashEvent event) {
        var author = event.getMember();
        var authorVoiceState = author.getVoiceState();
        if (authorVoiceState == null) {
            MessageUtils.replyWithEmbed(event, MessageUtils.buildBasicEmbed("Dont have VOICE_STATE cache enabled!"));
            return false;
        }
        var authorsChannel = authorVoiceState.getChannel();
        if (authorsChannel == null) {
            MessageUtils.replyWithEmbed(event, MessageUtils.buildBasicEmbed("You are not present on any voice channel!"));
            return false;
        }
        event.getGuild().getAudioManager().openAudioConnection(authorsChannel);
        return true;
    }

    private boolean isUrl(String string) {
        try {
            new URL(string);
            return true;
        } catch (MalformedURLException e) {
            return false;
        }
    }
}
