package labyrinthe.rencontres;

import java.util.Random;

/**
 * Représente un monstre qui peut avoir 3 type différent
 */
public abstract class Monstre extends Rencontre {


    @Override
    public abstract String Rencontrer();


    /**
     * Retourne un monstre choisi aléatoirement des 3 types de mosntres
     *
     * @return un monstre
     */
    public static Monstre choix(){
        Monstre m = null;
        int r = new Random().nextInt(2);
        switch (r) {
            case 0:
                m = new Gargouille();
                break;
            case 1:
                m = new Orque();
                break;
            case 2:
                m = new Gobelin();
                break;
        }
        return m;
    }
}
