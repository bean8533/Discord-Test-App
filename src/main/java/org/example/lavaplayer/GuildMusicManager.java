package org.example.lavaplayer;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;

public class GuildMusicManager {

    private TrackScheduler scheduler;
    private AudioForwarder forwarder;

    public GuildMusicManager(AudioPlayerManager manager) {
        AudioPlayer player = manager.createPlayer();
        scheduler = new TrackScheduler(player);
        player.addListener(scheduler);
        forwarder = new AudioForwarder(player);
    }

    public TrackScheduler getScheduler() {
        return scheduler;
    }

    public AudioForwarder getForwarder() {
        return forwarder;
    }
}
