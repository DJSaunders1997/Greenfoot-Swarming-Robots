import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A FireflyExplained object, subclass of the FireflySuper class.
 * FireflySuper super class hides some complexity that is unnessecarily complicated.
 * Follow the rules of the firefly synchronisation algorithm.
 * 
 * FireflyExplained has labels that show the internal clock of each firefly.
 * This is useful to visualising how the algorithm works.
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
public class FireflyExplained extends FireflySuper
{
    static int numberOfFireflys = 0;
    Label lbl;

    /**
     * Firefly Constructor 
     * Assigns the fireflys clock to a random amount.
     * Gives each fly its own unique number.
     */
    public FireflyExplained()
    {
        maxClockValue = 12;   
        currentClock = Greenfoot.getRandomNumber(maxClockValue) + 1;
                      
        lbl = new Label(currentClock, 30);  //30 is for size
    }

    public void addedToWorld(World world)
    {
        //add label on top of firefly only once firefly has been added to the this world
        getWorld().addObject(lbl, getX(), getY());
        
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
        
        currentClock++;
        
        //flashing
        if(currentClock >= maxClockValue)    //
        {
            //now flash
            setImage("fireflyFlash.png");   //make fly flash

            currentClock = 1;   //reset clock. Lowest value is 1 with the clock analogy.

            Greenfoot.playSound("ping.mp3");//play sound (optional)

            //colour label
            lbl.setFillColor(Color.RED);
        }
        //not flashing
        else    
        {
            //if a neighbour fly is flashing
            if(neighboutFlyFlashing() == true) 
            {
                currentClock++;
            }
            
            //currentClock++;
            
            setImage("firefly.png");    //make fly unflash
                
            //colour label
            lbl.setFillColor(Color.WHITE);
            
            /*
            //ensure the clock value is never greater than 12
            if (currentClock > maxClockValue) 
            {
                currentClock = maxClockValue;
            }
            */
        }
        
        //update label value and position
        lbl.setValue(currentClock);
        lbl.setLocation(getX(), getY() );
    }
}
