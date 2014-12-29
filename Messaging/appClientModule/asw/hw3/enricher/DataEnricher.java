package asw.hw3.enricher;

import javax.jms.*;

public class DataEnricher {
	
	private Queue codaOrdiniSenzaId;
	private int idOrder;
	
	public DataEnricher() {
		this.idOrder = 0;
	}
	
	public String assignIdOrder(String order) {
		if (order!=null && !order.equals("")) {
			this.idOrder += 1;
			return this.idOrder+" "+order;
		} else
			return null;
	}
	
	public void receiveMessageByQueue() {
		//this.codaOrdiniSenzaId.
		
	}
	 

}
