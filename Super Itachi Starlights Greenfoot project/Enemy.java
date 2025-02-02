import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Enemies (aka Starlight Glimmer from My Little Pony) appear and walk across
 * at random intervals.
 * Upon contact w/ the Player, they decrease playerHealth in the HealthBar class.
 * They can be extinguished by Shurikens that the Player throws.
 * If an Enemy makes it across the screen w/o being extinguished, the Player
 * loses affluence.
 * 
 */
public class Enemy extends Actor
{
    private GreenfootImage [] frames;
    private int frameIndex;
    private boolean hasContactedPlayer;
    
    public Enemy()
    {
        frames = new GreenfootImage[36];
        for (int i = 0; i < frames.length; i++){
            frames[i] = new GreenfootImage("SL_" + (i) + ".png");
        }
        frameIndex = Greenfoot.getRandomNumber(36);
        hasContactedPlayer = false;
    }
    
    /**
     * Act - do whatever the Enemy wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        animateWalking();
        moveLeft();
        checkForEdge();
    }
    
    /**
     * Method animateWalking
     * Animate the Enemy walking.
     */
    public void animateWalking()
    {
        frameIndex++;
        if (frameIndex == frames.length){
            frameIndex = 0;
        }
        setImage(frames[frameIndex]);
        move(-1);
    }
    
    /**
     * Method moveLeft
     * In addition to moving left in the animateWalking method, move left
     * additional cells to simulate the Player running rightward when they're at
     * the center of the world.
     */
    public void moveLeft()
    {
        if (Greenfoot.isKeyDown("right")){
            MyWorld myWorld = (MyWorld)getWorld();
            if (myWorld.getPlayer().isAtCenter()){
                move(-3);
            }
        }
    }
    
    /**
     * Method checkForEdge
     * Remove the Enemy if it makes it across the screen and mutate Player
     * variables accordingly.
     */
    public void checkForEdge()
    {
        if (getX() <= -35){
            MyWorld myWorld = (MyWorld)getWorld();
            myWorld.getPlayer().enemyLeaked();
            getWorld().removeObject(this);
        }
    }
    
    /**
     * Accessor returnHasContactedPlayer
     * Called in the Player class to have the Player loses health a max. of
     * one time per Enemy.
     */
    public boolean returnHasContactedPlayer()
    {
        return hasContactedPlayer;
    }
    
    /**
     * Mutator wasContacted
     * Called in the Player class if contacted by an Enemy.
     * Works collaboratively w/ returnHasContactedPlayer.
     */
    public void wasContacted()
    {
        hasContactedPlayer = true;
    }
}
