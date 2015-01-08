package asw.hw3.dominio;

import java.util.*; 

/** 
 * Classe di supporto per generare una sequenza di ordini casuale. 
 * Vuole in input un insieme di (nomi di) clienti e un insieme di (nomi di) prodotti. 
 * Il metodo getOrdine() crea e restituisce un ordine casuale. 
 */
public class GeneratoreOrdini {
	
	/** Nomi di clienti da usare nella generazione di ordini. */ 
	private String[] clienti; 

	/** Nomi di prodotti da usare nella generazione di ordini. */ 
	private String[] prodotti; 
	
	/** Crea un nuovo generatore di ordini. */ 
	public GeneratoreOrdini(String[] clienti, String[] prodotti) {
		this.clienti = clienti; 
		this.prodotti = prodotti; 
	}
	
	/** Crea un nuovo ordine casuale (con id nullo). */ 
	public Ordine getRandomOrdine() {
		String cliente = getClienteRandom(); 
		
		int numeroProdottiOrdine = getRandom(5)+1; 
		List<String> prodotti = new ArrayList<String>(numeroProdottiOrdine); 
		for (int i=0; i<numeroProdottiOrdine; i++) {
			prodotti.add( getProdottoRandom() ); 
		}
		return new Ordine(cliente, prodotti); 
	}
	
	/** Genera un numero casuale intero tra 0 e max (escluso). */ 
	private int getRandom(int max) {
		return (int) (Math.random()*max); 
	}
	
	/** Restituisce un cliente a caso. */ 
	private String getClienteRandom() {
		return clienti[getRandom(clienti.length)];
	}

	/** Restituisce un prodotto a caso. */ 
	private String getProdottoRandom() {
		return prodotti[getRandom(prodotti.length)];
	}
	
	/** Applicazione di prova. 
	 * Genera e visualizza 10 ordini casuale. */ 
	public static void main(String[] args) {
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
		for (int i=0; i<10; i++) {
			Ordine o = g.getRandomOrdine(); 
			System.out.println("Ordine: " + o.toString()); 
			o.setIdOrdine(i+1);
			System.out.println("Ordine con id: " + o.toString());
		}
		
		System.out.println(); 
		
		Ordine o = g.getRandomOrdine();
		System.out.println("Ordine: " + o.toString());
		o.setIdOrdine(99);
		System.out.println("Ordine con id: " + o.toString());
		IntestazioneOrdine io = o.getIntestazioneOrdine(); 
		List<RigaOrdine> righe = o.getRigheOrdine(); 
		System.out.println("Intestazione ordine: " + io.toString());
		for (RigaOrdine r: righe) {
			System.out.println("Riga ordine: " + r.toString());			
		}
		Ordine oo = new Ordine(io, righe);
		System.out.println("Ordine ricostruito: " + oo.toString());
	}

}
