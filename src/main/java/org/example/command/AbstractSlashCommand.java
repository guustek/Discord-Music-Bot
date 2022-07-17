package org.example.command;

import com.freya02.botcommands.api.application.ApplicationCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
public abstract class AbstractSlashCommand extends ApplicationCommand {

    protected final EmbedBuilder embedBuilder = new EmbedBuilder();

    protected MessageEmbed buildReplyMessage(ReplyType replyType, String text) {
        var embed = embedBuilder
                .setColor(replyType.getColor())
                .addField(replyType.toString(), text, false)
                .build();
        embedBuilder.clear();
        return embed;
    }
}
