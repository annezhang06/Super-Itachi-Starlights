import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The Player can collect Bills (found on top of Blocks) to add to their affluence.
 * Each Bill gives the Player a random affluence value between 1 and 10.
 * 
 */
public class Bill extends Actor
{
    /**
     * Act - do whatever the Bill wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        moveLeft();
    }
    
    /**
     * Method moveLeft
     * Simulate the Player running rightward when they're at the center of the
     * world by moving to the left.
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
     * Accessor isOnBlock
     * Returns true if the Bill is "on" a Block when created; false otherwise.
     * Called in the MyWorld class; Bills that aren't on a Block when added
     * will be removed, right after being added.
     */
    public boolean isOnBlock()
    {
        return isTouching(Block.class);
    }
}
