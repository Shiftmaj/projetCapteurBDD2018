package donnees;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import controleur.ControleurPrincipal;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import modele.Parametre;

public class ParametreDAO {

    private String xml;

    public ParametreDAO() {
        this.xml = null;
    }

    public void modifierParametre(int heures, int elements, float superieurA, float inferieurA, boolean boolHeure){

        if(inferieurA>superieurA){
            JSONObject objet = new JSONObject();

            if(boolHeure){
                if(heures > 0){
                    try {
                        objet.put("inferieur_a", inferieurA);
                        objet.put("superieur_a", superieurA);
                        objet.put("heures", heures);
                    }
                    catch (JSONException e){
                        e.printStackTrace();
                    }
                }
            }
            else {
                if(elements > 2){
                    try {
                        objet.put("inferieur_a", inferieurA);
                        objet.put("superieur_a", superieurA);
                        objet.put("quantite_entree", elements);
                    }
                    catch (JSONException e){
                        e.printStackTrace();
                    }
                }
            }

            try {
                // Contact à l'api
                URL url = new URL("https://papi.capture.tenam.re/parametre");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setDoOutput(true);
                con.setRequestMethod("PUT");
                con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                con.setRequestProperty("Accept", "application/json");

                OutputStream os = con.getOutputStream();
                os.write(objet.toString().getBytes(StandardCharsets.UTF_8));

                // NECESSAIRE POUR L'API :
                con.getResponseMessage();

                os.close();
            }
            catch (Exception e){
                e.printStackTrace();
                System.out.println("NON");
            }
        }
    }

    public Parametre rechercherParametre(){
        try
        {

            URL urlListeParam = new URL("https://papi.capture.tenam.re/parametre");
            String derniereBalise = "</parametres>";
            InputStream flux = urlListeParam.openConnection().getInputStream();
            Scanner lecteur = new Scanner(flux);
            lecteur.useDelimiter(derniereBalise);
            xml = lecteur.next() + derniereBalise;

        } catch(IOException e) {
            e.printStackTrace();

        }
        try
        {
            DocumentBuilder parseur =  DocumentBuilderFactory.newInstance().newDocumentBuilder();
            @SuppressWarnings("deprecation")
            Document document = parseur.parse(new StringBufferInputStream(xml));
            String racine = document.getDocumentElement().getNodeName();
            System.out.println(racine);

            NodeList listeNoeudsParametre = document.getElementsByTagName("parametres");
            for(int position = 0; position < listeNoeudsParametre.getLength(); position++)
            {
                Parametre parametre = new Parametre();
                Element noeudParametres = (Element)listeNoeudsParametre.item(position);
                String nbHeure = noeudParametres.getElementsByTagName("heures").item(0).getTextContent();
                parametre.setNbHeure(Integer.valueOf(nbHeure));
                String nbElement = noeudParametres.getElementsByTagName("quantite_entree").item(0).getTextContent();
                parametre.setNbElement(Integer.valueOf(nbElement));
                String superieur_a = noeudParametres.getElementsByTagName("superieur_a").item(0).getTextContent();
                parametre.setSuperieurA(Float.valueOf(superieur_a));
                String inferieur_a = noeudParametres.getElementsByTagName("inferieur_a").item(0).getTextContent();
                parametre.setInferieurA(Float.valueOf(inferieur_a));


                return parametre;
            }

        }catch (ParserConfigurationException e)
        {
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