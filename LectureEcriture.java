import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Classe qui lit le fichier a traduire en Coreen et qui ecrit en suite
 * le fichier en format HTML dans lequel est sauvegarde les unicodes de chaque Hangul
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
public class LectureEcriture {



    private static ArrayList<String> tableauDeLignesString = new ArrayList<String>();

    /**
     * Cette méthode sert a la lecture d'un nom de fichier donne au clavier
     *
     * @return nomFichierALire le nom du fichier texte à lire
     */
    protected static String lireClavier() {
        String nomFichierALire;

        Scanner Clavier = new Scanner(System.in);
        nomFichierALire = Clavier.nextLine();

        return nomFichierALire;
    }

    /**
     * Cette methode sert a verifier la validite du fichier texte donné en parametre
     *
     * @param nomFichierALire pour le fichier a verifier
     * @return nomFichierValideALire pour le nom de fichier valide a lire
     */
    protected static BufferedReader lireFichier (String nomFichierALire) {
        FileReader lecture;
        BufferedReader nomFichierValideALire = null;

        try {
            lecture = new FileReader(nomFichierALire);
            nomFichierValideALire = new BufferedReader(lecture);

        } catch (FileNotFoundException e) {
            System.err.println(Constantes.FICHIER_iNTROUVABLE_ERR);
            System.exit(1);
        }

        return nomFichierValideALire;
    }

    /**
     * Cette methode sert a la creation du tableau en format String des lignes lues
     *
     * @param nomFichierValideALire pour le nom de fichier en lecture
     * @return tableauDeLignesString pour l'ArrayList qui renferme les lignes lues en format String
     */
    protected static ArrayList<String> creerTableauDeLignesString(String nomFichierValideALire) {
        BufferedReader entree;
        String ligne;

        try {
            entree = lireFichier (nomFichierValideALire);
            while (entree.ready()) {
                ligne = entree.readLine().trim();
                tableauDeLignesString.add(ligne);
            }
            entree.close();
            lireFichier (nomFichierValideALire).close();

        } catch (IOException e) {
            System.err.println(Constantes.ENTREE_SORTIE_ERR);
        }

        return tableauDeLignesString;
    }



    /**
     * Cette methode sert a auvegarder le resultat de la traduction faite dans le fichier Resultat.html
     *
     * @param resultatTraduction pour la chaine de caracteres a afficher
     */
    protected static void enregistrerFichier(String resultatTraduction) {
        PrintWriter sortie;

        try {
            sortie = new PrintWriter(Constantes.NOM_FICHIER_SORTIE);
            sortie.print(resultatTraduction);
            sortie.close();
        } catch (IOException e) {
            System.err.println(Constantes.ENTREE_SORTIE_ERR);
            System.exit(2);
        }
    }
}

