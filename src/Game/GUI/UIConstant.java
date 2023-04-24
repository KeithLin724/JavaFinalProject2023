package Game.GUI;

import static base.BaseGameConstant.SCALE;

public class UIConstant {
    public enum Buttons {
        B_WIDTH_DEFAULT(140),
        B_HEIGHT_DEFAULT(56),
        B_WIDTH((int) (B_WIDTH_DEFAULT.value * SCALE)),
        B_HEIGHT((int) (B_HEIGHT_DEFAULT.value * SCALE));

        public final int value;

        Buttons(int value) {
            this.value = value;
        }

    }
}
