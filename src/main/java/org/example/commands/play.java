package org.example.commands;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.example.ICommand;
import org.example.lavaplayer.PlayerManager;

import java.util.ArrayList;
import java.util.List;

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
        List<OptionData> options = new ArrayList<>();
        options.add(new OptionData(OptionType.STRING, "name", "name of song to play", true));

        return Commands.slash(getName(),getDescription()).addOptions(options);
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        Guild guild = event.getGuild();
        if (guild==null) {
            event.reply("Error: Server not found").setEphemeral(true).queue();
            return;
        }

        Member member = event.getMember();
        GuildVoiceState memberVoiceState = member.getVoiceState();

        if (!memberVoiceState.inAudioChannel()) {
            event.reply("Not in a voice channel").setEphemeral(true).queue();
            return;
        }

        Member self  = event.getGuild().getSelfMember();
        GuildVoiceState selfVoiceState = self.getVoiceState();

        if (!selfVoiceState.inAudioChannel()) {
            event.getGuild().getAudioManager().openAudioConnection(memberVoiceState.getChannel());
        } else {
            if (selfVoiceState.getChannel() != memberVoiceState.getChannel()) {
                event.reply("Already in a voice channel").setEphemeral(true).queue();
                return;
            }
        }

        PlayerManager playerManager = PlayerManager.getInstance();
        event.reply("Playing song").queue();
        playerManager.play(event.getGuild(), event.getOption("name").getAsString());
    }
}
