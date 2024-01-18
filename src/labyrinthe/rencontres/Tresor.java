package labyrinthe.rencontres;

import java.util.Random;

/**
 * Représente un tresor qui peut avoir 3 type différent
 */
public abstract class Tresor extends Rencontre {

    public abstract String Rencontrer();
    /**
     * Retourne un tresor choisi aléatoirement des 3 types de tresors
     *
     * @return un tresor
     */
    public static Tresor choix(){
        Tresor t = null;
        int r = new Random().nextInt(2);
        switch (r) {
            case 0:
                t = new SacDeButin();
                break;
            case 1:
                t = new Potion();
                break;
            case 2:
                t = new ArtefactMagique();
                break;
        }
        return t;
    }


}
