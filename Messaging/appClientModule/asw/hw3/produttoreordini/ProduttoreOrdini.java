package asw.hw3.produttoreordini;

import java.io.IOException;
import java.util.logging.Logger;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import asw.hw3.dominio.GeneratoreOrdini;
import asw.hw3.dominio.Ordine;
import asw.jms.simpleproducer.SimpleProducer;
import asw.util.logging.AswLogger;

/*
 * Crea una sequenza di ordini casuali e li invia alla coda codaOrdiniSenzaId. 
 */
public class ProduttoreOrdini {
	
    private Queue codaOrdiniSenzaId;
    private ConnectionFactory connectionFactory;

	/* logger */ 
	private static Logger logger = AswLogger.getInstance().getLogger("asw.hw3.produttoreordini"); 

	/*
	 * Crea un nuovo ProduttoreOrdini per la coda codaOrdiniSenzaId. 
	 */
    public ProduttoreOrdini(Queue codaOrdiniSenzaId, ConnectionFactory connectionFactory) {
    	this.codaOrdiniSenzaId = codaOrdiniSenzaId; 
    	this.connectionFactory = connectionFactory; 
	}
	
	/* 
	 * Il metodo principale. 
	 */ 
	public void run() {
		
    	/* crea un produttore su codaOrdiniSenzaId */ 
    	SimpleProducer p = new SimpleProducer("Produttore ordini (senza id)", codaOrdiniSenzaId, connectionFactory);
        logger.info("Creato produttore: " + p.toString());

        /* avvia il produttore */ 
        p.connect(); 
        
        /* genera 100 ordini casuali e li invia alla coda codaOrdiniSenzaId */ 
		String[] clienti = 
				new String[] { "Alice", "Bernardo", "Carlo", "Diana", "Elisa" };
		String[] prodotti = 
				new String[] { 
					"PC", "Notebook", "Netbook", "Tablet", 
					"Schermo", "Tastiera", "Mouse", "Webcam", 
					"Lettore_DVD", "Pennetta_USB",   
					"Windows", "Linux", 
					"Antivirus", "Photoshop" 
				};
		GeneratoreOrdini g = new GeneratoreOrdini(clienti, prodotti); 
		for (int i=0; i<100; i++) {
			Ordine o = g.getRandomOrdine();
			String jsonOrder = "";
			logger.info("Creo ordine (senza id): " + o.toString());
			ObjectMapper mapper = new ObjectMapper();
			try {
				jsonOrder = mapper.writeValueAsString(o);
			} catch (JsonGenerationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			p.sendMessage(jsonOrder); 
		}
		
		/* disconnette il produttore */ 
		p.disconnect(); 
		
        /* termina */ 
		System.exit(0); 
        
	}

}
