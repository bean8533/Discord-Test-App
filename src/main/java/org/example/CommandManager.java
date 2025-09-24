package org.example;

import org.example.commands.*;

import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CommandManager extends ListenerAdapter {

    private final Map<String, ICommand> commands = new ConcurrentHashMap<>();

    // add commands here
    public CommandManager() {
        addCommand(new coinflip());
        addCommand(new emote());
        addCommand(new giverole());
        // addCommand(new play());
        addCommand(new ProfilePicture());
        addCommand(new roles());
        addCommand(new say());
        addCommand(new test());
        addCommand(new ticket());
        addCommand(new WordCount());
    }

    // helper for commandManager
    private void addCommand(ICommand command) {
        commands.put(command.getName(), command);
    }

    // Pushes commands to guild when ready
    @Override
    public void onGuildReady(@NotNull GuildReadyEvent event) {
        List<CommandData> commandData = new ArrayList<>();

        for (ICommand command : commands.values()) {
            commandData.add(command.getData());
        }

        event.getGuild().updateCommands().addCommands(commandData).queue();
        System.out.println("Commands registered for guild: " + event.getGuild().getName());
    }

    // Slash interactions
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        String commandName = event.getName();
        ICommand command = commands.get(commandName);

        if (command != null) {
            command.execute(event);
        }
    }

    // Button interactions
    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
        String[] idParts = event.getComponentId().split(":");
        String commandName = idParts[0];
        ICommand command = commands.get(commandName);

        if (command != null) {
            command.onButtonInteraction(event);
        }
    }

    // modal interaction
    @Override
    public void onModalInteraction(@NotNull ModalInteractionEvent event) {
        String[] idParts = event.getModalId().split(":");
        String commandName = idParts[0];
        ICommand command = commands.get(commandName);

        if (command != null) {
            command.onModalInteraction(event);
        }
    }

    // Message context interactions
    @Override
    public void onMessageContextInteraction(@NotNull MessageContextInteractionEvent event) {
        String commandName = event.getName();
        ICommand command = commands.get(commandName);

        if (command != null) {
            command.onMessageContextInteraction(event);
        }
    }

    // User context interactions
    @Override
    public void onUserContextInteraction(@NotNull UserContextInteractionEvent event) {
        String commandName = event.getName();
        ICommand command = commands.get(commandName);

        if (command != null) {
            command.onUserContextInteraction(event);
        }
    }
}
