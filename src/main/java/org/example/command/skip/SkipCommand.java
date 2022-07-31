package org.example.command.skip;

import com.freya02.botcommands.api.application.slash.GuildSlashEvent;
import com.freya02.botcommands.api.prefixed.BaseCommandEvent;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.Event;
import org.example.MessageUtils;
import org.example.audio.PlayerManager;
import org.example.command.general.BaseCommand;

import java.util.ArrayList;
import java.util.List;

public class SkipCommand extends BaseCommand {

    public static final String NAME = "skip";
    public static final String DESCRIPTION = " Skip currently playing track.";

    @Override
    public void execute(GuildSlashEvent event, Object... args) {
        skip(event, event.getGuild());
    }

    @Override
    public void execute(BaseCommandEvent event, Object... args) {
        skip(event, event.getGuild());
    }

    private void skip(Event event, Guild guild) {
        PlayerManager playerManager = PlayerManager.getPlayerManager();
        AudioTrack playingTrack = playerManager.getTrackManager(guild).getAudioPlayer().getPlayingTrack();
        if (playingTrack == null) {
            MessageUtils.replyWithEmbed(event, MessageUtils.buildBasicEmbed("Nothing is playing"));
            return;
        }
        List<MessageEmbed> embeds = new ArrayList<>();
        embeds.add(MessageUtils.buildTrackInfoEmbed("Skipped", playingTrack));
        playerManager.getTrackManager(guild).getAudioPlayer().stopTrack();
        AudioTrack nextTrack = playerManager.getTrackManager(guild).getAudioPlayer().getPlayingTrack();
        if(nextTrack == null)
            embeds.add(MessageUtils.buildBasicEmbed("Queue is empty"));
        else
            embeds.add(MessageUtils.buildTrackInfoEmbed("Currently playing", nextTrack));
        MessageUtils.replyWithEmbed(event, embeds);
    }
}
