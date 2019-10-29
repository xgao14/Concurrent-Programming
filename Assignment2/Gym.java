package Assignment2;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class Gym implements Runnable {
	
	private static final int GYM_SIZE = 30;
	private static final int GYM_REGISTERED_CLIENTS = 10000;
	private Map<WeightPlateSize.WPSize, Integer> noOfWeightPlates;
	private Set<Integer> clients; // for generating fresh client ids
	private ExecutorService executor;
	// various semaphores - declaration omitted
	//private Semaphore wpMutex[] = {new Semaphore(1), new Semaphore(1), new Semaphore(1)};
	private Semaphore wpNMutex = new Semaphore(1);

	private Semaphore atMutex[] = {new Semaphore(5), new Semaphore(5), 
			new Semaphore(5), new Semaphore(5), new Semaphore(5), 
			new Semaphore(5), new Semaphore(5), new Semaphore(5)};
	
	public Gym() {
		executor = Executors.newFixedThreadPool (GYM_SIZE);
				
		noOfWeightPlates = new HashMap<WeightPlateSize.WPSize, Integer>();
		noOfWeightPlates.put(WeightPlateSize.WPSize.SMALL_3KG, 110);
		noOfWeightPlates.put(WeightPlateSize.WPSize.MEDIUM_5KG,  90);
		noOfWeightPlates.put(WeightPlateSize.WPSize.LARGE_10KG, 75);
		
		clients = new LinkedHashSet<Integer>();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		for(int numCli = 0; numCli < GYM_REGISTERED_CLIENTS; numCli++) {
			int newClientID = -1;
			do {
				newClientID = new Random().nextInt(GYM_REGISTERED_CLIENTS);
			}while(clients.contains(newClientID));
			
			Client cli = Client.generateRandom(newClientID);
			
			//Add client into set
			clients.add(newClientID);
			executor.execute( new Runnable () {
				public void run () {
					//Start doing exercise
					boolean getAllequipments = false;
					while(cli.remainExNum() > 0) {
						Exercise ex = cli.getCurExercise();
						Map<WeightPlateSize.WPSize, Integer> m = ex.getWPSMap();
						try {
							while(!getAllequipments) {
									atMutex[ex.getAT().ordinal()].acquire();
									wpNMutex.acquire();
									for(Map.Entry<WeightPlateSize.WPSize, Integer> entry : m.entrySet()) {
										if(noOfWeightPlates.get(entry.getKey()) - entry.getValue() < 0 ) {
											getAllequipments = false;
											wpNMutex.release();
											atMutex[ex.getAT().ordinal()].release();
											break;
										}
										else {
											getAllequipments = true;
										}
									}
									if(getAllequipments) {
										for(Map.Entry<WeightPlateSize.WPSize, Integer> entry : m.entrySet()) {
												noOfWeightPlates.replace(entry.getKey(), 
														noOfWeightPlates.get(entry.getKey()) - entry.getValue());
										}
										wpNMutex.release();
									}
							}
							cli.doExercise();
							getAllequipments = false;
							for(Map.Entry<WeightPlateSize.WPSize, Integer> entry : m.entrySet()) {
								wpNMutex.acquire();
								noOfWeightPlates.replace(entry.getKey(), 
											noOfWeightPlates.get(entry.getKey()) + entry.getValue());	
								wpNMutex.release();
							}
							
							atMutex[ex.getAT().ordinal()].release();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						/*try {
							atMutex[ex.getAT().ordinal()].acquire();
							for(Map.Entry<WeightPlateSize.WPSize, Integer> entry : m.entrySet()) {
								if(noOfWeightPlates.get(entry.getKey()) - entry.getValue() >= 0 ) {
									wpMutex[entry.getKey().ordinal()].acquire();
									noOfWeightPlates.replace(entry.getKey(), 
											noOfWeightPlates.get(entry.getKey()) - entry.getValue());
									wpMutex[entry.getKey().ordinal()].release();
								}
							}
							cli.doExercise();
							for(Map.Entry<WeightPlateSize.WPSize, Integer> entry : m.entrySet()) {
								wpMutex[entry.getKey().ordinal()].acquire();
									noOfWeightPlates.replace(entry.getKey(), 
											noOfWeightPlates.get(entry.getKey()) + entry.getValue());
								wpMutex[entry.getKey().ordinal()].release();
									
							}
							atMutex[ex.getAT().ordinal()].release();
							
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}*/
						//End
					}
				}
			});
		}
		
	}
	
	public void shutdown() {
		executor.shutdown();
	}
	
}
