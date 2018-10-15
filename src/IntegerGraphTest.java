import java.util.*;

public class IntegerGraphTest {
	public static Scanner input = new Scanner(System.in);
	
	public static void main(String args[]) {
		Integer tmp;
		List<Integer> lst = new ArrayList<Integer>();
		for(int i=0;i<10;i++) { //inserisce dei nodi numerati da 1 a 10 nel grafo
			tmp = new Integer(new Integer(i+1));
			lst.add(tmp);
		}

		Graph3<Integer> g = new GraphImpl3<Integer>(lst);
		try {
			g.addEdge(lst.get(0),lst.get(3)); //arco da 1 a 4
		} catch(Exception e) {
			e.printStackTrace();
		} try {
			g.addEdge(lst.get(3),lst.get(4)); //arco da 4 a 5
		} catch(Exception e) {
			System.out.println("Nodo non trovato");
		} try {
			g.addEdge(lst.get(6),lst.get(2)); //arco da 7 a 3
		} catch(Exception e) {
			System.out.println("Nodo non trovato");
		} try {
			g.addEdge(lst.get(4),lst.get(9)); //arco da 5 a 10
		} catch(Exception e) {
			System.out.println("Nodo non trovato");
		}

		try {
			System.out.println("1 e 4 sono adiacenti: " + g.nextTo(lst.get(0),lst.get(3)));
		} catch(Exception e) {
			System.out.println("Nodo non trovato");
		}

		Integer new_entry = new Integer(11);
		System.out.println("L'elemento 11 è nella lista: " + g.member(new_entry));

		g.addNode(new Integer(11)); //aggiunge un nodo "11"
		try{
			g.addNode(new Integer(11)); //prova ad aggiungere di nuovo "11"
		} catch(Exception e) {
			System.out.println("Nodo già presente");
		}

		System.out.println("L'elemento 1 è nella lista: " + g.member(lst.get(0)));

		System.out.println("La dimensione del grafo è " + g.vSize());

		try {
			g.removeNode(lst.get(5)); //rimuove il nodo "6"
		} catch(Exception e) {
			System.out.println("Nodo non trovato");
		}
		try {
			g.removeNode(lst.get(5)); //prova a rimuovere di nuovo "6"
		} catch(Exception e) {
			System.out.println("Nodo non trovato");
		}
		try {
			System.out.println("Il diametro del grafo è " + g.diameter());
		} catch(Exception e) {
			System.out.println("Impossibile calcolare il diametro");
		}
	}
}
