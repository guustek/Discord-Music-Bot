package org.example.command.bass;

import com.freya02.botcommands.api.application.annotations.AppOption;
import com.freya02.botcommands.api.application.slash.GuildSlashEvent;
import com.freya02.botcommands.api.application.slash.annotations.DoubleRange;
import com.freya02.botcommands.api.application.slash.annotations.JDASlashCommand;
import org.example.command.general.BaseSlashCommand;
import org.example.command.general.CommandExecutor;

public class BassSlashCommandHandler extends BaseSlashCommand {

    public BassSlashCommandHandler(CommandExecutor commandExecutor) {
        super(commandExecutor);
    }

    @JDASlashCommand(name = "bass", description = "Boosts bass by certain percentage")
    public void handle(
            GuildSlashEvent event,
            @AppOption(
                    name = "percentage",
                    description = "Percentage by which bass should be increased (1-200), 0 - remove boost")
            @DoubleRange(from = 0, to = 200)
            double percentage) {
        event.deferReply().queue();
        commandExecutor.execute(event, percentage);
    }
}
