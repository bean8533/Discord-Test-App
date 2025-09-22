package org.example.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.example.ICommand;

public class coinflip implements ICommand {
    @Override
    public String getName() {
        return "coinflip";
    }

    @Override
    public String getDescription() {
        return "flip a coin";
    }

    @Override
    public CommandData getData() {
        return Commands.slash(getName(), getDescription());
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        event.reply("Heads or Tails?")
                .addActionRow(
                        Button.primary(getName() + ":heads", "Heads"),
                        Button.primary(getName() + ":tails", "Tails")
                ).queue();
    }

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        event.deferReply().queue();

        String buttonName = event.getComponentId().split(":")[1];
        boolean coinflip = Math.random() < .5;
        String resultMessage = "";

        if (buttonName.equals("heads")) {
            String headsResult = coinflip ? "win" : "lose";
            resultMessage = "You chose heads, you " + headsResult;
        }
        else if (buttonName.equals("tails")) {
            String tailsResult = coinflip ? "lose" : "win";
            resultMessage = "You chose tails, you " + tailsResult;
        }

        event.getHook().sendMessage(resultMessage).queue();
    }
}
