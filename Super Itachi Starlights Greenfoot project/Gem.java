import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The Player collects Gems, which are found on top of Blocks.
 * There are three varieties of Gem: Jubilation, Prosperity, and Harmony.
 * 
 */
public class Gem extends Actor
{
    private String gemType;
    private boolean isInPlay;
    
    /**
     * Gem Constructor
     * The boolean parameter is necessary here b/c the three Gem objects that
     * are shown in the top left as part of the Player's stats should *not*
     * move left to simulate rightward running, so the act method only applies
     * for Gems that are "in play"/part of the "game".
     */
    public Gem(int typeNo, boolean partOfGame)
    {
        if (typeNo == 1){
            gemType = "jubilation";
            setImage("orange gem.png");
        }
        else if (typeNo == 2){
            gemType = "prosperity";
            setImage("purple gem2.png");
        }
        else if (typeNo == 3){
            gemType = "harmony";
            setImage("red gem2.png");
        }
        isInPlay = partOfGame;
    }
    
    /**
     * Act - do whatever the Gem wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        if (isInPlay){
            moveLeft();
        }
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
     * Returns true if the Gem is "on" a Block when created; false otherwise.
     * Called in the MyWorld class; Gems that aren't on a Block when added
     * will be removed, right after being added.
     */
    public boolean isOnBlock()
    {
        return isTouching(Block.class);
    }
    
    /**
     * Mutator hover
     * Called in the MyWorld class to make the Gem "hover" above the Blocks on
     * which it exists (for visual appeal).
     */
    public void hover()
    {
        setLocation(getX(), getY()-10);
    }
    
    /**
     * Method getGemType
     * Returns the Gem's type.
     * Called by Player to know what type of gem they've collected.
     */
    public String getGemType()
    {
        return gemType;
    }
}
