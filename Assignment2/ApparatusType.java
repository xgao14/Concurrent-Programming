package Assignment2;

import java.util.Random;

public class ApparatusType {
	
	public enum AType {
		LEGPRESSMACHINE , 
		BARBELL , 
		HACKSQUATMACHINE , 
		LEGEXTENSIONMACHINE ,
		LEGCURLMACHINE , 
		LATPULLDOWNMACHINE , 
		PECDECKMACHINE ,
		CABLECROSSOVERMACHINE
		};
	public AType e_at;
	public ApparatusType(AType e_at) {
		this.e_at = e_at;
	}
	public static ApparatusType generateAT() {
		int val = new Random().nextInt(8);
		ApparatusType at = new ApparatusType(AType.values()[val]);
		return at;
	}
}
