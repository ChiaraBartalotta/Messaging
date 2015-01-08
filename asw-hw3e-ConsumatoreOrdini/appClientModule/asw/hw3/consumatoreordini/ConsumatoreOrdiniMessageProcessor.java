package asw.hw3.consumatoreordini;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import asw.hw3.dominio.Ordine;
import asw.hw3.dominio.SerializeDeserializeJSON;

import java.util.logging.Logger;

import asw.util.logging.AswLogger;

/*
 * Ascoltatore di messaggi dalla coda codaOrdini. 
 * Implementa MessageListener. 
 * 
 * Elabora un ordine ricevuto dalla coda codaOrdini,   
 * e nello specifico lo visualizza. 
 */
public class ConsumatoreOrdiniMessageProcessor implements MessageListener {
	
	/* logger */ 
	private static Logger logger = AswLogger.getInstance().getLogger("asw.hw3.consumatoreordini"); 
	
	/* numero di messaggi ricevuti */ 
	private int messaggiRicevuti; 

	/**
	 * Crea un nuovo ConsumatoreOrdiniMessageProcessor. 
	 */
	public ConsumatoreOrdiniMessageProcessor() {
		this.messaggiRicevuti = 0; 
	}
	
    /**
     * Gestisce la ricezione di un messaggio JMS m
     * (implementa MessageListener).
     */
    public synchronized void onMessage(Message m) {
    	/* ricevuto un messaggio, ne delega l'elaborazione
    	 * al metodo processMessage */
//		logger.fine("MessageListener.onMessage()");
    	if (m instanceof TextMessage) {
    		TextMessage  message = (TextMessage) m;
    		try {
    			/* gestisce la ricezione del messaggio message */
    			String text = message.getText();
    	    	/* delega la vera gestione del messaggio 
    	    	 * al metodo processMessage */
    	    	this.processMessage(text);
    		} catch (JMSException e) {
    			logger.info("MessageListener.onMessage(): JMSException: " + e.toString());
    		}
    	}
    }
	
	/* 
	 * Gestisce la ricezione di un messaggio: 
	 * visualizza l'ordine ricevuto. 
	 */ 
	private void processMessage(String message) {
		/* conta il messaggio ricevuto */ 
		this.messaggiRicevuti++; 
		/* il messaggio ricevuto � un ordine */
		SerializeDeserializeJSON deser = new SerializeDeserializeJSON();
		Ordine o = (Ordine) deser.deserializeObject(message, Ordine.class);
		//Ordine o = new Ordine(message);
		/* visualizza l'ordine */ 
		logger.info("Ricevuto ordine completo: "+ o.toString()); 
	}
	
	/*
	 * Restituisce un report del lavoro fatto. 
	 */
	public String toString() {
		return this.getClass().getSimpleName() + ": " + messaggiRicevuti + " message(s) received.";
	}

}
