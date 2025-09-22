package org.example.commands;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;
import org.example.ICommand;

import java.util.EnumSet;

public class ticket implements ICommand {

    @Override
    public String getName() {
        return "ticket";
    }

    @Override
    public String getDescription() {
        return "open a ticket";
    }

    @Override
    public CommandData getData() {
        return Commands.slash(getName(), getDescription());
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        event.reply("Would you like to send a ticket?")
                .addActionRow(
                        Button.primary(getName() + ":ticket", "Open ticket"),
                        Button.danger(getName() + ":cancel", "Cancel")
                ).queue();
    }

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        String buttonName = event.getComponentId().split(":")[1];

        if (buttonName.equals("ticket")) {
            TextInput ticketSubject = TextInput.create("ticketSubject", "Subject", TextInputStyle.SHORT)
                    .setPlaceholder("Subject of ticket").setMaxLength(100).build();

            TextInput ticketBody = TextInput.create("ticketBody", "Body", TextInputStyle.PARAGRAPH)
                    .setPlaceholder("Please write your ticket here").setMinLength(30).setMaxLength(1000).build();

            Modal ticketModal = Modal.create(getName() + ":ticket", "Ticket")
                    .addComponents(ActionRow.of(ticketSubject), ActionRow.of(ticketBody))
                    .build();

            event.replyModal(ticketModal).queue();
        } else if (buttonName.equals("cancel")) {
            event.getMessage().delete().queue();
            event.reply("Ticket cancelled").setEphemeral(true).queue();
        }
    }

    @Override
    public void onModalInteraction(ModalInteractionEvent event) {
        String modalName = event.getModalId().split(":")[1];

        if (modalName.equals("ticket")) {
            String subject = event.getValue("ticketSubject").getAsString();
            String body = event.getValue("ticketBody").getAsString();
            User user = event.getUser();
            Guild guild = event.getGuild();

            if (guild == null) {
                event.reply("Error: Server not found").setEphemeral(true).queue();
                return;
            }

            String channelName = "ticket: " + user.getEffectiveName();

            guild.createTextChannel(channelName).addPermissionOverride(guild.getMember(user),
                            EnumSet.of(Permission.VIEW_CHANNEL, Permission.MESSAGE_SEND), null)
                    .queue(ticketChannel -> {
                        if (ticketChannel == null) {
                            event.reply("Error: Ticket not sent").setEphemeral(true).queue();
                            return;
                        }

                        ticketChannel.sendMessage("User: " + user.getName()
                                        + "\nSubject: " + subject
                                        + "\nBody: " + body)
                                .queue();

                        event.reply("Ticket sent").setEphemeral(true).queue();
                    });
        }
    }
}
