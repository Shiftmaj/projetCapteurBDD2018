package donnees;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.StringJoiner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import controleur.ControleurPrincipal;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import modele.Parametre;

public class ParametreDAO {

    String xml = null;
    ControleurPrincipal controleurPrincipal = ControleurPrincipal.getInstance();

    public ParametreDAO() {

    }

    public void modifierParametre(int heures, int element, float superieurA, float inferieurA, boolean boolHeure){

        if(inferieurA>superieurA){
            if(boolHeure){
                if(heures > 0){
                    try {
                        // Création du Json
                        JSONObject objet = new JSONObject();
                        objet.put("inferieur_a", inferieurA);
                        objet.put("superieur_a", superieurA);
                        objet.put("heures", heures);

                        System.out.println(objet.toString());
                        System.out.println(superieurA);

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

                    }catch (Exception e){
                        e.printStackTrace();
                        System.out.println("NON");
                    }
                }
            }
            else {
                if(element > 2){
                    try {
                        URL url = new URL("https://papi.capture.tenam.re/parametre");
                        URLConnection con = url.openConnection();
                        HttpURLConnection http = (HttpURLConnection)con;
                        http.setRequestMethod("PUT");
                        http.setDoOutput(true);

                        Map<String,String> arguments = new HashMap<>();
                        arguments.put("quantite_entree", "" + element);
                        arguments.put("superieur_a", "" + superieurA);
                        arguments.put("inferieur_a", "" + inferieurA);

                        StringJoiner sj = new StringJoiner("&");
                        for(Map.Entry<String,String> entree: arguments.entrySet())
                            sj.add(URLEncoder.encode(entree.getKey(),"UTF-8") + "="
                                    + URLEncoder.encode(entree.getValue(), "UTF-8"));
                        byte[] sortie = sj.toString().getBytes(StandardCharsets.UTF_8);
                        int longueur = sortie.length;

                        http.setFixedLengthStreamingMode(longueur);
                        http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
                        http.connect();
                        try(OutputStream flux = http.getOutputStream()){
                            flux.write(sortie);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                        System.out.println("NON");
                    }
                }
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