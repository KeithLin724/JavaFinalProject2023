package Game.audio;

/**
 * This is an enumeration class called `GameAudio` that defines different audio
 * files used in a game.
 * Each audio file is represented as a constant with a unique name and an
 * associated integer index and
 * file name. The class also defines several arrays that group the audio files
 * based on their type,
 * such as songs, UI sounds, and effects. The constructor of the class
 * initializes the index and file
 * name of each constant.
 */
public enum GameAudio {
        // songs
        MENU_1(0, "menu"),
        LEVEL_1(1, "level1"),
        LEVEL_2(2, "level2"),

        // ui
        CLICK(0, "click"),

        // player
        DIE(0, "die"),
        JUMP(1, "jump"),
        GAMEOVER(2, "gameover"),
        LVL_COMPLETED(3, "lvlcompleted"),
        ATTACK_ONE(4, "attack1"),
        ATTACK_TWO(5, "attack2"),
        ATTACK_THREE(6, "attack3"),
        PLAYER_GET_HIT(7, "player_get_hit"),
        ENEMY_ATTACK(8, "enemy_attack"),
        ENEMY_DIE(9, "enemy_die"),
        ENEMY_FOUND_PLAYER(10, "find_player");

        public final int arrayIndex;
        public final String fileName;

        public static final GameAudio[] allSongAudio = {
                        MENU_1,
                        LEVEL_1,
                        LEVEL_2,
        };

        public static final GameAudio[] allUISoundsAudio = {
                        CLICK,
        };

        public static final GameAudio[] allEffectAudio = {
                        DIE,
                        JUMP,
                        GAMEOVER,
                        LVL_COMPLETED,
                        ATTACK_ONE,
                        ATTACK_TWO,
                        ATTACK_THREE,
                        PLAYER_GET_HIT,
                        ENEMY_ATTACK,
                        ENEMY_DIE,
                        ENEMY_FOUND_PLAYER,
        };

        public static final GameAudio[] allAttackSound = {
                        ATTACK_ONE,
                        ATTACK_TWO,
                        ATTACK_THREE,
        };

        public static final GameAudio[] allEnemyAttackEffectSound = {
                        ENEMY_ATTACK,
        };

        GameAudio(int arrayIndex, String fileName) {
                this.arrayIndex = arrayIndex;
                this.fileName = fileName;
        }

}
