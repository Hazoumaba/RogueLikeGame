package labyrinthe.soumission;

import labyrinthe.code_squelette.Labyrinthe;
import labyrinthe.code_squelette.Piece;
import labyrinthe.code_squelette.RencontreType;

public class MonAventure extends labyrinthe.code_squelette.Aventure {

    /**
     * Initialize une Aventure avec un Labyrinthe
     *
     * @param c - la carte de l'Aventure
     */
    public MonAventure(Labyrinthe c) {
        super(c);
    }

    @Override
    public boolean estPacifique() {

        boolean test1=true;
        boolean test2=true;
        for( int i=0; i < carte.nombreDePieces(); i++ ) {
            if (carte.getPieces()[i].getRencontreType()== RencontreType.BOSS){
                test1=false;
            }
            if (carte.getPieces()[i].getRencontreType()== RencontreType.MONSTRE){
                test2=false;
            }

        }
        return (test1 && test2);
    }

    @Override
    public boolean contientDuTresor() {
        boolean test=false;
        for( int i=0; i < carte.nombreDePieces(); i++ ) {
            if (carte.getPieces()[i].getRencontreType()== RencontreType.TRESOR){
                test=true;
            }
        }
        return test;
    }

    @Override
    public int getTresorTotal() {
        int nb=0;
        for( int i=0; i < carte.nombreDePieces(); i++ ) {
            if (carte.getPieces()[i].getRencontreType()== RencontreType.TRESOR){
                nb+=1;
            }
        }
        return nb;
    }

    @Override
    public boolean contientBoss() {
        boolean test=false;
        for( int i=0; i < carte.nombreDePieces(); i++ ) {
            if (carte.getPieces()[i].getRencontreType()== RencontreType.BOSS){
                test=true;
            }
        }
        return test;
    }

    @Override
    public Piece[] cheminJusquAuBoss() {

        Piece[] chemin=new Piece[carte.nombreDePieces()];
        for (int i = 0; i < carte.nombreDePieces(); i++) {
            for (int j = 0; j < carte.nombreDePieces(); j++) {
                if ((carte.getPieces()[i].getID())< carte.getPieces()[j].getID()) {
                    Piece temp = carte.getPieces()[i];
                    carte.getPieces()[i] = carte.getPieces()[j];
                    carte.getPieces()[j] = temp;
                }
            }
        }
        int i=0;
        int j=1;
        boolean test=false;
        while ((i< carte.nombreDePieces()) && (j< carte.nombreDePieces())&& (carte.getPieces()[i].getID() == carte.getPieces()[j].getID() - 1) ){
            test=true;
            chemin[i]=carte.getPieces()[i];
            if (carte.getPieces()[j].getRencontreType()== RencontreType.BOSS){
                chemin[j]=carte.getPieces()[j];
                break;
            }
            i+=1;
            j+=1;
        }

        chemin=DIROgue.eliminernull(chemin);
        boolean test1=true;
        for (int k=0;k<chemin.length-1;k++){
            if (!(carte.existeCorridorEntre(chemin[k],chemin[k+1]))){

                test1=false;
            }
        }
        if (!(contientBoss())|| !(test) || !(test1) ||  !(chemin[chemin.length-1].getRencontreType()==RencontreType.BOSS)){
            return new Piece[0];
        }
 
        else {
            return chemin;
        }
    }
}
