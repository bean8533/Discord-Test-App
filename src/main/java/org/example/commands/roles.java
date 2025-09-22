package org.example.commands;

import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.example.ICommand;

import java.util.List;

public class roles implements ICommand {
    @Override
    public String getName() {
        return "roles";
    }

    @Override
    public String getDescription() {
        return "show all roles";
    }

    @Override
    public CommandData getData() {
        return Commands.slash(getName(),getDescription());
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        event.deferReply(true).queue();

        StringBuilder message = new StringBuilder();

        if (event.getGuild()!=null) {
            List<Role> roles = event.getGuild().getRoles();

            for (Role role : roles) {
                message.append(role.getAsMention()).append("\n");
            }
        }

        event.getHook().sendMessage(message.toString()).queue();
    }
}
