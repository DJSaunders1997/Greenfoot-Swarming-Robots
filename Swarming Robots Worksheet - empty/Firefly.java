import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Firefly extends FireflySuper
{
    static int numberOfFireflys = 0;

    public Firefly()
    {
        maxClockValue = 100;   
        //sets each fireflies clock to a random 
        currentClock = Greenfoot.getRandomNumber(maxClockValue) + 1;    

        //Used to keep track of what number each firefly is to fix concurrency issues.
        robotNumber = numberOfFireflys;
        numberOfFireflys++;                          
    }

    public void act()
    {  
        //Fill in with logic from worksheet
    }

}

