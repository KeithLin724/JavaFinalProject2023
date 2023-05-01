package Game.GUI;

import static base.BaseGameConstant.SCALE;

public enum GameEnvironment {
  BIG_CLOUD_WIDTH_DEFAULT(448),
  BIG_CLOUD_HEIGHT_DEFAULT(101),
  BIG_CLOUD_WIDTH((int) (BIG_CLOUD_WIDTH_DEFAULT.value * SCALE)),
  BIG_CLOUD_HEIGHT((int) (BIG_CLOUD_HEIGHT_DEFAULT.value * SCALE)),

  SMALL_CLOUD_WIDTH_DEFAULT(74),
  SMALL_CLOUD_HEIGHT_DEFAULT(24),
  SMALL_CLOUD_WIDTH((int) (SMALL_CLOUD_WIDTH_DEFAULT.value * SCALE)),
  SMALL_CLOUD_HEIGHT((int) (SMALL_CLOUD_HEIGHT_DEFAULT.value * SCALE));

  public final int value;

  GameEnvironment(int value) {
    this.value = value;
  }
}
