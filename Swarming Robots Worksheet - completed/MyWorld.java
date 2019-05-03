import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class MyWorld extends World
{

    public MyWorld()
    {    
        super(1200, 800, 1); 
        
        for(int i=0; i<10; i++)
        {
         Firefly fly = new Firefly();
         addObject(fly, Greenfoot.getRandomNumber(1200), Greenfoot.getRandomNumber(800));
         fly.setRotation(Greenfoot.getRandomNumber(360));         
        }
        
    }
}
