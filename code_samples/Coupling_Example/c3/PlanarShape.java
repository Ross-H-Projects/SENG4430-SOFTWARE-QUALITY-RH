import java.util.Scanner;
import java.lang.Math;

public abstract class PlanarShape implements Comparable<PlanarShape>
{
	protected int numberOfPoints;

	protected Point[] pointsArray;

	public abstract String toString();

	public abstract double area();

	public abstract double originDistance();

	public int compareTo(PlanarShape o)
	{
		double diff = o.area() - this.area();

		if(Math.abs(diff) <= 0.05)
		{
			if(o.originDistance() < this.originDistance())
			{
				return 0; 	//o or second shape is placed before first shape
			}
			else
				return 1; 	//this or first shape is placed before second shape
		}
		else if(diff > 0.00)
		{
			return 1;		//this or first shape is placed before second shape
		}
		else
			return -1;		//o or second shape is placed before first shape
	}
}