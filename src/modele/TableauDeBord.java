package modele;

public class TableauDeBord {

    private float min;
    private float max;
    private float moyenne;
    private float mode;
    private float mediane;
    private float heure;
    private float sauvegarde;

    public TableauDeBord(float min, float max, float moyenne,
                         float mode, float mediane, float heure, float sauvegarde){
        this.min = min;
        this.max = max;
        this.moyenne = moyenne;
        this.mode = mode;
        this.mediane = mediane;
        this.heure = heure;
        this.sauvegarde = sauvegarde;
    }

    public float getMin() {
        return min;
    }

    public void setMin(float min) {
        this.min = min;
    }

    public float getMax() {
        return max;
    }

    public void setMax(float max) {
        this.max = max;
    }

    public float getMoyenne() {
        return moyenne;
    }

    public void setMoyenne(float moyenne) {
        this.moyenne = moyenne;
    }

    public float getMode() {
        return mode;
    }

    public void setMode(float mode) {
        this.mode = mode;
    }

    public float getMediane() {
        return mediane;
    }

    public void setMediane(float mediane) {
        this.mediane = mediane;
    }

    public float getHeure() {
        return heure;
    }

    public void setHeure(float heure) {
        this.heure = heure;
    }

    public float getSauvegarde() {
        return sauvegarde;
    }

    public void setSauvegarde(float sauvegarde) {
        this.sauvegarde = sauvegarde;
    }
}
