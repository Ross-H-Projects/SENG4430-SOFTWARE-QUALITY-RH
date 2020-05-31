import static java.lang.Math.abs;

public class Semicircle extends PlanarShape{

	private double radius, semicircleArea;
	private Point center, perpVec;

	public Semicircle(double x0_Coord, double y0_Coord, double x1_Coord, double y1_Coord)
	{
		center = new Point(x0_Coord, y0_Coord);
		perpVec = new Point(x1_Coord, y1_Coord);
	}

	public String toString()
	{
		StringBuilder semicircleForm = new StringBuilder("SEMI=[");
		
		semicircleForm.append(center.toString());

		semicircleForm.append(perpVec.toString());

		semicircleForm.append("]: ");

		semicircleForm.append(String.format("%5.2f", semicircleArea));
		return semicircleForm.toString();
	}

	public double area()
	{
		radius = ((center.get_x_coord()-perpVec.get_x_coord())*(center.get_x_coord()-perpVec.get_x_coord()) +
			(center.get_y_coord()-perpVec.get_y_coord())*(center.get_y_coord()-perpVec.get_y_coord()));
		semicircleArea = (Math.PI*radius)/2;
		return semicircleArea;
	}


	
	public double originDistance()
	{
		double distFromCenter = (((center.get_x_coord()*center.get_x_coord())+(center.get_y_coord()*center.get_y_coord()))*(1/2));
		// adjustment of circle position to origin
		double adj_x = perpVec.get_x_coord() - center.get_x_coord();
		double adj_y = perpVec.get_y_coord() - center.get_y_coord();
		//calculating extremity coordinates and returning them back to original circle position
		double clockwiseExt_x = adj_y + center.get_x_coord();
		double clockwiseExt_y = (-adj_x) + center.get_y_coord();
		double counterclockwiseExt_x = (-adj_y) + center.get_x_coord();
		double counterclockwiseExt_y = adj_x + center.get_y_coord();
		//calculating distance from centre 
		double distFromClockwiseExtremity = (((clockwiseExt_x*clockwiseExt_x)+(clockwiseExt_y*clockwiseExt_y))*(1/2));
		double distFromCounterclockwiseExtremity = (((counterclockwiseExt_x*counterclockwiseExt_x)+(counterclockwiseExt_y*counterclockwiseExt_y))*(1/2));


		if(distFromCenter<distFromClockwiseExtremity && distFromCenter<distFromCounterclockwiseExtremity)
		{
			return distFromCenter;
		}
		else if(distFromClockwiseExtremity<distFromCenter && distFromClockwiseExtremity<distFromCounterclockwiseExtremity)
			return distFromClockwiseExtremity;
		else
			return distFromCounterclockwiseExtremity;
	}
}
	