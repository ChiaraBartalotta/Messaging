package asw.hw3.dataenricher;

import java.util.logging.Logger;

import javax.jms.*;

import asw.jms.simpleasynchconsumer.SimpleAsynchConsumer;
import asw.jms.simpleasynchconsumer.SimpleAsynchConsumerThread;
import asw.util.logging.AswLogger;

public class DataEnricherReceiveMessage {
	
	private Queue codaOrdiniSenzaId;
    private ConnectionFactory connectionFactory;
    private Queue codaOrdiniConId;
    private SimpleAsynchConsumer c = null; 
	private static Logger logger = AswLogger.getInstance().getLogger("asw.hw3.dataenricher"); 
    
    public DataEnricherReceiveMessage(Queue codaSenzaId, ConnectionFactory connectionFactory, Queue codaConId) {
    	this.codaOrdiniSenzaId = codaSenzaId;
    	this.codaOrdiniConId = codaConId;
    	this.connectionFactory = connectionFactory;
    }
    
    public void run() {
		MessageListener listener = new DataEnricherProcessorMessage(this.codaOrdiniConId, this.connectionFactory);
		
    	c = new SimpleAsynchConsumer("Consumatori ricezione ordini senza Id", this.codaOrdiniSenzaId, connectionFactory, listener);
        logger.info("Creato consumatore ricezione ordini senza Id: " + c.toString());
        /* avvia il consumatore (entro un thread) */ 
        SimpleAsynchConsumerThread tc = new SimpleAsynchConsumerThread(c);
        tc.start(); 

    	/* attende la terminazione del thread - poi termina il programma */ 
        try {
			tc.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 
        /*c.disconnect(); 
        System.exit(0);*/
        
	}
}
