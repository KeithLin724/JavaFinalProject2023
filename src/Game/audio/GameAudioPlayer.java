package Game.audio;

import java.util.Arrays;
import java.util.Random;
import java.util.logging.Logger;

import javax.sound.sampled.BooleanControl;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

import Game.Loader.GameElementLoader;

public class GameAudioPlayer {

    private static final Logger LOGGER = Logger.getLogger(GameAudioPlayer.class.getName());

    private Clip[] songs, effects, uiSoundEffects;
    private int currentSongId;

    private float volume = 1f;

    private boolean songMute, effectMute;

    // for the attack sound
    private Random random = new Random();

    private final String folderPath;

    public GameAudioPlayer(String folderPath) {
        this.folderPath = folderPath;

        loadSongs();
        loadEffects();
        loadUISoundEffects();

        playSong(GameAudio.MENU_1);
    }

    private void loadUISoundEffects() {
        this.uiSoundEffects = GameElementLoader.loadClip(folderPath, GameAudio.allUISoundsAudio);
    }

    private void loadSongs() {
        this.songs = GameElementLoader.loadClip(folderPath, GameAudio.allSongAudio);
    }

    private void loadEffects() {
        this.effects = GameElementLoader.loadClip(folderPath, GameAudio.allEffectAudio);

        updateEffectsVolume();
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

    /**
     * The function stops the current song and plays a level completed sound effect
     * in a Java game.
     */
    public void levelCompleted() {
        this.stopSong();
        playEffect(GameAudio.LVL_COMPLETED);
    }

    /**
     * This function plays a random attack sound from a list of three possible
     * sounds.
     */
    public void playAttackSound() {
        playEffect(GameAudio.allAttackSound[random.nextInt(3)]);
    }

    /**
     * This function plays the sound effect for an enemy attack in a game.
     */
    public void playEnemyAttackSound() {
        playEffect(GameAudio.ENEMY_ATTACK);
    }

    /**
     * This function plays a game audio effect by setting its position to the
     * beginning and starting it.
     * 
     * @param gameAudio gameAudio is an object of the GameAudio class, which
     *                  contains information about a
     *                  specific audio effect that needs to be played. The object
     *                  likely contains properties such as the
     *                  audio file path, the index of the effect in the effects
     *                  array, and any other relevant information
     *                  needed to play the effect.
     */
    public void playEffect(GameAudio gameAudio) {
        this.effects[gameAudio.arrayIndex].setMicrosecondPosition(0);
        this.effects[gameAudio.arrayIndex].start();
    }

    /**
     * This function plays a UI sound effect in a Java game.
     * 
     * @param gameAudio It is an object of the class GameAudio, which likely
     *                  contains information about a
     *                  specific sound effect to be played in the game's user
     *                  interface. The parameter is used to determine
     *                  which sound effect to play from an array of UI sound
     *                  effects.
     */
    public void playUiEffect(GameAudio gameAudio) {
        this.uiSoundEffects[gameAudio.arrayIndex].setMicrosecondPosition(0);
        this.uiSoundEffects[gameAudio.arrayIndex].start();
    }

    /**
     * The function plays a game audio clip continuously and updates its volume.
     * 
     * @param gameAudio It is an object of the class GameAudio, which likely
     *                  contains information about a
     *                  specific audio file that is to be played. The parameter is
     *                  used to determine which audio file to
     *                  play and at what volume.
     */
    public void playSong(GameAudio gameAudio) {
        this.stopSong();

        currentSongId = gameAudio.arrayIndex;
        updateSongVolume();

        this.songs[currentSongId].setMicrosecondPosition(0);
        this.songs[currentSongId].loop(Clip.LOOP_CONTINUOUSLY);
    }

    /**
     * The function toggles the mute state of all songs in a list.
     */
    public void toggleSongMute() {
        this.songMute = !this.songMute;

        for (var clip : this.songs) {
            BooleanControl booleanControl = (BooleanControl) clip.getControl(BooleanControl.Type.MUTE);
            booleanControl.setValue(songMute);
        }

    }

    /**
     * The function toggles the mute state of sound effects and updates the mute
     * state of all sound effect
     * clips accordingly.
     */
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

    /**
     * This function updates the volume of a song being played using a FloatControl
     * object.
     */
    private void updateSongVolume() {
        FloatControl gainControl = clipToFloatControl(songs[currentSongId]);

        float range = gainControl.getMaximum() - gainControl.getMinimum();
        float gain = (range * volume) + gainControl.getMinimum();

        gainControl.setValue(gain);

    }

    /**
     * This function updates the volume of a set of audio effects.
     */
    private void updateEffectsVolume() {
        Arrays.stream(effects).forEach(clip -> {
            FloatControl gainControl = clipToFloatControl(clip);

            float range = gainControl.getMaximum() - gainControl.getMinimum();
            float gain = (range * volume) + gainControl.getMinimum();

            gainControl.setValue(gain);
        });
    }

}
