package org.example.command.leave;

import com.freya02.botcommands.api.annotations.CommandMarker;
import com.freya02.botcommands.api.prefixed.BaseCommandEvent;
import com.freya02.botcommands.api.prefixed.TextCommand;
import com.freya02.botcommands.api.prefixed.annotations.JDATextCommand;
import org.example.EmbedMessage;
import org.example.audio.PlayerManager;
import org.example.command.ReplyType;

@CommandMarker
public class LeaveTextCommand extends TextCommand {

    @JDATextCommand(name = "leave", description = "Leave audio channel")
    public void handle(BaseCommandEvent event) {
        var audioManager = event.getGuild().getAudioManager();
        var connectedChannel = audioManager.getConnectedChannel();
        if (connectedChannel == null) {
            EmbedMessage.replyWithEmbed(event, EmbedMessage.buildBasicEmbed(ReplyType.INFO, "Not connected to any channel"));
            return;
        }
        PlayerManager playerManager = PlayerManager.getPlayerManager();
        playerManager.getTrackManager(event.getGuild()).getScheduler().getQueue().clear();
        playerManager.getTrackManager(event.getGuild()).getAudioPlayer().stopTrack();
        EmbedMessage.replyWithEmbed(event, EmbedMessage.buildBasicEmbed(ReplyType.SUCCESS, "Left " + connectedChannel.getName()));
    }
}
