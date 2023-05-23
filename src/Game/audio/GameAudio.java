package Game.audio;

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
        ATTACK_THREE(6, "attack3");

        public final int arrayIndex;
        public final String fileName;

        public static final String[] allSongNames = {
                        MENU_1.fileName,
                        LEVEL_1.fileName,
                        LEVEL_2.fileName,
        };

        public static final String[] allUISoundsName = {
                        CLICK.fileName,
        };

        public static final String[] allEffectNames = {
                        DIE.fileName,
                        JUMP.fileName,
                        GAMEOVER.fileName,
                        LVL_COMPLETED.fileName,
                        ATTACK_ONE.fileName,
                        ATTACK_TWO.fileName,
                        ATTACK_THREE.fileName,
        };

        public static final GameAudio[] allAttackSound = {
                        ATTACK_ONE,
                        ATTACK_TWO,
                        ATTACK_THREE,
        };

        GameAudio(int arrayIndex, String fileName) {
                this.arrayIndex = arrayIndex;
                this.fileName = fileName;
        }

}
