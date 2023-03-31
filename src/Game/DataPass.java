package Game;

final public class DataPass {

    public record AniData(int aniTick, int aniIndex, int aniSpeed) {

    }

    public record ImageScaleData(int imgScaleX, int imgScaleY, int imageScale) {

    }

    public record GamePlayerSpeedData(float playSpeed) {

    }

}
