/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iesvirgendelcarmen.domxmlparser;

import org.w3c.dom.Document;
// import org.w3c.dom.NamedNodeMap;
// import org.w3c.dom.Node;
// import org.w3c.dom.NodeList;

import org.xml.sax.SAXException;
// import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import java.io.File;
import java.io.IOException;


/**
 *
 * @author Administrador
 */
public class DomUtil {
    
    public static DocumentBuilder newBuilder(boolean validation)
        throws ParserConfigurationException {
        
            // Creamos la factoría JAXP para los constructores de documentos
            DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
            // Configuramos la factoría
            domFactory.setValidating(validation);
            domFactory.setNamespaceAware(true);
            // Creamos el contructor de documentos (posteriormente se podrá usar 
            // para leer archivos XML o bien crearlos y almacenarlos o transmitirlos
            DocumentBuilder domBuilder = domFactory.newDocumentBuilder();
            // Establecemos el manejador de errores (XML bien formado, válido...)
            // domBuilder.setErrorHandler(null);
            return domBuilder;
    }
    
    public static Document newDocument()
            throws ParserConfigurationException {
        
        DocumentBuilder domBuilder = newBuilder(false);

        Document document = domBuilder.newDocument();
        
        return document;
    }
    
    public static Document parse(String path, boolean validation) 
        throws ParserConfigurationException, IOException, SAXException {

        DocumentBuilder domBuilder = newBuilder(validation);
        
        Document document = domBuilder.parse(new File (path));
        
        return document;
    }
}
