package uk.ac.ebi.mydas.extendedmodel;

import java.io.IOException;

import org.xmlpull.v1.XmlSerializer;

import uk.ac.ebi.mydas.exceptions.DataSourceException;
import uk.ac.ebi.mydas.model.DasMethod;

@SuppressWarnings("serial")
public class DasMethodE extends DasMethod {

	/**
	 * Constructor to create a method from the value of its content
	 * @param id Method id
	 * @param label label of the method
	 * @throws DataSourceException 
	 */
	public DasMethodE(String id, String label,String cvId) throws DataSourceException {
		super(id, label,cvId);
	}
	/**
	 * Constructor to create a method from the parent
	 * @param id Method id
	 * @param label label of the method
	 * @throws DataSourceException 
	 */
	public DasMethodE(DasMethod method) throws DataSourceException {
		super(method.getId(), method.getLabel(),method.getCvId());
	}
	/**
	 * Generates the piece of XML into the XML serializer object to describe a DasMethod 
	 * @param DAS_XML_NAMESPACE XML namespace to link with the elements to create
	 * @param serializer Object where the XML is been written 
	 * @throws IOException If the XML writer have an error
	 * @throws IllegalStateException a method has been invoked at an illegal or inappropriate time.
	 * @throws IllegalArgumentException indicate that a method has been passed an illegal or inappropriate argument.
	 */
	public void serialize(String DAS_XML_NAMESPACE,XmlSerializer serializer)throws IllegalArgumentException, IllegalStateException, IOException{
        serializer.startTag(DAS_XML_NAMESPACE, "METHOD");
        if (this.getId() != null && this.getId().length() > 0){
            serializer.attribute(DAS_XML_NAMESPACE, "id", this.getId());
        }
        if (this.getCvId() != null && this.getCvId().length() > 0){
            serializer.attribute(DAS_XML_NAMESPACE, "cvId", this.getCvId());
        }
        if (this.getLabel() != null && this.getLabel().length() > 0){
            serializer.text(this.getLabel());
        }
        serializer.endTag(DAS_XML_NAMESPACE, "METHOD");
	}

}
