package org.example.command.pope;

import com.freya02.botcommands.api.application.slash.GuildSlashEvent;
import com.freya02.botcommands.api.prefixed.BaseCommandEvent;
import net.dv8tion.jda.api.events.Event;
import org.example.MessageUtils;
import org.example.command.general.BaseCommand;

import java.time.Duration;

public class PopeCommand extends BaseCommand {

    public static final String NAME = "pope";
    public static final String DESCRIPTION = "2137.";

    private final PopeService popeService;

    public PopeCommand(PopeService popeService) {
        this.popeService = popeService;
    }

    @Override
    public void execute(GuildSlashEvent event, Object... args) {
        time(event);
    }

    @Override
    public void execute(BaseCommandEvent event, Object... args) {
        time(event);
    }

    private void time(Event event) {
        Duration duration = popeService.calculateDelay();
        String result = String.format("%d:%02d:%02d",
                duration.toHours(),
                duration.toMinutesPart(),
                duration.toSecondsPart());
        MessageUtils.replyWithEmbed(event, MessageUtils.buildBasicEmbed(result));
    }
}
