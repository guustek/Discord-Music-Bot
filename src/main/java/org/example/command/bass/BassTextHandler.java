package org.example.command.bass;

import com.freya02.botcommands.api.prefixed.BaseCommandEvent;
import com.freya02.botcommands.api.prefixed.annotations.JDATextCommand;
import com.freya02.botcommands.api.prefixed.annotations.TextOption;
import org.example.MessageUtils;
import org.example.command.general.BaseTextHandler;
import org.example.command.general.Command;

public class BassTextHandler extends BaseTextHandler {

    public BassTextHandler(Command command) {
        super(command);
    }

    @JDATextCommand(name = BassCommand.NAME, description = BassCommand.DESCRIPTION)
    public void handle(BaseCommandEvent event, @TextOption String percentage) {
        try {
            float percentageFloat = Float.parseFloat(percentage);
            command.execute(event, percentageFloat);
        } catch (NumberFormatException e) {
            MessageUtils.replyWithEmbed(event, MessageUtils.buildBasicEmbed("Supplied percentage is not a valid number!"));
        }
    }
}
