package controleur;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class Controleur {

    @FXML
    private Text min;

    @FXML
    private Text max;

    @FXML
    private Text moyenne;

    @FXML
    private Text mode;

    @FXML
    private Text mediane;

    @FXML
    private Text derniereHeure;

    @FXML
    private Text dernierElement;

    @FXML
    private Text erreur;

    @FXML
    private TextField nbElement;

    @FXML
    private TextField nbHeure;

    @FXML
    private TextField minParam;

    @FXML
    private TextField maxParam;

    @FXML
    private RadioButton heureChoix;

    @FXML
    private RadioButton elementChoix;

    private boolean boolHeure;
    private boolean boolQuitter = false;
    private int heure;
    private int element;
    private double limitMin;
    private double limitMax;


    @FXML
    private void actualiser() {

        initValeur();
        initChoixSelect();

        if (estValeursCorrect()) {
            // TODO: Call API, receive data, create TableauDeBord constructor and set Values to Text



            if(boolHeure){
                derniereHeure.setText("" + heure);
                dernierElement.setText("XXX");
            }
            else {
                derniereHeure.setText("XXX");
                dernierElement.setText("" + element);
            }

            //TEST:
            System.out.println(element + " | " + heure + " | " + limitMin + " | " + limitMax);
        }
        else{
            erreur.setVisible(true);
        }
    }

    private void initValeur(){
        limitMin = 125.0;
        limitMax = -40.0;
        heure = 1;
        element = 1000;
        boolQuitter = false;
        erreur.setVisible(false);
    }

    private void initChoixSelect(){
        if (heureChoix.isSelected()) {
            boolHeure = true;
        } else {
            boolHeure = false;
        }
    }

    private boolean estValeursCorrect(){
        try {

            String tempElement = nbElement.getText();
            String tempHeure = nbHeure.getText();
            String tempLimiteMin = minParam.getText();
            String tempLimiteMax = maxParam.getText();

            if (tempLimiteMin.equals("")) {
                limitMin = 125.0;
            } else {
                limitMin = Double.parseDouble(tempLimiteMin);
            }

            if (tempLimiteMax.equals("")) {
                limitMax = -40.0;
            } else {
                limitMax = Double.parseDouble(tempLimiteMax);
            }

            if (boolHeure) {
                if (tempHeure.equals("")) {
                } else {
                    heure = Integer.parseInt(tempHeure);
                }
            } else {
                if (tempElement.equals("")) {
                } else {
                    element = Integer.parseInt(tempElement);
                }
            }
        } catch (NumberFormatException numberFormatException) {
            boolQuitter = true;
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return !boolQuitter;
    }
}