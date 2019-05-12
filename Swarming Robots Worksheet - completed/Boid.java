import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Boid extends BoidSuper
{

    public void act() 
    {
        move(5);
        loopThroughEdge();

        neighbours = getBoidNeighbours(150);

        if (neighbours.size() != 0)
        {
            int[] intArray = {cohesion(), alignment(), separation(60)};
            int newHeading = averageOfAngles(intArray);
            turnSlightly(newHeading);

        }
        
    }    
}
