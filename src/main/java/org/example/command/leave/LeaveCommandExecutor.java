package org.example.command.leave;

import com.freya02.botcommands.api.application.slash.GuildSlashEvent;
import com.freya02.botcommands.api.prefixed.BaseCommandEvent;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.Event;
import org.example.MessageUtils;
import org.example.audio.PlayerManager;
import org.example.audio.TrackManager;
import org.example.command.general.CommandExecutor;

public class LeaveCommandExecutor implements CommandExecutor {
    @Override
    public void execute(GuildSlashEvent event, Object... args) {
        leave(event, event.getGuild());
    }


    @Override
    public void execute(BaseCommandEvent event, Object... args) {
        leave(event, event.getGuild());
    }

    private void leave(Event event, Guild guild) {
        var audioManager = guild.getAudioManager();
        var connectedChannel = audioManager.getConnectedChannel();
        if (connectedChannel == null) {
            MessageUtils.replyWithEmbed(event, MessageUtils.buildBasicEmbed("Not connected to any channel"));
            return;
        }
        PlayerManager playerManager = PlayerManager.getPlayerManager();
        TrackManager trackManager = playerManager.getTrackManager(guild);
        trackManager.getScheduler().getQueue().clear();
        trackManager.getAudioPlayer().stopTrack();
        audioManager.closeAudioConnection();
        MessageUtils.replyWithEmbed(event, MessageUtils.buildBasicEmbed("Left " + connectedChannel.getName()));
    }
}
