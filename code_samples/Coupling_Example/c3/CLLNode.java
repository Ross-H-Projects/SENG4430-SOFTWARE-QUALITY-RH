
public class CLLNode<T>
{

	private T data;
	private CLLNode<T> next, prev;

	public CLLNode()
	{
		data = null;
		next = null;
		prev = null;
	}

	public CLLNode(T init_data, CLLNode init_next, CLLNode init_prev)
	{
		data = init_data;
		next = init_next;
		prev = init_prev;
	}

	public CLLNode<T> get_next()
	{
		return next;
	}

	public CLLNode<T> get_prev()
	{
		return prev;
	}

	public T get_data()
	{
		return data;
	}

	public void set_next(CLLNode<T> new_next)
	{
		next = new_next;
	}

	public void set_prev(CLLNode<T> new_prev)
	{
		prev = new_prev;
	}

	public void set_data(T new_data)
	{
		data = new_data;
	}
}