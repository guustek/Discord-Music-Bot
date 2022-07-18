package org.example.command.join;

import com.freya02.botcommands.api.annotations.CommandMarker;
import com.freya02.botcommands.api.annotations.Optional;
import com.freya02.botcommands.api.application.ApplicationCommand;
import com.freya02.botcommands.api.application.annotations.AppOption;
import com.freya02.botcommands.api.application.slash.GuildSlashEvent;
import com.freya02.botcommands.api.application.slash.annotations.JDASlashCommand;
import net.dv8tion.jda.api.entities.AudioChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import org.example.EmbedMessage;
import org.example.command.ReplyType;

@CommandMarker
public class JoinSlashCommand extends ApplicationCommand {

    @JDASlashCommand(name = "join", description = "Join voice channel")
    public void handle(
            GuildSlashEvent event,
            @Optional @AppOption(name = "channel", description = "Channel to join") VoiceChannel channel) {
        event.deferReply().queue();
        if (channel != null) {
            joinChannel(event, channel);
            return;
        }
        handleJoinAuthorsChannel(event);
    }

    private void handleJoinAuthorsChannel(GuildSlashEvent event) {
        var author = event.getMember();
        var authorVoiceState = author.getVoiceState();
        if (authorVoiceState == null) {
            EmbedMessage.replyWithEmbed(event, EmbedMessage.buildBasicEmbed(ReplyType.ERROR, "Dont have VOICE_STATE cache enabled!"));
            return;
        }
        var authorsChannel = authorVoiceState.getChannel();
        if (authorsChannel == null) {
            EmbedMessage.replyWithEmbed(event, EmbedMessage.buildBasicEmbed(ReplyType.ERROR, "You are not present on any voice channel!"));
            return;
        }
        joinChannel(event, authorsChannel);
    }

    public void joinChannel(GuildSlashEvent event, AudioChannel authorsChannel) {
        var audioManager = event.getGuild().getAudioManager();
        var connectedChannel = audioManager.getConnectedChannel();
        audioManager.openAudioConnection(authorsChannel);
        if (connectedChannel == null || ! connectedChannel.getId().equals(authorsChannel.getId())) {
            EmbedMessage.replyWithEmbed(event, EmbedMessage.buildBasicEmbed(ReplyType.SUCCESS, "Connected to " + authorsChannel.getName()));
            return;
        }
        EmbedMessage.replyWithEmbed(event, EmbedMessage.buildBasicEmbed(ReplyType.SUCCESS, "Already in " + connectedChannel.getName()));
    }

}
