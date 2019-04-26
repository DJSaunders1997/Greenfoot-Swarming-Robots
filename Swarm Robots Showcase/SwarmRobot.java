import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;

/**
 * SwarmRobot is a parent to all swarm robots. 
 * It contains methods used by both Boids and Fireflys.  
 * 
 * @author David Saunders 
 * @version 1.0 19.03.2019
 */
public class SwarmRobot extends Actor
{
    int visionDistance = 100;   //the distance around the robot that it can see other robots 
    //100 is ideal for Boids
    //300+ is ideal for Fireflys
    
    /**
     * Method getListOfNeighbours
     * Used by fireflys and boids.
     * Looks for actors of class c
     * Creates a list of objects with class c, in radious visionDistance. These are the neighbours.
     *
     * @param c What class of actors should be counted as neighbours.
     * @return A list of neighbours within distance visionDistance. 
     */
    public <A> java.util.List <A> getListOfNeighbours (Class c)//java.lang.Class<A> cls)
    {        
        List<A> neighbours;
        neighbours = getNeighbours(visionDistance, true, c);

        return neighbours;
    }

    /**
     * Method bounceOffEdge
     * Can be used by fireflys and boids.
     * Will make actors reverse direction when a collision with a wall occurs.
     */
    public void bounceOffEdge()
    {
        int margin = 2;

        if (getX() <= margin || getX() >= getWorld().getWidth() - margin)   //left or right side
        {
            turn(180);
        }
        if (getY() <= margin || getY() >= getWorld().getHeight() - margin)  //top or bottom side
        {
            turn(180);
        }
    }

    /**
     * Method loopThroughEdge
     * Can be used by fireflys and boids.
     * Will make actors appear on the opposite side of the world when a collision with a wall occurs.     *
     */
    public void loopThroughEdge()
    {
        int margin = 2;

        if (getX() < margin) //left side
        {  
            setLocation(getWorld().getWidth()+ margin, getY());
        }
        else if (getX() > getWorld().getWidth() - margin)   //right side
        {
            setLocation(margin, getY());
        }

        if (getY() < margin)    //top side
        {  
            setLocation(getX(), getWorld().getHeight() - margin);
        }
        else if(getY() > getWorld().getHeight() - margin)   //bottom side
        {  
            setLocation(getX(), margin);
        }
    }
}
