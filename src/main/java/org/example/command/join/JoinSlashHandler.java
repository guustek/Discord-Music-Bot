package org.example.command.join;

import com.freya02.botcommands.api.annotations.CommandMarker;
import com.freya02.botcommands.api.annotations.Optional;
import com.freya02.botcommands.api.application.annotations.AppOption;
import com.freya02.botcommands.api.application.slash.GuildSlashEvent;
import com.freya02.botcommands.api.application.slash.annotations.JDASlashCommand;
import net.dv8tion.jda.api.entities.VoiceChannel;
import org.example.command.general.BaseSlashHandler;
import org.example.command.general.Command;

@CommandMarker
public class JoinSlashHandler extends BaseSlashHandler {

    public JoinSlashHandler(Command command) {
        super(command);
    }

    @JDASlashCommand(name = JoinCommand.NAME, description = JoinCommand.DESCRIPTION)
    public void handle(
            GuildSlashEvent event,
            @Optional @AppOption(name = "channel", description = "Channel to join") VoiceChannel channel) {
        event.deferReply().queue();
        command.execute(event, channel);
    }
}
