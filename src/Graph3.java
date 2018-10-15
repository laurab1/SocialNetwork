import java.util.*;

public interface Graph3<E> extends Iterable<E> {
	//overview: tipo modificabile che rappresenta un grafo G i cui nodi contengono oggetti di
	//tipo E.
	//typical element: G=<V,E> | V è l'insieme dei nodi, E è l'insieme degli archi

	public int vSize();
	//effects: restituisce il numero di nodi di this.

	public boolean member(E elem) throws NullPointerException;
	//effects: restituisce true se esiste un nodo contenente e, altrimenti false.
	//throws: NullPointerException (unchecked).

	public void addNode(E elem) throws NullPointerException, IllegalArgumentException;
	//modifies: this.
	//effects: se elem!=null e elem non compare in this aggiunge un nodo (scollegato) 
	//contenente elem a this.
	//throws: NullPointerException, IllegalArgumentException (unchecked).
	
	public void addEdge(E e1, E e2) throws NullPointerException, IllegalArgumentException, NodeNotFoundException;
	//modifies: this.
	//effects: se e1!=null && e2!= null && e1,e2 sono contenuti in this, congiunge i due nodi.
	//throws: NullPointerException (unchecked), NodeNotFoundException (checked).

	public void removeNode(E elem) throws NullPointerException, NodeNotFoundException;
	//modifies: this.
	//effects: se elem!=null && elem appartiene a this, rimuove il nodo contenente elem da G e
	//contestualmente, tutti gli archi ad esso collegati.
	//throws: NullPointerException (unchecked), NodeNotFoundException (checked).

	public void removeEdge(E e1, E e2) throws NullPointerException, NodeNotFoundException;
	//modifies: this.
	//effects: se e1!=null && e2!=null && nextTo(e1,e2)==true && e1,e2 appartengono a this,
	//rimuove l'arco che collega e1 ed e2.
	//throws: NullPointerException (unchecked), NodeNotFoundException (checked).

	public boolean nextTo(E e1, E e2) throws NullPointerException, NodeNotFoundException;
	//effects: se e1!=null && e2!=null && e1,e2 appartengono a this && e2 è adiacente
	//a e1 restituisce true; se non lo è restituisce false.
	//throws: NullPointerException (unchecked), NodeNotFoundException (checked).

	public int shortestPath(E e1, E e2) throws NullPointerException, NodeNotFoundException;
	//effects: se e1!=null && e2!=null && e1,e2 appartengono a this && e2 è raggiungibile da e1,
	//restituisce il minimo numero di archi compresi tra e1 ed e2.
	//throws: NullPointerException (unchecked), NodeNotFoundException (checked).

	public int diameter() throws NodeNotFoundException;
	//effects: restituisce il cammino tra i due elementi più distanti in G; -1 se G è vuoto.
	
	public Iterator<E> iterator();
	//effects: restituisce un iteratore per gli elementi di this.
}
