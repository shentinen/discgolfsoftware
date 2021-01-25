package dgSofta;

/**
 * Poikkeusluokka tietorakenteesta aiheutuvilla poikkeuksille
 * @author Santeri Hentinen
 * @version 19.3.2020
 *
 */
public class SailoException extends Exception {
    private static final long serialVersionUID = 1L;
    
    /**
     * @param viesti Poikkeuksen viesti
     */
    public SailoException(String viesti) {
        super(viesti);
    }
}
