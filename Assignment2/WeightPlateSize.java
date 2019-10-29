package Assignment2;

import java.util.Random;

public class WeightPlateSize {
	public enum WPSize {
		SMALL_3KG , 
		MEDIUM_5KG , 
		LARGE_10KG 
	}
	public WPSize e_wps; 
	public WeightPlateSize(WPSize e_wps) {
		this.e_wps = e_wps;
	}
	public static WeightPlateSize generateWPS() {
		int val = new Random().nextInt(3);
		WeightPlateSize wps = new WeightPlateSize(WPSize.values()[val]);
		return wps;
	}
}
