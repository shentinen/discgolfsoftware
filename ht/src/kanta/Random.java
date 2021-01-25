package kanta;

/**
 * @author Santeri Hentinen
 * @version 25.3.2020
 *
 */
public class Random {
    
    /**
     * Satunnaislukugeneraattori
     * @param ala alaraja
     * @param yla yläraja
     * @return palauttaa generoidun luvun
     */
    public static int rand(int ala, int yla) {
        double n = (yla-ala)*Math.random() + ala;
        return (int)Math.round(n);
    }
}
