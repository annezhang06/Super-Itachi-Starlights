import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The character that the user plays as.
 * Player can run, jump, collect items and throw shurikens.
 * 
 */
public class Player extends Actor
{
    private GreenfootImage[] rightFrames;
    private GreenfootImage[] leftFrames;
    private int frameIndex;
    private int iterTimer;
    private static final int FRAMES_INTERVAL = 2; //change frames every #+1 iterations
    
    private boolean isRunningRight;
    private boolean isRunningLeft;
    private boolean jumpable;
    private boolean isAirborne;
    private boolean isFalling;
    private int vSpeed;
    private static final double GRAVITATIONAL_ACCEL_PER_ITER = 1.3;
    private static final int JUMPING_V0 = -23;
    private boolean isFacingRight;
    private boolean isOnBlock;
    
    private int shurikens;
    private int throwIters;
    private static final int THROWING_INTERVAL = 10;
    
    private boolean isBeingTransparent;
    private int transparencyIters;
    private int enemiesLeaked;
    private int affluence; //lost when Starlights are leaked; gained from coins
    private int jubilationGems;
    private int prosperityGems;
    private int harmonyGems;
    
    public Player()
    {
        getImage().scale(34,88);
        rightFrames = new GreenfootImage[6];
        for (int i = 0; i < rightFrames.length; i++){
            rightFrames[i] = new GreenfootImage("running-right-itachi-" + (i+1) + ".png");
            rightFrames[i].scale(63,60);
        }
        leftFrames = new GreenfootImage[6];
        for (int i = 0; i < leftFrames.length; i++){
            leftFrames[i] = new GreenfootImage("running-right-itachi-" + (i+1) + ".png");
            leftFrames[i].mirrorHorizontally();
            leftFrames[i].scale(63,60);
        }
        frameIndex = 0;
        iterTimer = 0;
        isRunningRight = false;
        isRunningLeft = false;
        jumpable = true;
        isAirborne = false;
        isFalling = false;
        isFacingRight = true;
        isOnBlock = false;
        shurikens = 10;
        throwIters = 0;
        
        isBeingTransparent = false;
        transparencyIters = 0;
        enemiesLeaked = 0;
        affluence = 50;
        jubilationGems = 0;
        prosperityGems = 0;
        harmonyGems = 0;
    }
    
    /**
     * Act - do whatever the Player wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        checkForFall();
        checkForLanding();
        checkMovementKeys();
        checkForEnemy();
        modulateTransparency();
        checkForReload();
        checkForGem();
        checkForBill();
        throwShuriken();
        displayStats();
        stopGameAtNegativeAffluence();
    }
    
    /**
     * Method checkMovementKeys
     * Check what keys are down and move/animate Player accordingly.
     */
    public void checkMovementKeys()
    {
        if (!Greenfoot.isKeyDown("up")){
            jumpable = true;
        }
        if (Greenfoot.isKeyDown("right")){
            if (isFacingRight == false){
                isFacingRight = true;
            }
            moveToCenter();
            if (!isAirborne){
                runRight();
            }
            else if (isAirborne && getImage() == leftFrames[3]){
                setImage(rightFrames[3]);
            }
        }
        else if (Greenfoot.isKeyDown("left")){
            if (isFacingRight == true){
                isFacingRight = false;
            }
            if (!isAtEdge()){
                move(-3);
            }
            if (!isAirborne){
                runLeft();
            }
            else if (isAirborne && getImage() == rightFrames[3]){
                setImage(leftFrames[3]);
            }
        }
        if (Greenfoot.isKeyDown("up") && !isAirborne && jumpable){
            jump();
            jumpable = false;
        }
        if (!Greenfoot.isKeyDown("right") && !Greenfoot.isKeyDown("left") && !Greenfoot.isKeyDown("up")){
            beStanding();
        }
    }
    
    /**
     * Method runRight
     * Animate the Player running right.
     */
    public void runRight()
    {
        if (isRunningRight == false){
            frameIndex = 0;
            iterTimer = 0;
        }
        else{
            if (iterTimer == 0){
                frameIndex++;
                if (frameIndex == rightFrames.length){
                    frameIndex = 0;
                }
            }
            if (iterTimer == 0){
                iterTimer = FRAMES_INTERVAL;
            }
            else{
                iterTimer--;
            }
        }
        if (iterTimer == 0){
            setImage(rightFrames[frameIndex]);
        }
        if (isRunningRight == false){
            isRunningRight = true;
            isRunningLeft = false;
            setPreciseLocation();
        }
    }
    
    /**
     * Method moveToCenter
     * Move the Player rightward, when jumping or running, but only while
     * it's left of center.
     * (If the user has the Player run rightward when it's at the center,
     * other objects instead move *left*.)
     */
    public void moveToCenter()
    {
        if (getX() < 450){
            move(3);
            if (getX() > 450){
                setLocation(450, getY());
            }
        }
    }
    
    /**
     * Method runLeft
     * Animate the Player running left.
     */
    public void runLeft()
    {
        if (isRunningLeft == false){
            frameIndex = 0;
            iterTimer = 0;
        }
        else{
            if (iterTimer == 0){
                frameIndex++;
                if (frameIndex == leftFrames.length){
                    frameIndex = 0;
                }
            }
            if (iterTimer == 0){
                iterTimer = FRAMES_INTERVAL;
            }
            else{
                iterTimer--;
            }
        }
        if (iterTimer == 0){
            setImage(leftFrames[frameIndex]);
        }
        if (isRunningLeft == false){
            isRunningLeft = true;
            isRunningRight = false;
            setPreciseLocation();
        }        
    }
    
    /**
     * Method jump
     * Move the Player in the world in such a way that makes it look like
     * they're jumping.
     */
    public void jump()
    {
        if (isFacingRight){
            setImage(rightFrames[3]);
        }
        else{
            setImage(leftFrames[3]);
        }
        vSpeed = JUMPING_V0;
        applyGravity();
    }
    
    /**
     * Method applyGravity
     * Modulate the "vertical velocity" of the Player every iteration to
     * simulate gravity (when the Player is jumping/falling).
     */
    public void applyGravity()
    {
        isAirborne = true;
        isRunningRight = false;
        isRunningLeft = false;
        isOnBlock = false;
        setLocation(getX(), getY()+vSpeed);
        vSpeed = (int)(vSpeed + GRAVITATIONAL_ACCEL_PER_ITER);
    }
    
    /**
     * Method checkForFall
     * Have the player start to "fall" according to gravity if they run off
     * a group of Blocks.
     */
    public void checkForFall()
    {
        if (isOnBlock){
            if (getOneObjectAtOffset(0, getImage().getHeight()/2, Block.class) == null){
                applyGravity();
            }
        }
    }
    
    /**
     * Method checkForLanding
     * Continue/stop applying gravity as necessary to make the Player land on
     * Blocks or the Ground.
     */
    public void checkForLanding()
    {
        if (isAirborne && vSpeed > 0){
            isFalling = true;
        }
        else{
            isFalling = false;
        }
        if (isAirborne){
            if (onSurface() && isFalling){
                vSpeed = 0;
                isAirborne = false;
            }
            else{
                applyGravity();
            }
        }
    }
    
    /**
     * Method onSurface
     * Returns true if the Player is in contact with a Block or the Ground;
     * false otherwise.
     */
    public boolean onSurface()
    {
        Actor blockUnder = getOneObjectAtOffset(0, getImage().getHeight()/2, Block.class);
        Actor groundUnder = getOneObjectAtOffset(0, getImage().getHeight()/2, Ground.class);
        if (blockUnder != null || groundUnder != null){
            if (blockUnder != null){
                isOnBlock = true;
            }
            return true;
        }
        else{
            return false;
        }
    }
    
    /**
     * Method beStanding
     * Set the Player's image to standing, facing in the current most logical
     * direction.
     */
    public void beStanding(){
        if (isRunningRight || isRunningLeft || !isAirborne){
            isRunningRight = false;
            isRunningLeft = false;
            setImage("standing-still-right-itachi-1.png");
            getImage().scale(34,88);
            if (!isFacingRight){
                getImage().mirrorHorizontally();
            }
            setPreciseLocation();
        }
    }
    
    /**
     * Method setPreciseLocation
     * Set the y-location of the Player just right (when running and standing,
     * on Blocks and the Ground).
     */
    public void setPreciseLocation()
    {
        setLocation(getX(), getY()-40);
        Actor blockBelow = getOneObjectAtOffset(0, getImage().getHeight()/2, Block.class);
        Actor groundBelow = getOneObjectAtOffset(0, getImage().getHeight()/2, Ground.class);
        while (blockBelow == null && groundBelow == null){
            setLocation(getX(), getY()+1);
            blockBelow = getOneObjectAtOffset(0, getImage().getHeight()/2, Block.class);
            groundBelow = getOneObjectAtOffset(0, getImage().getHeight()/2, Ground.class);
        } 
        if (!isRunningRight && !isRunningLeft){
            setLocation(getX(), getY()+2);
        }
        if (isOnBlock){
            setLocation(getX(), getY()+3);
        }
    }
    
    /**
     * Method checkForEnemy
     * If the Player has contacted an Enemy, adjust the HealthBar accordingly
     * and start the modulating-transparency "effect".
     */
    public void checkForEnemy()
    {
        Enemy enemy = (Enemy)getOneIntersectingObject(Enemy.class);
        if (enemy != null && !enemy.returnHasContactedPlayer()){
            enemy.wasContacted();
            MyWorld myWorld = (MyWorld)getWorld();
            myWorld.getHealthBar().loseHealth();
            isBeingTransparent = true;
            transparencyIters = 0;
        }
    }
    
    /**
     * Method modulateTransparency
     * Called if the Player is contacted by an Enemy and is losing health;
     * purely visual.
     */
    public void modulateTransparency()
    {
        if (isBeingTransparent){
            transparencyIters++;
            if (transparencyIters <= 15){
                getImage().setTransparency(180);
            }
            else if (transparencyIters <= 30){
                getImage().setTransparency(255);
            }
            else if (transparencyIters <= 45){
                getImage().setTransparency(180);
            }
            else if (transparencyIters == 46){
                getImage().setTransparency(255);
                resetImageTransparencies();
                isBeingTransparent = false;
            }
        }
    }
    
    /**
     * Method resetImageTransparencies
     * The Player's animation array images need to be reset to opaque
     * once the transparency "effect" is over.
     */
    public void resetImageTransparencies()
    {
        for (int i = 0; i < rightFrames.length; i++){
            rightFrames[i].setTransparency(255);
        }
        for (int i = 0; i < leftFrames.length; i++){
            leftFrames[i].setTransparency(255);
        }
    }
    
    /**
     * Method checkForReload
     * Add 10 shurikens to the Player if they touch a Reload object (a croissant).
     * Remove the Reload and play a sound.
     */
    public void checkForReload()
    {
        Reload touchingReload = (Reload)getOneIntersectingObject(Reload.class);
        if (touchingReload != null){
            shurikens = shurikens + 10;
            getWorld().removeObject(touchingReload);
            Greenfoot.playSound("reload collected.mp3");
        }
    }
    
    /**
     * Method checkForGem
     * If the Player is touching a Gem, check which type of Gem it was and add to the
     * Player's corresponding Gem count (variable).
     * Remove the Gem and play a sound.
     */
    public void checkForGem()
    {
        Gem touchingGem = (Gem)getOneIntersectingObject(Gem.class);
        if (touchingGem != null){
            if (touchingGem.getGemType() == "jubilation"){
                jubilationGems++;
                Greenfoot.playSound("j gem collected.mp3");
            }
            else if (touchingGem.getGemType() == "prosperity"){
                prosperityGems++;
                Greenfoot.playSound("p gem collected.mp3");
            }
            else if (touchingGem.getGemType() == "harmony"){
                harmonyGems++;
                Greenfoot.playSound("h gem collected.mp3");
            }
            getWorld().removeObject(touchingGem);
        }
    }
    
    /**
     * Method checkForBill
     * If the Player is touching a Bill, add to their affluence a random value
     * between 1 and 10.
     * Remove the Bill and play a sound.
     */
    public void checkForBill()
    {
        Bill touchingBill = (Bill)getOneIntersectingObject(Bill.class);
        if (touchingBill != null){
            int value = Greenfoot.getRandomNumber(10)+1;
            affluence = affluence + value;
            getWorld().removeObject(touchingBill);
            Greenfoot.playSound("bill collected.mp3");
        }
    }
    
    /**
     * Method throwShuriken
     * If the user presses the spacebar, add a Shuriken to the Player's current
     * location and have it travel in the direction the Player is facing.
     */
    public void throwShuriken()
    {
        if (Greenfoot.isKeyDown("space") && shurikens > 0){
            if (throwIters == 0){
                if (isFacingRight){
                    getWorld().addObject(new Shuriken(true), getX(), getY()-5);
                }
                else if (!isFacingRight){
                    getWorld().addObject(new Shuriken(false), getX(), getY()-5);
                }
                shurikens--;
                throwIters = THROWING_INTERVAL;
            }
            else{
                throwIters--;
            }
        }
        else{
            throwIters = 0;
        }
    }
    
    /**
     * Method displayStats
     * Display the Player's enemiesLeaked, affluence, gems collected, and shurikens
     * stats.
     * (This method looks overly complicated b/c I tried to keep the left "margin" of
     * the screen consistent, while the lines of text are *centered* to the
     * assigned locations.)
     */
    public void displayStats()
    {
        int enemiesX = 92 + 5*String.valueOf(enemiesLeaked).length();
        getWorld().showText("Enemies leaked: " + enemiesLeaked, enemiesX, 25);
        for (int xCoord = enemiesX-5; xCoord >= 97; xCoord = xCoord-5){
            getWorld().showText(" ", xCoord, 25);
        }
        
        int affluenceX = 63 + 5*String.valueOf(affluence).length();
        if (affluence < 0){
            affluenceX = affluenceX-3;
        }
        getWorld().showText("Affluence: " + affluence, affluenceX, 55);
        for (int xCoord = affluenceX-1; xCoord >= 60; xCoord--){
            getWorld().showText(" ", xCoord, 55);
        }
        
        int jGemsX = 123 + 5*String.valueOf(jubilationGems).length();
        getWorld().showText("Jubilation gems: " + jubilationGems, jGemsX, 85);
        for (int xCoord = jGemsX-5; xCoord >= 128; xCoord = xCoord-5){
            getWorld().showText(" ", xCoord, 85);
        }
        
        int pGemsX = 126 + 5*String.valueOf(prosperityGems).length();
        getWorld().showText("Prosperity gems: " + prosperityGems, pGemsX, 115);
        for (int xCoord = pGemsX-5; xCoord >= 131; xCoord = xCoord-5){
            getWorld().showText(" ", xCoord, 115);
        }
        
        int hGemsX = 119 + 5*String.valueOf(harmonyGems).length();
        getWorld().showText("Harmony gems: " + harmonyGems, hGemsX, 145);
        for (int xCoord = hGemsX-5; xCoord >= 124; xCoord = xCoord-5){
            getWorld().showText(" ", xCoord, 145);
        }
        
        getWorld().showText("Shurikens: " + shurikens, 770, 60);
    }
    
    /**
     * Method stopGameAtNegativeAffluence
     * If affluence goes below zero, end the game and play a trombone sound.
     */
    public void stopGameAtNegativeAffluence()
    {
        if (affluence < 0){
            getWorld().showText("GAME OVER", getWorld().getWidth()/2, getWorld().getHeight()/2-20);
            getWorld().showText("Negative affluence", getWorld().getWidth()/2, getWorld().getHeight()/2+20);
            Greenfoot.playSound("game over trombone.mp3");
            Greenfoot.stop();
        }
    }
    
    /**
     * Mutator enemyLeaked
     * When an Enemy makes it across the screen, add to the Player's count of
     * enemiesLeaked, minus 5 from the Player's affluence, and play a sound.
     * (Called in the Enemy class.)
     */
    public void enemyLeaked()
    {
        enemiesLeaked++;
        affluence = affluence - 5;
        Greenfoot.playSound("enemy leaked.mp3");
    }
    
    /**
     * Accessor isAtCenter
     * Returns true if the Player is at the center of the world (x-coord = 450).
     * Used in other classes wherever the objects need to know whether it's
     * time to simulate rightward running.
     */
    public boolean isAtCenter()
    {
        if (getX() == 450){
            return true;
        }
        else{
            return false;
        }
    }
}
