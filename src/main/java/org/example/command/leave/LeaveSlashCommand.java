package org.example.command.leave;

import com.freya02.botcommands.api.annotations.CommandMarker;
import com.freya02.botcommands.api.application.ApplicationCommand;
import com.freya02.botcommands.api.application.slash.GuildSlashEvent;
import com.freya02.botcommands.api.application.slash.annotations.JDASlashCommand;
import org.example.EmbedMessage;
import org.example.audio.PlayerManager;
import org.example.command.ReplyType;

@CommandMarker
public class LeaveSlashCommand extends ApplicationCommand {

    @JDASlashCommand(name = "leave", description = "Leave audio channel")
    public void handle(GuildSlashEvent event) {
        event.deferReply().queue();
        var audioManager = event.getGuild().getAudioManager();
        var connectedChannel = audioManager.getConnectedChannel();
        if (connectedChannel == null) {
            event.getHook().editOriginalEmbeds(EmbedMessage.replyEmbed(ReplyType.INFO, "Not connected to any channel")).queue();
            return;
        }
        audioManager.closeAudioConnection();
        PlayerManager playerManager = PlayerManager.getPlayerManager();
        playerManager.getTrackManager(event.getGuild()).getScheduler().getQueue().clear();
        playerManager.getTrackManager(event.getGuild()).getAudioPlayer().stopTrack();
        event.getHook().editOriginalEmbeds(EmbedMessage.replyEmbed(ReplyType.SUCCESS, "Left " + connectedChannel.getName())).queue();
    }
}
