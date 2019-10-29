package Assignment2;

public class Assignment2 {
	public static void main ( String [] args ) {
		Gym gym = new Gym();
		Thread thread = new Thread( gym);
		thread.start ();
		try {
			thread.join ();
			gym.shutdown();
		} catch ( InterruptedException e) {
			// TODO Auto - generated catch block
			gym.shutdown();
			e.printStackTrace ();
		}
	}
}
