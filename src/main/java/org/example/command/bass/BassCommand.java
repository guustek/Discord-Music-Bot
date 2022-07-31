package org.example.command.bass;

import com.freya02.botcommands.api.application.slash.GuildSlashEvent;
import com.freya02.botcommands.api.prefixed.BaseCommandEvent;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.Event;
import org.example.MessageUtils;
import org.example.audio.PlayerManager;
import org.example.command.general.BaseCommand;

public class BassCommand extends BaseCommand {

    public static final String NAME = "bass";
    public static final String DESCRIPTION = "Boosts bass by certain percentage.";

    @Override
    public void execute(GuildSlashEvent event, Object... args) {
        bassBoost(event, event.getGuild(), ((Number) args[0]).floatValue());
    }

    @Override
    public void execute(BaseCommandEvent event, Object... args) {
        bassBoost(event, event.getGuild(), ((Number) args[0]).floatValue());
    }

    private static void bassBoost(Event event, Guild guild, float percentage) {
        if (percentage < 0 || percentage > 200) {
            MessageUtils.replyWithEmbed(event, MessageUtils.buildBasicEmbed("Supplied percentage is not between 0 and 200."));
            return;
        }
        PlayerManager playerManager = PlayerManager.getPlayerManager();
        playerManager.getTrackManager(guild).bassBoost(percentage);
        if (percentage == 0) {
            MessageUtils.replyWithEmbed(event, MessageUtils.buildBasicEmbed("Removed bass boost."));
            return;
        }
        MessageUtils.replyWithEmbed(event, MessageUtils.buildBasicEmbed("Boosted bass by " + percentage + "%"));
    }
}
