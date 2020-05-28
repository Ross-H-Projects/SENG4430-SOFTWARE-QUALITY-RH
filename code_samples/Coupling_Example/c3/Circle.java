import static java.lang.Math.abs;

public class Circle extends PlanarShape{

	private double radius, circleArea;
	private Point center;


	public Circle(double xCoord, double yCoord, double rad)
	{
		radius = rad;
		center = new Point(xCoord, yCoord);
	}

	@Override
	public String toString()
	{
		StringBuilder circleForm = new StringBuilder("CIRCLE=[");
		
		circleForm.append(center.toString());

		circleForm.append("]: ");

		circleForm.append(String.format("%5.2f", circleArea));
		return circleForm.toString();
	}

	@Override
	public double area()
	{
		circleArea = (Math.PI)*(radius*radius);

		return circleArea;
	}

	public double originDistance()
	{
		double distFromCenter = ((center.get_x_coord()*center.get_x_coord())+(center.get_y_coord()*center.get_y_coord())*(1/2));
		double distFromPerimeter = abs(distFromCenter-radius);
		return distFromPerimeter;
	}


}