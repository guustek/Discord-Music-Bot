package org.example.command.queue;

import com.freya02.botcommands.api.prefixed.BaseCommandEvent;
import com.freya02.botcommands.api.prefixed.annotations.JDATextCommand;
import org.example.command.general.BaseTextHandler;
import org.example.command.general.Command;

public class QueueTextHandler extends BaseTextHandler {

    public QueueTextHandler(Command command) {
        super(command);
    }

    @JDATextCommand(name = QueueCommand.NAME, description = QueueCommand.DESCRIPTION)
    public void handle(BaseCommandEvent event) {
        command.execute(event);
    }
}
