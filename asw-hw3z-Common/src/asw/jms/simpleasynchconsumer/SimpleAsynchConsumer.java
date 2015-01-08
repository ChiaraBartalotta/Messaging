package asw.jms.simpleasynchconsumer;

import javax.jms.*;

import asw.util.cancellable.Cancellable;
import asw.util.logging.AswLogger;
import asw.util.sleep.Sleeper;

import java.util.logging.Logger;

/*
 * SimpleAsynchConsumer.java
 *
 * Un SimpleAsynchConsumer è un endpoint per ricevere messaggi
 * da una generica destinazione JMS (coda o argomento).
 * 
 * La ricezione dei messaggi avviene secondo la modalità asincrona.
 *
 * Delega la ricezione dei messaggi ricevuti ad un MessageListener
 * associato a questo consumer.
 *
 * I messaggi potranno essere inviati da SimpleProducer.
 *
 * Variante di un esempio nel tutorial per Java EE.
 *
 * @author Luca Cabibbo
 */
public class SimpleAsynchConsumer implements Cancellable {

	/* logger */
	private static Logger logger = AswLogger.getInstance().getLogger("asw.jms.simpleasynchconsumer");

	/* nome di questo consumer */
    private String name;
    /* destinazione di questo consumer */
    private Destination destination;
    /* connection factory di questo consumer */
    private ConnectionFactory connectionFactory;

    /* per il callback dei messaggi ricevuti */
    private MessageListener listener;

    /* connessione jms */
    private Connection connection = null;
    /* sessione jms */
    private Session session = null;
    /* per la ricezione di messaggi da destination */
    MessageConsumer messageConsumer = null;

    /* è stato cancellato? */
    private boolean cancelled;

    /**
     * Crea un nuovo SimpleAsynchConsumer, di nome n, per una destinazione di nome dn.
     */
    public SimpleAsynchConsumer(String n, Destination d, ConnectionFactory cf, MessageListener listener) {

        this.name = n;
        this.destination = d;
        this.connectionFactory = cf;
        this.listener = listener;
        this.cancelled = false;
    }

    /**
     * Apre la connessione alla destinazione JMS di questo consumer.
     */
    public void connect() {
        try {
        	logger.fine(this.toString() + ": Connecting...");
            connection = connectionFactory.createConnection();
            session = connection.createSession(
                                false, // non transazionale
				Session.AUTO_ACKNOWLEDGE);
        	messageConsumer = session.createConsumer(destination);
        	logger.info(this.toString() + ": Connected");
        } catch (Exception e) {
            logger.info(this.toString() + ": Connection problem: " + e.toString());
            disconnect();
        }
    }

    /**
     * Si disconnette dalla destinazione JMS di questo consumer.
     */
    public void disconnect() {
        if (connection != null) {
            try {
            	logger.fine(this.toString() + ": Disconnecting...");
                connection.close();
                connection = null;
            	logger.info(this.toString() + ": " + this.listener.toString());
            	logger.info(this.toString() + ": Disconnected");
            } catch (JMSException e) {
            	logger.info(this.toString() + ": Disconnection problem: " + e.toString());
            }
        }
    }

    /**
     * Riceve infiniti messaggi dalla destinazione (sessione di ricezione)
     * di questo consumer.
     */
    public void receiveMessages() {
        try {
    		messageConsumer.setMessageListener(this.listener);
            /* avvia la consegna di messaggi per la connessione */
    		connection.start();
    		/* va avanti, fino a quando non viene fermato */
    		while (!isCancelled()) {
				Sleeper.sleep(10);
    		}
            /* è stato fermato, arresta la consegna di messaggi per la connessione */
            connection.stop();
        } catch (JMSException e) {
        	logger.info(this.toString() + ": Error while receiving messages: " + e.toString());
            System.exit(1);
        }
    }

    /**
     * Cancella questo consumer.
     */
    public void cancel() {
    	this.cancelled = true;
    }

    /**
     * Questo consumer è stato cancellato?
     */
    public boolean isCancelled() {
    	return this.cancelled;
    }

    /**
     * Restituisce una descrizione di questo consumer.
     */
    public String toString() {
        return "SimpleAsynchConsumer[" +
                "name=" + name +
//                ", destination=" + destination.toString() +
                "]";
    }

}
