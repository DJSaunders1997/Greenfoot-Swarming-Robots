import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;

/**
 * BoidSuper is a parent to all fireflys. 
 * It contains methods used by Fireflys.  
 * Hides unnessecarily complicated or unintuitive code from the Firefly class.
 * 
 * @author David Saunders 
 * @version 1.0 25.04.2019
 */
public class BoidSuper extends SwarmRobot
{    
    List<BoidSuper> neighbours;
    
    /**
     * Method getBoidNeighbours
     * Gets a list of neighbour boids.
     * Hides another level of abstraction from the boid class.
     *
     * @param visionDistance A parameter
     * @return A list of objects which have BoidSuper as a superclass
     */
    public List<BoidSuper> getBoidNeighbours(int visionDistance)
    {
        return getListOfNeighbours(BoidSuper.class, visionDistance);    //called from the swarm robot superclass
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
     * @param seperationDistance the smaller radius a boid will look for seperations neighbours. 
     * @return The heading needed to move away from other neighbours.
     */
    public int seperation(int seperationDistance) 
    {
        List<BoidSuper> sepNeighbours;
        sepNeighbours = getBoidNeighbours(seperationDistance);

        if(sepNeighbours.size() != 0)
        {            
            //initilise x and y
            //x and y will be the "centre of mass" (COM) of the local boids
            int x=0;
            int y=0;

            //add the x and y coordinates of the neighbour boids
            for ( BoidSuper b: sepNeighbours) 
            {  
                x = x + b.getX();
                y = y + b.getY();
            }
            
            //calculate COM
            int xAverage = x / (sepNeighbours.size());
            int yAverage = y / (sepNeighbours.size());

            int curRot = getRotation();            
            turnTowards(xAverage, yAverage);    //faceCOM             
            turn(180);  //face opposite of COM
            move(1);            
            int newRot = getRotation(); //Save this new angle            
            setRotation(curRot);    //return boid facing normal direction
            
            return newRot;
        } 
        else {
           return getRotation(); 
        }
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
    
    /**
     * Method turnSlightly
     * Turns a boid towards a new direction.
     * Will turn the shortest way towards the new angle.
     *
     * @param newAngle The angle the boid shall turn towards.
     * @param turnAmount A parameter how much a boid shall turn
     */
    public void turnSlightly(int newAngle) 
    {       
        int currentAngle = getRotation();

        if (currentAngle != newAngle)
        {
            /*
            In Java % is remainder not modulo. 
            Originally used Math.floorMod then IntMath.mod for modulo but it is not supported on the Greenfoot website            
            The formula
                (a % b + b) % b
            will perform modulo correctly with negitive numbers
            https://stackoverflow.com/questions/4412179/best-way-to-make-javas-modulus-behave-like-it-should-with-negative-numbers/25830153#25830153
            */
            
            int clockwiseDifference = (newAngle-currentAngle % 360 + 360) % 360;        //does newAngle-currentAngle modulo 360
            int counterclockwiseDifference = (currentAngle-newAngle % 360 + 360) % 360; //performs currentAngle - newAngle modulo 360
            
            int min = Math.min(clockwiseDifference, counterclockwiseDifference);

            if (min == clockwiseDifference)    //turning clockwise is the shortest angular distance
            {
                turn(1);
            }
            else                //turning counter-clockwise is the shortest angular distance
            {
                turn(-1);
            }        
        }
    }
    
}
