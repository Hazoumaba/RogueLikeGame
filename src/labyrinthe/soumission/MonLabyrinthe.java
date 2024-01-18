package labyrinthe.soumission;

import labyrinthe.code_squelette.Exterieur;
import labyrinthe.code_squelette.Piece;

import java.util.Arrays;

public class MonLabyrinthe implements labyrinthe.code_squelette.Labyrinthe  {
    /**
     * On initialise la labyrinthe t avec 0 piece
     */
    public Piece[] t=new Piece[0];
    /**
     * On initialise la liste de corridors corridorListe de la labyrinthe avec 0 corridors
     */
    public Piece[][] corridorListe=new Piece[0][];
    @Override
    public Piece[] getPieces() {
        return t;
    }

    @Override
    public int nombreDePieces() {
        return t.length;
    }

    @Override
    public void ajouteEntree(Exterieur out, Piece e) {
        Piece[] tab;
        if (!find(getPieces(), out)){
            t=ajoutTab(t,out);
        }
        if (!find(getPieces(), e)){
            t=ajoutTab(t,e);
        }

        tab=nouveauCorridor(out,e);
        corridorListe=setListeCorridor(corridorListe,tab);
        tab=nouveauCorridor(e,out);
        corridorListe=setListeCorridor(corridorListe,tab);

    }

    @Override
    public void ajouteCorridor(Piece e1, Piece e2) {
        Piece[] tab=new Piece[2];
        if (!find(getPieces(), e1)){
            t=ajoutTab(t,e1);
        }
        if (!find(getPieces(), e2)){
            t=ajoutTab(t,e2);
        }
        tab=nouveauCorridor(e1,e2);
        corridorListe=setListeCorridor(corridorListe,tab);
        tab=nouveauCorridor(e2,e1);
        corridorListe=setListeCorridor(corridorListe,tab);
    }

    @Override
    public boolean existeCorridorEntre(Piece e1, Piece e2) {
        Piece[] tab=new Piece[2];
        tab=nouveauCorridor(e1,e2);
        boolean test1=((find(getPieces(),e1)) && (find(getPieces(),e1)));
        boolean test2=false;
        for( int i=0; i < getListeCorridor().length; i++ ) {
            if (Arrays.equals(tab, getListeCorridor()[i])){
                test2=true;
            }
        }
        return (test1 && test2);
    }

    @Override
    public Piece[] getPiecesConnectees(Piece e) {
        Piece[] tab = new Piece[getPieces().length];
        if ((!find(getPieces(), e))) {
            return null;

        } else {
            int j = 0;
            for (int i = 0; i < getPieces().length; i++) {
                if (existeCorridorEntre(e, getPieces()[i])) {

                    tab[j] = getPieces()[i];
                    j += 1;
                }
            }


        }
        int c=0;
        for (int i = 0; i < getPieces().length; i++){
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
     * Retoune un tableau qui contient les deux pièces données comme paramètre
     * @param e - Une piece
     * @param e1 - Une autre piece
     * @return Un tableau de pieces
     */
    public Piece[] nouveauCorridor(Piece e,Piece e1){
        Piece[] tab=new Piece[2];
        tab[0]=e;
        tab[1]=e1;
        return tab;
    }

    /**
     * Retourne un tableau de pieces augmentée avec la piece donné comme paramétre
     * @param tab Un tableau de pieces
     * @param e Une piece
     * @return Un tableau de pieces
     */
    public Piece[] ajoutTab(Piece[] tab,Piece e) {
        Piece[] temp = new Piece[tab.length + 1];
        for (int i = 0; i < tab.length; i++) {
            temp[i] = tab[i];
        }
        temp[temp.length-1] = e;
        return temp;

    }

    /**
     * Retourne vrai si et seulement si la Piece fait partie du Labyrinthe
     * @param tab Un tableau de pieces
     * @param e Une piece
     * @return True si la piece e appartient au tableau ;false sinon
     */
    public boolean find(Piece[] tab,Piece e) {
        boolean test;
        test= false;
        for( int i=0; i < tab.length; i++ ) {
            if (tab[i]==e){
                test=true;
            }
        }
        return test;
    }

    /**
     * Retourne une matrice qui contient tout les corridor entre les pieces su labrinthe .
     *
     * @return - l'ensemble des corridors.
     */
    public Piece[][] getListeCorridor() {
        return corridorListe;
    }

    /**
     * Retourne une matrice de corridors augmentée avec la corridor donné comme paramétre
     * @param ListeCorridor Une matrice de corridors
     * @param tab Un tableau de pieces
     * @return Une matrice de corridors
     */
    public Piece[][] setListeCorridor(Piece[][] ListeCorridor,Piece[] tab ){
        Piece[][] temp = new Piece[ListeCorridor.length + 1][];
        for (int i = 0; i < ListeCorridor.length; i++) {
            temp[i] = ListeCorridor[i];
        }
        temp[temp.length-1] = tab;
        return temp;

    }

    /**
     Retourne le nombre total de Corridors dans le Labyrinthe.
     *
     * @return - Le nombre de Corridors.
     */
    public int nombreDeCorridor() {
        return corridorListe.length;
    }
}
