import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Firefly extends FireflySuper
{
    public Firefly()
    {                 
         maxClockValue = 12;
         currentClock = Greenfoot.getRandomNumber(12)+1;
    }

    public void act()
    {  
        crawlAround();
        bounceOffEdge();
        
        currentClock++;
        
        if(currentClock > maxClockValue)
        {
            currentClock = 1;
            setImage("fireflyFlash.png"); 
            Greenfoot.playSound("ping.mp3");
        }
        else
        {
         setImage("firefly.png");  
         
         if(neighbourFlyFlashing(1000))
         {
             currentClock++;
         }
         
        }
        
    }

}

