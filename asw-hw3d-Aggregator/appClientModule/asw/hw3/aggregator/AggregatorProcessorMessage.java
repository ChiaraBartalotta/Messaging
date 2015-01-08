package asw.hw3.aggregator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.TextMessage;

import asw.hw3.dominio.*;
import asw.util.logging.AswLogger;

public class AggregatorProcessorMessage implements MessageListener {
	
	private ConnectionFactory connectionFactory;
	private Queue codaOrdiniRiaggregati;
	private static Logger logger = AswLogger.getInstance().getLogger("asw.hw3.aggregator");
	private Map<IntestazioneOrdine, List<RigaOrdine>> ordiniRiaggregati;
	private List<RigaOrdine> righeOrdineInAttesa;
	
	public AggregatorProcessorMessage(Queue codaOrdiniRiaggregati, ConnectionFactory connectionFactory) {
		this.codaOrdiniRiaggregati = codaOrdiniRiaggregati;
		this.connectionFactory = connectionFactory;
		this.ordiniRiaggregati = new HashMap<IntestazioneOrdine, List<RigaOrdine>>();
		this.righeOrdineInAttesa = new ArrayList<RigaOrdine>();
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
	
	private void processReceiveMessageByQueue(String mex) {
		SerializeDeserializeJSON serializeDeserializeJSON = new SerializeDeserializeJSON();
		IntestazioneOrdine intOrdine = (IntestazioneOrdine) serializeDeserializeJSON.deserializeObject(mex, IntestazioneOrdine.class);
		if (intOrdine.getIdOrdine()>0 && intOrdine.getCliente()!=null && !intOrdine.getCliente().equals("") && intOrdine.getNumeroRigheOrdine()>0) 
			processHeaderOrder(intOrdine);
		else {
			RigaOrdine riga = (RigaOrdine) serializeDeserializeJSON.deserializeObject(mex, RigaOrdine.class);
			processRowOrder(riga);
		}
	}
	
	private void processHeaderOrder(IntestazioneOrdine intOrdine) {
		List<RigaOrdine> righeOrdine = new ArrayList<RigaOrdine>();
		for(RigaOrdine r : this.righeOrdineInAttesa) {
			if (r.getIdOrdine()==intOrdine.getIdOrdine()) {
				righeOrdine.add(r);
				//this.righeOrdineInAttesa.remove(r);
			}
		}
		this.ordiniRiaggregati.put(intOrdine, righeOrdine);
	}
	
	private void processRowOrder(RigaOrdine riga) {
		IntestazioneOrdine intOrdine = getHeaderByIdOrder(riga.getIdOrdine());
		if (intOrdine==null) 
			this.righeOrdineInAttesa.add(riga);
		else {
			List<RigaOrdine> righe = this.ordiniRiaggregati.get(intOrdine);
			righe.add(riga);
			if (righe.size()==intOrdine.getNumeroRigheOrdine()) 
				sendMessageOrder(intOrdine, righe);
			else {
				this.ordiniRiaggregati.put(intOrdine, righe);
			}
		}
	}
	
	private IntestazioneOrdine getHeaderByIdOrder(int idOrdine) {
		for(IntestazioneOrdine i : this.ordiniRiaggregati.keySet()) {
			if (i.getIdOrdine()==idOrdine)
				return i;
		}
		return null;
	}
	
	private void sendMessageOrder(IntestazioneOrdine intOrdine, List<RigaOrdine> righe) {
		List<String> nomiProdotti = new ArrayList<String>();
		for(RigaOrdine riga : righe) 
			nomiProdotti.add(riga.getProdotto());
		Ordine ordine = new Ordine(intOrdine.getIdOrdine(), intOrdine.getCliente(), nomiProdotti);
		SerializeDeserializeJSON serializeDeserializeJSON = new SerializeDeserializeJSON();
		String jsonOrdine = serializeDeserializeJSON.serializeObject(ordine);
		AggregatorProduceMessage aggrProcc = new AggregatorProduceMessage(this.codaOrdiniRiaggregati, this.connectionFactory,jsonOrdine);
		aggrProcc.run();
	}
}
