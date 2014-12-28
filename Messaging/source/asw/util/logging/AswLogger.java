package asw.util.logging;

import java.io.FileInputStream;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class AswLogger {
	
	/* Il riferimento all'istanza singleton di AswLogger. */
	private static AswLogger instance;

	/* file di configurazione per il logging */ 
	private static final String LOGGING_CONFIG_FILE = "../asw-util/logging.properties";
	
	/**
	 * Restituisce il riferimento all'istanza singleton di ControllerLogging
	 * @return il riferimento all'istanza singleton di ControllerLogging
	 */
	public static synchronized AswLogger getInstance() {
		/* Se non esiste, allora crea l'istanza singleton di ControllerLogging */
		if (instance == null) {
			instance = new AswLogger();
		}
		return instance;
	}

	/* Crea un nuovo oggetto di tipo AswLogger e lo inizializza. */
	private AswLogger() {
		LogManager logManager = LogManager.getLogManager(); 
		try {
			logManager.readConfiguration(new FileInputStream(LOGGING_CONFIG_FILE));
		} catch (Exception e) {
			/* Stampa il messaggio dell'eccezione */
			System.err.println(e);			
		}
	}
	
	/* Restituisce un logger. */ 
	public Logger getLogger(String name) {
		return Logger.getLogger(name); 
	}
	
	
}
