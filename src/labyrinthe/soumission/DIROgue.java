package labyrinthe.soumission;

import labyrinthe.code_squelette.*;
import labyrinthe.rencontres.*;

import java.util.Arrays;

import static java.lang.Integer.parseInt;

/**
 * *Contient une méthode main pour exécuter le programme.
 */
public class DIROgue {
	/**
	 *  Méthode main responsable de l'éxecution du programme.
	 * @param args
	 */
	public static void main(String[] args) {
		Exterieur lExterieur = Exterieur.getExterieur();
		MonLabyrinthe labyrinthe = new MonLabyrinthe();
		MonAventure aventure =new MonAventure(labyrinthe);

		labyrinthe.t=labyrinthe.ajoutTab(labyrinthe.getPieces(), lExterieur);
		java.util.Scanner scanner = new java.util.Scanner(System.in);
		String commande = scanner.nextLine();
		int i = 1;
		Piece[][] tab1 = new Piece[50][];
			Boolean cond ;
			cond=((commande.equals("piece " + Integer.toString(i) + " monstre")) || (commande.equals("piece " + Integer.toString(i) + " rien"))
					|| (commande.equals("piece " + Integer.toString(i) + " boss")) || (commande.equals("piece " + Integer.toString(i) + " tresor")));
			if (cond) {
				while (((commande.equals("piece " + Integer.toString(i) + " monstre")) || (commande.equals("piece " + Integer.toString(i) + " rien"))
						|| (commande.equals("piece " + Integer.toString(i) + " boss")) || (commande.equals("piece " + Integer.toString(i) + " tresor")) && (i <= 50))) {
					RencontreType type = RencontreType.valueOf((commande.substring(commande.indexOf(Integer.toString(i)) + 2, commande.length())).toUpperCase());
					labyrinthe.t = labyrinthe.ajoutTab(labyrinthe.t, new Piece(i, type));
					i += 1;
					commande = scanner.nextLine();
				}
				if (commande.equals("CORRIDORS")) {
					commande = scanner.nextLine();
					int x=0;
					int y=0;
					String cmd = commande.substring(commande.indexOf(" ") + 1);
					if (isNumeric(cmd.substring(0, cmd.indexOf(" "))) && isNumeric(cmd.substring(cmd.indexOf(" ") + 1))){
						x = parseInt(cmd.substring(0, cmd.indexOf(" ")));
						y = parseInt(cmd.substring(cmd.indexOf(" ") + 1));
						cond=(("corridor".equals(commande.substring(0, commande.indexOf(" "))))
								&& (x < i))
								&& (y < i);
					}
					else {
						cond=false;
					}
				if (cond) {
					while (!(commande.equals("FIN")) && ("corridor".equals(commande.substring(0, commande.indexOf(" "))))
							&& (x < i)
							&& (y < i)){

						if (x == 0) {
							labyrinthe.ajouteEntree(lExterieur, labyrinthe.getPieces()[y]);
						} else if (y == 0) {
							labyrinthe.ajouteEntree(lExterieur, labyrinthe.getPieces()[x]);
						} else {
							labyrinthe.ajouteCorridor(labyrinthe.getPieces()[x], labyrinthe.getPieces()[y]);
						}

						commande = scanner.nextLine();
						if ((commande.equals("FIN")) || !("corridor".equals(commande.substring(0, commande.indexOf(" "))))) {
							break;
						}
						cmd = commande.substring(commande.indexOf(" ") + 1);
						if (isNumeric(cmd.substring(0, cmd.indexOf(" "))) && isNumeric(cmd.substring(cmd.indexOf(" ") + 1))){
							x = parseInt(cmd.substring(0, cmd.indexOf(" ")));
							y = parseInt(cmd.substring(cmd.indexOf(" ") + 1));
						}
						else{
							cond=false;
							break;
						}



					}
					if (commande.equals("FIN")) {
						System.out.println(genererRapport(aventure));
						System.out.println(genererScenario(aventure));
					} else {
						System.out.print("Erreur,Commande Invalide");
					}
				}
				else {
					System.out.print("Erreur,Commande Invalide");
				}
				}
				else {
					if (commande.equals("FIN")) {
						System.out.println(genererRapport(aventure));
						System.out.println(genererScenario(aventure));
					}
					else {
						System.out.print("Erreur,Commande Invalide");
					}
				}
			}
			else {

				System.out.print("Erreur,Commande Invalide");
			}
	}

	/**
	 * Retourne le rapport de l’aventure
	 * @param a Aventure
	 * @return Chaine de Caractères
	 */
	public static String genererRapport(Aventure a) {
		a = new MonAventure(a.getLabyrinthe());
		Labyrinthe l=a.getLabyrinthe();
		String rapport = "Donjon avec " +l.nombreDePieces() + " pieces";
		Piece[] tab =new Piece[l.nombreDePieces()];
		int k=0;
		for (int j = 0; j < l.nombreDePieces(); j++) {
			if (!(existe(l.getPieces()[j],tab ))){
				tab[k]=l.getPieces()[j];
				k+=1;
			}
		}
		tab=eliminernull(tab);
		for (int j = 0; j < tab.length; j++){
			rapport=rapport+"\n"+tab[j]+" : "+Arrays.toString(l.getPiecesConnectees(tab[j]));
		}
		if (a.estPacifique()){
			rapport+="\n"+"pacifique";
		}
		else {
			rapport+="\n"+"non pacifique";
		}
		if (a.contientBoss()){
			rapport+="\n"+"Contient un boss.";
		}
		else {
			rapport+="\n"+"Ne contient pas un boss.";
		}
		if (a.contientDuTresor()){
			if (a.getTresorTotal()==1){
				rapport+="\n"+"Contient "+a.getTresorTotal()+" tresor.";
			}
			else {
				rapport+="\n"+"Contient "+a.getTresorTotal()+" tresors.";
			}
		}
		else {
			rapport+="\n"+"Ne contient pas des tresors.";
		}
		rapport+="\n"+"Chemin jusqu’au boss :"+"\n"+toutLeselements(a.cheminJusquAuBoss());

		return rapport;
	}

	/**
	 * Retourne le scenario de l’aventure
	 * @param a Aventure
	 * @return Chaine de Caractères
	 */
	public static String genererScenario(Aventure a) {
		a = new MonAventure(a.getLabyrinthe());
		Labyrinthe l=a.getLabyrinthe();
		String scenario="Scenario"+"\n";
		for (int i=0;i<a.cheminJusquAuBoss().length;i++){
			if (a.cheminJusquAuBoss()[i].getRencontreType()==RencontreType.MONSTRE){
				Monstre m = Monstre.choix();
				scenario+=m.Rencontrer()+"\n";
			}
			else if (a.cheminJusquAuBoss()[i].getRencontreType()==RencontreType.TRESOR){
				Tresor t = Tresor.choix();
				scenario+=t.Rencontrer()+"\n";
			}
			else if (a.cheminJusquAuBoss()[i].getRencontreType()==RencontreType.RIEN){
				Rien rien=new Rien();
				scenario+=rien.Rencontrer()+"\n";
			} else if (a.cheminJusquAuBoss()[i].getRencontreType()==RencontreType.BOSS) {
				Monstre m=new Boss();
				scenario+=m.Rencontrer()+"\n";}


		}


		return scenario;
	}

	/**
	 * Retourne vrai si et seulement si la Piece fait partie du Labyrinthe(Piece avec le meme id)
	 * @param a Une piece
	 * @param tab Un tableau de pieces
	 * @return True si la piece e appartient au tableau ;false sinon
	 */
	public static boolean existe(Piece a, Piece[] tab) {
		boolean test = false;
		for (int i = 0; i < tab.length; i++) {
			if (tab[i]!=null) {
				if (a.getID() == tab[i].getID()) {
					test = true;
				}
			}
		}
		return test;
	}

	/**
	 * Retourne un tableau de pieces réduit des valeurs null
	 * @param tab Tableau de pieces
	 * @return Tableau de pieces sans aucune valeur null
	 */
	public static Piece[] eliminernull(Piece[] tab){
		int c=0;
		for (int i = 0; i < tab.length; i++){
			if (tab[i]==null){
				c+=1;
			}
		}
		if (c!=0) {
			Piece[] temp = new Piece[tab.length - c];
			for (int i = 0; i < tab.length-c; i++) {
				temp[i] = tab[i];
			}
			return temp;
		}
		else {
			return tab;
		}
	}

	/**
	 * Retoune tout les elements (pieces) d'un tableau sous forme d'une chaine de caractères(élément par ligne)
	 * @param tab tableau de pieces
	 *
	 * @return Chaine de caractères
	 */
	public static String toutLeselements(Piece[] tab){
		String ch ="";
		for (int i=0;i<tab.length;i++){
			ch+=tab[i]+"\n";
		}

		return ch;
	}

	/**
	 * Retourne vrai si et seulement la chaine est numérique
	 * @param ch Chaine de caracteres
	 * @return true si la chaine est numérique;false sinon
	 */
	public static boolean isNumeric(String ch){
		boolean test=true;
		char character;
		for(int i=0;i<ch.length();i++){
			if (!(ch.charAt(i)>='0' && ch.charAt(i)<='9')){
				test=false;
			}
		}
		return test;
	}
}
