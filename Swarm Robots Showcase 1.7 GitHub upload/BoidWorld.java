import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A world that populates the screen with Boids with random location and initial direction.
 * Choose between populating with Boid's or BoidExplained's
 * 
 * @author David Saunders 
 * @version 1.0 19.03.2019
 */
public class BoidWorld extends World
{
    int numOfBoids = 15;

    public BoidWorld()
    {    
        super(1200, 800, 1); 
        
        for(int i = 0; i < numOfBoids; i++) {    
            int x = Greenfoot.getRandomNumber(1200);
            int y = Greenfoot.getRandomNumber(800);
            int rotation = Greenfoot.getRandomNumber(359);
            
            //Change the object type to either Boid or BoidExplained 

            BoidExplained boid = new BoidExplained();
            
            addObject(boid, x, y);
            boid.setRotation(rotation);
        }
    }
}
