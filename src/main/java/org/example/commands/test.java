package org.example.commands;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.example.ICommand;

public class test implements ICommand {
    @Override
    public String getName() {
        return "test";
    }

    @Override
    public String getDescription() {
        return "test command";
    }

    @Override
    public CommandData getData() {
        return Commands.slash(getName(),getDescription());
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        User user = event.getUser();
        String username = user.getName();

        event.reply("User " + username + " ran test command").queue();
    }
}
