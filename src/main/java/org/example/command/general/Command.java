package org.example.command.general;

import com.freya02.botcommands.api.application.slash.GuildSlashEvent;
import com.freya02.botcommands.api.prefixed.BaseCommandEvent;

public interface Command {

    void execute(GuildSlashEvent event, Object... args);

    void execute(BaseCommandEvent event, Object... args);
}
