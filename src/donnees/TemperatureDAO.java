package donnees;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import model.Temperature;

public class TemperatureDAO {
    List<Temperature> listeTemperature;
    String xml = null;

    public TemperatureDAO() {
        listeTemperature = new ArrayList<>();

    }

    public Temperature rechercherTemperature() {
        try {
            URL urlListeTemperature = new URL("https://papi.capture.tenam.re/temperature");
            String derniereBalise = "</statistiques>";
            InputStream flux = urlListeTemperature.openConnection().getInputStream();
            Scanner lecteur = new Scanner(flux);
            lecteur.useDelimiter(derniereBalise);
            xml = lecteur.next() + derniereBalise;

        } catch (IOException e) {
            e.printStackTrace();

        }
        try {
            DocumentBuilder parseur = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            @SuppressWarnings("deprecation")
            Document document = parseur.parse(new StringBufferInputStream(xml));
            String racine = document.getDocumentElement().getNodeName();

            NodeList listeNoeudsTemperature = document.getElementsByTagName("statistique");
            for (int position = 0; position < listeNoeudsTemperature.getLength(); position++) {
                Temperature temperature = new Temperature();
                Element noeudTemperature = (Element) listeNoeudsTemperature.item(position);
                String minimum = noeudTemperature.getElementsByTagName("minimum").item(0).getTextContent();
                temperature.setMinimum((minimum != "") ? Float.parseFloat(minimum) : 999999);
                String maximum = noeudTemperature.getElementsByTagName("maximum").item(0).getTextContent();
                temperature.setMaximum((maximum != "") ? Float.parseFloat(maximum) : 999999);
                String moyenne = noeudTemperature.getElementsByTagName("moyenne").item(0).getTextContent();
                temperature.setMoyenne((moyenne != "") ? Float.parseFloat(moyenne) : 999999);
                String mode = noeudTemperature.getElementsByTagName("mode").item(0).getTextContent();
                temperature.setMode((mode != "") ? Float.parseFloat(mode) : 999999);
                String mediane = noeudTemperature.getElementsByTagName("mediane").item(0).getTextContent();
                temperature.setMediane((mediane != "") ? Float.parseFloat(mediane) : 999999);
                return temperature;
            }

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }
}