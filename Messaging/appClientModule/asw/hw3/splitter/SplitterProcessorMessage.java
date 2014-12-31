package asw.hw3.splitter;

import java.util.List;
import java.util.logging.Logger;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.TextMessage;

import asw.hw3.dominio.IntestazioneOrdine;
import asw.hw3.dominio.Ordine;
import asw.hw3.dominio.RigaOrdine;
import asw.hw3.dominio.SerializeDeserializeJSON;
import asw.util.logging.AswLogger;

public class SplitterProcessorMessage implements MessageListener {
	
	private ConnectionFactory connectionFactory;
	private Queue codaIntestazioniOrdine;
	private Queue codaRigheOrdine;
	private static Logger logger = AswLogger.getInstance().getLogger("asw.hw3.splitter"); 
	//private int idOrder;
	
	public SplitterProcessorMessage(Queue codaIntestazioniOrdine, Queue codaRigheOrdine, ConnectionFactory connectionFactory) {
		this.codaIntestazioniOrdine = codaIntestazioniOrdine;
		this.codaRigheOrdine = codaRigheOrdine;
		this.connectionFactory = connectionFactory;
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
	
	private void processReceiveMessageByQueue(String messageOrderWithId) {
		SerializeDeserializeJSON serializeDeserializeJSON = new SerializeDeserializeJSON();
		Ordine ordine = (Ordine) serializeDeserializeJSON.deserializeObject(messageOrderWithId, Ordine.class);
		manageHeaderOrders(messageOrderWithId, ordine, serializeDeserializeJSON );
		manageRowOrders(messageOrderWithId, ordine, serializeDeserializeJSON );
	}
	
	private void manageHeaderOrders(String messageOrderWithId, Ordine ordine, SerializeDeserializeJSON  serializeDeserializeJSON) {
		IntestazioneOrdine intOrder = new IntestazioneOrdine(ordine.getIdOrdine(), ordine.getCliente(), ordine.getProdotti().size());
		String intOrderJson = serializeDeserializeJSON.serializeObject(intOrder);
		SplitterHeaderOrdersProduceMessage splitterHeader = new SplitterHeaderOrdersProduceMessage(this.codaIntestazioniOrdine, this.connectionFactory, intOrderJson);
		splitterHeader.run();
	}
	
	private void manageRowOrders(String messageOrderWithId, Ordine ordine, SerializeDeserializeJSON  serializeDeserializeJSON) {
		List<String> prodotti = ordine.getProdotti();
		for(int i=0; i<prodotti.size(); i++) {
			RigaOrdine row = new RigaOrdine(ordine.getIdOrdine(), i, prodotti.get(i));
			String rowJson = serializeDeserializeJSON.serializeObject(row);
			SplitterRowOrdersProduceMessage splitterRow = new SplitterRowOrdersProduceMessage(this.codaRigheOrdine, this.connectionFactory, rowJson);
			splitterRow.run();
		}
	}
	
}
