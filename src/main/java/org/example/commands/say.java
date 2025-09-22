package org.example.commands;

import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.example.ICommand;

import java.util.ArrayList;
import java.util.List;

public class say implements ICommand {

    @Override
    public String getName() {
        return "say";
    }

    @Override
    public String getDescription() {
        return "make the bot say a message";
    }

    @Override
    public CommandData getData() {
        List<OptionData> options = new ArrayList<>();
        options.add(new OptionData(OptionType.STRING, "message", "what the bot says", true));
        options.add(new OptionData(OptionType.CHANNEL, "channel", "the channel")
                .setChannelTypes(ChannelType.TEXT, ChannelType.GUILD_PUBLIC_THREAD));

        return Commands.slash(getName(),getDescription()).addOptions(options);
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        OptionMapping messageOption = event.getOption("message");
        OptionMapping channelOption = event.getOption("channel");

        if (messageOption == null) {
            event.reply("Error: You must provide a message").setEphemeral(true).queue();
            return;
        }

        String message = messageOption.getAsString();
        MessageChannel channel;

        if (channelOption != null) {
            channel = channelOption.getAsChannel().asTextChannel();
        }
        else {
            channel = event.getChannel();
        }

        channel.sendMessage(message).queue();

        event.reply("Message sent").setEphemeral(true).queue();
    }
}
