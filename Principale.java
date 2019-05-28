import java.util.ArrayList;

/**
 * Classe principal du logiciel qui lit le fichier à traduire en Coreen et
 * l'envoie aux differentes methodes qui analysent son contenu.
 *
 * @author Hanen Bondka
 * Code Permanent: BONH27568208
 * Courriel: bondka.hanen@courrier.uqam.ca
 *
 * @author Chaimaa Frouni
 * Code Permanent: FROC03589602
 * Courriel: frouni.chaimaa@courrier.uqam.ca
 *
 * Cours: INF2120-10
 * @version 2019-03-31
 */
public class Principale {

    protected static ArrayList<Ligne> arrayUnicode = new ArrayList<>();
    protected static ArrayList<String> tabDeLignes;
    protected static String chaineDeCaracteresPourHtml = "";
    protected static Ligne ligneDeHangul;
    private static String nomFichierALire;


    public static void main(String[] args) {

        try {
            System.out.println(Constantes.PRESENTATION_LOGICIEL);
            System.out.println(Constantes.SOLLICITATION_MSG);
            nomFichierALire= LectureEcriture.lireClavier();
            tabDeLignes = LectureEcriture.creerTableauDeLignesString(nomFichierALire);
            Hangul.construireTableauUnicode();
            TraitementHtml.construireTableauDeMemeTailleColonne ();
            TraitementHtml.construireChaineDeCaracteresHtml ();
            chaineDeCaracteresPourHtml = TraitementHtml.tableauHtml(chaineDeCaracteresPourHtml);
            LectureEcriture.enregistrerFichier(chaineDeCaracteresPourHtml);

        } catch (Exception e) {
            System.out.println(Constantes.MSG_ERR_FICHIER_INVALIDE);
            System.exit(3);
        }
        System.out.println(Constantes.FIN_NORMALE_PROGRAMME);
    }

}
