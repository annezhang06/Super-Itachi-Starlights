import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The "bullet" of this game.
 * The Player can extinguish Enemies by throwing Shurikens,
 * of which they have a limited number (but they can get Reloads).
 * 
 */
public class Shuriken extends Actor
{
    private GreenfootImage[] frames;
    private int frameIndex;
    private int iterTimer;
    private static final int FRAMES_INTERVAL = 1;
    private boolean isGoingRight;
    private boolean enemyHit;
    
    /**
     * Shuriken Constructor
     * When the Shuriken is created, it is set to travel either
     * right or left.
     */
    public Shuriken(boolean goingRight)
    {
        frames = new GreenfootImage[8];
        for (int i = 0; i < frames.length; i++){
            frames[i] = new GreenfootImage("shuriken_" + (i+1) + ".png");
        }
        frameIndex = 0;
        iterTimer = 0;
        isGoingRight = goingRight;
        enemyHit = false;
    }
    
    /**
     * Act - do whatever the Bullet wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        spin();
        travel();
        checkForEnemy();
        removeIfNecessary();
    }
    
    /**
     * Method spin
     * Animate the Shuriken spinning using an array of frames.
     */
    public void spin()
    {
        if (iterTimer == 0){
            iterTimer = FRAMES_INTERVAL;
        }
        else{
            iterTimer--;
        }
        if (iterTimer == 0){
            frameIndex++;
            if (frameIndex == frames.length){
                frameIndex = 0;
            }
            setImage(frames[frameIndex]);
        }
    }
    
    /**
     * Method travel
     * In addition to moving either +10 or -10 (based on direction set
     * during construction) per iter, also simulate the Player's running w/
     * everyone else when the player is at the center.
     */
    public void travel()
    {
        if (isGoingRight){
            move(10);
        }
        else{
            move(-10);
        }
        if (Greenfoot.isKeyDown("right")){
            MyWorld myWorld = (MyWorld)getWorld();
            if (myWorld.getPlayer().isAtCenter()){
                move(-3);
            }
        }
    }
    
    /**
     * Method checkForEnemy
     * Check if the Shuriken is touching an Enemy.
     * If it is, consider it extinguished.
     */
    public void checkForEnemy()
    {
        if (isTouching(Enemy.class)){
            removeTouching(Enemy.class);
            enemyHit = true;
        }
    }
    
    /**
     * Method removeIfNecessary
     * Remove the Shuriken if it's hit an Enemy or if it's reached the
     * edge of the world.
     */
    public void removeIfNecessary()
    {
        if (enemyHit || isAtEdge()){
            getWorld().removeObject(this);
        }
    }
}
