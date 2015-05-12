package com.iesvirgendelcarmen.domxmlparser;

// import org.xml.sax.XMLReader;
// import com.iesvirgendelcarmen.xmlparser.DomPrinter;

import java.io.FileNotFoundException;
import org.w3c.dom.Document;
// import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
// import org.w3c.dom.CharacterData;
// import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

import org.xml.sax.SAXException;
// import org.xml.sax.SAXParseException;

// import javax.xml.parsers.DocumentBuilder;
// import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

// import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

/*
 * Paquete: xmlParser.
 * jgutierrez AT iesvirgendelcarmen DOT com.
 * Licensed under Creative Commons
 */


/**
 *
 * @author Juan Gualberto
 */
public class domXmlParser extends DomUtil {
    
    public static int recursiveNodeWalker (Node nodo, int ident) {
        
    String NODE_TYPES[] = new String [] {
        "",
        "ELEMENT",
        "ATTRIBUTE",
        "TEXT",
        "CDATA_SECTION",
        "ENTITY_REFERENCE",
        "ENTITY",
        "PROCESSING_INSTRUCTION",
        "COMMENT",
        "DOCUMENT",
        "DOCUMENT_TYPE",
        "DOCUMENT_FRAGMENT",
        "NOTATION"
        };
        
        int total = 1;
        
        do {
            for (int i=0; i<ident;i++) {
                System.out.print(" ");
            }
            System.out.println(
                "Encontrado NODO - \t TIPO: "+ NODE_TYPES[nodo.getNodeType()]+
                "\t NOMBRE: "+nodo.getNodeName()+
                "\t VALOR: "+nodo.getNodeValue());
            
            if (nodo.hasChildNodes()) {
                total += recursiveNodeWalker(nodo.getFirstChild(), ident+1);
            }
        }
        while ( (nodo=nodo.getNextSibling()) !=null );
        return total;
    }
    
    public static int recursiveXMLWalker(Node nodo, int ident) {
        
        int total = 1;
        
        Element elemento;
        
        do {
            
            switch (nodo.getNodeType()){
                case 0: {
                    
                }
                case 1:{ // ELEMENT
                    for (int i=0; i<ident;i++) {
                        System.out.print(" ");
                    }
                    
                    System.out.print("Encontrado ELEMENTO: "+ nodo.getNodeName());
                    
                    /*if (nodo.getTextContent()!=null && nodo.getFirstChild().getNodeType() == 3){ // si el hijo es texto
                        System.out.print("\t CONTENIDO: "+nodo.getTextContent());
                    }*/
                    
                    elemento = (Element)nodo;
                    if ( elemento.getChildNodes().getLength() <= 1 ){
                        System.out.print("\t CONTENIDO: " + 
                                elemento.getTextContent());
                    }
                    
                    System.out.println(".");
                }
                default:{
                    
                }
            }
            if (nodo.hasChildNodes()) {
                total += recursiveXMLWalker(nodo.getFirstChild(), ident+1);
            }
        }
        while ( (nodo=nodo.getNextSibling() ) !=null );
        
        return total;
    }
    
    public static int printXMLNodeTree(Document documento){
        // Element root = documento.getDocumentElement();
        Node root = documento.getFirstChild();
        return recursiveNodeWalker(root, 0);
    }
    
    public static int printXMLTree(Document documento){
        // Element root = documento.getDocumentElement();
        Node root = documento.getFirstChild();
        return recursiveXMLWalker(root, 0);
    }
    /**
     * 
     * @param nodo
     */
    public static void recorrerRecursivo(Node nodo){
        do{
            if (nodo instanceof Element) {
                Element elemento = (Element) nodo;
                String etiqueta = elemento.getNodeName();
                NamedNodeMap atributos =  elemento.getAttributes();
                System.out.println("encontrado ELEMENTO "+etiqueta);
                if (atributos!=null){
                    int numAtributos = atributos.getLength();
                    for (int i = 0; i < numAtributos; i++) {
                        Node atributo = atributos.item(i);
                        System.out.print(" ["+
                        atributo.getNodeName()+"="+
                        atributo.getNodeValue()+"]");

                    }
                }
            }
            if(nodo instanceof Text){
                String etiqueta = nodo.getNodeValue();
                etiqueta = etiqueta.replace("\n,", " ").replace("\t", " ").replace("\r", " ").trim();
                if (etiqueta.length()>0) {
                    System.out.println("\nEncontrado TEXTO: "+etiqueta);
                }
                
            }
            
            if(nodo.getFirstChild()!=null){
                recorrerRecursivo(nodo.getFirstChild());
            }
                
        }while((nodo = nodo.getNextSibling())!= null);
    }
    
    private static final String cabecera = "<html> \n"
            + " <head>  \n"
            + "   <link href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css\" rel=\"stylesheet\" />  \n"
            + " <meta charset='UTF-8'"
            + ">  \n"
            + " </head>  \n"
            + " <body>  \n";

    private static final String pie = " </body> \n</html>\n";
    
    /**
     * Este método genera el HTML con tablas de la colección de CDs
     * @param doc
     * @param fichero_salida
    */
    public static void myCDcollection (Document doc, String fichero_salida){
        
        // cargo en una lista los CDs
        NodeList listaCD = doc.getElementsByTagName("cd");
        Element tmpCD;
        try {
            PrintWriter pw = new PrintWriter(fichero_salida);
            pw.println(cabecera + "<table class='table'>");
            pw.println("<tr> <th>TITLE</th> \n <th>ARTIST</th> </tr>");
            for (int i = 0; i < listaCD.getLength(); i++) {
                tmpCD = (Element) listaCD.item(i);
                // Element tmpNode = (Element) tmpCD.getFirstChild();
                pw.println("<tr>");
                /* do {
                    System.out.println("   <td> "+ tmpNode.getTextContent() +" </td>");
                } while ( (tmpNode = (Element) tmpNode.getNextSibling()) != null */

                pw.println("   <td>"+
                        tmpCD.getElementsByTagName("title").item(0).getTextContent() +
                        "</td>");

                pw.println("   <td>"+
                        tmpCD.getElementsByTagName("artist").item(0).getTextContent() +
                        "</td>");

                pw.println("</tr>");
            }
            pw.println("</table>"+pie);
            pw.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Error al crear el archivo..." + ex.getMessage());
        }
    }
   
    
    public static void myCDcollectionByArtist (Document doc, String fichero_salida){
        
        // cargo en una lista los CDs
        
        
        NodeList listaNodosCD = doc.getElementsByTagName("cd");
        Element elementCD;
        
        ListaCd miLista = new ListaCd();
        
        String titulo = null;
        String artista = null;
        String precio = null;
        String pais = null;
        String compania = null;
        Integer anio;
        try {
            for (int i = 0; i < listaNodosCD.getLength(); i++) {
                elementCD = (Element) listaNodosCD.item(i);
                    // Element tmpNode = (Element) tmpCD.getFirstChild();
                titulo = elementCD.getElementsByTagName("title").item(0).getTextContent();
                artista = elementCD.getElementsByTagName("artist").item(0).getTextContent();
                precio = elementCD.getElementsByTagName("price").item(0).getTextContent();
                pais = elementCD.getElementsByTagName("country").item(0).getTextContent();
                compania = elementCD.getElementsByTagName("company").item(0).getTextContent();
                anio = new Integer(elementCD.getElementsByTagName("year").item(0).getTextContent());
                miLista.add(new Cd(artista, titulo, pais, precio, compania, anio));

            }
            
            
            Collections.sort(miLista, new CompareCdByArtist());
            PrintWriter pw = new PrintWriter(fichero_salida);
            pw.println(cabecera+"<table class='table'>");
            pw.println("<tr> <th>TITLE</th> \n <th>ARTIST</th> </tr>");
            
            for (Cd miCd : miLista){
                pw.println("<tr>");
                    pw.println("<td>"+miCd.artista+"</td>");
                    pw.println("<td>"+miCd.titulo+"</td>");
                    pw.println("<td>"+miCd.pais+"</td>");
                    pw.println("<td>"+miCd.precio+"</td>");
                    pw.println("<td>"+miCd.compania+"</td>");
                    pw.println("<td>"+miCd.anio+"</td>");
            
                pw.println("</tr>");
            }
             pw.println("</table>"+pie);
             pw.close();
            
            
            
        } catch (FileNotFoundException ex){
            System.out.println("Error al crear el archivo..."+ ex.getMessage());
        }
        
          
          
        
        
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            //leemos el documento que queremos trabajar
            Document doc = DomUtil.parse("cdcatalog.xml", false);
            
            /*
            Node raiz = doc.getDocumentElement();
            //Esto me devuelve el nodo raiz en este caso catalog
            System.out.println("El nodo raiz es: "+raiz.getNodeName());
            //Busco en el documento el elemnto plantas
            NodeList listaPlantas = doc.getElementsByTagName("PLANT");
            System.out.println("En este catalog hay "+listaPlantas.getLength()+" plantas");
            recorrerRecursivo(raiz);*/
            //myCDcollection(doc, "salida.txt");
            myCDcollectionByArtist(doc, "salida.txt");
            myCDcollectionByArtist(doc, "salida.html");
            
            
        } catch (ParserConfigurationException ex) {
            //Logger.getLogger(domXmlParser.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error de configuracion del parser"+ex.getMessage());
        } catch (IOException ex) {
            //Logger.getLogger(domXmlParser.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("No se puede abrir el archivo: "+ex.getMessage());
        } catch (SAXException ex) {
            //Logger.getLogger(domXmlParser.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error en el fichero XML: "+ex.getMessage());
        }
    }
    
}
