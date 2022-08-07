package org.example;

import com.freya02.botcommands.api.CommandsBuilder;
import com.freya02.botcommands.api.builder.ExtensionsBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.example.command.bass.BassCommand;
import org.example.command.bass.BassSlashHandler;
import org.example.command.bass.BassTextHandler;
import org.example.command.clear.ClearCommand;
import org.example.command.clear.ClearSlashHandler;
import org.example.command.clear.ClearTextHandler;
import org.example.command.join.JoinCommand;
import org.example.command.join.JoinSlashHandler;
import org.example.command.join.JoinTextHandler;
import org.example.command.leave.LeaveCommand;
import org.example.command.leave.LeaveSlashHandler;
import org.example.command.leave.LeaveTextHandler;
import org.example.command.np.NpCommand;
import org.example.command.np.NpSlashHandler;
import org.example.command.np.NpTextHandler;
import org.example.command.pause.PauseCommand;
import org.example.command.pause.PauseSlashHandler;
import org.example.command.pause.PauseTextHandler;
import org.example.command.play.PlayCommand;
import org.example.command.play.PlaySlashHandler;
import org.example.command.play.PlayTextHandler;
import org.example.command.pope.PopeCommand;
import org.example.command.pope.PopeService;
import org.example.command.pope.PopeTextHandler;
import org.example.command.queue.QueueCommand;
import org.example.command.queue.QueueSlashHandler;
import org.example.command.queue.QueueTextHandler;
import org.example.command.resume.ResumeCommand;
import org.example.command.resume.ResumeSlashHandler;
import org.example.command.resume.ResumeTextHandler;
import org.example.command.skip.SkipCommand;
import org.example.command.skip.SkipSlashHandler;
import org.example.command.skip.SkipTextHandler;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.util.Collection;
import java.util.Properties;
import java.util.Set;


public class Bot {

    public static final String TOKEN_PROPERTY_KEY = "TOKEN";
    public static final String COMMAND_PREFIX_PROPERTY_KEY = "COMMAND_PREFIX";

    private static final Collection<GatewayIntent> INTENTS = Set.of(
            GatewayIntent.DIRECT_MESSAGES,
            GatewayIntent.GUILD_MESSAGES,
            GatewayIntent.GUILD_MESSAGE_REACTIONS,
            GatewayIntent.GUILD_VOICE_STATES,
            GatewayIntent.GUILD_MEMBERS
            //GatewayIntent.GUILD_PRESENCES
    );
    private final Properties properties;
    private JDA jda;

    public Bot() {
        this.properties = loadProperties();
    }

    private Properties loadProperties() {
        Properties prop = new Properties();
        String token = System.getenv(TOKEN_PROPERTY_KEY);
        String command_prefix = System.getenv(COMMAND_PREFIX_PROPERTY_KEY);
        prop.setProperty(TOKEN_PROPERTY_KEY, token);
        prop.setProperty(COMMAND_PREFIX_PROPERTY_KEY, command_prefix);
        return prop;
    }

    public void start() {
        try {
            this.jda = JDABuilder
                    .create(properties.getProperty(TOKEN_PROPERTY_KEY), INTENTS)
                    .setActivity(Activity.playing("Ram pam pam!"))
                    .setStatus(OnlineStatus.ONLINE)
                    .build()
                    .awaitReady();
            var builder = CommandsBuilder.newBuilder(996830793433366568L)
                    .textCommandBuilder(textCommandsBuilder -> textCommandsBuilder.addPrefix(properties.getProperty(COMMAND_PREFIX_PROPERTY_KEY)));
            builder.extensionsBuilder(this :: registerHandlersWithExecutors);
            builder.build(jda, this.getClass().getPackageName());
        } catch (LoginException | InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void registerHandlersWithExecutors(ExtensionsBuilder builder) {

        var bassCommand = new BassCommand();
        builder.registerInstanceSupplier(BassTextHandler.class, ctx -> new BassTextHandler(bassCommand));
        builder.registerInstanceSupplier(BassSlashHandler.class, ctx -> new BassSlashHandler(bassCommand));

        var clearCommand = new ClearCommand();
        builder.registerInstanceSupplier(ClearTextHandler.class, ctx -> new ClearTextHandler(clearCommand));
        builder.registerInstanceSupplier(ClearSlashHandler.class, ctx -> new ClearSlashHandler(clearCommand));

        var joinCommand = new JoinCommand();
        builder.registerInstanceSupplier(JoinTextHandler.class, ctx -> new JoinTextHandler(joinCommand));
        builder.registerInstanceSupplier(JoinSlashHandler.class, ctx -> new JoinSlashHandler(joinCommand));

        var leaveCommand = new LeaveCommand();
        builder.registerInstanceSupplier(LeaveTextHandler.class, ctx -> new LeaveTextHandler(leaveCommand));
        builder.registerInstanceSupplier(LeaveSlashHandler.class, ctx -> new LeaveSlashHandler(leaveCommand));

        var npCommand = new NpCommand();
        builder.registerInstanceSupplier(NpTextHandler.class, ctx -> new NpTextHandler(npCommand));
        builder.registerInstanceSupplier(NpSlashHandler.class, ctx -> new NpSlashHandler(npCommand));

        var pauseCommand = new PauseCommand();
        builder.registerInstanceSupplier(PauseTextHandler.class, ctx -> new PauseTextHandler(pauseCommand));
        builder.registerInstanceSupplier(PauseSlashHandler.class, ctx -> new PauseSlashHandler(pauseCommand));

        var playCommand = new PlayCommand();
        builder.registerInstanceSupplier(PlayTextHandler.class, ctx -> new PlayTextHandler(playCommand));
        builder.registerInstanceSupplier(PlaySlashHandler.class, ctx -> new PlaySlashHandler(playCommand));

        var queueCommand = new QueueCommand();
        builder.registerInstanceSupplier(QueueTextHandler.class, ctx -> new QueueTextHandler(queueCommand));
        builder.registerInstanceSupplier(QueueSlashHandler.class, ctx -> new QueueSlashHandler(queueCommand));

        var resumeCommand = new ResumeCommand();
        builder.registerInstanceSupplier(ResumeTextHandler.class, ctx -> new ResumeTextHandler(resumeCommand));
        builder.registerInstanceSupplier(ResumeSlashHandler.class, ctx -> new ResumeSlashHandler(resumeCommand));

        var skipCommand = new SkipCommand();
        builder.registerInstanceSupplier(SkipTextHandler.class, ctx -> new SkipTextHandler(skipCommand));
        builder.registerInstanceSupplier(SkipSlashHandler.class, ctx -> new SkipSlashHandler(skipCommand));

        var popeService = new PopeService(this.jda);
        this.jda.addEventListener(popeService);
        var popeCommand = new PopeCommand(popeService);
        builder.registerInstanceSupplier(PopeTextHandler.class, ctx -> new PopeTextHandler(popeCommand));
    }
}
