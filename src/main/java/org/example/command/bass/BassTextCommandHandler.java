package org.example.command.bass;

import com.freya02.botcommands.api.prefixed.BaseCommandEvent;
import com.freya02.botcommands.api.prefixed.annotations.JDATextCommand;
import com.freya02.botcommands.api.prefixed.annotations.TextOption;
import org.example.MessageUtils;
import org.example.command.general.BaseTextCommand;
import org.example.command.general.CommandExecutor;

public class BassTextCommandHandler extends BaseTextCommand {

    public BassTextCommandHandler(CommandExecutor commandExecutor) {
        super(commandExecutor);
    }

    @JDATextCommand(name = "bass", description = "Boosts bass by certain percentage")
    public void handle(BaseCommandEvent event, @TextOption String percentage) {
        try {
            float percentageFloat = Float.parseFloat(percentage);
            commandExecutor.execute(event, percentageFloat);
        } catch (NumberFormatException e) {
            MessageUtils.replyWithEmbed(event, MessageUtils.buildBasicEmbed("Supplied percentage is not a valid number!"));
        }
    }
}
