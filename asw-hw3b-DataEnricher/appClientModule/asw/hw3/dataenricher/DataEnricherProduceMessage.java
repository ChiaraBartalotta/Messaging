package asw.hw3.dataenricher;

import java.util.logging.Logger;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;

import asw.jms.simpleproducer.SimpleProducer;
import asw.util.logging.AswLogger;

public class DataEnricherProduceMessage {
	private Queue codaOrdiniConId;
	private ConnectionFactory connectionFactory;
	private String messageText;

	/* logger */ 
	private static Logger logger = AswLogger.getInstance().getLogger("as.hw3.dataenricher"); 

	public DataEnricherProduceMessage(Queue codaOrdiniConId, ConnectionFactory connectionFactory, String messageText) {
		this.codaOrdiniConId = codaOrdiniConId; 
		this.connectionFactory = connectionFactory; 
		this.messageText = messageText;
	}

	public void run() {

		SimpleProducer p = new SimpleProducer("Produttore ordini con id", codaOrdiniConId, connectionFactory);
		logger.info("Creato produttore con Id: " + p.toString());
		p.connect(); 
		p.sendMessage(this.messageText); 
		/* disconnette il produttore */ 
		p.disconnect(); 
		System.exit(0); 
	}
}
