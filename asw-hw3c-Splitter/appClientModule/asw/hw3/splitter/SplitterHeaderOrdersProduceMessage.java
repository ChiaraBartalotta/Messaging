package asw.hw3.splitter;

import java.util.logging.Logger;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;

import asw.jms.simpleproducer.SimpleProducer;
import asw.util.logging.AswLogger;

public class SplitterHeaderOrdersProduceMessage {
	private ConnectionFactory connectionFactory;
	private Queue codaIntestazioniOrdine;
	private String messageText;

	/* logger */ 
	private static Logger logger = AswLogger.getInstance().getLogger("as.hw3.splitter"); 

	public SplitterHeaderOrdersProduceMessage(Queue codaIntestazioniOrdine, ConnectionFactory connectionFactory, String messageText) {
		this.codaIntestazioniOrdine= codaIntestazioniOrdine; 
		this.connectionFactory = connectionFactory; 
		this.messageText = messageText;
	}

	public void run() {

		SimpleProducer p = new SimpleProducer("Produttore Intestazioni ordini", codaIntestazioniOrdine, connectionFactory);
		logger.info("Creata intestazione ordine: " + p.toString());
		p.connect(); 
		p.sendMessage(this.messageText); 
		p.disconnect(); 
		System.exit(0); 
	}
}
