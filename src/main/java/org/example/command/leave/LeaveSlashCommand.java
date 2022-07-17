package org.example.command.leave;

import com.freya02.botcommands.api.annotations.CommandMarker;
import com.freya02.botcommands.api.application.slash.GuildSlashEvent;
import com.freya02.botcommands.api.application.slash.annotations.JDASlashCommand;
import org.example.command.AbstractSlashCommand;
import org.example.command.ReplyType;

@CommandMarker
public class LeaveSlashCommand extends AbstractSlashCommand {

    @JDASlashCommand(name = "leave", description = "Leave audio channel")
    public void handle(GuildSlashEvent event) {
        var audioManager = event.getGuild().getAudioManager();
        var connectedChannel = audioManager.getConnectedChannel();
        if (connectedChannel == null) {
            event.replyEmbeds(buildReplyMessage(ReplyType.INFO, "Not connected to any channel")).queue();
            return;
        }
        audioManager.closeAudioConnection();
        event.replyEmbeds(buildReplyMessage(ReplyType.SUCCESS, "Left " + connectedChannel.getName())).queue();
    }
}
