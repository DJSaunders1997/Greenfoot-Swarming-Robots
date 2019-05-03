import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;

public class Boid extends BoidSuper
{

    public void act() 
    {
        move(5);
        loopThroughEdge();

        neighbours = getBoidNeighbours(150);

        if (neighbours.size() != 0)
        {
            int[] intArray = {cohesion(), alignment(), seperation(60)};
            int newHeading = averageOfAngles(intArray);
            turnSlightly(newHeading);

        }
        
    }    
}
