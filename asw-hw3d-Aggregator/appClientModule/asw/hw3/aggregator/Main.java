package asw.hw3.aggregator;

import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.Queue;

public class Main {
	@Resource(lookup = "jms/asw/CodaIntestazioniOrdine") 
	private static Queue codaIntestazioniOrdine;
	@Resource(lookup = "jms/asw/CodaRigheOrdine") 
	private static  Queue codaRigheOrdine;
    @Resource(lookup = "jms/asw/ConnectionFactory")
    private static ConnectionFactory connectionFactory;
    @Resource(lookup = "jms/asw/CodaOrdiniRiaggregati")    		
    private static Queue codaOrdiniRiaggregati;
    

    public static void main(String[] args) {
    	AggregatorReceiveMessage aggregator = new AggregatorReceiveMessage(codaIntestazioniOrdine, codaRigheOrdine, connectionFactory, codaOrdiniRiaggregati);
    	aggregator.run();   	
    }
}
