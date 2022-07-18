package org.example.command.skip;

import com.freya02.botcommands.api.annotations.CommandMarker;
import com.freya02.botcommands.api.prefixed.BaseCommandEvent;
import com.freya02.botcommands.api.prefixed.TextCommand;
import com.freya02.botcommands.api.prefixed.annotations.JDATextCommand;
import org.example.audio.PlayerManager;

@CommandMarker
public class SkipTextCommand extends TextCommand {

    @JDATextCommand(name = "skip", description = "Skip currently playing track")
    public void handle(BaseCommandEvent event) {
        PlayerManager.getPlayerManager().skip(event);
    }
}
