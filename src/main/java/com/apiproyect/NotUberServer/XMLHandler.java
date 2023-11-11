package com.apiproyect.NotUberServer;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class XMLHandler {

    @Value("${xml.file.path}")
    private String filePath;

    private static Document readXML(String filePath) {
        try {
            SAXBuilder saxBuilder = new SAXBuilder();
            File inputFile = new File(filePath);
            return saxBuilder.build(inputFile);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void updateXML(Document document, String elementType, String fieldToUpdate, String newValue) {
        // Realiza las actualizaciones necesarias en el documento XML
        Element rootElement = document.getRootElement();

        // Busca el elemento correspondiente al tipo (driver o employee)
        Element elementToUpdate = rootElement.getChild(elementType);

        if (elementToUpdate != null) {
            // Busca el campo a actualizar y actualiza su valor
            Element fieldElement = elementToUpdate.getChild(fieldToUpdate);

            if (fieldElement != null) {
                fieldElement.setText(newValue);
            }
        }
    }

    public <T> List<T> getAllUsers(String elementType, Class<T> clazz) {
        Document document = readXML(filePath);
        Element rootElement = document.getRootElement();

        return rootElement.getChildren(elementType).stream()
                .map(element -> convertElementToObject(element, clazz))
                .collect(Collectors.toList());
    }

    // Método auxiliar para convertir un elemento a un objeto Java
    private <T> T convertElementToObject(Element element, Class<T> clazz) {
        try {
            // Convierte el Element a un String XML
            String xmlString = new XMLOutputter().outputString(element);

            // Configura el contexto JAXB para la clase especificada
            JAXBContext jaxbContext = JAXBContext.newInstance(clazz);

            // Crea el unmarshaller
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            // Deserializa el String XML al objeto Java
            StringReader reader = new StringReader(xmlString);
            return (T) unmarshaller.unmarshal(reader);
        } catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void saveXML(Document document, String filePath) {
        try {
            XMLOutputter xmlOutput = new XMLOutputter();
            xmlOutput.setFormat(Format.getPrettyFormat());

            FileWriter writer = new FileWriter(filePath);
            xmlOutput.output(document, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



