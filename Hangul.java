import java.util.ArrayList;

/**
 * Classe qui represente un hangul, mot coreen compose d'une consonne
 * initiale, d'une voyelle et d'une consonne finale. L'objet hangul est en fait
 * un unicode.
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
public class Hangul {

    public static final String[] CONSONNE_INITIALE = {"G", "GG", "N", "D", "DD", "R", "M", "B", "BB",
            "S", "SS", "", "J", "JJ", "C", "K", "T", "P", "H"};
    public static final String[] VOYELLE = {"A", "AE", "YA", "YAE", "EO", "E", "YEO", "YE", "O",
            "WA", "WAE", "OE", "YO", "U", "WEO", "WE", "WI", "YU", "EU", "YI", "I"};
    public static final String[] CONSONNE_FINALE = {"", "G", "GG", "GS", "N", "NJ", "NH", "D", "L",
            "LG", "LM", "LB", "LS", "LT", "LP", "LH", "M", "B", "BS", "S", "SS", "NG",
            "J", "C", "K", "T", "P", "H"};

    private static int valeurUnicodeConsonneInitiale;
    private static int valeurUnicodeDeVoyelle;
    private static int valeurUnicodeConsonneFinale;
    private static int positionVoyelleDansLeMot;
    private static String voyelleTrouvee;
    private static String consomneInitialeIdentifiee;
    private static String consomneFinaleIdentifiee;
    protected static int taillePlusGrandeLigne;
    private int unicodeHangul;

    /**
     * Constructeur sans arguments
     */
    public Hangul() {
    }

    /**
     * Constructeur a trois parametres pour la construction de l'objet Hangul
     *
     * @param valeurUnicodeConsonneInitiale pour poids consonne initiale
     * @param valeurUnicodeDeVoyelle  pour poids voyelle
     * @param valeurUnicodeConsonneFinale pour poids consonne finale
     */
    public Hangul(int valeurUnicodeConsonneInitiale, int valeurUnicodeDeVoyelle,
                  int valeurUnicodeConsonneFinale) {

        unicodeHangul = 44032 + 588 * (valeurUnicodeConsonneInitiale - 1) + 28 *
                (valeurUnicodeDeVoyelle - 1) + valeurUnicodeConsonneFinale;
    }

    /**
     * Cette methode qui retourne la valeur de l'attribut unicodeHangul
     *
     * @return unicodeHangul pour l'Unicode Hangul
     */
    public int getUnicodeHangul() {
        return unicodeHangul;
    }

    /**
     * Cette methode est une reecriture de la methode toString afin d'avoir l'unicode
     * Hangul dans le format demande
     *
     * @return
     */
    @Override
    public String toString() {
        String affichageUnicode = "";
        affichageUnicode = "<td>" + "&#" + getUnicodeHangul() + ";</td>";
        return affichageUnicode;
    }

    /**
     * Cette methode lit ligne par ligne le tableau de String recu suite a la lecture
     * du fichier donne a traduire et assusre la construction du tableau d'unicodes correspondants
     *
     * Elle appelle la methode extraireMots pour avoir un array de mots par ligne, qui a son tour
     * appelle la methode analyserVoyelleDansChaqueMot afin de verifier s'il sagit d'un mot valide
     */
    protected static void construireTableauUnicode () {
        String ligne;
        for (int i = 0; i < Principale.tabDeLignes.size(); ++i) {
            ligne = Principale.tabDeLignes.get(i);
            if (!ligne.isEmpty()) {
                Principale.ligneDeHangul = Hangul.analyserVoyelleDansChaqueMot(Hangul.extraireMots(ligne));
            } else {
                Principale.ligneDeHangul = null;
            }
            Principale.arrayUnicode.add(Principale.ligneDeHangul);
        }
    }

    /**
     * Cette methode calcule la valeur unicode de la lettre romaisee trouvee dans le mot
     *
     * @param lettreIdentifiee la lettre à traiter
     * @param lettre le tableau de lettres coréennes
     * @return int indice unicode de cette lettre
     */
    private static int calculerValeurUnicodeLettreRomanisee(String lettreIdentifiee, String[] lettre) {
        int unicodeLettreRomanisee = 0;
        for (int i = 0; i < lettre.length; ++i) {
            if (lettreIdentifiee.equals(lettre[i])) {
                unicodeLettreRomanisee = i;
            }
        }
        return unicodeLettreRomanisee;
    }

    /**
     * Cette methode decoupe le mot en partie consonne initiale et partie consonne finale
     * et appelle les methodes de recherches correspondantes de ces items afin de
     * trouver leurs poids Unicode
     *
     * @param mot               pour le mot en cours d'analyse
     * @param voyelleIdentifiee pour le poids unicode de la valeurUnicodeDeVoyelle
     * @param positionVoyelleDansLeMot     pour la position de la valeurUnicodeDeVoyelle
     * dans le mot en cours d'analyse
     */
    private static Hangul decouperMotEnUnHangul(String mot, String voyelleIdentifiee,
                                                int positionVoyelleDansLeMot) {
        Ligne ligne = new Ligne();
        Hangul hangul = new Hangul();

        if (mot.length() == voyelleIdentifiee.length()) {
            valeurUnicodeConsonneInitiale = 12;
            valeurUnicodeConsonneFinale = 0;
        }
        if (mot.length() > voyelleIdentifiee.length()) {
            traiterConsonneInitiale(mot, voyelleIdentifiee, positionVoyelleDansLeMot);
        }
        if (positionVoyelleDansLeMot + voyelleIdentifiee.length() < mot.length()) {
            traiterConsonneFinale(mot, voyelleIdentifiee, positionVoyelleDansLeMot);
        }
        if (valeurUnicodeDeVoyelle != -1 && valeurUnicodeConsonneInitiale != -1
                && valeurUnicodeConsonneFinale != -1) {
            hangul = new Hangul(valeurUnicodeConsonneInitiale,
                    valeurUnicodeDeVoyelle, valeurUnicodeConsonneFinale);
        }
        return hangul;
    }

    /**
     * Cette methode sert a calculer la valeur unicode de la consonne initiale
     *
     * @param mot le mot ou se trouve la consonne initiale
     * @param positionVoyelleDansLeMot indice de la voyelle dans le mot
     * @param voyelleTrouvee la voyelle dans le mot
     * @return valeur unicode de la consonne initiale
     */
    private static int traiterConsonneInitiale(String mot, String voyelleTrouvee,
                                               int positionVoyelleDansLeMot) {
        String consIniEnAnalyse;
        String partieConsInitial = null;
        String partieConsFinal = null;

        partieConsInitial = mot.substring(0, positionVoyelleDansLeMot);
        consIniEnAnalyse = Hangul.lettreExiste(partieConsInitial, Hangul.CONSONNE_INITIALE);
        if (consIniEnAnalyse != null) {
            consomneInitialeIdentifiee = consIniEnAnalyse;
            valeurUnicodeConsonneInitiale = calculerValeurUnicodeLettreRomanisee(consomneInitialeIdentifiee,
                    Hangul.CONSONNE_INITIALE) + 1;
        } else {
            valeurUnicodeConsonneInitiale = -1;
            System.err.println(Constantes.CONS_INIT_INVALIDE);
            System.exit(1);
        }
        return valeurUnicodeConsonneInitiale;
    }

    /**
     * Cette methode sert a calculer la valeur unicode de la consonne finale
     *
     * @param mot le mot ou se trouve la consonne finale
     * @param indiceVoyelle indice de la voyelle dans le mot
     * @param voyelleIdentifiee la voyelle dans le mot
     * @return valeur unicode de la consonne finale
     */
    private static int traiterConsonneFinale(String mot, String voyelleIdentifiee, int indiceVoyelle) {
        String partieConsomneFinal;
        String consFinaleEnAnalyse;
        partieConsomneFinal = mot.substring(indiceVoyelle + voyelleIdentifiee.length(), mot.length());
        consFinaleEnAnalyse = Hangul.lettreExiste(partieConsomneFinal, Hangul.CONSONNE_FINALE);
        if (consFinaleEnAnalyse != null) {
            consomneFinaleIdentifiee = consFinaleEnAnalyse;
            valeurUnicodeConsonneFinale = calculerValeurUnicodeLettreRomanisee(consomneFinaleIdentifiee,
                    Hangul.CONSONNE_FINALE);
        } else {
            valeurUnicodeConsonneFinale = -1;
            System.err.println(Constantes.CONS_FIN_INVALIDE);
            System.exit(1);
        }
        return valeurUnicodeConsonneFinale;
    }

    /**
     * Methode qui extrait les mots d'une seule ligne et les enregistre dans
     * un array ensembleDeMot
     *
     * @param ligne pour la ligne a en extraire les mots
     * @return ensembleDeMot tableau qui contient les mots d'une ligne
     */
    public static String[] extraireMots(String ligne) {
        String[] arrayDeMotDansUneLigne = ligne.split(" ");
        if (arrayDeMotDansUneLigne.length > taillePlusGrandeLigne) {
            taillePlusGrandeLigne = arrayDeMotDansUneLigne.length;
        }
        return arrayDeMotDansUneLigne;
    }

    /**
     * Methode qui analyse les voyelles de chaque mot et place les hanguls de
     * chaque ligne dans un arraylist.
     *
     * @param arrayDeMotDansUneLigne ensemble de mot d'une ligne
     * @return ligneDeHangul ligne d'unicodes d'une ligne
     */
    public static Ligne analyserVoyelleDansChaqueMot(String[] arrayDeMotDansUneLigne) {
        Ligne ligneDeHangul = new Ligne();
        String voyelleEnAnalyse;
        String mot;

        for (int i = 0; i < arrayDeMotDansUneLigne.length; ++i) {
            mot = arrayDeMotDansUneLigne[i];
            voyelleEnAnalyse = lettreExiste(mot, Hangul.VOYELLE);

            if (voyelleEnAnalyse != null) {
                voyelleTrouvee = voyelleEnAnalyse;
                valeurUnicodeDeVoyelle = calculerValeurUnicodeLettreRomanisee(voyelleTrouvee,
                        Hangul.VOYELLE) + 1;
                positionVoyelleDansLeMot = mot.indexOf(voyelleTrouvee);
                ligneDeHangul.add(decouperMotEnUnHangul(mot, voyelleTrouvee, positionVoyelleDansLeMot));
            } else {
                valeurUnicodeDeVoyelle = -1;
                System.err.println(Constantes.MOT_INVALIDE);
                System.exit(1);
            }
        }
        return ligneDeHangul;
    }

    /**
     * Methode qui verifie qu'un mot est valide.
     *
     * @param mot le mot a traiter
     * @param lettre tableau de lettre coréeenes
     * @return la lettre identifiee dans le tableau de lettre coreenes
     */
    public static String lettreExiste(String mot, String[] lettre) {
        String lettreIdentifiee = null;
        ArrayList<String> listeLettresTrouvees = new ArrayList<String>();

        for (int i = 0; i < lettre.length; ++i) {
            if (mot.toUpperCase().contains(lettre[i])) {
                listeLettresTrouvees.add(lettre[i]);
                lettreIdentifiee = listeLettresTrouvees.get(0);
                for (int k = 0; k < listeLettresTrouvees.size(); ++k) {
                    if (listeLettresTrouvees.get(k).length() > lettreIdentifiee.length()) {
                        lettreIdentifiee = listeLettresTrouvees.get(k);
                    }
                }
            }
        }
        return lettreIdentifiee;
    }


}

