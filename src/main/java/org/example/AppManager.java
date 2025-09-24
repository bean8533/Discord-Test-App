package org.example;

import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

import javax.security.auth.login.LoginException;

public class AppManager {
    private static final Dotenv config = Dotenv.configure().load();

    public AppManager() throws LoginException {
        String token = config.get("DISCORD_TOKEN");
        JDABuilder builder = JDABuilder.createDefault(token);

        builder.enableIntents(GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_PRESENCES);

        // Event listeners
        builder.addEventListeners(new CommandManager());

        // Caching
        builder.setMemberCachePolicy(MemberCachePolicy.NONE);
        builder.setChunkingFilter(ChunkingFilter.NONE);

        // Bot status
        builder.setStatus(OnlineStatus.ONLINE);
        builder.setActivity(Activity.watching("you"));

        builder.build();
    }
}
