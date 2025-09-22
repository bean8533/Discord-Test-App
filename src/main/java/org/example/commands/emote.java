package org.example.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.example.ICommand;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class emote implements ICommand {
    @Override
    public String getName() {
        return "emote";
    }

    @Override
    public String getDescription() {
        return "send an emote";
    }

    @Override
    public CommandData getData() {
        List<OptionData> options = new ArrayList<>();
        options.add(new OptionData(OptionType.STRING, "type", "the type of emote", true)
                .addChoice("Hug", "hug")
                .addChoice("Wave", "wave")
                .addChoice("Cry", "cry")
                .addChoice("Nervous", "nervous")
                .addChoice("Excited", "excited"));

        return Commands.slash(getName(),getDescription()).addOptions(options);
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        OptionMapping typeOption = event.getOption("type");

        if (typeOption == null) {
            event.reply("Error: Invalid emote type").setEphemeral(true).queue();
            return;
        }

        final String message = getEmoteType(typeOption);

        event.getHook().sendMessage(message).queue();

        event.reply("Emote sent").setEphemeral(true).queue();
    }

    // helper for emote command
    @NotNull
    private static String getEmoteType(OptionMapping emoteType) {
        return switch (emoteType.getAsString()) {
            case "hug" -> "\\(￣︶￣*\\))";
            case "wave" -> "o(*￣▽￣*)ブ";
            case "cry" -> "（；´д｀）ゞ";
            case "nervous" -> "o((⊙﹏⊙))o.";
            case "excited" -> "q(≧▽≦q)";
            default -> throw new IllegalStateException("Unexpected value: " + emoteType.getAsString());
        };
    }
}