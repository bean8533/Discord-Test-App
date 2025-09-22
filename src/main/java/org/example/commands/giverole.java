package org.example.commands;

import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.example.ICommand;

import java.util.ArrayList;
import java.util.List;

public class giverole implements ICommand {
    @Override
    public String getName() {
        return "giverole";
    }

    @Override
    public String getDescription() {
        return "give a user a role";
    }

    @Override
    public CommandData getData() {
        List<OptionData> options = new ArrayList<>();
        options.add(new OptionData(OptionType.USER, "user", "target user", true));
        options.add(new OptionData(OptionType.ROLE, "role", "role given to user", true));

        return Commands.slash(getName(),getDescription()).addOptions(options);
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        OptionMapping userOption = event.getOption("user");
        OptionMapping roleOption = event.getOption("role");

        if (userOption == null) {
            event.reply("Error: You must provide a user").setEphemeral(true).queue();
            return;
        }
        if (roleOption == null) {
            event.reply("Error: You must provide a role").setEphemeral(true).queue();
            return;
        }

        User user = userOption.getAsUser();
        Role role = roleOption.getAsRole();

        if (event.getGuild()!=null) event.getGuild().addRoleToMember(user, role).queue();
        else {
            event.reply("Error: Command not sent in server").setEphemeral(true).queue();
            return;
        }

        event.reply(role.getName() + " given to " + user.getName()).setEphemeral(true).queue();
    }
}
