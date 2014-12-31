package asw.hw3.dominio;

import java.util.*; 

/** 
 * Un oggetto Ordine rappresenta un ordine effettuato da un cliente 
 * e relativo ad un certo numero di prodotti. 
 * 
 * Si noti che si tratta di una rappresentazione AUTOCONTENUTA: 
 * un singolo oggetto Ordine rappresenta un ordine intero, senza 
 * referenziare n� oggetti Cliente (non ci sono) n� oggetti Prodotto (non ci sono) 
 * n� oggetti RigaOrdine (rappresentano un'altra cosa). 
 */
public class Ordine {
	
	/** 
	 * Il numero identificativo dell'ordine. 
	 * Se vale 0 vuol dire che non � stato assegnato nessun identificatore all'ordine. 
	 */
	private int idOrdine; 
	
	/** Il (nome del) cliente dell'ordine. */ 
	private String cliente;
	
	/** Elenco dei (nomi dei) prodotti a cui l'ordine si riferisce. */ 
	private List<String> prodotti; 
	
	/** Crea un nuovo ordine. */ 
	public Ordine(int id, String cliente, List<String> prodotti) {
		this.idOrdine = id; 
		this.cliente = cliente; 
		this.prodotti = prodotti; 
	}
	
	/** Crea un nuovo ordine, con identificatore non assegnato. */ 
	public Ordine(String cliente, List<String> prodotti) {
		this(0, cliente, prodotti); 
	}
	
	
	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public List<String> getProdotti() {
		return prodotti;
	}

	public void setProdotti(List<String> prodotti) {
		this.prodotti = prodotti;
	}

	public int getIdOrdine() {
		return idOrdine;
	}

	/** 
	 * Calcola una descrizione testuale di questo ordine. 
	 * Sono possibili due forme, a secondo che l'ordine abbia o meno un identificatore. 
	 * Ordine con identificatore: <id> <cliente> <numero prodotti ordinati> <prodotto 1> ... <prodotto n> 
	 * Ordine senza identificatore: <cliente> <numero prodotti ordinati> <prodotto 1> ... <prodotto n> 
	 */
	public String toString() {
		String desc = "";
		if (idOrdine>0) {
			desc += idOrdine + " ";
		}
		desc += cliente + " "; 
		desc += prodotti.size() + " "; 
		for (String p: prodotti) {
			desc += p + " "; 
		}
		return desc; 
	}
	
	/** 
	 * Crea un nuovo ordine, a partire da una descrizione testuale dell'ordine, 
	 * da una delle seguenti forme: 
	 * Ordine con identificatore: <id> <cliente> <numero prodotti ordinati> <prodotto 1> ... <prodotto n> 
	 * Ordine senza identificatore: <cliente> <numero prodotti ordinati> <prodotto 1> ... <prodotto n> 
	 */
	public Ordine(String desc) {
		Scanner in = new Scanner(desc); 
		if (in.hasNextInt()) {
			this.idOrdine = in.nextInt();
		} else {
			this.idOrdine = 0;			
		}
		this.cliente = in.next(); 
		int numeroProdottiOrdinati = in.nextInt(); 
		this.prodotti = new ArrayList<String>(numeroProdottiOrdinati); 
		for (int i=0; i<numeroProdottiOrdinati; i++) {
			this.prodotti.add(in.next()); 
		}
		in.close(); 
	}
	
	/** Crea e restituisce l'intestazione di questo ordine. */ 
	public IntestazioneOrdine getIntestazioneOrdine() {
		return new IntestazioneOrdine(idOrdine, cliente, prodotti.size()); 
	}
	
	/** Crea e restituisce l'elenco delle righe d'ordine di questo ordine. */ 
	public List<RigaOrdine> getRigheOrdine() {
		List<RigaOrdine> righeOrdine = new ArrayList<RigaOrdine>(prodotti.size());
		for (int i=0; i<prodotti.size(); i++) {
			righeOrdine.add ( new RigaOrdine(idOrdine, i, prodotti.get(i)) ); 
		}
		return righeOrdine; 
	}
	
	/** 
	 * Crea un nuovo ordine, a partire da un'intestazione e 
	 * da un elenco di righe d'ordine (nell'ipotesi che le informazioni siano coerenti). 
	 */
	public Ordine(IntestazioneOrdine intestazioneOrdine, List<RigaOrdine> righeOrdine) {
		this.idOrdine = intestazioneOrdine.getIdOrdine(); 
		this.cliente = intestazioneOrdine.getCliente(); 
		int numeroProdotti = intestazioneOrdine.getNumeroRigheOrdine(); 
		this.prodotti = new ArrayList<String>(numeroProdotti); 
		for (RigaOrdine r: righeOrdine) {
			prodotti.add ( r.getProdotto() );
		}
	}
	
	/** Assegna un id all'ordine. */ 
	public void setIdOrdine(int idOrdine) {
		this.idOrdine = idOrdine; 
	}

}
