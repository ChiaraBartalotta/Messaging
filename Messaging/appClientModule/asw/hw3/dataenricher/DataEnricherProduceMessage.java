package asw.hw3.dataenricher;

import java.util.logging.Logger;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;

import asw.hw3.dominio.GeneratoreOrdini;
import asw.hw3.dominio.Ordine;
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

		/* crea un produttore su codaOrdiniSenzaId */ 
		SimpleProducer p = new SimpleProducer("Produttore ordini con id", codaOrdiniConId, connectionFactory);
		logger.info("Creato produttore con Id: " + p.toString());
		/* avvia il produttore */ 
		p.connect(); 
		p.sendMessage(this.messageText); 
		/* disconnette il produttore */ 
		p.disconnect(); 
		/* termina */ 
		System.exit(0); 
	}
}
