package asw.hw3.splitter;

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
    @Resource(lookup = "jms/asw/CodaOrdiniConId")    		
    private static Queue codaOrdiniConId;
    

    public static void main(String[] args) {
    	SplitterReceiveMessage splitter = new SplitterReceiveMessage(codaOrdiniConId, connectionFactory, codaIntestazioniOrdine, codaRigheOrdine);
    	splitter.run();   	
    }

}
