package org.example.commands;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.managers.AudioManager;
import org.example.ICommand;

public class play implements ICommand {

    @Override
    public String getName() {
        return "play";
    }

    @Override
    public String getDescription() {
        return "play music";
    }

    @Override
    public CommandData getData() {
        return Commands.slash(getName(),getDescription());
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        Guild guild = event.getGuild();
        if (guild==null) {
            event.reply("Error: Server not found").setEphemeral(true).queue();
            return;
        }

        VoiceChannel channel = guild.getVoiceChannelsByName("music",true).get(0);
        AudioManager audioManager = guild.getAudioManager();
        if (channel==null) {
            event.reply("Error: No music channel found").setEphemeral(true).queue();
            return;
        }

        AudioPlayerManager playerManager = new DefaultAudioPlayerManager();
        AudioSourceManagers.registerRemoteSources(playerManager);

        // set audio send handler and open connection
    }
}
