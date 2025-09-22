package org.example.commands;

import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.example.ICommand;

public class WordCount implements ICommand {
    @Override
    public String getName() {
        return "Word count";
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public CommandData getData() {
        return Commands.message(getName());
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {

    }

    @Override
    public void onMessageContextInteraction(MessageContextInteractionEvent event) {
        String commandName = event.getName();

        if (commandName.equals("Word count")) {
            event.reply("Words: " + event.getTarget().getContentRaw().split("\\s+").length).queue();
        }
    }
}
