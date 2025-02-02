import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import greenfoot.Color;
/**
 * Power bar showing the health of the Player, which decreases
 * when they are contacted by an Enemy.
 */
public class HealthBar extends Actor
{
    int playerHealth;
    int healthGainIterTimer;
    private static final int healthGainInterval = 180;
    private static final int fullHealth = 48;
    private static final int healthBarFillWidth = 240;
    private static final int healthBarFillHeight = 20;
    private static final int pixelsPerHealthPt = 5;
    
    public HealthBar()
    {
        playerHealth = fullHealth;
        update();
        healthGainIterTimer = healthGainInterval;
    }
    
    /**
     * Act - do whatever the HealthBar wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        gainHealth();
        stopGameAtZeroHealth();
    }
    
    /**
     * Method update
     * Draw the HealthBar to show the current amount of playerHealth.
     */
    public void update()
    {
        setImage(new GreenfootImage(healthBarFillWidth + 2, healthBarFillHeight + 2));
        GreenfootImage myImage = getImage();
        myImage.setColor(Color.WHITE);
        myImage.fillRect(0, 0, healthBarFillWidth+1, healthBarFillHeight+1);
        myImage.setColor(Color.GREEN);
        myImage.fillRect(1, 1, playerHealth*pixelsPerHealthPt-1, healthBarFillHeight-1);
    }
    
    /**
     * Mutator loseHealth
     * Called in the Player class when the Player is contacted by an Enemy.
     */
    public void loseHealth()
    {
        playerHealth = playerHealth - 4;
        update();
        healthGainIterTimer = healthGainInterval;
    }
    
    /**
     * Method gainHealth
     * While the HealthBar isn't full, add back "1" health every
     * healthGainInterval iterations.
     */
    public void gainHealth()
    {
        if (playerHealth < fullHealth){
            healthGainIterTimer--;
        }
        if (healthGainIterTimer == 0){
            healthGainIterTimer = healthGainInterval;
            playerHealth++;
            update();
        }
    }
    
    /**
     * Method stopGameAtZeroHealth
     * If playerHealth reaches zero, end the game and play a trombone sound.
     */
    public void stopGameAtZeroHealth()
    {
        if (playerHealth <= 0){
            getWorld().showText("GAME OVER", getWorld().getWidth()/2, getWorld().getHeight()/2-20);
            getWorld().showText("No health remaining", getWorld().getWidth()/2, getWorld().getHeight()/2+20);
            Greenfoot.playSound("game over trombone.mp3");
            Greenfoot.stop();
        }
    }
}
