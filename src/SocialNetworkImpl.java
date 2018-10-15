import java.util.*;

public class SocialNetworkImpl implements SocialNetwork {
	//overview: SocialNetwork implementa un grafo di oggetti di tipo User, con alcune funzionalità
	//utili a gestire una rete sociale.
	
	private Graph3<User> snet;
	private String logged;
	//AF: Graph3<User>, dove i nodi del grafo rappresentano gli utenti del social network e
	//il campo mail dell'utente viene usato come chiave primaria.
	//IR: snet!=null && , per 0<=i<snet.vSize(), se l'utente u(i) è iscritto al social network,
	//allora getByKey(s)!=null, dove s==u_i.getKey(). Inoltre, per 0<=i<snet.vSize() e i!=j,
	//se u(i) e u(j) sono iscritti al social network, deve valere u(i).getKey()!=u(j).getKey().
	
	public SocialNetworkImpl() {
		snet = new GraphImpl3<User>();
	}
	//effects: inizializza una rete sociale, inizialmente vuota.

	public SocialNetworkImpl(Collection<User> coll) {
		snet = new GraphImpl3<User>(coll);
	}
	//effects: inizializza una rete sociale a partire dalla collezione di utenti coll, tra
	//cui non esistono inizialmente vincoli di amicizia.

	public void signIn(String n, String s, String e) throws NullPointerException, IllegalArgumentException {
		if(n==null || s==null || e==null) throw new NullPointerException();
		if(getByKey(e)!=null) throw new IllegalArgumentException();

		Vector<String> v = new Vector<String>();
		User new_user = new UserImpl(n,s,e,v);
		snet.addNode(new_user);
	}
	//effects: se s!=null && n!=null && e!=null, registra un nuovo utente con i dati passati come 
	//parametro, se la mail e non risulta essere già in uso.
	//throws: NullPointerException(), IllegalArgumentException().

	public void logIn(String e) throws NullPointerException, NodeNotFoundException {
		if(e==null) throw new NullPointerException();
		if(getByKey(e)==null) throw new NodeNotFoundException();

		logged = e;
	}
	//effects: se e!=null, permette all'utente con email==e di accedere al social network, purché
	//questi sia già registrato.
	//throws: NullPointerException, NodeNotFoundException.

	public String profile() {
		return getByKey(logged).toString();
	}
	//effects: restituisce le informazioni personali dell'utente loggato in formato stringa.

	public void changeName(String n) {
		getByKey(logged).updateName(n);
	}
	//effects: se n!=null, modifica il campo nome dell'utente loggato.

	public void changeSurname(String s) {
		getByKey(logged).updateSurname(s);
	}
	//effects: se s!=null, modifica il campo cognome dell'utente loggato.

	private User getByKey(String k) throws NullPointerException {
		if(k==null) throw new NullPointerException();

		Iterator<User> it = snet.iterator();
		
		for(User u : snet) {
			if(u.getKey().equals(k))
				return u;
		}
		return null;
	}
	//effects: se k!=null restituisce l'utente avente chiave k. Se nessun utente ha chiave
	//k restituisce null.
	//throws: NullPointerException (unchecked).

	public void addFriend(String s) throws NodeNotFoundException {
		snet.addEdge(getByKey(logged),getByKey(s));
	}
	//effects: se s!=null && s è la mail di un utente registrato, aggiunge l'utente alla lista 
	//degli amici dell'utente chiamante.

	public void removeFriend(String s) throws NodeNotFoundException {
		snet.removeEdge(getByKey(logged),getByKey(s));
	}
	//effects: se s!=null && s è la mail di un utente amico, rimuove l'utente con mail==s dalla
	//lista degli amici dell'utente chiamante.

	public void sendMsg(String s, String msg) throws NullPointerException, NodeNotFoundException {
		if(s==null || msg==null) throw new NullPointerException();

		User u = getByKey(s);
		if(u==null) throw new NodeNotFoundException();

		u.getMsg(msg + " " + logged);
	}
	//effects: se s!=null && msg!=null && s è la mail di un utente registrato, invia il messaggio
	//s all'utente u da parte dell'utente chiamante.
	//throws: NullPointerException, NodeNotFoundException.

	public Vector<String> myMsg() {
		return getByKey(logged).viewMsg();
	}
	//effects: restituisce la casella di posta dell'utente.

	public Vector<User> usersList() {
		Vector<User> nodes = new Vector<User>();
		User tmp = null;
		Iterator<User> it = snet.iterator();

		for(User u : snet)
			nodes.add(u.copy());

		return nodes;
	}
	//effects: restituisce un vettore contenente gli utenti di this.

	public Vector<User> friendsList() throws NodeNotFoundException {
		Vector<User> linked = new Vector<User>();
		User tmp=null;
		
		Iterator<User> it = snet.iterator();

		for(User u : snet)
			if(snet.nextTo(getByKey(logged),u))
				linked.add(u.copy());
	
		return linked;
	}
	//effects: restituisce un vettore contenente la lista dei nodi u_i per cui vale
	//nextTo(getByKey(logged),u_i).

	public int distance(String s) throws NodeNotFoundException {
		return snet.shortestPath(getByKey(logged),getByKey(s))-1;
	}
	//effects: restituisce il numero di intermediari tra l'utente loggato e l'utente avente
	//email s, se s è la mail di un utente registrato.
	//throws: NodeNotFoundException.

	public int diameter() throws NodeNotFoundException {
		int diam = 0;
		int tmp = 0;
		Iterator<User> it = snet.iterator();
		
		for(User u : snet) {
			if(!getByKey(logged).equals(u))
				tmp = distance(u.getKey());
		if(tmp>diam)
			diam = tmp;
		}
		return diam;
	}
	//effects: restituisce un indice di compattezza della rete sociale di cui l'utente loggato
	//fa parte.
}
