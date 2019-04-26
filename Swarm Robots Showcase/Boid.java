import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;

/**
 * A Boid object, subclass of the BoidSuper class
 * Based on algorithms described by Reynolds
 * BoidExplained shows the direction the rules make the boid turn
 * 
 * @author David Saunders 
 * @version 1.0 25.04.2019
 */
public class Boid extends BoidSuper
{
    /**
     * Method act
     * Is mainly used to call the 3 rules of seperation, alignment, and cohesion.
     * Firstly a boid must move, then consider the rules for flocking. 
     * Each rule is a fuction that returns a new heading, this is multiplied by the weight for that rule.
     * The result of every rule times the weight is then put into an array, from which the average heading can be calculated.
     * From the average heading determine which direction the boid should turn, then turn it turnAmount in that direction.
     */
    public void act() 
    {
        move(5);
        loopThroughEdge();  //method from Swarm Robot superclass
        
        neighbours = getBoidNeighbours(100);    //method from Swarm Robot superclass

        //Only call rules if there are neighbours
        if (neighbours.size() != 0)
        {          
            int newHeading;
            int[] rotAngle = {seperation(40), alignment(), cohesion()};                
            newHeading = averageOfAngles(rotAngle); //method from Swarm Robot superclass

            //only turn boid turnAmount in the direction of the average of headings 
            turnSlightly(newHeading);
        }
    }

}
