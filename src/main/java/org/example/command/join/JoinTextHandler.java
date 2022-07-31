package org.example.command.join;

import com.freya02.botcommands.api.annotations.CommandMarker;
import com.freya02.botcommands.api.annotations.Optional;
import com.freya02.botcommands.api.prefixed.BaseCommandEvent;
import com.freya02.botcommands.api.prefixed.annotations.JDATextCommand;
import com.freya02.botcommands.api.prefixed.annotations.TextOption;
import org.example.command.general.BaseTextHandler;
import org.example.command.general.Command;

@CommandMarker
public class JoinTextHandler extends BaseTextHandler {

    public JoinTextHandler(Command command) {
        super(command);
    }

    @JDATextCommand(name = JoinCommand.NAME, description = JoinCommand.DESCRIPTION)
    public void handle(
            BaseCommandEvent event,
            @Optional @TextOption(name = "Channel name") String channelName) {
        command.execute(event, channelName);
    }
}
