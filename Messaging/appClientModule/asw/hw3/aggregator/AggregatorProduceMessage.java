package asw.hw3.aggregator;

import java.util.logging.Logger;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;

import asw.jms.simpleproducer.SimpleProducer;
import asw.util.logging.AswLogger;

public class AggregatorProduceMessage {
	private Queue codaOrdiniRiaggregati;
	private ConnectionFactory connectionFactory;
	private String messageText;
	private static Logger logger = AswLogger.getInstance().getLogger("as.hw3.dataenricher"); 

	public AggregatorProduceMessage(Queue codaOrdiniRiaggregati, ConnectionFactory connectionFactory, String messageText) {
		this.codaOrdiniRiaggregati = codaOrdiniRiaggregati; 
		this.connectionFactory = connectionFactory; 
		this.messageText = messageText;
	}

	public void run() {
		SimpleProducer p = new SimpleProducer("Produttore ordini aggregati", codaOrdiniRiaggregati, connectionFactory);
		logger.info("Creato ordine aggregato: " + p.toString());
		p.connect(); 
		p.sendMessage(this.messageText); 
		p.disconnect(); 
		System.exit(0); 
	}
}
