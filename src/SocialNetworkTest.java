import java.util.*;
import java.io.File;

public class SocialNetworkTest {
	
	public static Scanner input = new Scanner(System.in);
	public static int choice = -1;
	public static String nome, cognome, mail;

	public static void main(String args[]) {
		ArrayList<User> usr = new ArrayList<User>();
		usr.add(new UserImpl("Laura","Bussi","laurabussi@live.it",new Vector<String>()));
		usr.add(new UserImpl("Totta","Barta","tottini@gmail.com",new Vector<String>()));
		usr.add(new UserImpl("Francesco","Minucci","ciuni@gmail.com",new Vector<String>()));
		usr.add(new UserImpl("Luca","Dido","dido@alice.com",new Vector<String>()));
		usr.add(new UserImpl("Alessandro","Carpi","carpi@alice.com",new Vector<String>()));
		usr.add(new UserImpl("Cassandra","Machetti","cassandra@live.it",new Vector<String>()));
		
		SocialNetwork snet = new SocialNetworkImpl(usr);

		try {
			snet.logIn("laurabussi@live.it");
			snet.addFriend(usr.get(1).getKey());
			snet.addFriend(usr.get(4).getKey());
			snet.addFriend(usr.get(5).getKey());
			snet.logIn("tottini@gmail.com");
			snet.addFriend(usr.get(2).getKey());
			snet.addFriend(usr.get(4).getKey());
			snet.logIn("ciuni@gmail.com");
			snet.addFriend(usr.get(3).getKey());
		} catch(Exception e) {
			e.printStackTrace();
		}

		mMenu(snet); //chiama il menu principale
	}

	public static void mMenu(SocialNetwork sn) {
		/*menu principale per la registrazione e l'accesso*/
		while(choice!=0) {
			System.out.println("1.Registrati\n2.Accedi\n0.Esci");
			choice = input.nextInt();
			input.nextLine();
			switch(choice) {
				case 1: { 
					System.out.println("Nome:");
					nome = input.nextLine();
					System.out.println("Cognome:");
					cognome = input.nextLine();
					System.out.println("Mail:");
					mail = input.nextLine();
					try {
						sn.signIn(nome,cognome,mail);
					} catch(Exception e) { //eccezione i/o o utente non registrato
						e.printStackTrace();
					}
				}
				break; //dopo la registrazione torna al menu principale
				case 2: {
					System.out.println("E-mail:");
					mail = input.nextLine();
					try {
						sn.logIn(mail);
					} catch(NodeNotFoundException e) {
						System.out.println("Utente non registrato");
						break;
					}
					uMenu(sn); //apri il menu utente
				}
				break;
				case 0: return;
				default: System.out.println("Scelta non valida");
				break;
			}
		}
	}

	public static void uMenu(SocialNetwork sn) {
		/*menu utente: permette di visualizzare il profilo o accedere ai sottomenu*/
		int choice = -1;
		while(choice!=0) {
			try {
				System.out.println("Il diametro della tua rete di contatti è: " + sn.diameter());
			} catch(Exception e) {
				System.out.println("Impossibile calcolare il diametro");
			}
			System.out.println("1.Modifica Profilo\n2.Vedi profilo\n3.Gestisci amici\n4.Messaggi\n0.Esci dall'account");
			choice = input.nextInt();
			input.nextLine();
			switch(choice) {
				case 1: pMenu(sn); //gestisci profilo
				break;
				case 2: System.out.println(sn.profile()); //vedi profilo
				break;
				case 3: fMenu(sn); //gestisci amici
				break;
				case 4: msgMenu(sn); //messaggi
				break;
				case 0: return;
				default: System.out.println("Scelta non valida");
				break;
			}
		}
	}

	public static void pMenu(SocialNetwork sn) {
		/*menu per la gestione del profilo*/
		int choice = -1;
		while(choice!=0) {
			System.out.println("1.Modifica nome\n2.Modifica cognome\n0.Indietro");
			choice = input.nextInt();
			input.nextLine();
			switch(choice) {
				case 1: System.out.println("Nuovo nome:"); //modifica nome
					nome = input.nextLine();
					sn.changeName(nome);
				break;
				case 2: System.out.println("Nuovo cognome:"); //modifica cognome
					cognome = input.nextLine();
					sn.changeSurname(cognome);
				break;
				case 0: return;
				default: System.out.println("Scelta non valida");
				break;
			}
		}
	}

	public static void fMenu(SocialNetwork sn) {
		/*menu per la gestione degli amici*/
		int choice = -1;
		while(choice!=0) {
			System.out.println("1.Aggiungi amico\n2.Rimuovi amico\n3.Lista amici\n0.Indietro");
			choice = input.nextInt();
			input.nextLine();
			switch(choice) {
				case 1: System.out.println("E-mail amico:"); //aggiungi amico
					mail = input.nextLine();
					try {
						sn.addFriend(mail);
					} catch(Exception e) {
						System.out.println("Utente non trovato");
					}
				break;
				case 2: System.out.println("E-mail amico:"); //rimuovi amico
					mail = input.nextLine();
					try {
						sn.removeFriend(mail);
					} catch(Exception e) {
						System.out.println("Utente non trovato");
					}
				break;
				case 3: try { //vedi lista amici
						Vector<User> myfriends = sn.friendsList();
						for(int i=0;i<myfriends.size();i++)
							System.out.println(myfriends.get(i));
					} catch(Exception e) {
						System.out.println("Impossibile stampare");
					}
				break;
				case 0: return;
				default: System.out.println("Scelta non valida");
				break;
			}
		}
	}

	public static void msgMenu(SocialNetwork sn) {
		/*menu per la gestione dei messaggi*/
		int choice = -1;
		String msg = new String("Vuoto");
		while(choice!=0) {
			System.out.println("1.Invia un messaggio\n2.Casella di posta\n0.Indietro");
			choice = input.nextInt();
			input.nextLine();
			switch(choice) {
				case 1: System.out.println("Mail destinatario:"); //invia messaggio
					mail = input.nextLine();
					System.out.println("Inserisci il messaggio:");
					msg = input.nextLine();
					try {
						sn.sendMsg(mail,msg);
					} catch(Exception e) {
						System.out.println("Utente non trovato");
					}
				break;
				case 2: System.out.println("I tuoi messaggi:"); //vedi posta in arrivo
					Vector<String> m_b = sn.myMsg();
					for(int i=0;i<m_b.size();i++)
						System.out.println(m_b.get(i));
				break;
				case 0: return;
				default: System.out.println("Scelta non valida");
			}
		}
	}
}
