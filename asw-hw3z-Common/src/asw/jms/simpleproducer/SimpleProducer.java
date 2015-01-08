package asw.jms.simpleproducer;

import java.util.logging.Logger;

import javax.jms.*;

import asw.util.logging.AswLogger;

/*
 * SimpleProducer.java
 *
 * Un SimpleProducer è un endpoint per inviare messaggi
 * a una generica destinazione JMS (coda o argomento).
 *
 * I messaggi potranno poi essere ricevuti da oggetti SynchConsumer (in modo sincrono)
 * oppure da AsynchConsumer (in modo asincrono).
 *
 * Variante di un esempio nel tutorial per Java EE.
 *
 * @author Luca Cabibbo
 */
public class SimpleProducer {

	/* logger */
	private static Logger logger = AswLogger.getInstance().getLogger("asw.jms.simpleproducer");

	/* nome di questo producer */
    private String name;
    /* destinazione di questo producer */
    private Destination destination;
    /* connection factory di questo producer */
    private ConnectionFactory connectionFactory;

    /* connessione jms */
    private Connection connection = null;
    /* sessione jms */
    private Session session = null;
    /* per l'invio di messaggi a destination */
    private MessageProducer messageProducer = null;

    /* numero di messaggi inviati */
    private int messagesSent;

    /**
     * Crea un nuovo SimpleProducer, di nome n, per una destinazione di nome dn.
     */
    public SimpleProducer(String n, Destination d, ConnectionFactory cf) {

        this.name = n;
        this.destination = d;
        this.connectionFactory = cf;
        this.messagesSent = 0;
    }

    /**
     * Apre la connessione alla destinazione JMS di questo producer.
     */
    public void connect() {
        try {
        	logger.fine(this.toString() + ": Connecting...");
            connection = connectionFactory.createConnection();
            session = connection.createSession(
                                false, // non transazionale
				Session.AUTO_ACKNOWLEDGE);
        	messageProducer = session.createProducer(destination);
        	logger.info(this.toString() + ": Connected");
        } catch (Exception e) {
            logger.info(this.toString() + ": Connection problem: " + e.toString());
            disconnect();
        }
    }

    /**
     * Si disconnette dalla destinazione JMS di questo producer.
     */
    public void disconnect() {
        if (connection != null) {
            try {
            	logger.fine(this.toString() + ": Disconnecting...");
                connection.close();
                connection = null;
            	logger.info(this.toString() + ": Disconnected (" + messagesSent + " message(s) sent)");
            } catch (JMSException e) {
            	logger.info(this.toString() + ": Disconnection problem: " + e.toString());
            }
        }
    }

    /**
     * Invia un messaggio di testo text alla destinazione JMS di questo producer.
     */
    public void sendMessage(String text) {
        try {
        	TextMessage message = session.createTextMessage();
            message.setText(text);
            logger.fine(this.name + ": Sending message: " + message.getText());
            messageProducer.send(message);
            this.messagesSent++;
        } catch (JMSException e) {
        	logger.info(this.name + ": Error while sending message: " + e.toString());
        }
    }

    /**
     * Restituisce una descrizione di questo producer.
     */
    public String toString() {
        return "SimpleProducer[" +
                "name=" + name +
//                ", destination=" + destination.toString() +
                "]";

    }

}
