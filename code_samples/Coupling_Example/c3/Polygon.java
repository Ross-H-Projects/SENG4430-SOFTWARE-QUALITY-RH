/*This is the Node class where an array of Points will be stored so that each Polygon node of the linked list has an array of points



*/
import static java.lang.Math.abs;
public class Polygon extends PlanarShape{

	private Point[] polygonPoint;
	private int numberOfPoints;
	private double x1, x2, y1, y2, polygonArea;
	


	public Polygon()
	{
		numberOfPoints = 0;
		polygonPoint = new Point[0];

	}

	public Polygon(int init_numberOfPoints, Point[] init_points)
	{
		numberOfPoints = init_numberOfPoints;
		polygonPoint = new Point[numberOfPoints];
		
		for (int i = 0; i < numberOfPoints; i++)
		{
			polygonPoint[i] = init_points[i];
		}

	}

	@Override
	public double area()
	{
		polygonArea = 0;
		for (int j = 0; j < numberOfPoints-1; j++)
		{
			x1 = polygonPoint[j].get_x_coord();
			y1 = polygonPoint[j].get_y_coord();
			x2 = polygonPoint[j+1].get_x_coord();
			y2 = polygonPoint[j+1].get_y_coord();
			polygonArea = polygonArea + ((x2 + x1)*(y2 - y1));
		
		}
		polygonArea = abs(polygonArea*0.5);
		return polygonArea;
	}

	@Override
	public double originDistance()
	{
		double closestPoint = 0.00;

		for(int i=0; i < numberOfPoints; i++)
			{
				if(closestPoint>polygonPoint[i].calcDistOrigin())
				{
					closestPoint = polygonPoint[i].calcDistOrigin();
				}

			}
		return closestPoint;
	}

	@Override
	public String toString()
	{
		StringBuilder polygonForm = new StringBuilder("POLY=[");

		for (int i=0; i < numberOfPoints-1; i++)
		{
			polygonForm.append(polygonPoint[i].toString());
		}
		polygonForm.append("]: ");

		polygonForm.append(String.format("%5.2f", polygonArea));
		return polygonForm.toString();
	}


	

}