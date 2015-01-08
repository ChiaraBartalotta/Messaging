package asw.hw3.consumatoreordini;

import javax.jms.*;
import javax.annotation.Resource;

/**
 * L'applicazione asw-hw3e: 
 *  - legge una sequenza di ordini dalla coda jms/asw/CodaOrdiniRiaggregati  
 *  - visualizza gli ordini letti  
 */
public class Main {

	/* L'iniezione delle dipendenze pu� avvenire solo nella main class. */ 
	@Resource(lookup = "jms/asw/CodaOrdiniRiaggregati")		/* versione finale */ 
    private static Queue codaOrdiniRiaggregati;
    @Resource(lookup = "jms/asw/ConnectionFactory")
    private static ConnectionFactory connectionFactory;

    public static void main(String[] args) {

    	ConsumatoreOrdini client = new ConsumatoreOrdini(codaOrdiniRiaggregati, connectionFactory);
    	client.run(); 
        
    }

}
