package modele;

public class Temperature {

    private int idTemperature;
    private float minimum;
    private float maximum;
    private float moyenne;
    private float mode;
    private float mediane;


    public Temperature() {
        this.minimum = 0;
        this.maximum = 0;
        this.moyenne = 0;
        this.mode = 0;
        this.mediane = 0;
    }

    public int getIdTemperature() {
        return idTemperature;
    }


    public void setIdTemperature(int idTemperature) {
        this.idTemperature = idTemperature;
    }


    public float getMinimum() {
        return minimum;
    }


    public void setMinimum(float minimum) {
        this.minimum = minimum;
    }


    public float getMaximum() {
        return maximum;
    }


    public void setMaximum(float maximum) {
        this.maximum = maximum;
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


}
