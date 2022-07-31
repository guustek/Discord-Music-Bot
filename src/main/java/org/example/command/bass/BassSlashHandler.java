package org.example.command.bass;

import com.freya02.botcommands.api.application.annotations.AppOption;
import com.freya02.botcommands.api.application.slash.GuildSlashEvent;
import com.freya02.botcommands.api.application.slash.annotations.DoubleRange;
import com.freya02.botcommands.api.application.slash.annotations.JDASlashCommand;
import org.example.command.general.BaseSlashHandler;
import org.example.command.general.Command;

public class BassSlashHandler extends BaseSlashHandler {

    public BassSlashHandler(Command command) {
        super(command);
    }

    @JDASlashCommand(name = BassCommand.NAME, description = BassCommand.DESCRIPTION)
    public void handle(
            GuildSlashEvent event,
            @AppOption(
                    name = "percentage",
                    description = "Percentage by which bass should be increased (1-200), 0 - remove boost")
            @DoubleRange(from = 0, to = 200)
            double percentage) {
        event.deferReply().queue();
        command.execute(event, percentage);
    }
}
