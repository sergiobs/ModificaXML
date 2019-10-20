import java.io.File;
import java.io.IOException;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.xpath.*;

public class ModifyXMLFile_DOM {
	public static void main(String argv[]) throws XPathExpressionException, TransformerConfigurationException, TransformerFactoryConfigurationError {
 	   try {
		String filepath_S3e = "c:\\temp\\ORI_3463_2_0-red.xml";
		
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document doc_S3e3 = docBuilder.parse(filepath_S3e);

	    //Obtener el elemento raíz del documento
	    Element raiz = doc_S3e3.getDocumentElement();
	    
	    //Obtener la lista de nodos que tienen etiqueta "MCS"
	    NodeList listaMCS = raiz.getElementsByTagName("MCS");
	    
	    //Recorrer la lista de empleados
	    for(int i=0; i<listaMCS.getLength(); i++) {   
	    	System.out.println("longitud: " + listaMCS.getLength());
	        //Obtener de la lista un empleado tras otro
	        Node MCS = listaMCS.item(i);
	        
            // borramos el nodo cuyo atributo cumpla un criterio
            for (int aa=0;aa<MCS.getAttributes().getLength();aa++) {
            	if (MCS.getAttributes().item(aa).getNodeName()=="Identificador") {
            		if (MCS.getAttributes().item(aa).getNodeValue().equals("20500")) {
            			System.out.println("Se borra: " + MCS.getAttributes().item(aa).getNodeValue().equals("20500"));
            			MCS.getParentNode().removeChild(MCS);
            		}
            	}
            }

	        System.out.print(MCS.getNodeName()+": ");
            for (int aa=0;aa<MCS.getAttributes().getLength();aa++) {
            	System.out.print(MCS.getAttributes().item(aa).getNodeName()+ ": " + MCS.getAttributes().item(aa).getNodeValue()+", ");
            }
            System.out.println();
            

	        NodeList datosMCS = MCS.getChildNodes();
	        for(int j=0; j<datosMCS.getLength(); j++) {

	            Node dato = datosMCS.item(j);
	            //Comprobar que el dato se trata de un nodo de tipo Element
	            if(dato.getNodeType()==Node.ELEMENT_NODE) {
	                //Mostrar el nombre del tipo de dato
	                System.out.print(dato.getNodeName()+": ");
	                
	                //leo el numero de atributos de "Umbrales de salida" y hago un bucle para imprimirlos	                
	                for (int aa=0;aa<dato.getAttributes().getLength();aa++) {
	                	System.out.print(dato.getAttributes().item(aa).getNodeName()+ ": " + dato.getAttributes().item(aa).getNodeValue()+", ");
	                }
	                System.out.println();
	            }
	        }
	        
	        // Exportar nuevamente el XML
	        Transformer transformer = TransformerFactory.newInstance().newTransformer();
	        StreamResult output = new StreamResult(new File("c:\\temp\\ORI_3463_2_0-red_modificado.xml"));
	        Source input = new DOMSource(doc_S3e3);
	        try {
				transformer.transform(input, output);
			} catch (TransformerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	    }
		

	   } catch (ParserConfigurationException pce) {
		pce.printStackTrace();
	   } catch (IOException ioe) {
		ioe.printStackTrace();
	   } catch (SAXException sae) {
		sae.printStackTrace();
	   }
	}
}