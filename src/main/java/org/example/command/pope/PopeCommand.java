package org.example.command.pope;

import com.freya02.botcommands.api.application.slash.GuildSlashEvent;
import com.freya02.botcommands.api.prefixed.BaseCommandEvent;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.Event;
import org.example.MessageUtils;
import org.example.command.general.BaseCommand;

public class PopeCommand extends BaseCommand {

    public static final String NAME = "pope";
    public static final String DESCRIPTION = "2137.";

    private final PopeService popeService;

    public PopeCommand(PopeService popeService) {
        this.popeService = popeService;
    }

    @Override
    public void execute(GuildSlashEvent event, Object... args) {
        toggle(event, event.getGuild());
    }

    @Override
    public void execute(BaseCommandEvent event, Object... args) {
        toggle(event,event.getGuild());
    }

    private void toggle(Event event, Guild guild) {
        boolean result = popeService.toggleEnabledOnGuild(guild.getIdLong());
        String message = result ? "Enabled" : "Disabled";
        MessageUtils.replyWithEmbed(event, MessageUtils.buildBasicEmbed(message + " pope service"));
        
    }
}
