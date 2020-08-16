package main.java.controllers;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This is a controller for the Music
 * Author: A'maya
 */
public class MusicController {
    /**
     * I added this because it got too annoying to copy and past code everywhere lol
     */
    public static void playMusic() {
        // path to music mp3 file
        String musicPath = "src/main/resources/music/music.mp3";

        // instantiating Media
        Media media = new Media(new File(musicPath).toURI().toString());

        // instantiating MediaPlayer
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
    }

    /**
     * I added multiple threads so the button sound can play as well - A'maya
     */
    public static void multipleThreads() {

        // path to music mp3 file
        String musicPath = "src/main/resources/music/music.mp3";

        // instantiating Media
        Media media = new Media(new File(musicPath).toURI().toString());

        // instantiating MediaPlayer
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);

        //by setting this property to true, the audio will be played
        ExecutorService service = Executors.newFixedThreadPool(10);
        Runnable r = () -> mediaPlayer.setAutoPlay(true);
        service.execute(r);

    }

    /**
     * This is for the button clicking sound
     */
    public static void buttonClick() {
        // adding button sound mp3 file
        String buttonSound = "src/main/resources/music/buttonSound.mp3";
        Media buttonMedia = new Media(new File(buttonSound).toURI().toString());
        MediaPlayer buttonPlayer = new MediaPlayer(buttonMedia);
        buttonPlayer.play();
    }
}
