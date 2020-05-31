public class Point
{
	private double x_coord, y_coord;
	private int iD;
	public static double dist;

	public Point()
	{
		x_coord = 0;
		y_coord = 0;
		dist = 0;
	}
	public Point(double new_x_coord, double new_y_coord)
	{
		x_coord = new_x_coord;
		y_coord = new_y_coord;
	}

	public double get_x_coord()
	{
		return x_coord;
	}

	public double get_y_coord()
	{
		return y_coord;
	}

	public void set_x_coord(double new_x_coord)
	{
		x_coord = new_x_coord;
	}

	public void set_y_coord(double new_y_coord)
	{
		y_coord = new_y_coord;
	}

	public double calcDistOrigin()
	{
		double hyp;
		hyp = (x_coord*x_coord) + (y_coord*y_coord);
		dist = hyp*(0.5);
		return dist;
	}


	public String toString()
	{
		String pointForm = "(" + String.format("%4.2f", x_coord) + "," + String.format("%4.2f", y_coord) + ")"; 
		return pointForm;
	}


}