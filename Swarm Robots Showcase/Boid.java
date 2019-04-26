import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;

/**
 * A Boid object, subclass of the SwarmRobot class
 * Based on algorithms described by Reynolds
 * BoidWithArrows shows the direction the rules make the boid turn
 * 
 * Seperation is shown with a green arrow
 * Cohesion is shown with a red arrow
 * Alignment is shown with a blue arrow
 * 
 * @author David Saunders 
 * @version 1.0 19.03.2019
 */
public class Boid extends BoidSuper
{
    //turnAmount is the maximum degrees a boid can turn in an act cycle.
    //a highervalue may cause the boids to flock quicker, but will appear more unnatural. 
    //if the value is too high then the boids will just spin.
    int turnAmount = 1;


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

        //getListOfNeighbours is called from swarm Robot superclass
        //Boid.class is the parameter so this boid knows to only look for boid objects        
        neighbours = getBoidNeighbours();   //method from BoidSuper superclass

        loopThroughEdge();  //method from Swarm Robot superclass

        //Don't look for neighbours if there arn't any!
        if (neighbours.size() != 0)
        {           
            //see if there are any seperation neighbours.
            List<Boid> sepNeighbours;
            sepNeighbours = getNeighbours(40, true, Boid.class);

            int newHeading;

            if (sepNeighbours.size()==0)    //no sep neighbours. means boid isnt close to any others
            {
                //put new heading values into an array and calculate the average of those headings
                int[] rotAngle = { alignment(), cohesion()};
                newHeading = averageOfAngles(rotAngle); //method from Swarm Robot superclass
            }
            else 
            {  // there are sep neighbours, so boid is close to others. Need to call seperation.
                                
                int[] rotAngle = {newSeperation(), alignment(), cohesion()};                
                newHeading = averageOfAngles(rotAngle); //method from Swarm Robot superclass
            }

            //only turn boid turnAmount in the direction of the average of headings 
            if (newHeading - getRotation() > 0)
            {
                turn(turnAmount);
            }
            else{
                turn(-turnAmount);        
            }
        }
    }

    public int newSeperation() 
    {

        List<Boid> newNeighbours;
        newNeighbours = getNeighbours(40, true, Boid.class);

        if(newNeighbours.size() != 0)
        {
            
            //initilise x and y
            //x and y will be the "centre of mass" of the local boids
            int x=0;
            int y=0;

            //add the x and y coordinates of the neighbour boids
            for ( Boid b: newNeighbours) 
            {  
                x = x + b.getX();
                y = y + b.getY();
            }
            
            //no +1 as neighbours excluding this boid
            int xAverage = x / (newNeighbours.size());
            int yAverage = y / (newNeighbours.size());

            int curRot = getRotation();
            //faceCOM
            turnTowards(xAverage, yAverage);
            //face opposite of COM
            turn(180);
            move(1);
            //Save this new angle
            int newRot = getRotation();
            //return boid facing normal direction
            setRotation(curRot);
            
            return newRot;
        }
        
        return 0;
    }
}
