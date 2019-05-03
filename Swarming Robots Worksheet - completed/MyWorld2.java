import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class MyWorld2 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MyWorld2 extends World
{

    /**
     * Constructor for objects of class MyWorld2.
     * 
     */
    public MyWorld2()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(1200, 800, 1); 
        
        for(int i=0; i<10; i++)
        {
         Boid bird = new Boid();
         addObject(bird, Greenfoot.getRandomNumber(1200), Greenfoot.getRandomNumber(800));
         bird.setRotation(Greenfoot.getRandomNumber(360));         
        }
    }
}
