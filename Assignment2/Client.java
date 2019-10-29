package Assignment2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Client {
	private int id;
	private List<Exercise> routine;
	private int index;
	public Client(int id) {
		this.id = id;
		this.routine = new ArrayList<Exercise>();
		this.index = 0;
	}
	
	public void addExercise(Exercise e) {
		routine.add(e);
	}
	
	public static Client generateRandom(int id) {
		Client c = new Client(id);
		int exNum = new Random().nextInt(5) + 15;
		for(int i = 0; i < exNum; i++) {
			c.addExercise(Exercise.generateRandom());
		}
		return c;
	}
	
	public int remainExNum() {
		return routine.size() - index;
	}
	
	public Exercise getCurExercise() {
		return routine.get(index);
	}
	
	public int getID() {
		return id;
	}
	
	public void doExercise() throws InterruptedException {
		if(index < routine.size()) {
			System.out.println("\n\nClient " + id + " start exercise :");
			System.out.println("ApparatusType: " + routine.get(index).getAT());
			Map<WeightPlateSize.WPSize, Integer> wpsMap = routine.get(index).getWPSMap();
			for(Map.Entry<WeightPlateSize.WPSize, Integer> entry : wpsMap.entrySet()) {
				System.out.print(entry.getKey() + ": " + entry.getValue() + "	");
			}
			System.out.print("\n");
			Thread.sleep(routine.get(index).getDuration());
			System.out.println("Client " + id + " finish exercise ");
			index++;
			if(index == routine.size()) {
				System.out.println("Client " + id + " finish all exercises ");
			}
		}
	}
}
