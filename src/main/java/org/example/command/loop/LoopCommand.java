package org.example.command.loop;

import com.freya02.botcommands.api.application.slash.GuildSlashEvent;
import com.freya02.botcommands.api.prefixed.BaseCommandEvent;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.Event;
import org.example.MessageUtils;
import org.example.audio.PlayerManager;
import org.example.audio.TrackScheduler;
import org.example.command.general.BaseCommand;

public class LoopCommand extends BaseCommand {

    public static final String NAME = "loop";
    public static final String DESCRIPTION = "Toggle loop on currently playing track.";

    @Override
    public void execute(GuildSlashEvent event, Object... args) {
        Guild guild = event.getGuild();
        toggle(event,guild);
    }

    @Override
    public void execute(BaseCommandEvent event, Object... args) {
        Guild guild = event.getGuild();
        toggle(event,guild);
    }

    private void toggle(Event event, Guild guild) {
        TrackScheduler scheduler = PlayerManager.getPlayerManager().getTrackManager(guild).getScheduler();
        scheduler.toggleLoop();
        String toggle = scheduler.isLooped() ? "on" : "off";
        MessageUtils.replyWithEmbed(event,MessageUtils.buildBasicEmbed("Loop is " + toggle));
    }
}
