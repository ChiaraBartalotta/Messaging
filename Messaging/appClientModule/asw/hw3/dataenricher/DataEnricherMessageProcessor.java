package asw.hw3.dataenricher;

import java.util.logging.Logger;

import javax.jms.*;

import asw.util.logging.AswLogger;

public class DataEnricherMessageProcessor implements MessageListener {
	
	/*private Queue codaOrdiniSenzaId;
	private Queue codaOrdiniConId;*/
	private ConnectionFactory connectionFactory;
	private Queue codaOrdiniConId;
	private static Logger logger = AswLogger.getInstance().getLogger("asw.hw3.dataenricher"); 
	private int idOrder;
	
	/*public DataEnricherMessageProcessor(Queue coMitt, Queue coDest,  ConnectionFactory cn) {
		this.codaOrdiniSenzaId = coMitt;
		this.codaOrdiniConId = coDest;
		this.connectionFactory = cn;
		this.idOrder = 0;
	}*/
	
	public DataEnricherMessageProcessor(Queue codaOrdiniConId, ConnectionFactory connectionFactory) {
		this.idOrder = 0;
		this.codaOrdiniConId = codaOrdiniConId;
		this.connectionFactory = connectionFactory;
	}
	
	public String assignIdOrder(String order) {
		if (order!=null && !order.equals("")) {
			this.idOrder += 1;
			return this.idOrder+" "+order;
		} else
			return null;
	}
	

	@Override
	public void onMessage(Message message) {
		if (message instanceof TextMessage) {
    		TextMessage  messageText = (TextMessage) message;
    		try {
    			String text = messageText.getText();
    			processReceiveMessageByQueue(text);
    		} catch (JMSException e) {
    			logger.info("MessageListener.onMessage(): JMSException: " + e.toString());
    		}
    	}
		
	}
	
	public void processReceiveMessageByQueue(String messageOrder) {
		String messageOrderWithId = assignIdOrder(messageOrder);
		logger.info("Assegnato l'id all'ordine numero "+this.idOrder+": "+ messageOrderWithId);
		DataEnricherProduceMessage dataep = new DataEnricherProduceMessage(this.codaOrdiniConId, this.connectionFactory, messageOrderWithId);
		dataep.run();
	}
	 

}
