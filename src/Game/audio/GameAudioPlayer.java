package Game.audio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sound.sampled.BooleanControl;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import base.loader.BaseLoader;
import base.loader.FileNameFormatter;

public class GameAudioPlayer {

    private static final Logger LOGGER = Logger.getLogger(GameAudioPlayer.class.getName());

    private Clip[] songs, effects;
    private int currentSongId;

    private float volume = 1f;

    private boolean songMute, effectMute;

    // for the attack sound
    private Random random = new Random();

    private final String folderPath;

    public GameAudioPlayer(String folderPath) {
        this.folderPath = folderPath;
        try {
            loadSongs();
            loadEffects();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            LOGGER.log(Level.SEVERE, "load sounds error", e);
        }
        playSong(GameAudio.MENU_1);
    }

    private void loadSongs()
            throws FileNotFoundException,
            UnsupportedAudioFileException,
            IOException,
            LineUnavailableException {
        this.songs = new Clip[GameAudio.allSongNames.length];

        for (int i = 0; i < this.songs.length; i++) {
            this.songs[i] = loadClip(GameAudio.allSongNames[i]);
        }

    }

    private void loadEffects()
            throws FileNotFoundException,
            UnsupportedAudioFileException,
            IOException,
            LineUnavailableException {
        this.effects = new Clip[GameAudio.allEffectNames.length];

        for (int i = 0; i < this.effects.length; i++) {
            this.effects[i] = loadClip(GameAudio.allEffectNames[i]);
        }

        updateEffectsVolume();
    }

    private String soundPath(String fileName) {
        return FileNameFormatter.of(folderPath + fileName, FileNameFormatter.SOUND);
    }

    private Clip loadClip(String fileName)
            throws FileNotFoundException,
            UnsupportedAudioFileException,
            IOException,
            LineUnavailableException {
        return BaseLoader.loadClip(soundPath(fileName));
    }

    public void setVolume(float volume) {
        this.volume = volume;
        updateSongVolume();
        updateEffectsVolume();
    }

    public void stopSong() {
        if (this.songs[currentSongId].isActive()) {
            this.songs[currentSongId].stop();
        }
    }

    public void setLevelSong(int levelIndex) {
        if (levelIndex % 2 == 0) {
            playSong(GameAudio.LEVEL_1);
        } else {
            playSong(GameAudio.LEVEL_2);
        }
    }

    public void levelCompleted() {
        this.stopSong();
        playEffect(GameAudio.LVL_COMPLETED);

    }

    public void playAttackSound() {
        playEffect(GameAudio.allAttackSound[random.nextInt(3)]);
    }

    public void playEffect(GameAudio gameAudio) {
        this.effects[gameAudio.arrayIndex].setMicrosecondPosition(0);
        this.effects[gameAudio.arrayIndex].start();
    }

    public void playSong(GameAudio gameAudio) {
        this.stopSong();

        currentSongId = gameAudio.arrayIndex;
        updateSongVolume();

        this.songs[currentSongId].setMicrosecondPosition(0);
        this.songs[currentSongId].loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void toggleSongMute() {
        this.songMute = !this.songMute;

        for (var clip : this.songs) {
            BooleanControl booleanControl = (BooleanControl) clip.getControl(BooleanControl.Type.MUTE);
            booleanControl.setValue(songMute);
        }

    }

    public void toggleEffectMute() {
        this.effectMute = !this.effectMute;

        for (var clip : this.effects) {
            BooleanControl booleanControl = (BooleanControl) clip.getControl(BooleanControl.Type.MUTE);
            booleanControl.setValue(effectMute);
        }

        if (!effectMute) {
            playEffect(GameAudio.JUMP);
        }
    }

    private FloatControl clipToFloatControl(Clip clip) {
        return (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
    }

    private void updateSongVolume() {
        FloatControl gainControl = clipToFloatControl(songs[currentSongId]);

        float range = gainControl.getMaximum() - gainControl.getMinimum();
        float gain = (range * volume) + gainControl.getMinimum();

        gainControl.setValue(gain);

    }

    private void updateEffectsVolume() {
        Arrays.stream(effects).forEach(clip -> {
            FloatControl gainControl = clipToFloatControl(clip);

            float range = gainControl.getMaximum() - gainControl.getMinimum();
            float gain = (range * volume) + gainControl.getMinimum();

            gainControl.setValue(gain);
        });
    }

}
