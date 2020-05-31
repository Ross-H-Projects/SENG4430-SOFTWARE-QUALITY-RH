import java.util.Iterator;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;


public class LinkedList<T extends PlanarShape> implements Iterable<T>{

	private CLLNode<T> sentinel; 
	private int list_length; // 
	private int modCount; // counts the amount of modifications done to the list

		/*Creates an empty SLList. */
		public LinkedList()
		{
			CLLNode<T> newNode = new CLLNode<T>();
			sentinel = newNode;
			sentinel.set_next(sentinel);
			sentinel.set_prev(sentinel);

			modCount = 0;
			list_length = 0;
		}

		public void append(T data)
		{
			if(sentinel.get_next().equals(sentinel))
			{
				CLLNode<T> newNode = new CLLNode<T>(data, sentinel, sentinel);
				sentinel.set_next(newNode);
				sentinel.set_prev(newNode);
			}
			else
			{
				CLLNode<T> newNode = new CLLNode<T>(data, sentinel, sentinel.get_prev());
				newNode.get_prev().set_next(newNode);
				sentinel.set_prev(newNode);
			}

			modCount++;
			list_length++;
		}

		public void preppend(T data)
		{
			if(sentinel.get_next().equals(sentinel))
			{
				CLLNode<T> newNode = new CLLNode<T>(data, sentinel, sentinel);
				sentinel.set_next(newNode);
				sentinel.set_prev(newNode);
			}
			else
			{
				CLLNode<T> newNode = new CLLNode<T>(data, sentinel, sentinel.get_next());
				newNode.get_next().set_prev(newNode);
				sentinel.set_next(newNode);
			}

			modCount++;
			list_length++;
		}

		private CLLNode<T> get_node(int idx)
		{

            CLLNode<T> tObj = this.sentinel;
            for (int i = 0; i < idx; i++)
            {
                tObj = tObj.get_next();
                if (tObj == this.sentinel)
                    return tObj;
            }
            return tObj;

		}
		/*
		public void removeHead()
		{
			CLLNode<T> 
			
			current.get_prev().set_next(current.get_next());
			current.get_next().set_prev(current.get_prev());
			current = current.get_next();
			list_length--;
			modCount++;
		}
		*/

		public int size()
		{
			return list_length;
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

		//Iterator 
		@Override
		public Iterator<T> iterator()
		{
			return new LinkedListIterator(sentinel);
		}

		private class LinkedListIterator implements Iterator<T>
		{
			private CLLNode<T> iteratorNode;
			private int curPos, expectedModCount;

			private LinkedListIterator(CLLNode<T> tempNode)
			{
				curPos = 0;
				iteratorNode = tempNode;
				expectedModCount = modCount;
			}
			//Overriding hasNext Iterator function due to sentinel node restrictionx
			@Override
			public boolean hasNext()
			{
				if(iteratorNode.get_next().equals(sentinel))
					return false;
				else
					return true;
			}

			public T next()
			{
				if (modCount != expectedModCount)
         			throw new ConcurrentModificationException
            			("Cannot mutate in context of iterator");
            	if (!hasNext())
         			throw new NoSuchElementException
                   		("There are no more elements");
                this.curPos += 1;
      			iteratorNode = LinkedList.this.get_node(this.curPos);
      			return iteratorNode.get_data();
			}

			public void remove()
			{
				throw new UnsupportedOperationException
            		("remove not supported by LinkedList");
			}


		}

		
		
	

}