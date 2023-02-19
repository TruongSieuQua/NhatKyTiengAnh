package edu.truong.meanings_generator.service.getdataservice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class GetDataServicePool {
	@SuppressWarnings("unused")
	private static final long EXPIRED_TIME_IN_MILISECOND = 1200; // 1.2s
    private static int NUMBER_OF_DATASERVICE = 5;
 
    private final List<GetDataService> available = Collections.synchronizedList(new ArrayList<GetDataService>());
    private final List<GetDataService> inUse = Collections.synchronizedList(new ArrayList<GetDataService>());
 
    private final AtomicInteger count = new AtomicInteger(0);
    
    public synchronized void setNumberOfObject(int n) {
    	NUMBER_OF_DATASERVICE = n;
    }
    
    public synchronized GetDataService get() {
        if (!available.isEmpty()) {
        	GetDataService gds = available.remove(0);
            inUse.add(gds);
            return gds;
        }
        if (count.get() == NUMBER_OF_DATASERVICE) {
            this.waitingUntilTaxiAvailable();
            return this.get();
        }
        GetDataService gds = this.create();
        inUse.add(gds);
        return gds;
    }
 
    public synchronized void release(GetDataService gds) {
        System.out.println(gds.name + "is done!");
    	inUse.remove(gds);
        available.add(gds);
        notify();
    }
 
    private GetDataService create() {
    	GetDataService gds = new GetDataService("gds-" + count.incrementAndGet());
        return gds;
    }
 
    private void waitingUntilTaxiAvailable() {
        try {
			wait(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
