package com.apiproyect.NotUberServer;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
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

    // Método auxiliar para convertir un elemento a un objeto Java utilizando solo JDOM
    private <T> T convertElementToObject(Element element, Class<T> clazz) {
        try {
            T instance = clazz.getDeclaredConstructor().newInstance();

            // Obtén todos los campos de la clase del objeto
            Field[] fields = clazz.getDeclaredFields();

            // Itera sobre los campos y establece los valores desde el elemento XML
            for (Field field : fields) {
                field.setAccessible(true);
                String fieldName = field.getName();

                // Busca el elemento correspondiente al campo en el XML
                Element fieldElement = element.getChild(fieldName);

                if (fieldElement != null) {
                    // Obtiene el valor del elemento y establece el campo en el objeto
                    String fieldValue = fieldElement.getText();
                    field.set(instance, convertStringToFieldType(fieldValue, field.getType()));
                }
            }

            return instance;
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Método auxiliar para convertir un String a un tipo específico de campo
    private Object convertStringToFieldType(String stringValue, Class<?> fieldType) {
        if (fieldType == String.class) {
            return stringValue;
        } else if (fieldType == Integer.class || fieldType == int.class) {
            return Integer.parseInt(stringValue);
        } else if (fieldType == Double.class || fieldType == double.class) {
            return Double.parseDouble(stringValue);
        } else if (fieldType == Long.class || fieldType == long.class) {
            return Long.parseLong(stringValue);
        } else if (fieldType == Boolean.class || fieldType == boolean.class) {
            return Boolean.parseBoolean(stringValue);
        }
        return stringValue;
    }

    public void registerUser(String elementType, Object user) {
        Document document = readXML(filePath);
        Element rootElement = document.getRootElement();

        // Crea un nuevo elemento para el usuario
        Element newUserElement = convertObjectToElement(user, elementType);

        // Agrega el nuevo elemento al elemento raíz
        rootElement.addContent(newUserElement);

        // Guarda los cambios en el archivo XML
        saveXML(document, filePath);
    }

    private Element convertObjectToElement(Object object, String elementType) {
        Element element = new Element(elementType);

        // Obtén todos los campos de la clase del objeto
        Field[] fields = object.getClass().getDeclaredFields();

        // Itera sobre los campos y agrega elementos al XML
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                String fieldName = field.getName();
                Object fieldValue = field.get(object);

                // Crea un nuevo elemento para cada campo y agrega al elemento principal
                Element fieldElement = new Element(fieldName);
                fieldElement.setText(String.valueOf(fieldValue));
                element.addContent(fieldElement);

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return element;
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



