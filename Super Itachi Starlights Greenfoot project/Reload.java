import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Shuriken reload croissants found on top of Blocks.
 * The Player can collect these to get more Shurikens.
 * 1 croissant = 10 Shurikens
 */
public class Reload extends Actor
{
    /**
     * Act - do whatever the Reload wants to do. This method is called whenever
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
     * Remove the croissant if it has crossed the entire screen.
     */
    public void removeAtEdge()
    {
        if (getX() < -15){
            getWorld().removeObject(this);
        }
    }
    
    /**
     * Accessor isOnBlock
     * Returns true if the Reload is "on" a Block when created; false otherwise.
     * Called in the MyWorld class; Reloads that aren't on a Block when added
     * will be removed, right after being added.
     */
    public boolean isOnBlock()
    {
        return isTouching(Block.class);
    }
}
