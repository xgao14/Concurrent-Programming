package Assignment2;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Exercise {
	private ApparatusType.AType at;
	private Map<WeightPlateSize.WPSize, Integer> weight;
	private int duration;
	public Exercise(ApparatusType.AType at, Map<WeightPlateSize.WPSize, Integer> weight, int duration) {
		this.at = at;
		this.weight = weight;
		this.duration = duration;
	}
	public static Exercise generateRandom() {
		Map<WeightPlateSize.WPSize, Integer> ex_m = new HashMap<WeightPlateSize.WPSize, Integer>();
		int totleWeight = 0;	
		while(totleWeight == 0) {
			int tw = 0;
			for(WeightPlateSize.WPSize w : WeightPlateSize.WPSize.values()) {
				tw = new Random().nextInt(10);
				ex_m.put(w, tw);
				totleWeight += tw;
			}
		}
		
		Exercise e = new Exercise(ApparatusType.generateAT().e_at, ex_m, new Random().nextInt(1000));
		return e;
	}
	public static Exercise generateRandom(Map<WeightPlateSize.WPSize, Integer> weight) {
		Map<WeightPlateSize.WPSize, Integer> ex_m = new HashMap<WeightPlateSize.WPSize, Integer>();
		
		int totleWeight = 0;
		
		while(totleWeight != 0) {
			int tw = 0;
			for(Map.Entry<WeightPlateSize.WPSize, Integer> entry : weight.entrySet()) {
				tw = new Random().nextInt(10);
				ex_m.put(entry.getKey(), tw);
				totleWeight += tw;
			}
		}
		
		Exercise e = new Exercise(ApparatusType.generateAT().e_at, ex_m, new Random().nextInt(1000));
		return e;
	}
	public ApparatusType.AType getAT() {
		return at;
	}
	public int getDuration() {
		return duration;
	}
	public Map<WeightPlateSize.WPSize, Integer> getWPSMap() {
		return weight;
	}
}
