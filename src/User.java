import java.util.*;

public interface User {
	//overview: tipo modificabile che implementa l'utente di un social network.
	//typical element: un utente è una quadrupla <name,surname,email,msg_box>, dove msg_box è un 
	//contenitore di posta.

	public String getKey();
	//effects: restituisce il campo email dell'utente.

	public void updateName(String n) throws NullPointerException;
	//modifies: this.name
	//effects: se n!=null modifica il campo nome dell'utente.
	//throws: NullPointerException (unchecked)

	public void updateSurname(String s) throws NullPointerException;
	//modifies: this.surname
	//effects: se s!=null modifica il campo cognome dell'utente.
	//throws: NullPointerException (unchecked)

	public void getMsg(String s) throws NullPointerException;
	//modifies: this.msg_box
	//effects: se s!= null aggiorna la casella di posta dell'utente con il messaggio s.
	//throws: NullPointerException (unchecked)

	public Vector<String> viewMsg();
	//effects: visualizza i messaggi ricevuti dall'utente.

	public User copy();
	//effects: restituisce una copia di this.

	public String toString();
	//effects: restituisce le informazioni contenute in this in formato stringa.

	public boolean equals(User u) throws NullPointerException;
	//effects: se u!= null restituisce true se this è uguale ad e, false altrimenti.
	//throws: NullPointerException (unchecked).
}
