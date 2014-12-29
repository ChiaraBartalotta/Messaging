package asw.hw3.dataenricher;

import javax.annotation.Resource;
import javax.jms.*;


public class Main {

	@Resource(lookup = "jms/asw/CodaOrdiniSenzaId")    		
    private static Queue codaOrdiniSenzaId;
    @Resource(lookup = "jms/asw/ConnectionFactory")
    private static ConnectionFactory connectionFactory;
    @Resource(lookup = "jms/asw/CodaOrdiniConId")    		
    private static Queue codaOrdiniConId;
    

    public static void main(String[] args) {
    	DataEnricherReceiveMessage dataEnricher = new DataEnricherReceiveMessage(codaOrdiniSenzaId, connectionFactory, codaOrdiniConId);
    	dataEnricher.run();  
    }

}
