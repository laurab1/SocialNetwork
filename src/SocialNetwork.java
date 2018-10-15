import java.util.*;

public interface SocialNetwork {
	//overview: SocialNetwork implementa una rete sociale.
	//typical element: <U,E> | U è un insieme di oggetti di tipo User, E è l'insieme delle
	//connessioni di amicizia.

	public void signIn(String n, String s, String e) throws NullPointerException, IllegalArgumentException;
	//effects: se n!=null && s!=null && e!=null, registra un nuovo utente con i dati passati 
	//come parametro.
	//throws: NullPointerException (unchecked), NodeNotFoundException (checked).

	public void logIn(String e) throws NullPointerException, NodeNotFoundException;
	//effects: se e!=null, permette all'utente con email==e di accedere al social network.
	//throws: NullPointerException (unchecked), NodeNotFoundException (checked).

	public String profile();
	//effects: restituisce le informazioni sull'utente loggato.

	public void changeName(String n);
	//effects: se n!= null, aggiorna il campo nome dell'utente loggato.

	public void changeSurname(String s);
	//effects: se s!= null aggiorna il campo cognome dell'utente loggato.

	public void addFriend(String s) throws NodeNotFoundException;
	//effects: se s!=null ed esiste un utente con mail==s, lo aggiunge u alla lista degli amici 
	//dell'utente loggato.
	//throws: NodeNotFoundException (checked).

	public void removeFriend(String s) throws NodeNotFoundException;
	//effects: se s!=null e l'utente u con mail==s ha una relazione di amicizia con l'utente loggato, 
	//rimuove u dalla lista degli amici dell'utente loggato.

	public void sendMsg(String s, String msg) throws NullPointerException, NodeNotFoundException;
	//effects: se s!=null ed esiste un utente con mail==s, invia un messaggio da parte dell'utente 
	//loggato.
	//throws: NullPointerException, UserNotFoundException.
	
	public Vector<String> myMsg();
	//effects: restituisce la casella di posta dell'utente.

	public Vector<User> usersList();
	//effects: restituisce la lista degli utenti del social network.

	public Vector<User> friendsList() throws NodeNotFoundException;
	//effects: restituisce la lista degli amici dell'utente chiamante u.
	
	public int distance(String s) throws NodeNotFoundException;
	//effects: restituisce il numero di intermediari tra l'utente loggato e l'utente avente
	//email s.
	//throws: NodeNotFoundException.

	public int diameter() throws NodeNotFoundException;
	//effects: restituisce un indice di compattezza della rete sociale di cui l'utente loggato
	//fa parte.
}
