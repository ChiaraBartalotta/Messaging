package asw.hw3.splitter;

import java.util.logging.Logger;

import javax.jms.ConnectionFactory;
import javax.jms.MessageListener;
import javax.jms.Queue;

import asw.jms.simpleasynchconsumer.SimpleAsynchConsumer;
import asw.jms.simpleasynchconsumer.SimpleAsynchConsumerThread;
import asw.util.logging.AswLogger;

public class SplitterReceiveMessage {
	
    private SimpleAsynchConsumer c = null; 
	private static Logger logger = AswLogger.getInstance().getLogger("asw.hw3.splitter");
	private ConnectionFactory connectionFactory;
	private Queue codaIntestazioniOrdine;
	private Queue codaRigheOrdine;
	private Queue codaOrdiniConId;
	
	 public SplitterReceiveMessage(Queue codaOrdiniConId, ConnectionFactory connectionFactory, Queue codaIntestazioniOrdine, Queue codaRigheOrdine) {
		    this.codaOrdiniConId = codaOrdiniConId;
	    	this.codaIntestazioniOrdine = codaIntestazioniOrdine;
	    	this.codaRigheOrdine = codaRigheOrdine;
	    	this.connectionFactory = connectionFactory;
	    }
	    
	    public void run() {
			MessageListener listener = new SplitterProcessorMessage(codaIntestazioniOrdine, codaRigheOrdine, connectionFactory);
			
	    	c = new SimpleAsynchConsumer("Consumatori ricezione ordini con Id", this.codaOrdiniConId, connectionFactory, listener);
	        logger.info("Creato consumatore ricezione ordini con Id: " + c.toString());
	        /* avvia il consumatore (entro un thread) */ 
	        SimpleAsynchConsumerThread tc = new SimpleAsynchConsumerThread(c);
	        tc.start(); 
	        try {
				tc.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} 
	        /*c.disconnect(); 
	        System.exit(0);*/
	        
		}

	
}
