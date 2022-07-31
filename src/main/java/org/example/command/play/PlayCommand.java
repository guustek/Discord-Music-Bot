package org.example.command.play;

import com.freya02.botcommands.api.application.slash.GuildSlashEvent;
import com.freya02.botcommands.api.prefixed.BaseCommandEvent;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.Event;
import org.example.MessageUtils;
import org.example.audio.PlayerManager;
import org.example.command.general.BaseCommand;

import java.net.MalformedURLException;
import java.net.URL;

public class PlayCommand extends BaseCommand {

    public static final String NAME = "play";
    public static final String DESCRIPTION = "Play track or playlist.";

    @Override
    public void execute(GuildSlashEvent event, Object... args) {
        String search = null;
        if (args != null && args.length > 0)
            search = (String) args[0];
        searchAndPlay(event, event.getMember(), search);
    }

    @Override
    public void execute(BaseCommandEvent event, Object... args) {
        String search = null;
        if (args != null && args.length > 0)
            search = (String) args[0];
        searchAndPlay(event, event.getMember(), search);
    }

    private void searchAndPlay(Event event, Member author, String search) {
        if (search == null || search.isEmpty()) {
            MessageUtils.replyWithEmbed(event, MessageUtils.buildBasicEmbed("Search field is empty!"));
            return;
        }
        if (! author.getGuild().getAudioManager().isConnected()) {
            var success = joinAuthorsChannel(event, author);
            if (! success)
                return;
        }
        if (! isUrl(search))
            search = "ytsearch:" + search;
        PlayerManager.getPlayerManager().searchAndLoadTrack(event, author, search);
    }

    private boolean joinAuthorsChannel(Event event, Member author) {
        var authorVoiceState = author.getVoiceState();
        if (authorVoiceState == null) {
            MessageUtils.replyWithEmbed(event, MessageUtils.buildBasicEmbed("Can't read your voice state!"));
            return false;
        }
        var authorsChannel = authorVoiceState.getChannel();
        if (authorsChannel == null) {
            MessageUtils.replyWithEmbed(event, MessageUtils.buildBasicEmbed("You are not present on any voice channel!"));
            return false;
        }
        author.getGuild().getAudioManager().openAudioConnection(authorsChannel);
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
