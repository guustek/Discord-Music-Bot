package org.example.command.join;

import com.freya02.botcommands.api.application.slash.GuildSlashEvent;
import com.freya02.botcommands.api.prefixed.BaseCommandEvent;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.middleman.AudioChannel;
import net.dv8tion.jda.api.events.Event;
import org.example.MessageUtils;
import org.example.command.general.BaseCommand;

import java.util.Optional;

public class JoinCommand extends BaseCommand {

    public static final String NAME = "join";
    public static final String DESCRIPTION = "Join voice channel.";

    @Override
    public void execute(GuildSlashEvent event, Object... args) {
        AudioChannel channel = (AudioChannel) args[0];
        if (channel == null) {
            var optionalAuthorChannel = getAuthorsChannel(event, event.getMember());
            if (optionalAuthorChannel.isEmpty())
                return;
            channel = optionalAuthorChannel.get();
        }
        joinChannel(event, event.getGuild(), channel);
    }

    @Override
    public void execute(BaseCommandEvent event, Object... args) {
        var channelName = (String) args[0];
        AudioChannel channel;
        if (channelName == null || channelName.isEmpty()) {
            var optionalAuthorChannel = getAuthorsChannel(event, event.getMember());
            if (optionalAuthorChannel.isEmpty())
                return;
            channel = optionalAuthorChannel.get();
        }
        else {
            var channelOptional = event.getGuild().getVoiceChannelsByName(channelName, true)
                    .stream()
                    .findFirst();
            if (channelOptional.isEmpty()) {
                MessageUtils.replyWithEmbed(event, MessageUtils.buildBasicEmbed("Could not find channel:  " + channelName));
                return;
            }
            channel = channelOptional.get();
        }
        joinChannel(event, event.getGuild(), channel);
    }

    private Optional<AudioChannel> getAuthorsChannel(Event event, Member author) {
        var authorVoiceState = author.getVoiceState();
        if (authorVoiceState == null) {
            MessageUtils.replyWithEmbed(event, MessageUtils.buildBasicEmbed("Can't read your voice state!"));
            return Optional.empty();
        }
        var channel = authorVoiceState.getChannel();
        if (channel == null)
            MessageUtils.replyWithEmbed(event, MessageUtils.buildBasicEmbed("You are not present on any voice channel!"));
        return Optional.ofNullable(channel);
    }

    private void joinChannel(Event event, Guild guild, AudioChannel channel) {
        var audioManager = guild.getAudioManager();
        var connectedChannel = audioManager.getConnectedChannel();
        audioManager.openAudioConnection(channel);
        if (connectedChannel == null || ! connectedChannel.getId().equals(channel.getId())) {
            MessageUtils.replyWithEmbed(event, MessageUtils.buildBasicEmbed("Connected to " + channel.getName()));
            return;
        }
        MessageUtils.replyWithEmbed(event, MessageUtils.buildBasicEmbed("Already in " + connectedChannel.getName()));
    }
}
