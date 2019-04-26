import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;

/**
 * BoidSuper is a parent to all fireflys. 
 * It contains methods used by Fireflys.  
 * Hides unnessecarily complicated or unintuitive code from the Firefly class.
 * 
 * @author David Saunders 
 * @version 1.0 19.03.2019
 */
public class BoidSuper extends SwarmRobot
{
    
    List<BoidSuper> neighbours;
        
    //returns any objects which have BoidSuper as a superclass
    //hides another level of abstraction from the boid class
    public List<BoidSuper> getBoidNeighbours()
    {
        return getListOfNeighbours(BoidSuper.class);    //called from the swarm robot superclass
    }

    
    /**
     * Method averageOfAngles
     * Used only for boid flocking algorithm.
     * 
     * The average of a set of angles cannot be found by simpily adding together the angles and dividing by the number of angles.
     * A more mathematical approach is needed.
     *
     * @param angleArray An integer array with every item being an angle between 0 - 359 
     * @return An integer stating the average angle of the angles in angleArray
     */
    public int averageOfAngles(int[] angleArray) 
    {       
        //https://stackoverflow.com/questions/491738/how-do-you-calculate-the-average-of-a-set-of-circular-data

        double sumCos = 0;   //sum of cosines
        double sumSin = 0;   //sum of sines       

        for ( int i : angleArray) 
        {  
            double iRad = Math.toRadians(i);
            sumCos = sumCos + Math.cos(iRad);
            sumSin = sumSin + Math.sin(iRad); 
        }

        int aveRotation = (int) Math.toDegrees(Math.atan2(sumSin, sumCos));

        if(aveRotation < 0)
        {
            aveRotation += 360;
        } 

        return aveRotation;   
    }
        
    /**
     * Method seperation
     * Maintains distance between this boid and nearby neighbours.
     *
     * @return The heading needed to move away from other neighbours.
     */
    protected int seperation() 
    {
        //initilise x and y
        //x and y will be the "centre of mass" of the local boids
        int x=0;
        int y=0;
        
        //add the x and y coordinates of the neighbour boids
        for ( BoidSuper b: neighbours) 
        {  
            x = x + b.getX();
            y = y + b.getY();
        }

        //no +1 as neighbours excluding this boid
        int xAverage = x / (neighbours.size());
        int yAverage = y / (neighbours.size());

        int curRot = getRotation();
        //faceCOM
        turnTowards(xAverage, yAverage);
        //face opposite of COM
        turn(180);
        //Save this new angle
        int newRot = getRotation();
        //return boid facing normal direction
        setRotation(curRot);

        return newRot;
    }

    /**
     * Method alignment
     * Maintain firection of flock
     *
     * @return The heading that is the average of the neighbours heading.
     */
    protected int alignment() 
    {
        //put the angle of each neighbour boid in an array.
        //loop through array 

        //create new array with size as big as how many neighbours the boid has
        int[] headingArray = new int[neighbours.size()];

        //for loop to go through everyneighbours heading
        for ( int i=0; i<neighbours.size(); i++ )
        {
            headingArray[i] = neighbours.get(i).getRotation(); 
        }

        //get the average heading of the boids
        int newRot = averageOfAngles(headingArray);

        return newRot;
    }

    /**
     * Method cohesion
     * Maintane cohesion of flock by attracting this boid to the "Centre Of Mass" of neighbours.
     *
     * @return The heading towards the centre of the neighbours.
     */
    protected int cohesion() 
    {
        //initilise x and y
        //x and y will be the "centre of mass" of the local boids
        int x = 0;
        int y = 0 ;

        //add the x and y coordinates of the neighbour boids
        for ( BoidSuper b: neighbours) 
        {  
            x = x + b.getX();
            y = y + b.getY();
        }

        //get the average of the coordinates by dividing the sum of the x,y coordinates by the number of boids
        int xAverage = x / (neighbours.size());
        int yAverage = y / (neighbours.size());

        //save the current heading of this boid
        int currentHeading = getRotation();

        //turn towards the average coordinates of neighbours
        //save the new rotation of the boid
        //set the boids heading back to its original heading
        turnTowards(xAverage, yAverage);        
        int newRot = getRotation();         
        setRotation(currentHeading);

        return newRot;
    }    
    
}
