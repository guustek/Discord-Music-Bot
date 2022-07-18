package org.example;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import org.example.command.ReplyType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EmbedMessage {

    public static List<MessageEmbed> limitTracksEmbeds(List<MessageEmbed> list) {
        List<MessageEmbed> result = new ArrayList<>(list);
        var resultAmount = result.size();
        result = result.stream().limit(10).collect(Collectors.toList());
        if (resultAmount > 10)
            result.set(
                    result.size() - 1,
                    EmbedMessage.replyEmbed(ReplyType.INFO, "And " + (resultAmount - 9) + " more tracks")
            );
        return result;
    }

    public static MessageEmbed replyEmbed(ReplyType replyType, String text) {
        return new EmbedBuilder()
                //.setColor(replyType.getColor())
                .setDescription(text)
                .build();
    }

    public static MessageEmbed audioInfoEmbed(String title, AudioTrack track) {
        return new EmbedBuilder()
                .setTitle(title)
                .addField("", String.format("[%s](%s)", track.getInfo().title, track.getInfo().uri), true)
                .setFooter(track.getUserData(User.class).getName(), track.getUserData(User.class).getAvatarUrl())
                .setThumbnail("https://img.youtube.com/vi/" + track.getIdentifier() + "/default.jpg")
                .build();
    }
}
