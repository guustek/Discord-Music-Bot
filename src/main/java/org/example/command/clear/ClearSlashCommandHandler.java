package org.example.command.clear;

import com.freya02.botcommands.api.application.slash.GuildSlashEvent;
import com.freya02.botcommands.api.application.slash.annotations.JDASlashCommand;
import org.example.command.general.BaseSlashCommand;
import org.example.command.general.CommandExecutor;

public class ClearSlashCommandHandler extends BaseSlashCommand {

    public ClearSlashCommandHandler(CommandExecutor commandExecutor) {
        super(commandExecutor);
    }

    @JDASlashCommand(name = "clear", description = "Clears the track queue. This command does not remove currently playing track")
    public void handle(GuildSlashEvent event) {
        event.deferReply().queue();
        commandExecutor.execute(event);
    }
}
