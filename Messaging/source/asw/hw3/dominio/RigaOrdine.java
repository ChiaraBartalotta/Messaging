package asw.hw3.dominio;

import java.util.Scanner;

/** 
 * Un oggetto RigaOrdine rappresenta una riga di un ordine effettuato da un cliente,  
 * ed � relativa ad un certo prodotto. 
 * 
 * Si noti che si tratta di una rappresentazione AUTOCONTENUTA: 
 * un singolo oggetto RigaOrdine rappresenta una intera riga d'ordine, senza 
 * referenziare n� oggetti Cliente (non ci sono) n� oggetti Prodotto (non ci sono). 
 */
public class RigaOrdine {

	/** L'identificatore dell'ordine. */ 
	private int idOrdine; 
	
	/** Il numero della riga d'ordine (da 0 in poi). */ 
	private int numeroRiga; 
	
	/** Il prodotto a cui la riga d'ordine si riferisce. */ 
	private String prodotto; 
	
	/** Crea una nuova riga d'ordine. */ 
	public RigaOrdine(int idOrdine, int numeroRiga, String prodotto) {
		this.idOrdine = idOrdine; 
		this.numeroRiga = numeroRiga; 
		this.prodotto = prodotto; 
	}
	
	public int getNumeroRiga() {
		return numeroRiga;
	}

	public void setNumeroRiga(int numeroRiga) {
		this.numeroRiga = numeroRiga;
	}

	public void setIdOrdine(int idOrdine) {
		this.idOrdine = idOrdine;
	}



	public void setProdotto(String prodotto) {
		this.prodotto = prodotto;
	}



	/** Restituisce una descrizione testuale di questa riga d'ordine, nella forma: 
	 * <id ordine> <numero riga> <prodotto> */
	public String toString() {
		String desc = "";
		desc += idOrdine + " "; 
		desc += numeroRiga + " "; 
		desc += prodotto + " "; 
		return desc; 
	}

	/** Crea una nuova riga d'ordine, a partire da una descrizione testuale, nella forma: 
	 *  <id ordine> <numero riga> <prodotto> */
	public RigaOrdine(String desc) {
		Scanner in = new Scanner(desc); 
		this.idOrdine = in.nextInt(); 
		this.numeroRiga = in.nextInt(); 
		this.prodotto = in.next(); 
		in.close();
	}

	public String getProdotto() {
		return this.prodotto;
	}

	public Integer getIdOrdine() {
		return this.idOrdine;
	}

	
}
