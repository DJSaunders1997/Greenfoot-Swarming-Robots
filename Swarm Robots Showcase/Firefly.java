import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A Firefly object, subclass of the FireflySuper class.
 * FireflySuper super class hides some complexity that is unnessecarily complicated.
 * Follow the rules of the firefly synchronisation algorithm.
 * 
 * Pseudo-code:
 * Count from 1 to 12 repeatedly in a cycle
 * When the clock equals 12 flash and reset clock
 * If a fly is not flashing but detects another fireflys flash
 *  increase clock by 1
 * 
 * @author David Saunders 
 * @version 1.0 19.03.2019
 */
public class Firefly extends FireflySuper
{

    /**
     * Firefly Constructor 
     * Assigns the fireflys clock to a random amount.
     * Gives each fly its own unique number.
     */
    public Firefly()
    {
        maxClockValue = 12;   
        currentClock = Greenfoot.getRandomNumber(maxClockValue) + 1;
    }
    
    /**
     * Method act 
     * All important firefly behaviour is contained within this method.
     * Firstly a firefly must move, and then consider the logic for flashing. 
     * The algorithm is implemented in a single if else statement.      
     */
    public void act()
    {  
        crawlAround();  //method from FireflySuper class

        //decide between bounceOffEdge and loopThroughEdge
        bounceOffEdge();    //method from Swarm Robot superclass
        
        if(currentClock >= maxClockValue)   //flashing
        {
            //now flash
            setImage("fireflyFlash.png");   //make fly flash

            currentClock = 1;   //reset clock. Lowest value is 1 with the clock analogy.

            Greenfoot.playSound("ping.mp3");//play sound (optional)
        }        
        else    //not flashing
        {
            //if a neighbour fly is flashing
            if(neighbourFlyFlashing(1000) == true) 
            {
                currentClock++;
            }

            setImage("firefly.png");    //make fly unflash
            currentClock++;
        }
        
    }
}
