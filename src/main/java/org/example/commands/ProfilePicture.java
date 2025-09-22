package org.example.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.example.ICommand;

public class ProfilePicture implements ICommand {
    @Override
    public String getName() {
        return "Profile picture";
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public CommandData getData() {
        return Commands.context(Command.Type.USER, getName());
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {

    }

    @Override
    public void onUserContextInteraction(UserContextInteractionEvent event) {
        String commandName = event.getName();

        if (commandName.equals("Profile picture")) {
            if (event.getTarget().getAvatarUrl()!=null) {
                event.reply(event.getTarget().getAvatarUrl()).queue();
                return;
            }

            event.reply("Error: Avatar not found").queue();
        }
    }
}
