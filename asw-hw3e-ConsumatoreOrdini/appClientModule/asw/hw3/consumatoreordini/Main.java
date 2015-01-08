package asw.hw3.consumatoreordini;

import javax.jms.*;
import javax.annotation.Resource;

/**
 * L'applicazione asw-hw3e: 
 *  - legge una sequenza di ordini dalla coda jms/asw/CodaOrdiniRiaggregati  
 *  - visualizza gli ordini letti  
 */
public class Main {

	/* L'iniezione delle dipendenze può avvenire solo nella main class. */ 
//	@Resource(lookup = "jms/asw/CodaOrdiniRiaggregati")		/* versione finale */ 
	@Resource(lookup = "jms/asw/CodaOrdiniSenzaId")    		/* ATTENZIONE: nella versione finale dovrà leggere da "jms/asw/CodaOrdiniRiaggregati" */ 
    private static Queue codaOrdini;
    @Resource(lookup = "jms/asw/ConnectionFactory")
    private static ConnectionFactory connectionFactory;

    public static void main(String[] args) {

    	ConsumatoreOrdini client = new ConsumatoreOrdini(codaOrdini, connectionFactory);
    	client.run(); 
        
    }

}
