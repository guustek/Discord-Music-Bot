package org.example.command.join;

import com.freya02.botcommands.api.annotations.CommandMarker;
import com.freya02.botcommands.api.annotations.Optional;
import com.freya02.botcommands.api.prefixed.BaseCommandEvent;
import com.freya02.botcommands.api.prefixed.annotations.JDATextCommand;
import com.freya02.botcommands.api.prefixed.annotations.TextOption;
import org.example.command.general.BaseTextCommand;
import org.example.command.general.CommandExecutor;

@CommandMarker
public class JoinTextCommandHandler extends BaseTextCommand {

    public JoinTextCommandHandler(CommandExecutor commandExecutor) {
        super(commandExecutor);
    }

    @JDATextCommand(name = "join", description = "Join voice channel")
    public void handle(
            BaseCommandEvent event,
            @Optional @TextOption(name = "Channel name") String channelName) {
        commandExecutor.execute(event, channelName);
    }
}
