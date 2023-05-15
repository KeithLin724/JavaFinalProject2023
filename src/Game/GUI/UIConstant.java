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

    public enum URMButtons {
        URM_SIZE_DEFAULT(56),
        URM_SIZE((int) (URM_SIZE_DEFAULT.value * SCALE));

        public final int value;

        URMButtons(int value) {
            this.value = value;
        }

    }

    public enum VolumeButtons {
        VOLUME_DEFAULT_WIDTH(28),
        VOLUME_DEFAULT_HEIGHT(44),
        SLIDER_DEFAULT_WIDTH(215),

        VOLUME_WIDTH((int) (VOLUME_DEFAULT_WIDTH.value * SCALE)),
        VOLUME_HEIGHT((int) (VOLUME_DEFAULT_HEIGHT.value * SCALE)),
        SLIDER_WIDTH((int) (SLIDER_DEFAULT_WIDTH.value * SCALE));

        public final int value;

        VolumeButtons(int value) {
            this.value = value;
        }
    }

    public enum StatusBar {
        STATUS_BAR_WIDTH((int) (192 * SCALE)),
        STATUS_BAR_HEIGHT((int) (58 * SCALE)),
        STATUS_BAR_X((int) (10 * SCALE)),
        STATUS_BAR_Y((int) (10 * SCALE)),

        HEALTH_BAR_WIDTH((int) (150 * SCALE)),
        HEALTH_BAR_HEIGHT((int) (4 * SCALE)),

        HEALTH_BAR_X_START((int) (34 * SCALE)),
        HEALTH_BAR_Y_START((int) (14 * SCALE));

        public final int value;

        StatusBar(int value) {
            this.value = value;
        }

    }

}
