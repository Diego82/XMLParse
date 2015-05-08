/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.iesvirgendelcarmen.saxxmlparser;

import org.xml.sax.*;
import org.xml.sax.helpers.*;
import java.io.*;

/**
 *
 * @author juangu AT ujaen DOT es
 */
public class saxParser extends DefaultHandler {

    @Override
    public void startDocument() throws SAXException {
        System.out.println("SAX Event: START DOCUMENT");
    }

    @Override
    public void endDocument() throws SAXException {
        System.out.println("SAX Event: END DOCUMENT");
    }

    @Override
    public void startElement(String namespaceURI,
            String localName,
            String qName,
            Attributes attr) throws SAXException {
        System.out.println("SAX Event: START ELEMENT[ " + localName + " ]");
    }

    @Override
    public void endElement(String namespaceURI,
            String localName,
            String qName) throws SAXException {
        System.out.println("SAX Event: END ELEMENT[ " + localName + " ]");
    }

    @Override
    public void characters(char[] ch,
            int start,
            int length) throws SAXException {
        System.out.print("SAX Event: CHARACTERS[ ");

        try {
            OutputStreamWriter output = new OutputStreamWriter(System.out);
            output.write(ch, start, length);
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(" ]");
    }

    /**
     * @param argv the command line arguments
     */
    public static void main(String[] argv) {
        if ( argv.length == 1 ) {
            String inputFile = argv[0];
            System.out.println("Processing '" + inputFile + "'.");
            System.out.println("SAX Events:");
            try {
                XMLReader reader = XMLReaderFactory.createXMLReader();
                reader.setContentHandler(new saxParser());
                reader.parse(new InputSource(
                        new FileReader(inputFile)));
            } catch (SAXException | IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Uso del programa: saxParser fichero.xml");
        }
    }
}
