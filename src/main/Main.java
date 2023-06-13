package main;

public class Main {
    public static void main(String[] args) {
        // open GPU
        // https://www.codenong.com/4627320/
        System.setProperty("sun.java2d.opengl", "true");

        // make swing more better
        System.setProperty("swing.aatext", "true");

        // about Java Swing 2D系统属性参数详解

        // https://developer.aliyun.com/article/446328#:~:text=%E7%AE%80%E4%BB%8B%EF%BC%9A%20Java%20Swing%202D%E7%B3%BB%E7%BB%9F%E5%B1%9E%E6%80%A7%E5%8F%82%E6%95%B0%E8%AF%A6%E8%A7%A3%20-Dsun.java2d.opengl%3Dtrue%20%2F%2F%20%E5%A6%82%E6%9E%9C%E7%A1%AC%E4%BB%B6%E5%8A%A0%E9%80%9F%E5%B7%B2%E7%BB%8F%E8%A2%ABenable%EF%BC%8C%E5%8F%AF%E4%BB%A5%E9%80%9A%E8%BF%87%E8%BF%99%E4%B8%AA%E9%80%89%E9%A1%B9%E6%9D%A5%E6%8F%90%E9%AB%98Swing%20GUI,%5Blog%20%20timestamp%5D%5D%2C%20%5Bcount%5D%2C%20%5Bout%3A%5D%2C%20%5Bhelp%5D%2C%20%5Bverbose%5D%20%2F%2F%E5%B8%AE%E5%8A%A9%E5%8F%91%E7%8E%B0%E5%93%AA%E4%B8%AASwing%E7%BB%84%E4%BB%B6%E5%9C%A8%E7%BB%98%E5%88%B6%E6%97%B6%E6%9C%89%E6%80%A7%E8%83%BD%E9%97%AE%E9%A2%98%E3%80%82

        Game game = new Game();
        game.runGame();
    }
}
