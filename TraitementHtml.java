
/**
 * Classe qui decrit le contenu du fichier HTML resultant du logiciel. Le fichier
 * HTML est compose d'unicode des caracteres coreens.
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
public class TraitementHtml {

    /**
     * Cette methode modifie le tableau de lignes des unicodes afin d'avoir
     * la meme taille de ligne, ajoute un null a chaque ligne qui est inferieure
     * a la ligne maximale du tableau
     */

    protected static void construireTableauDeMemeTailleColonne () {
        for (int i = 0; i < Principale.arrayUnicode.size(); ++i) {
            if (Principale.arrayUnicode.get(i) != null) {
                while (Principale.arrayUnicode.get(i).size() < Hangul.taillePlusGrandeLigne) {
                    Principale.arrayUnicode.get(i).add(null);
                }
            }
        }
    }

    /**
     * Cette methode assure la onstruction de chaine de carateres pour affichage html
     */
    protected static void construireChaineDeCaracteresHtml () {
        for (int i = 0; i < Hangul.taillePlusGrandeLigne; i++) {
            Principale.chaineDeCaracteresPourHtml += "<tr>";
            for (int l = Principale.arrayUnicode.size() - 1; l >= 0; l--) {
                if (Principale.arrayUnicode.get(l) == null) {
                    Principale.chaineDeCaracteresPourHtml += "<td width=\"15\"/>";
                } else {
                    if (Principale.arrayUnicode.get(l).get(i) == null) {
                        Principale.chaineDeCaracteresPourHtml += "<td></td>";
                    } else {
                       Principale.chaineDeCaracteresPourHtml += Principale.arrayUnicode.get(l).get(i).toString();
                    }
                }
            }
            Principale.chaineDeCaracteresPourHtml += "</tr>\n";
        }
    }

    /**
     * Cette méthode ecrit le code HTML disponible dans le fichier de sortie Resultat.html
     *
     * @param chaineDeCaractereUnicode pour le String des unicodes a afficher
     * @return le code HTML a afficher sur la sortie du fichier Resultat.html
     */
    protected static String tableauHtml(String chaineDeCaractereUnicode) {
        return "<!DOCTYPE html>" + "\n" +
                "<html>" + "\n" +
                "\t<head>" + "\n" +
                "\t\t<title>" + Constantes.titreTableauHtml + "</title>" + "\n" +
                "\t</head>" + "\n" +
                "\t<body>" + "\n" +
                "\t\t<hr>" + "\n" +
                "\t\t<table>" + "\n" +
                chaineDeCaractereUnicode +
                "\t\t</table>" + "\n" +
                "\t\t<hr>" + "\n" +
                "\t</body>" + "\n" +
                "</html>";
    }
}
