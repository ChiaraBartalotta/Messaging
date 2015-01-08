package asw.hw3.produttoreordini;

import javax.jms.*;
import javax.annotation.Resource;

/**
 * L'applicazione asw-hw3a: 
 *  - crea una sequenza di ordini casuale (senza id)
 *  - invia questi ordini alla coda jms/asw/CodaOrdiniGrezzi
 */
public class Main {

	/* L'iniezione delle dipendenze può avvenire solo nella main class. */ 
	@Resource(lookup = "jms/asw/CodaOrdiniSenzaId")
    private static Queue codaOrdiniSenzaId;
    @Resource(lookup = "jms/asw/ConnectionFactory")
    private static ConnectionFactory connectionFactory;

    public static void main(String[] args) {
    	
    	ProduttoreOrdini client = new ProduttoreOrdini(codaOrdiniSenzaId, connectionFactory);
    	client.run(); 

    }

}
