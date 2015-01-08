package asw.jms.simpleasynchconsumer;

import java.util.logging.Logger;

import asw.util.logging.AswLogger;

/*
 * SimpleAsynchConsumerThread.java
 *
 * Un SimpleAsynchConsumerThread riceve messaggi tramite un consumer.
 * Opera in un thread autonomo.
 * Quindi può operare in modo concorrente ad altri ConsumerThread.
 *
 * @author Luca Cabibbo
*/
public class SimpleAsynchConsumerThread extends Thread {

	/* logger */
	private static Logger logger = AswLogger.getInstance().getLogger("asw.jms.simpleasynchconsumer");

	/**
	 * Il consumer associato a questo thread.
	 */
    private SimpleAsynchConsumer simpleConsumer;

    /**
     * Crea un nuovo ConsumerThread per il consumer c.
     */
    public SimpleAsynchConsumerThread(SimpleAsynchConsumer c) {
        super();
        this.simpleConsumer = c;
    }

    /**
     * Attività di questo thread.
     */
    public void run() {
		logger.info("ConsumerThread: " + simpleConsumer.toString() + " ready to receive messages");
    	simpleConsumer.connect();

    	simpleConsumer.receiveMessages();

        simpleConsumer.disconnect();
    	logger.info("ConsumerThread: " + simpleConsumer.toString() + ": Done");
    }

}
