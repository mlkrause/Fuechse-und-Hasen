import java.util.ArrayList;

/**
 * Tier ist eine abstrakte Superklasse für Tiere. 
 * Sie verwaltet Eigenschaften, die alle Tiere gemein haben,
 * wie etwas das Alter oder eine Position.
 * 
 * @author 
 * @version 
 */
public abstract class Tier
{
    // Das Alter dieses Tieres.
    protected int alter;
    // Ist dieses Tier noch lebendig?
    private boolean lebendig;
    // Die Position dieses Tieres.
    protected Position position;
    
    protected Feld feld;

    /**
     * Erzeuge ein Tier mit Alter Null (ein Neugeborenes).
     */
    //erzeugt ein Tier
    public Tier(Feld feld, Position position)
    {
        alter = 0;
        lebendig = true;  
        //this.feld = feld;
        //System.out.println(position.gibZeile());
        setzePosition(position);
    }
    
    /**
     * Lasse dieses Tier agieren - es soll das tun, was
     * es tun muss oder möchte.
     * @param aktuellesFeld Das aktuell belegte Feld.
     * @param naechstesFeld Das zu belegende Feld.
     * @param neueTiere Liste, in die neue Tiere einzufügen sind.
     */
    abstract public void agiere(Feld aktuellesFeld, 
                             Feld naechstesFeld, ArrayList<Tier> neueTiere);
    
    /**
     * Prüfe, ob dieses Tier noch lebendig ist.
     * @return true wenn dieses Tier noch lebendig ist.
     */
    public boolean istLebendig()
    {
        return lebendig;
    }
    // Methode, welche die Tiere Sterben lässt. Bessere Variante gefunden.
    /*protected void sterben()
    {
        lebendig = false;
        if(position != null) {
            feld.raeumen();
            position = null;
            feld = null;
        }
    }*/

    /**
     * Signalisiere diesem Tier, dass es gestorben ist.   
     */
    public void setzeGestorben()
    {
        lebendig = false;
    }
    
    /**
     * Liefere das Alter dieses Tieres.
     * @return das Alter dieses Tieres.
     */
    public int gibAlter()
    {
        return alter;
    }

    /**
     * Setze das Alter dieses Tieres.
     * @param alter das Alter dieses Tieres.
     */
    public void setzeAlter(int alter)
    {
        this.alter = alter;
    }
    
    /**
     * Liefere die Position dieses Tieres.
     * @return die Position dieses Tieres.
     */
    public Position gibPosition()
    {
        return position;
    }
    protected Feld gibFeld()
    {
        return feld;
    }   
    /**
     * Setze die Position dieses Tieres.
     * @param zeile die vertikale Koordinate der Position.
     * @param spalte die horizontale Koordinate der Position.
     */
    public void setzePosition(int zeile, int spalte)
    {
        this.position = new Position(zeile, spalte);
    }

    /**
     * Setze die Position dieses Tieres.
     * @param position die Position dieses Tieres.
     */
    public void setzePosition(Position position)
    {
        this.position = position;
    }
}
