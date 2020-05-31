import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.IOException;
import java.lang.String;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;

public class PA2b{

	public static void main(String[] args) {

		Scanner inputScanner;
		LinkedList<PlanarShape> myLinkedList;
		LinkedList<PlanarShape> orderedLinkedList;

		String inputFile = args[0];
		File testData = new File(inputFile);

		myLinkedList = new LinkedList<PlanarShape>();
		orderedLinkedList = new LinkedList<PlanarShape>();

		

		try {
			inputScanner = new Scanner(testData);
		} catch (Exception e){
			System.out.println("FILE NOT FOUND");
            System.out.println(e.toString());
            return;
		}


		while(inputScanner.hasNextLine())
		{	
			String inputLine = inputScanner.nextLine(); 		//Splits input data into lines

			Scanner inputLineScanner = new Scanner(inputLine);	//Scans lines

			inputLineScanner.useDelimiter(" ");					

			while(inputLineScanner.hasNext())
			{
				String firstCharacter = inputLineScanner.next();

				PlanarShape input_shape = shapeFactory(firstCharacter, inputLineScanner);

				myLinkedList.append(input_shape);
				orderedLinkedList.insertInOrder(input_shape);

			}

		}

		System.out.print("Unordered List: \n");
		Iterator<PlanarShape> linkedList = myLinkedList.iterator();

		while(linkedList.hasNext()) {
                    System.out.println("\t" + linkedList.next());
                }
        System.out.print("Ordered List: \n");
        Iterator<PlanarShape> ordLinkedList = orderedLinkedList.iterator();

        while(ordLinkedList.hasNext()) {
                    System.out.println("\t" + ordLinkedList.next());
                }


		
	} //end of initialisation while loop
	
	
	public static PlanarShape shapeFactory(String firstChar, Scanner inputLineScanner_)
	{
		PlanarShape shape = null;
		int i=0;
		//Read the identification character;
		Scanner inputLineScanner = inputLineScanner_;
		char id_char = firstChar.charAt(0);
		


		switch (id_char)
		{

			case 'P':
				//read in polygon data;
					String singleIntegers = inputLineScanner.next();

					int amountOfPoints = Integer.parseInt(singleIntegers);


					amountOfPoints = amountOfPoints + 1;
					

					Point[] initPoints = new Point[amountOfPoints];

					while (inputLineScanner.hasNext())
					{
						String x_coords = inputLineScanner.next();
						String y_coords = inputLineScanner.next();

						initPoints[i] = new Point(Double.parseDouble(x_coords),Double.parseDouble(y_coords));


						i++;
					}

					initPoints[i] = initPoints[0];

					shape = new Polygon(amountOfPoints, initPoints);
					shape.area();
					i=0;
					return shape;
			case 'C':
				//read in circle data;
					String x_coord = inputLineScanner.next();
					String y_coord = inputLineScanner.next();
					String radius = inputLineScanner.next();

					shape = new Circle(Double.parseDouble(x_coord),Double.parseDouble(y_coord),Double.parseDouble(radius));
					shape.area();
					return shape;
			case 'S':
				//read in semicircle data;
				String x0_coord = inputLineScanner.next();
				String y0_coord = inputLineScanner.next();
				String x1_coord = inputLineScanner.next();
				String y1_coord = inputLineScanner.next();

				shape = new Semicircle(Double.parseDouble(x0_coord),Double.parseDouble(y0_coord),Double.parseDouble(x1_coord),Double.parseDouble(y1_coord));
				shape.area();
				return shape;
			default:
				return null;
		}
		//return shape;
	}



}
