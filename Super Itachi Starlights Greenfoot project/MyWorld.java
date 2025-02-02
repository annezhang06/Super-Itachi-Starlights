import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class MyWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MyWorld extends World
{
    private int blocksIterCounter;
    private int blocksInterval;
    private int reloadIterCounter;
    private int reloadInterval;
    private int gemsIterCounter;
    private int gemsInterval;
    private int billsIterCounter;
    private int billsInterval;
    Player player;
    HealthBar healthbar;
    
    /**
     * Constructor for objects of class MyWorld.
     * Set/randomize the initial iteration intervals for the Block, Reload, Gem,
     * and Bill classes (the respective intervals at which to add objects of those
     * classes), and initialize the iter counters for each of those classes to be
     * random numbers between 0 and the intervals.
     */
    public MyWorld()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(900, 600, 1, false); 
        setPaintOrder(Shuriken.class, Enemy.class, Player.class, Reload.class, Gem.class, Bill.class, Block.class);
    
        blocksInterval = Greenfoot.getRandomNumber(200)+150;
        blocksIterCounter = Greenfoot.getRandomNumber(blocksInterval);
        
        reloadInterval = Greenfoot.getRandomNumber(150)+450;
        reloadIterCounter = Greenfoot.getRandomNumber(reloadInterval);
        
        gemsInterval = Greenfoot.getRandomNumber(30)+40;
        gemsIterCounter = Greenfoot.getRandomNumber(gemsInterval);
        
        billsInterval = Greenfoot.getRandomNumber(55)+100;
        billsIterCounter = Greenfoot.getRandomNumber(billsInterval);
        
        player = new Player();
        healthbar = new HealthBar();
        
        addStartingObjects();
    }
    
    /**
     * Method addStartingObjects
     * Add all the objects that need to be in the world at the start of the game.
     */
    public void addStartingObjects()
    {
        addObject(new Sky(), 450, 300);
        
        addObject(new Hills(), 450, 330);
        Actor h = new Hills();
        h.getImage().mirrorHorizontally();
        addObject(h, 1350, 330);
        
        addObject(new Ground(), 450, 555);
        addObject(new Ground(), 1350, 555);
        
        addObject(player, 450, 463);
        
        showText("Health:", 600, 25);
        addObject(healthbar, 763, 25);
        
        addObject(new Gem(1, false), 23, 85);
        addObject(new Gem(2, false), 23, 115);
        addObject(new Gem(3, false), 23, 145);
    }
    
    public void act()
    {
        addEnemies();
        addBlocks();
        addReloads();
        addGems();
        addBills();
    }
    
    /**
     * Method addEnemies
     * Add Enemies to the world 0.5% of the time.
     */
    public void addEnemies()
    {
        if (Greenfoot.getRandomNumber(200) == 0){
            addObject(new Enemy(), 935, 463);
        }
    }
    
    /**
     * Method addBlocks
     * Add "groups" of Blocks of random lengths between 3 and 6 (inclusive)
     * at semi-random intervals.
     */
    public void addBlocks()
    {
        if (player.isAtCenter() && Greenfoot.isKeyDown("right")){
            blocksIterCounter++;
            if (blocksIterCounter == blocksInterval){
                blocksIterCounter = 0;
                blocksInterval = Greenfoot.getRandomNumber(200)+150;
            }
            if (blocksIterCounter == 0){
                int platformLength = Greenfoot.getRandomNumber(4)+3;
                for (int i = 0; i < platformLength; i++){
                    addObject(new Block(), 920 + i*33, 390);
                }
            } 
        }
    }
    
    /**
     * Method addReloads
     * Add Reloads on top of Block groups (<-effectively) at semi-random intervals.
     */
    public void addReloads()
    {
        if (player.isAtCenter() && Greenfoot.isKeyDown("right")){
            reloadIterCounter++;
            if (reloadIterCounter == reloadInterval){
                reloadIterCounter = 0;
                reloadInterval = Greenfoot.getRandomNumber(150)+450;
            }
            if (reloadIterCounter == 0){
                Reload r = new Reload();
                addObject(r, 910, 365);
                if (!r.isOnBlock()){
                    removeObject(r);
                }
            } 
        }
    }
    
    /**
     * Method addGems
     * Add Gems of random type on top of Block groups (<-effectively) at
     * semi-random intervals.
     */
    public void addGems()
    {
        if (player.isAtCenter() && Greenfoot.isKeyDown("right")){
            gemsIterCounter++;
            if (gemsIterCounter == gemsInterval){
                gemsIterCounter = 0;
                gemsInterval = Greenfoot.getRandomNumber(30)+40;
            }
            if (gemsIterCounter == 0){
                Gem g = new Gem(Greenfoot.getRandomNumber(3)+1, true);
                addObject(g, 910, 370);
                if (!g.isOnBlock()){
                    removeObject(g);
                }
                else{
                    g.hover();
                }
            } 
        }
    }
    
    /**
     * Method addBills
     * Add Bills on top of Block groups (<-effectively) at semi-random intervals.
     */
    public void addBills()
    {
        if (player.isAtCenter() && Greenfoot.isKeyDown("right")){
            billsIterCounter++;
            if (billsIterCounter == billsInterval){
                billsIterCounter = 0;
                billsInterval = Greenfoot.getRandomNumber(55)+100;
            }
            if (billsIterCounter == 0){
                Bill b = new Bill();
                addObject(b, 910, 360);
                if (!b.isOnBlock()){
                    removeObject(b);
                }
            } 
        }
    }
    
    /**
     * Accessor getPlayer
     * Returns the instance of the Player that was added to the world at
     * the start of the game.
     * Called in several other classes, mostly to check if the Player is at
     * the center of the world, for simulated rightward running created by
     * other objects moving left.
     */
    public Player getPlayer()
    {
        return player;
    }
    
    /**
     * Accessor getHealthBar
     * Returns the instance of the HealthBar that was added to the world at
     * the start of the game.
     * Called in the Player class in order to update the HealthBar.
     */
    public HealthBar getHealthBar()
    {
        return healthbar;
    }
}
