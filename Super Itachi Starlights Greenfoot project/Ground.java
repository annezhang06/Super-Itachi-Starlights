import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The thing the Player and Enemy objects have a good time on.
 * 
 */
public class Ground extends Actor
{
    public Ground()
    {
        getImage().scale(902,100);
    }
    
    /**
     * Act - do whatever the Ground wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        scrollLeft();
    }    
    
    /**
     * Method scrollLeft
     * Scroll left when the Player is running rightward at the center of
     * the world to make it look like the game screen is a camera screen
     * that's fixed on the Player as it traverses the land.
     * The two Ground objects resetting their locations as soon as they've fully
     * left the screen, like a conveyer belt, creates a scrolling effect.
     */
    public void scrollLeft()
    {
        if (Greenfoot.isKeyDown("right")){
            MyWorld myWorld = (MyWorld)getWorld();
            if (myWorld.getPlayer().isAtCenter()){
                move(-3);
                if (getX() <= -450){
                    setLocation(1350, getY());
                }
            }
        }
    }
}
