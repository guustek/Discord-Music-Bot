package org.example;

import com.freya02.botcommands.api.CommandsBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

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
            GatewayIntent.GUILD_VOICE_STATES
            //GatewayIntent.GUILD_MEMBERS,
            //GatewayIntent.GUILD_PRESENCES
    );
    private final Properties properties;

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
            JDA jda = JDABuilder
                    .create(properties.getProperty(TOKEN_PROPERTY_KEY), INTENTS)
                    .setActivity(Activity.playing("Ram pam pam!"))
                    .setStatus(OnlineStatus.ONLINE)
                    .build()
                    .awaitReady();
            var builder = CommandsBuilder.newBuilder(996830793433366568L)
                    .textCommandBuilder(textCommandsBuilder -> textCommandsBuilder.addPrefix(properties.getProperty(COMMAND_PREFIX_PROPERTY_KEY)));
            builder.build(jda, this.getClass().getPackageName());
        } catch (LoginException | InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
    }


}
