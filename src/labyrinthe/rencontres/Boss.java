package labyrinthe.rencontres;
/**
 * Représente une rencontre qui de type boss qui est un cas spécial du monstre Gargouille
 */
public class Boss extends Gargouille {

    @Override
    public String Rencontrer() {
        return "La bataille finale!";
    }

}
