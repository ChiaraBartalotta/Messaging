package asw.hw3.aggregator;

import java.util.logging.Logger;

import javax.jms.ConnectionFactory;
import javax.jms.MessageListener;
import javax.jms.Queue;

import asw.jms.simpleasynchconsumer.SimpleAsynchConsumer;
import asw.jms.simpleasynchconsumer.SimpleAsynchConsumerThread;
import asw.util.logging.AswLogger;

public class AggregatorReceiveMessage {
	private Queue codaIntestazioniOrdine;
	private Queue codaRigheOrdine;
	private ConnectionFactory connectionFactory;
    private Queue codaOrdiniRiaggregati;
    private SimpleAsynchConsumer c = null; 
    private SimpleAsynchConsumer c1 = null; 
	private static Logger logger = AswLogger.getInstance().getLogger("asw.hw3.aggregator"); 
    
    public AggregatorReceiveMessage(Queue codaIntestazioniOrdine, Queue codaRigheOrdine, ConnectionFactory connectionFactory, Queue codaOrdiniRiaggregati) {
    	this.codaIntestazioniOrdine = codaIntestazioniOrdine;
    	this.codaRigheOrdine = codaRigheOrdine;
    	this.connectionFactory = connectionFactory;
    	this.codaOrdiniRiaggregati = codaOrdiniRiaggregati;
    }
    
    public void run() {
		MessageListener listener = new AggregatorProcessorMessage(this.codaOrdiniRiaggregati, this.connectionFactory);

    	c = new SimpleAsynchConsumer("Consumatori ricezione intestazioni ordini", this.codaIntestazioniOrdine, connectionFactory, listener);
        logger.info("Creato consumatore ricezione intestazioni ordini: " + c.toString());
        SimpleAsynchConsumerThread tc = new SimpleAsynchConsumerThread(c);
        tc.start(); 
        
        c1 = new SimpleAsynchConsumer("Consumatori ricezione righe ordini", this.codaRigheOrdine, connectionFactory, listener);
        logger.info("Creato consumatore ricezione righe ordini: " + c1.toString());
        SimpleAsynchConsumerThread tc1 = new SimpleAsynchConsumerThread(c1);
        tc1.start(); 
        
        try {
			tc.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        try {
			tc1.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
