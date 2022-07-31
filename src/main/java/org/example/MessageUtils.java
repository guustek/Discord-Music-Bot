package org.example;

import com.freya02.botcommands.api.application.slash.GuildSlashEvent;
import com.freya02.botcommands.api.prefixed.BaseCommandEvent;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MessageUtils {

    public static List<MessageEmbed> limitTracksEmbeds(List<MessageEmbed> list) {
        List<MessageEmbed> result = new ArrayList<>(list);
        var resultAmount = result.size();
        result = result.stream().limit(10).collect(Collectors.toList());
        if (resultAmount > 10)
            result.set(
                    result.size() - 1,
                    MessageUtils.buildBasicEmbed("And " + (resultAmount - 9) + " more tracks")
            );
        return result;
    }

    public static void replyWithEmbed(Event event, MessageEmbed embed) {
        replyWithEmbed(event, List.of(embed));
    }

    public static void replyWithEmbed(Event event, List<MessageEmbed> embeds) {
        if(event==null)
            return;
        if (event instanceof GuildSlashEvent slashEvent) {
            if (slashEvent.isAcknowledged())
                slashEvent.getHook().editOriginalEmbeds(embeds).queue();
            else
                slashEvent.replyEmbeds(embeds).queue();
            return;
        }

        if (event instanceof BaseCommandEvent textEvent) {
            var firstEmbed = embeds.get(0);
            var sublist = embeds.stream().skip(1).toArray(MessageEmbed[] :: new);
            textEvent.reply(firstEmbed, sublist).queue();
        }
    }

    public static void replyWithText(Event event, String text) {
        if (event instanceof GuildSlashEvent slashEvent) {
            if (slashEvent.isAcknowledged())
                slashEvent.getHook().editOriginal(text).queue();
            else
                slashEvent.reply(text).queue();
            return;
        }

        if (event instanceof BaseCommandEvent textEvent) {
            textEvent.reply(text).queue();
        }
    }

    public static MessageEmbed buildBasicEmbed(String text) {
        return new EmbedBuilder()
                .setDescription(text)
                .build();
    }

    public static MessageEmbed buildTrackInfoEmbed(String title, AudioTrack track) {
        return new EmbedBuilder()
                .setTitle(title)
                .addField("", String.format("[%s](%s)", track.getInfo().title, track.getInfo().uri), true)
                .setFooter(track.getUserData(User.class).getName(), track.getUserData(User.class).getAvatarUrl())
                .setThumbnail("https://img.youtube.com/vi/" + track.getIdentifier() + "/default.jpg")
                .build();
    }
}
