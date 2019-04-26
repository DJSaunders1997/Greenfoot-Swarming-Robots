;import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A world that populates the screen with Fireflys with random location and initial direction.
 * Choose between populating with Firefly's or FireflyExplained's
 * 
 * @author David Saunders 
 * @version 1.0 19.03.2019
 */
public class FireflyWorld extends World
{
    int numOfFireflies = 10;
    
    public FireflyWorld()
    {    
        super(1200, 800, 1); 
        
        
        
        for(int i = 0; i < numOfFireflies; i++) 
        {
            int x = Greenfoot.getRandomNumber(1200);
            int y = Greenfoot.getRandomNumber(800);
            int rotation = Greenfoot.getRandomNumber(360);
            
            //Change the object type to either Firefly or FireflyExplained 
            
            //Firefly fly = new Firefly();
            Firefly fly = new Firefly();
                        
            addObject(fly, x, y); 
                        
            fly.setRotation(rotation);
        }
    }
}
