import java.util.*;

public class GraphImpl3<E> implements Graph3<E> {
	//overview: un grafo è una struttura modificabile composta di vertici collegati da archi.
	//Per rappresentarlo uso un vector contenente i nodi e uno contenente le etichette dei nodi.

	private class Node {
		//overview: implementa un nodo del grafo. Il campo elem rappresenta l'oggetto
		//di tipo E contenuto in this, adj la lista dei nodi connessi a this.
		private E elem;
		private ArrayList<E> adj;
		//AF: <elem,ADJ> con ADJ = {adj.get(0),...,adj.get(adj.size()-1)}
		//IR: elem!=null && adj!=null &&, per 0<=i<adj.size(), adj.get(i)!=null.
		
		public Node(E e) {
			elem = e;
			adj = new ArrayList<E>();
		}
		//effects: inizializza il nodo contenente l'oggetto e.

		private E getElem() {
			return elem;
		}
		//effects: restituisce il contenuto di this.

		private ArrayList<E> getAdj() {
			return adj;
		}
		//effects: restituisce la lista di adiacenza di this.
	}

	private Vector<Integer> labels;	
	private Vector<Node> nodes;
	//AF: <nodes.get(i),labels.get(i)> | 0<=i<nodes.size() && labels.get(i)==elem.hashCode(), con
	//elem = nodes.get(i).getElem()
	//IR: nodes!=null && labels!=null && nodes.size()==labels.size();
	//Per 0<=i,j<nodes.size(), i!=j, labels.get(i)!=labels.get(j) ==>
	//nodes.get(i).getElem()!=nodes.get(j).getElem();
	//Per 0<=i,j<nodes.size(), i!=j, se nodes.get(i) e nodes.get(j) sono adiacenti, allora
	//adj(i).indexOf(nodes.get(j).getElem())!=-1 && adj(j).indexOf(nodes.get(i).getElem())!=-1,
	//con adj(i)==nodes.get(i).getAdj() && adj(j)==nodes.get(j).getAdj();

	public GraphImpl3() {
		labels = new Vector<Integer>();
		nodes = new Vector<Node>();
	}
	//effects: inizializza il grafo vuoto.

	public GraphImpl3(Collection<E> coll) throws NullPointerException {
		this();

		if(coll==null) throw new NullPointerException();

		List<E> tmp = new ArrayList<E>(coll);
		Iterator<E> it = tmp.iterator();

		for(E e : tmp) {
			addNode(e);
		}
	}
	//effects: inizializza il grafo e, se coll!=null, per 0<=i<coll.size() inserisce un oggetto
	//di coll in this. Il grafo risultante è inizialmente sparso.
	//throws: NullPointerException (unchecked).

	public int vSize() {
		return nodes.size();
	}
	//effects: restituisce la lunghezza di nodes.

	public boolean member(E elem) throws NullPointerException {
		if(elem==null) throw new NullPointerException();

		for(int i=0;i<vSize();i++)
			if(elem.hashCode()==(labels.get(i).intValue())) {
				return true;
			}
		return false;
	}
	//effects: se elem!=null, restituisce true se esiste i tc nodes.get(i)getElem().equals(elem),
	//altrimenti false.
	//throws: NullPointerException (unchecked).

	public void addNode(E elem) throws NullPointerException, IllegalArgumentException {
		if(elem==null) throw new NullPointerException();
		if(member(elem)) throw new IllegalArgumentException();

		Node tmp = new Node(elem);
		Integer l = elem.hashCode();
		nodes.add(tmp);
		labels.add(l);
	}
	//modifies: this.
	//effects: se elem!=null && !member(elem) aggiunge a nodes un vertice contenente elem.
	//L'assenza di duplicati nel grafo deriva dalla correttezza del metodo hashCode() 
	//usato in member(elem).
	//throws: NullPointerException (unchecked), IllegalArgumentException (unchecked).

	public void addEdge(E e1, E e2) throws NullPointerException, IllegalArgumentException, NodeNotFoundException {
		if(e1==null || e2==null) throw new NullPointerException();
		if(!member(e1) || !member(e2)) throw new NodeNotFoundException();
		if(nextTo(e1,e2)) throw new IllegalArgumentException();

		int i=labels.indexOf(e1.hashCode());
		int j=labels.indexOf(e2.hashCode());

		(nodes.get(i)).getAdj().add(e2);
		(nodes.get(j)).getAdj().add(e1);
	}
	//modifies: this.
	//effects: se e1!=null && e2!=null && member(e1)==member(e2)==true, collega con un arco
	//i nodi contenenti e1 ed e2. Poiché il grafo non è orientato, aggiunge e1 alla adj(e2) e
	//e2 alla adj(e1).
	//throws: NullPointerException (unchecked), NodeNotFoundException (checked).

	public void removeNode(E elem) throws NullPointerException, NodeNotFoundException {
		if(elem==null) throw new NullPointerException();
		if(!member(elem)) throw new NodeNotFoundException();

		int i=labels.indexOf(elem.hashCode());

		for(int j=0;j<vSize();j++)
			if(j!=i && nextTo(nodes.get(j).getElem(),elem))
				removeEdge(nodes.get(j).getElem(),elem);

		labels.remove(i);
		nodes.remove(i);
	}
	//modifies: this.
	//effects: se elem!=null && member(elem)==true, rimuove il nodo contenente elem da this
	//assieme a tutti gli archi ad esso collegati.
	//throws: NullPointerException (unchecked), NodeNotFoundException (checked).

	public void removeEdge(E e1, E e2) throws NullPointerException, NodeNotFoundException {
		if(e1==null || e2==null) throw new NullPointerException();
		if(!member(e1) || !member(e2) || !nextTo(e1,e2)) 
			throw new NodeNotFoundException();

		int i=labels.indexOf(e1.hashCode());
		int j=labels.indexOf(e2.hashCode());
		nodes.get(i).getAdj().remove(e2);
		nodes.get(j).getAdj().remove(e1);
	}
	//modifies: this.
	//effects: se e1!=null && e2!=null && nextTo(e1,e2)==true, rimuove l'arco che collega e1
	//ed e2.
	//throws: NullPointerException (unchecked), NodeNotFoundException (checked).

	public boolean nextTo(E e1, E e2) throws NullPointerException, NodeNotFoundException {
		if(e1==null || e2==null) throw new NullPointerException();
		if(!member(e1) || !member(e2)) throw new NodeNotFoundException();

		int i = labels.indexOf(e1.hashCode());
		int j = labels.indexOf(e2.hashCode());
		return nodes.get(i).getAdj().indexOf(e2)!=-1 && nodes.get(j).getAdj().indexOf(e1)!=-1;
	}
	//effects: se e1!=null && e2!=null && member(e1)==member(e2)==true, restituisce true
	//se e2 è adiacente a e1; false altrimenti.
	//throws: NullPointerException (unchecked), NodeNotFoundException (checked).

	public int shortestPath(E e1, E e2) throws NullPointerException, NodeNotFoundException {
		if(e1==null || e2==null) throw new NullPointerException();
		if(!member(e1) || !member(e2)) throw new NodeNotFoundException();

		if(e1.equals(e2)) return 0;
		int dim = vSize();

		String color[] = new String[dim];
		int dist[] = new int[dim];
		Queue<E> q = new ArrayDeque<E>();

		for(int i=0;i<dim;i++) {
			color[i] = "White";
			dist[i] = -1;
		}

		int i = labels.indexOf(e1.hashCode());
		q.add(nodes.get(i).getElem());
		color[i] = "Grey";
		dist[i] = 0;
		E node = null;

		while(q.peek()!=null) { //finché la coda non è vuota
			node = q.poll();
			for(i=0;i<dim;i++) {
				if(nextTo(node,nodes.get(i).getElem()) && color[i]=="White") {
					color[i] = "Grey";
					dist[i] = dist[labels.indexOf(node.hashCode())] + 1;
					q.add(nodes.get(i).getElem());
				}
			}
			color[labels.indexOf(node.hashCode())] = "Black";
		}
		return dist[labels.indexOf(e2.hashCode())];
	}
	//effects: se e1!=null && e2!=null && member(e1)==member(e2)==true restituisce la lunghezza
	//del cammino minimo da e1 a e2. Se non esiste un cammino da e1 a e2 restituisce -1.
	//throws: NullPointerException (unchecked), NodeNotFoundException (checked).

	public int diameter() throws NodeNotFoundException {
		if(vSize()==0) return -1;
		int diam=0;

		for(int i=0;i<vSize();i++)
			for(int j=i+1;j<vSize();j++) {
				int tmp=shortestPath(nodes.get(i).getElem(),nodes.get(j).getElem());
				if(tmp>diam) diam=tmp;
			}
		return diam;
	}
	//effects: restituisce il cammino tra i due elementi più distanti in G, -1 se G è vuoto.

	public Iterator<E> iterator() {
		return new GraphIterator(this);
	}
	//effects: restituisce un iteratore per i nodi di this.

	private class GraphIterator implements Iterator<E> {
		Graph3<E> g1 = new GraphImpl3<E>();
		int index;

		GraphIterator(Graph3<E> g) {
			g1 = g;
			index = 0;
		}

		public boolean hasNext() {
			return index<g1.vSize();
		}

		public E next() {
			return nodes.get(index++).getElem();
		}

		public void remove() {
			throw new UnsupportedOperationException("remove");
		}
	}
}
