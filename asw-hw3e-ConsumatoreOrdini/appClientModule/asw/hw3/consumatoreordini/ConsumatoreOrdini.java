package asw.hw3.consumatoreordini;

import java.util.logging.Logger;

import javax.jms.ConnectionFactory;
import javax.jms.MessageListener;
import javax.jms.Queue;

import asw.jms.simpleasynchconsumer.SimpleAsynchConsumer;
import asw.jms.simpleasynchconsumer.SimpleAsynchConsumerThread;
import asw.util.cancellable.KeyboardKiller;
import asw.util.logging.AswLogger;

/*
 * Legge una sequenza di ordini dalla coda codaOrdini  
 * e li visualizza. 
 */
public class ConsumatoreOrdini {
	
    private Queue codaOrdini;
    private ConnectionFactory connectionFactory;
    
	private SimpleAsynchConsumer c = null; 
    
	/* logger */ 
	private static Logger logger = AswLogger.getInstance().getLogger("asw.hw3.consumatoreordini"); 

	/*
	 * Crea un nuovo ConsumatoreOrdini
	 * per la coda codaOrdini. 
	 */
    public ConsumatoreOrdini(Queue codaOrdini, ConnectionFactory connectionFactory) {
    	this.codaOrdini = codaOrdini; 
    	this.connectionFactory = connectionFactory; 
	}
	
	/* 
	 * Il metodo principale. 
	 */ 
	public void run() {
		
		/* crea il proprio processore/ascoltatore di messaggi */ 
		MessageListener listener = new ConsumatoreOrdiniMessageProcessor(); 
		
		/* crea un consumatore su codaOrdini - girerà messaggi al proprio processore/listener */ 
    	c = new SimpleAsynchConsumer("Consumatore ordini", codaOrdini, connectionFactory, listener);
        logger.info("Creato consumatore: " + c.toString());

        /* per interrompere il consumatore premendo INVIO */ 
        KeyboardKiller k = new KeyboardKiller(); 
        k.add(c); 
        k.start(); 

        /* avvia il consumatore (entro un thread) */ 
        SimpleAsynchConsumerThread tc = new SimpleAsynchConsumerThread(c);
        tc.start(); 

    	/* attende la terminazione del thread - poi termina il programma */ 
        try {
			tc.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 

        /* disconnette il consumatore */ 
        c.disconnect(); 
        /* termina */ 
        System.exit(0);
        
	}
	
}
