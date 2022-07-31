package org.example.command.leave;

import com.freya02.botcommands.api.annotations.CommandMarker;
import com.freya02.botcommands.api.application.slash.GuildSlashEvent;
import com.freya02.botcommands.api.application.slash.annotations.JDASlashCommand;
import org.example.command.general.BaseSlashCommand;
import org.example.command.general.CommandExecutor;

@CommandMarker
public class LeaveSlashCommandHandler extends BaseSlashCommand {

    public LeaveSlashCommandHandler(CommandExecutor commandExecutor) {
        super(commandExecutor);
    }

    @JDASlashCommand(name = "leave", description = "Leave audio channel")
    public void handle(GuildSlashEvent event) {
        event.deferReply().queue();
        commandExecutor.execute(event);
    }
}
