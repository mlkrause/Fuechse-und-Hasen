import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Ein rechteckiges Gitter von Feldpositionen.
 * Jede Position kann ein einzelnes Tier aufnehmen.
 * 
 * @author David J. Barnes und Michael Kölling
 * @version 2006.03.30
 */
public class Feld
{
    private static final Random rand = new Random();
    
    // Die Tiefe und die Breite des Feldes
    private int tiefe, breite;
    // Speicher für die Tiere
    private Tier[][] feld;

    /**
     * Erzeuge ein Feld mit den angegebenen Dimensionen.
     * @param tiefe die Tiefe des Feldes.
     * @param breite die Breite des Feldes.
     */
    public Feld(int tiefe, int breite)
    {
        this.tiefe = tiefe;
        this.breite = breite;
        feld = new Tier[tiefe][breite];
    }
    
    /**
     * Räume das Feld.
     */
    public void raeumen()
    {
        for(int zeile = 0; zeile < tiefe; zeile++) {
            for(int spalte = 0; spalte < breite; spalte++) {
                feld[zeile][spalte] = null;
            }
        }
    }
    
    /**
     * Platziere das gegebene Tier im Feld.
     * Wenn an der Position bereits ein Tier eingetragen ist,
     * geht es verloren.
     * @param tier das Tier das platziert werden soll.
     */
    public void platziere(Tier tier)
    {
        Position position = tier.gibPosition();
        feld[position.gibZeile()][position.gibSpalte()] = tier;
    }
    
    /**
     * Liefere das Tier an der angegebenen Position, falls vorhanden.
     * @param position die gewünschte Position.
     * @return das Tier an der angegebenen Position oder null, wenn
     *         dort kein Tier eingetragen ist.
     */
    public Tier gibTierAn(Position position)
    {
        return gibTierAn(position.gibZeile(), position.gibSpalte());
    }
    
    /**
     * Liefere das Tier an der angegebenen Position, falls vorhanden.
     * @param zeile die gewünschte Zeile.
     * @param spalte die gewünschte Spalte.
     * @return das Tier an der angegebenen Position oder null, wenn
     *         dort kein Tier eingetragen ist.
     */
    public Tier gibTierAn(int zeile, int spalte)
    {
        return feld[zeile][spalte];
    }
    
    /**
     * Wähle zufällig eine der Positionen, die an die gegebene Position
     * angrenzen, oder die gegebene Position selbst.
     * Die gelieferte Position liegt innerhalb der gültigen Grenzen
     * dieses Feldes.
     * @param position die Position, von der ein Nachbar zu wählen ist.
     * @return eine gültige Position innerhalb dieses Feldes. Das kann
     *         auch die gegebene Position selbst sein.
     */
    public Position zufaelligeNachbarposition(Position position)
    {
        int zeile = position.gibZeile();
        int spalte = position.gibSpalte();
        // Zufällig eine Abweichung von -1, 0 oder +1 für Zeile und Spalte wählen.
        int naechsteZeile = zeile + rand.nextInt(3) - 1;
        int naechsteSpalte = spalte + rand.nextInt(3) - 1;
        // Prüfen, ob die neue Position außerhalb der Feldgrenzen liegt.
        if(naechsteZeile < 0 || naechsteZeile >= tiefe
           || naechsteSpalte < 0 || naechsteSpalte >= breite) {
            return position;
        }
        else if(naechsteZeile != zeile || naechsteSpalte != spalte) {
            return new Position(naechsteZeile, naechsteSpalte);
        }
        else {
            return position;
        }
    }
    
    /**
     * Versuche, eine freie Nachbarposition zur gegebenen Position zu
     * finden. Wenn es keine gibt, liefere die gegebene Position, wenn
     * sie selbst frei ist. Ansonsten liefere null.
     * Die gelieferte Position liegt innerhalb der Feldgrenzen.
     * @param position die Position, für die eine Nachbarposition
     *                 zu liefern ist.
     * @return eine gültige Position innerhalb der Feldgrenzen. Das
     *         kann die gegebene Position selbst sein oder null, wenn
     *         alle Nachbarpositionen und die Position selbst belegt sind.
     */
    // Überprüft alle Nachbarpositionen und ob diese Verfügbar bzw frei von anderen Tieren sind.
    public Position freieNachbarposition(Position position)
    {
        Iterator<Position> nachbarn = nachbarpositionen(position);
        while(nachbarn.hasNext()) {
            Position naechste = nachbarn.next();
            if(feld[naechste.gibZeile()][naechste.gibSpalte()] == null) {
                return naechste;
            }
        }
        // Prüfen, ob die gegebene Position selbst frei ist.
        if(feld[position.gibZeile()][position.gibSpalte()] == null) {
            return position;
        } 
        else {
            return null;
        }
    }

    /**
     * Erzeuge einen Iterator über eine gemischte Liste von Nachbarpositionen
     * zu der gegebenen Position. Diese Liste enthält nicht die gegebene 
     * Position selbst. Alle Positionen liegen innerhalb des Feldes.
     * @param position die Position, für die Nachbarpositionen zu liefern sind.
     * @return ein Iterator über Nachbarpositionen zur gegebenen Position.
     */
    public Iterator<Position> nachbarpositionen(Position position)
    {
        int zeile = position.gibZeile();
        int spalte = position.gibSpalte();
        List<Position> positionen = new LinkedList<Position>();
        for(int zDiff = -1; zDiff <= 1; zDiff++) {
            int naechsteZeile = zeile + zDiff;
            if(naechsteZeile >= 0 && naechsteZeile < tiefe) {
                for(int sDiff = -1; sDiff <= 1; sDiff++) {
                    int naechsteSpalte = spalte + sDiff;
                    // Exclude invalid locations and the original location.
                    if(naechsteSpalte >= 0 && naechsteSpalte < breite && (zDiff != 0 || sDiff != 0)) {
                        positionen.add(new Position(naechsteZeile, naechsteSpalte));
                    }
                }
            }
        }
        Collections.shuffle(positionen,rand);
        return positionen.iterator();
    }

    /**
     * Liefere die Tiefe dieses Feldes.
     * @return die Tiefe dieses Feldes.
     */
    public int gibTiefe()
    {
        return tiefe;
    }
    
    /**
     * Liefere die Breite dieses Feldes.
     * @return die Breite dieses Feldes.
     */
    public int gibBreite()
    {
        return breite;
    }
    
    public Object gibObjektAn(Position position)
    {
        return feld[position.gibZeile()][position.gibSpalte()];
    }
}
