package org.example;

import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

public interface ICommand {

    String getName();

    String getDescription();

    CommandData getData();

    void execute(SlashCommandInteractionEvent event);

    default void onButtonInteraction(ButtonInteractionEvent event) {

    }

    default void onModalInteraction(ModalInteractionEvent event) {

    }

    default void onUserContextInteraction(UserContextInteractionEvent event) {

    }

    default void onMessageContextInteraction(MessageContextInteractionEvent event) {

    }
}
