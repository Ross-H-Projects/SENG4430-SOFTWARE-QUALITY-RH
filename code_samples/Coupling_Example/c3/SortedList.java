import java.util.Iterator;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;


public class SortedList<T extends PlanarShape> extends LinkedList<T>{

	public SortedList(){
		super();
	}

	public void insertInOrder(T data)
	{
		if(sentinel.get_next().equals(sentinel))//initialise sorted list
		{
			CLLNode<T> newNode = new CLLNode<T>(data, sentinel, sentinel);
			sentinel.set_next(newNode);
			sentinel.set_prev(newNode);
		}
		else 
		{
			CLLNode<T> currentNode = sentinel.get_next(); 

			while(currentNode != sentinel)
			{
				if(data.compareTo(currentNode.get_data()) <=0) 	//current node shape is smaller area/dist
				{												//so next node
					currentNode = currentNode.get_next();
				}
				else
				{
					CLLNode<T> newNode = new CLLNode<T>(data, currentNode, currentNode.get_prev());
					newNode.get_prev().set_next(newNode);
					newNode.get_next().set_prev(newNode);
					break;
				}
			}

			if (currentNode == sentinel)
			{
				CLLNode<T> newNode = new CLLNode<T>(data, sentinel, sentinel.get_prev()); //regular appending of node
				newNode.get_prev().set_next(newNode);
				sentinel.set_prev(newNode);
			}
		}
	}
}
