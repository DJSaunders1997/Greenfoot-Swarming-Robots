import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;

/**
 * A Boid object, subclass of the SwarmRobot class
 * Based on algorithms described by Reynolds
 * BoidWithArrows shows the direction the rules make the boid turn
 * 
 * separation is shown with a green arrow
 * Cohesion is shown with a red arrow
 * Alignment is shown with a blue arrow
 * 
 * @author David Saunders 
 * @version 1.0 25.04.2019
 */
public class BoidExplained extends BoidSuper
{
    Arrow separationArrow = new Arrow();
    Arrow cohesionArrow = new Arrow();
    Arrow alignmentArrow = new Arrow();

    /**
     * Boid Constructor
     * No parameters needed to be passed with the constructor.  
     */
    public BoidExplained()
    {        
        separationArrow.setImage("greenArrow.png");
        cohesionArrow.setImage("redArrow.png");
        alignmentArrow.setImage("blueArrow.png");

    } 

    public void addedToWorld(World world)
    {
        //add arrows on top of boid only once boid has been added to the world
        getWorld().addObject(separationArrow, getX(), getY());
        getWorld().addObject(cohesionArrow, getX(), getY());
        getWorld().addObject(alignmentArrow, getX(), getY());

        //sets direction of arrows to be same as boids only when initilising world
        int boidDirection = getRotation();  //variable because its more efficient than function calls

        separationArrow.setRotation(boidDirection);
        cohesionArrow.setRotation(boidDirection);
        alignmentArrow.setRotation(boidDirection);

    }

    /**
     * Method act
     * Is mainly used to call the 3 rules of separation, alignment, and cohesion.
     * Firstly a boid must move, then consider the rules for flocking. 
     * Each rule is a fuction that returns a new heading, this is multiplied by the weight for that rule.
     * The result of every rule times the weight is then put into an array, from which the average heading can be calculated.
     * From the average heading determine which direction the boid should turn, then turn it turnAmount in that direction.
     */
    public void act() 
    {
        move(5);
        loopThroughEdge();  //method from Swarm Robot superclass
        
        //update arrow locations
        separationArrow.setLocation(getX(),getY());
        alignmentArrow.setLocation(getX(),getY());
        cohesionArrow.setLocation(getX(),getY());
        
        neighbours = getBoidNeighbours(150);
        
        //Don't look for neighbours if there arn't any!
        if (neighbours.size() == 0)
        {         
            //only show arrows when there are neighbours
            separationArrow.getImage().setTransparency(0);//make invisable
            alignmentArrow.getImage().setTransparency(0);//make invisable
            cohesionArrow.getImage().setTransparency(0);//make invisable
        }
        //if the boid has neighbours
        else 
        {           
            //make arrows visable when they're effecting the boid            
            alignmentArrow.getImage().setTransparency(255);     //make visable
            cohesionArrow.getImage().setTransparency(255);      //make visable
            separationArrow.getImage().setTransparency(255);    //make visable
            
            separationArrow.setRotation(separation(40));
            cohesionArrow.setRotation(cohesion());
            alignmentArrow.setRotation(alignment());

            //see if there are any separation neighbours.
            List<BoidExplained> sepNeighbours;
            sepNeighbours = getNeighbours(40, true, BoidExplained.class);

            if (sepNeighbours.size()==0)    //no sep neighbours. means boid isnt close to any others
            {
                separationArrow.getImage().setTransparency(0);  //make invisable
            }
                              
            int newHeading;
            int[] rotAngle = {separation(40), alignment(), cohesion()};                
            newHeading = averageOfAngles(rotAngle); //method from Swarm Robot superclass
            
            //only turn boid turnAmount in the direction of the average of headings 
            turnSlightly(newHeading);            
        }
    }    

}
