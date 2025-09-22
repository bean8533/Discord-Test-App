package org.example;

import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;

public class appManager {
    private static final Dotenv config = Dotenv.configure().load();
    private final ShardManager shardManager;

    public appManager() throws LoginException {
        String token = config.get("DISCORD_TOKEN");
        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(token);

        builder.enableIntents(GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_PRESENCES);

        // Event listeners
        builder.addEventListeners(new commandManager());

        // Caching
        builder.setMemberCachePolicy(MemberCachePolicy.ALL);
        builder.setChunkingFilter(ChunkingFilter.ALL);
        builder.enableCache(CacheFlag.ONLINE_STATUS);

        // Bot status
        builder.setStatus(OnlineStatus.ONLINE);
        builder.setActivity(Activity.watching("you"));

        shardManager = builder.build();
    }

    public ShardManager getShardManager() {
        return shardManager;
    }

    public Dotenv getDotenv() {
        return config;
    }
}
