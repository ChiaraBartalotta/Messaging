package asw.hw3.dominio;

import java.util.Scanner;

/** 
 * Un oggetto IntestazioneOrdine rappresenta l'intestazione di un ordine effettuato da un cliente. 
 * 
 * Si noti che si tratta di una rappresentazione AUTOCONTENUTA: 
 * un singolo oggetto IntestazioneOrdine rappresenta una intera intestazione d'ordine, senza 
 * referenziare oggetti Cliente (non ci sono). 
 */
public class IntestazioneOrdine {

	/** Il numero identificativo dell'ordine. 
	 * Se vale 0 vuol dire che non � stato assegnato. */
	private int idOrdine; 
	
	/** Il (nome del) cliente dell'ordine. */ 
	private String cliente;

	/** Numero delle righe d'ordine nell'ordine. */ 
	private int numeroRigheOrdine; 
	
	/** Crea una nuova intestazione d'ordine. */ 
	public IntestazioneOrdine(int idOrdine, String cliente, int numeroRigheOrdine) {
		this.idOrdine = idOrdine; 
		this.cliente = cliente; 
		this.numeroRigheOrdine = numeroRigheOrdine; 
	}
	
	
	
	public void setIdOrdine(int idOrdine) {
		this.idOrdine = idOrdine;
	}



	public void setCliente(String cliente) {
		this.cliente = cliente;
	}



	public void setNumeroRigheOrdine(int numeroRigheOrdine) {
		this.numeroRigheOrdine = numeroRigheOrdine;
	}



	/** Restituisce una descrizione testuale di questa intestazione d'ordine, nella forma: 
	 * <id ordine> <cliente> <numero righe> */
	public String toString() {
		String desc = "";
		desc += idOrdine + " "; 
		desc += cliente + " "; 
		desc += numeroRigheOrdine + " "; 
		return desc; 
	}

	/** Crea una nuova intestazione d'ordine, a partire da una descrizione testuale, nella forma: 
	 *  <id ordine> <cliente> <numero righe> */
	public IntestazioneOrdine(String desc) {
		Scanner in = new Scanner(desc); 
		this.idOrdine = in.nextInt(); 
		this.cliente = in.next(); 
		this.numeroRigheOrdine = in.nextInt(); 
		in.close();
	}

	public int getIdOrdine() {
		return this.idOrdine;
	}

	public String getCliente() {
		return this.cliente;
	}

	public int getNumeroRigheOrdine() {
		return this.numeroRigheOrdine;
	}
	
}
