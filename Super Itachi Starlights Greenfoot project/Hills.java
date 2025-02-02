import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Knockoff old-school Windows background.
 */
public class Hills extends Actor
{
    private int iterTimer;
    private static final int MOVEMENT_INTERVAL = 5;
    
    public Hills()
    {
        getImage().scale(903,350);
        iterTimer = 0;
    }
    
    /**
     * Act - do whatever the Background wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        scrollLeft();
    }
    
    /**
     * Method scrollLeft
     * When the Player is running rightward and in the center of the world,
     * slowly move left as the Player stays in the same spot.
     * The two Hills objects resetting their locations as soon as they've fully
     * left the screen, like a conveyer belt, creates a scrolling effect.
     */
    public void scrollLeft()
    {
        if (iterTimer == 0){
            iterTimer = MOVEMENT_INTERVAL;
        }
        else{
            iterTimer--;
        }
        if (Greenfoot.isKeyDown("right") && iterTimer == 0){
            MyWorld myWorld = (MyWorld)getWorld();
            if (myWorld.getPlayer().isAtCenter()){
                move(-1);
                if (getX() <= -450){
                    setLocation(1352, getY());
                }
            }
        }
    }
}
