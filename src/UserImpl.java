import java.util.*;

public class UserImpl implements User {
	//overview: classe modificabile che implementa l'interfaccia User.
	
	private String key;
	private String name;
	private String surname;
	private Vector<String> msg_box;
	//AF: <name,surname,email,MB> con MB = {msg_box.get(0),...,msg_box.get(msg_box.size()-1)}
	//IR: name!=null && surname!=null && email!=null && msg_box!=null && msg_box.get(i)!=null
	//per 0<=i<msg_box.size() && il campo email non è modificabile.

	public UserImpl(String n, String s, String e, Vector<String> m) throws NullPointerException {
		if(n==null || s==null || e==null) throw new NullPointerException();
		name = n;
		surname = s;
		key = e;
		msg_box = new Vector<String>(m);
	}
	//effects: se n!=null && s!=null && e!=null crea un nuovo utente con i dati passati come parametro.
	//throws: NullPointerException (unchecked).

	public String getKey() {
		String s = new String(key);
		return s;
	}
	//effects: restituisce il campo email dell'utente.

	public void updateName(String n) throws NullPointerException {
		if(n==null) throw new NullPointerException();
		name = n;
	}
	//modifies: this.name
	//effects: se n!=null aggiorna ad n il campo nome dell'utente.
	//throws: NullPointerException (unchecked)

	public void updateSurname(String s) throws NullPointerException {
		if(s==null) throw new NullPointerException();
		surname = s;
	}
	//modifies: this.surname
	//effects: se s!=null aggiorna ad s il campo cognome dell'utente.
	//throws: NullPointerException (unchecked).

	public void getMsg(String s) throws NullPointerException {
		if(s==null) throw new NullPointerException();
		msg_box.add(new String(s));
	}
	//modifies: this.msg_box.
	//effects: se s!=null aggiunge s a msg_box.
	//throws: NullPointerException (unchecked).

	public Vector<String> viewMsg() {
		Vector<String> msgcpy = new Vector<String>();
		for(int i=0;i<msg_box.size();i++)
			msgcpy.add(new String(msg_box.get(i)));
		return msgcpy;
	}
	//effects: restituisce una copia di msg_box.

	public User copy() {
		Vector<String> msgcpy = viewMsg();
		User copy = new UserImpl(name,surname,key,msgcpy);
		
		return copy;
	}
	//effects: restituisce una copia di this.

	public String toString() {
		return (name + " " + surname + " " + key);
	}
	//effects: restituisce le informazioni contenute in this in formato stringa.

	public boolean equals(User u) throws NullPointerException {
		if(u==null) throw new NullPointerException();
		return key.equals(u.getKey());
	}
	//effects: se u!=null, restituisce true se this.key è uguale ad e.key, false altrimenti.
	//throws: NullPointerException (unchecked).
}
