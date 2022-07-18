package org.example.command.join;

import com.freya02.botcommands.api.annotations.CommandMarker;
import com.freya02.botcommands.api.annotations.Optional;
import com.freya02.botcommands.api.prefixed.BaseCommandEvent;
import com.freya02.botcommands.api.prefixed.TextCommand;
import com.freya02.botcommands.api.prefixed.annotations.JDATextCommand;
import com.freya02.botcommands.api.prefixed.annotations.TextOption;
import net.dv8tion.jda.api.entities.AudioChannel;
import org.example.EmbedMessage;
import org.example.command.ReplyType;

@CommandMarker
public class JoinTextCommand extends TextCommand {

    @JDATextCommand(name = "join", description = "Join voice channel")
    public void handle(
            BaseCommandEvent event,
            @Optional @TextOption(name = "Channel name") String channelName) {

        if (channelName != null && ! channelName.isEmpty()) {
            handleJoinSpecificChannel(event, channelName);
            return;
        }
        handleJoinAuthorsChannel(event);
    }

    private void handleJoinAuthorsChannel(BaseCommandEvent event) {
        var author = event.getMember();
        var authorVoiceState = author.getVoiceState();
        if (authorVoiceState == null) {
            event.reply(EmbedMessage.replyEmbed(ReplyType.ERROR, "Dont have VOICE_STATE cache enabled!")).queue();
            return;
        }
        var authorsChannel = authorVoiceState.getChannel();
        if (authorsChannel == null) {
            event.reply(EmbedMessage.replyEmbed(ReplyType.ERROR, "You are not present on any voice channel!")).queue();
            return;
        }
        joinChannel(event, authorsChannel);
    }

    private void handleJoinSpecificChannel(BaseCommandEvent event, String channelName) {
        var voiceChannel = event.getGuild().getVoiceChannelsByName(channelName, true).stream().findFirst();
        if (voiceChannel.isPresent()) {
            joinChannel(event, voiceChannel.get());
        }
        else {
            event.reply(EmbedMessage.replyEmbed(ReplyType.ERROR, "Channel + " + channelName + " not found!")).queue();
        }
    }

    public void joinChannel(BaseCommandEvent event, AudioChannel authorsChannel) {
        var audioManager = event.getGuild().getAudioManager();
        var connectedChannel = audioManager.getConnectedChannel();
        audioManager.openAudioConnection(authorsChannel);
        if (connectedChannel == null || ! connectedChannel.getId().equals(authorsChannel.getId())) {
            event.reply(EmbedMessage.replyEmbed(ReplyType.SUCCESS, "Connected to " + authorsChannel.getName())).queue();
            return;
        }
        event.reply(EmbedMessage.replyEmbed(ReplyType.SUCCESS, "Already in " + connectedChannel.getName())).queue();
    }


}
