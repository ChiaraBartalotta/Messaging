package asw.hw3.dataenricher;

import java.util.logging.Logger;

import javax.jms.*;

import asw.hw3.dominio.Ordine;
import asw.hw3.dominio.SerializeDeserializeJSON;
import asw.util.logging.AswLogger;

public class DataEnricherProcessorMessage implements MessageListener {
	
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
	
	public DataEnricherProcessorMessage(Queue codaOrdiniConId, ConnectionFactory connectionFactory) {
		this.idOrder = 0;
		this.codaOrdiniConId = codaOrdiniConId;
		this.connectionFactory = connectionFactory;
	}
	
	public String assignIdOrder(String order)  {
		if (order!=null && !order.equals("")) {
			this.idOrder += 1;
			String orderWithId = "";
			SerializeDeserializeJSON serializeDeserializeJSON = new SerializeDeserializeJSON();
			Ordine ordine = (Ordine) serializeDeserializeJSON.deserializeObject(order, Ordine.class);
			ordine.setIdOrdine(this.idOrder);
			orderWithId = serializeDeserializeJSON.serializeObject(ordine);
			/*ObjectMapper mapper = new ObjectMapper();
			try {
				Ordine ord = mapper.readValue(order, Ordine.class);
				ord.setIdOrdine(this.idOrder);
				orderWithId = mapper.writeValueAsString(ord);
			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			return orderWithId;
		} else
			return null;
	}
	

	@Override
	public synchronized void onMessage(Message message) {
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
	
	private void processReceiveMessageByQueue(String messageOrder) {
		String messageOrderWithId = assignIdOrder(messageOrder);
		logger.info("Assegnato l'id all'ordine numero "+this.idOrder+": "+ messageOrderWithId);
		DataEnricherProduceMessage dataep = new DataEnricherProduceMessage(this.codaOrdiniConId, this.connectionFactory, messageOrderWithId);
		dataep.run();
	}
	 

}
