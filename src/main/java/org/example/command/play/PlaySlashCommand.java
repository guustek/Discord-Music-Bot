package org.example.command.play;

import com.freya02.botcommands.api.annotations.CommandMarker;
import com.freya02.botcommands.api.application.ApplicationCommand;
import com.freya02.botcommands.api.application.annotations.AppOption;
import com.freya02.botcommands.api.application.slash.GuildSlashEvent;
import com.freya02.botcommands.api.application.slash.annotations.JDASlashCommand;
import org.example.EmbedMessage;
import org.example.audio.PlayerManager;
import org.example.command.ReplyType;

import java.net.MalformedURLException;
import java.net.URL;

@CommandMarker
public class PlaySlashCommand extends ApplicationCommand {

    @JDASlashCommand(name = "play", description = "Play track or playlist")
    public void playFromUrl(
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
            event.getHook().editOriginalEmbeds(EmbedMessage.replyEmbed(ReplyType.ERROR, "Dont have VOICE_STATE cache enabled!")).queue();
            return false;
        }
        var authorsChannel = authorVoiceState.getChannel();
        if (authorsChannel == null) {
            event.getHook().editOriginalEmbeds(EmbedMessage.replyEmbed(ReplyType.ERROR, "You are not present on any voice channel!")).queue();
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
