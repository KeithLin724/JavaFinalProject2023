package Game.role;

// set the enemy different type witch this 
// https://www.youtube.com/watch?v=N7zib3qm5Oc&list=PL4rzdwizLaxYmltJQRjq18a9gsSyEQQ-0&index=18

public enum GameEnemyType {
    ENEMY_TYPE_1(15, 5);

    public final int health;
    public final int damage;

    GameEnemyType(int health, int damage) {
        this.health = health;
        this.damage = damage;
    }
}
