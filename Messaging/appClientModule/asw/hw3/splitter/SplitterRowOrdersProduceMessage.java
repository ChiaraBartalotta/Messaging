package asw.hw3.splitter;

import java.util.logging.Logger;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;

import asw.jms.simpleproducer.SimpleProducer;
import asw.util.logging.AswLogger;

public class SplitterRowOrdersProduceMessage {
	private ConnectionFactory connectionFactory;
	private Queue codaRigheOrdine;
	private String messageText;

	/* logger */ 
	private static Logger logger = AswLogger.getInstance().getLogger("as.hw3.splitter"); 

	public SplitterRowOrdersProduceMessage(Queue codaRigheOrdine, ConnectionFactory connectionFactory, String messageText) {
		this.codaRigheOrdine = codaRigheOrdine; 
		this.connectionFactory = connectionFactory; 
		this.messageText = messageText;
	}

	public void run() {
		SimpleProducer p = new SimpleProducer("Produttore riga ordine", codaRigheOrdine, connectionFactory);
		logger.info("Creata riga ordine: " + p.toString());
		p.connect(); 
		p.sendMessage(this.messageText); 
		p.disconnect(); 
		System.exit(0); 
	}
}
