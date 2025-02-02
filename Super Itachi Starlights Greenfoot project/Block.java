import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The Player can run and jump on these "Mario" blocks that scroll by as the player runs.
 * The Player needs to go on Blocks to collect shuriken Reloads, Gems, and Bills.
 * 
 */
public class Block extends Actor
{
    /**
     * Act - do whatever the Block wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        moveLeft();
        removeAtEdge();
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
     * Method removeAtEdge
     * Remove the Block when it is finished scrolling across the screen.
     */
    public void removeAtEdge()
    {
        if (getX() < -20){
            getWorld().removeObject(this);
        }
    }
}


