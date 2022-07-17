package org.example.command.leave;

import com.freya02.botcommands.api.annotations.CommandMarker;
import com.freya02.botcommands.api.prefixed.BaseCommandEvent;
import com.freya02.botcommands.api.prefixed.annotations.JDATextCommand;
import org.example.command.AbstractTextCommand;
import org.example.command.ReplyType;

@CommandMarker
public class LeaveTextCommand extends AbstractTextCommand {

    @JDATextCommand(name = "leave", description = "Leave audio channel")
    public void handle(BaseCommandEvent event) {
        var audioManager = event.getGuild().getAudioManager();
        var connectedChannel = audioManager.getConnectedChannel();
        if (connectedChannel == null) {
            event.reply(buildReplyMessage(ReplyType.INFO, "Not connected to any channel")).queue();
            return;
        }
        audioManager.closeAudioConnection();
        event.reply(buildReplyMessage(ReplyType.SUCCESS, "Left " + connectedChannel.getName())).queue();
    }
}
