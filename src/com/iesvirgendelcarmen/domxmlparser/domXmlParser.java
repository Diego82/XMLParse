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
    
    private static String cabecera = "<html>"
            +"<head>\n"
            +"<link href = <link href='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css' rel='stylesheet'>\n"
            +"</head>\n"
            +"<body>\n";
    
    private static String pie = "</body>"+"<html>";
    
    /**
     * Este metodo genera el html con tablas de la coleccion de CD's
     * @param raiz 
     */
    public static void myCollection(Document doc, String ficheroSalida){
        NodeList listaCD = doc.getElementsByTagName("cd");
        Element tmpCD;
        try {
            PrintWriter pw = new PrintWriter(ficheroSalida);
            pw.println(cabecera+"<table class='table'>");
            pw.println("<tr> <th>TITLE</th> /n <th>ARTIST</th> <tr>");
            
        for(int i=0 ; i< listaCD.getLength() ; i++){
            tmpCD = (Element) listaCD.item(i);
            Element tmpNode = (Element) tmpCD.getFirstChild();
            
            System.out.println("<tr>");
            /*do {            
                System.out.println("<td>"+tmpNode.getNodeValue()+"</td>");
            } while ((tmpNode = (Element) tmpNode.getNextSibling()) != null);*/
            
            System.out.println("\t<td>"+tmpCD.getElementsByTagName("title").item(0).getTextContent()+ "</td>");
            System.out.println("\t<td>"+tmpCD.getElementsByTagName("artist").item(0).getTextContent()+ "</td>");
            
            System.out.println("</tr>"+pie);
        }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(domXmlParser.class.getName()).log(Level.SEVERE, null, ex);
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
            myCollection(doc, "salida.txt");
            
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
