package os;

public class Task {

	private long id;
	private Runnable function;
	private long startTime;
	private long endTime;

	public Task(Runnable function)	{
		this.function = function;
		this.id = (long)(Math.random() * Long.MAX_VALUE);
	}

	public Runnable get()	{
		return new Runnable() {
			@Override
			public void run() {
				started();

				function.run();

				ended();
			}
		};
	}

	protected void started()	{
		startTime = System.currentTimeMillis();
	}

	protected void ended()	{
		endTime = System.currentTimeMillis();
	}

	public long deltaTime()	{
		return endTime - startTime;
	}
}
