import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;

/**
 * FireflySuper is a parent to all fireflys. 
 * It contains methods used by Fireflys.  
 * Hides unnessecarily complicated or unintuitive code from the Firefly class.
 * 
 * @author David Saunders 
 * @version 1.0 19.03.2019
 */
public class FireflySuper extends SwarmRobot
{    
    static int numberOfFireflys = 0;    
    
    protected int maxClockValue;    
    protected int currentClock;
    protected int robotNumber;    //used only for firefly synchronisation algorithm.     
    //gives each robot a unique number that specifies when it was created in relation to other robot.
    //needed for checkOrder()

    /**
     * Firefly Constructor 
     * Gives each fly its own unique number.
     */
    public FireflySuper()
    {
        //Used to keep track of what number each firefly is.
        //Needed to fix concurrency issue.
        robotNumber = numberOfFireflys;
        numberOfFireflys++;                              
    }
    
    /**
     * Method neighbourFlyFlashing
     * Used only for firefly synchronisation algorithm.
     * Called when a firefly decides whether to flash earlier in its cycle.
     * Goes through every neighbour of the firefly and sees if they're flashing.
     * 
     * @param robotNum The number of the firefly.
     *  The first firefly created will have a 
     * @return Boolean returns true only if there are neighbour fireflys flashing
     */
    public boolean neighbourFlyFlashing(int visionDistance)
    {        
        List <FireflySuper> neighbours = getListOfNeighbours(FireflySuper.class, visionDistance);     

        //check if neighbours are flashing
        for ( FireflySuper neighbourFly : neighbours) 
        {
            //System.out.println(neighbourFly);

            //use checkOrder to see if any fireflys are flashing
            if (checkOrder(neighbourFly) == true)
            {
                return true;                
            }
        }
        return false;
    }

    /**
     * Method checkOrder
     * Used only for firefly synchronisation algorithm.
     * The Firefly Synchronisation Algorithm requires the concurrent execution of all agents.
     * Currently Greenfoot does not support the use of concurrency therefore a work-around is necessary.       
     * The clock of each firefly is incremented one at a time, and not simultaniously, this can cause issues.
     * 
     * Suppose there are 2 fireflys A and B, where A was created before B
     * At the start of the Act cycle A is flashing so B should increment its clock
     * A runs first, then resets its clock and is now no longer flashing
     * B looks at its neighbour A, if code for both fireflys was executed simultaneously then B would see A flash B would increments its clock
     * Instead B sees a neighbour that is not flashing so does not change its clock.
     *
     * To fix this issue each firefly has a unique number that specifies what number firefly it is.
     * If a neighbour has a higher number then the code for it this act cycle has not been run.
     * This means that in order to get the fireflys as if they're running concurrently 1 must be added to the value of the neighbours clock.
     * If a neighbour has a lower number then the code for it this act cycle has already been run.
     * This means that in order to get the fireflys as if they're running concurrently 1 must be subtracted to the value of the neighbours clock.      
     * Then it can be determined if that firefly should be flashing at the same time or not.
     *
     * @param neighbourFly A parameter
     * @return The return value
     */
    public boolean checkOrder(FireflySuper neighbourFly) 
    {
        //The neighbour flys clock value, accounting for its location in the cycle
        //set to the neighbour flys clock value so it can be adjusted later
        int neighbourClock = neighbourFly.currentClock;

        //If this fly flashes BEFORE the neighbour fly
        if(robotNumber < neighbourFly.robotNumber) 
        {
            //this accounts for what the value SHOULD BE if everything was executed at the same time
            //So the neighbour fly hasnt had chance to update its clock yet so this fixes it
            neighbourClock += 1;
        }
        //If this fly flashes AFTER the neighbour fly
        else if(robotNumber > neighbourFly.robotNumber) 
        {
            neighbourClock -= 1;
        }

        //This is standard "increase clock if neighbour fly is flashing"
        if(neighbourClock >= maxClockValue) 
        {
            return true;
        }   else 
        {
            return false;
        }
    }

    /**
     * Method crawlAround
     * Approximates the crawling behaviour of insects. 
     */
    public void crawlAround()
    {
        move(4);

        //gives the firefly a 10% chance of turning
        if (Greenfoot.getRandomNumber(100) < 10) 
        {
            //turn a random amount between 45 and -45
            int turnAmount = Greenfoot.getRandomNumber(90) - 45;
            turn(turnAmount);
        }

    }

}
