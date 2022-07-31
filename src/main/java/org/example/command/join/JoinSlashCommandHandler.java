package org.example.command.join;

import com.freya02.botcommands.api.annotations.CommandMarker;
import com.freya02.botcommands.api.annotations.Optional;
import com.freya02.botcommands.api.application.annotations.AppOption;
import com.freya02.botcommands.api.application.slash.GuildSlashEvent;
import com.freya02.botcommands.api.application.slash.annotations.JDASlashCommand;
import net.dv8tion.jda.api.entities.VoiceChannel;
import org.example.command.general.BaseSlashCommand;
import org.example.command.general.CommandExecutor;

@CommandMarker
public class JoinSlashCommandHandler extends BaseSlashCommand {

    public JoinSlashCommandHandler(CommandExecutor commandExecutor) {
        super(commandExecutor);
    }

    @JDASlashCommand(name = "join", description = "Join voice channel")
    public void handle(
            GuildSlashEvent event,
            @Optional @AppOption(name = "channel", description = "Channel to join") VoiceChannel channel) {
        event.deferReply().queue();
        commandExecutor.execute(event, channel);
    }
}
