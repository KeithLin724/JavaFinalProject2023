package Game.GUI;

import static base.BaseGameConstant.SCALE;

public class UIConstant {

    public enum MenuButtons {
        B_WIDTH_DEFAULT(140),
        B_HEIGHT_DEFAULT(56),
        B_WIDTH((int) (B_WIDTH_DEFAULT.value * SCALE)),
        B_HEIGHT((int) (B_HEIGHT_DEFAULT.value * SCALE));

        public final int value;

        MenuButtons(int value) {
            this.value = value;
        }

    }

    public enum PauseLayerButtons {
        SOUND_SIZE_DEFAULT(42),
        SOUND_SIZE((int) (SOUND_SIZE_DEFAULT.value * SCALE));

        public final int value;

        PauseLayerButtons(int value) {
            this.value = value;
        }

    }
}
