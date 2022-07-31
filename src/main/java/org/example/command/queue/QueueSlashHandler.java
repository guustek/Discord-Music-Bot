package org.example.command.queue;

import com.freya02.botcommands.api.annotations.CommandMarker;
import com.freya02.botcommands.api.application.slash.GuildSlashEvent;
import com.freya02.botcommands.api.application.slash.annotations.JDASlashCommand;
import org.example.command.general.BaseSlashHandler;
import org.example.command.general.Command;

@CommandMarker
public class QueueSlashHandler extends BaseSlashHandler {

    public QueueSlashHandler(Command command) {
        super(command);
    }

    @JDASlashCommand(name = QueueCommand.NAME, description = QueueCommand.DESCRIPTION)
    public void handle(GuildSlashEvent event) {
        event.deferReply().queue();
        command.execute(event);

    }
}