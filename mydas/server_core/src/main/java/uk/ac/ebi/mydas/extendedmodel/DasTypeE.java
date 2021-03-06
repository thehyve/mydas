package uk.ac.ebi.mydas.extendedmodel;

import java.io.IOException;

import org.xmlpull.v1.XmlSerializer;

import uk.ac.ebi.mydas.model.DasType;

@SuppressWarnings("serial")
public class DasTypeE extends DasType {

	public DasTypeE(String id, String category, String cvId,String label) {
		super(id, category, cvId,label);
	}
	public DasTypeE(DasType type) {
		super(type.getId(), type.getCategory(), type.getCvId(),type.getLabel());
	}
	public void serialize(String DAS_XML_NAMESPACE, XmlSerializer serializer, Integer count, boolean featuresCommand) throws IllegalArgumentException, IllegalStateException, IOException{
		this.serialize( DAS_XML_NAMESPACE, serializer, count, false, false, false, featuresCommand);
	}	

	/**
	 * Generates the piece of XML into the XML serializer object to describe a DasType 
	 * @param DAS_XML_NAMESPACE XML namespace to link with the elements to create
	 * @param serializer Object where the XML is been written 
	 * @param count number of this type in the requested segment. null if not desirable
	 * @param hasReferenceFeatures indicates if this type has any reference
	 * @param hasSubParts indicates if this type has any sub part
	 * @param hasSuperParts indicates if this type has any super part
     * @param featuresCommand indicates whether this method was called from features command or not
	 * @throws IOException If the XML writer have an error
	 * @throws IllegalStateException a method has been invoked at an illegal or inappropriate time.
	 * @throws IllegalArgumentException indicate that a method has been passed an illegal or inappropriate argument.
	 */
	public void serialize(String DAS_XML_NAMESPACE, XmlSerializer serializer, Integer count, boolean hasReferenceFeatures, boolean hasSubParts, boolean hasSuperParts, boolean featuresCommand)
			throws IllegalArgumentException, IllegalStateException, IOException{
        //categorize indicates if the categories will be included in the type of the feature --> this parameter (right next to count) was removed since 1.6.1
		serializer.startTag(DAS_XML_NAMESPACE, "TYPE");
		serializer.attribute(DAS_XML_NAMESPACE, "id", this.getId());
		if (this.getCvId() != null && this.getCvId().length() > 0){
			serializer.attribute(DAS_XML_NAMESPACE, "cvId", this.getCvId());
		}
		// Handle DasReferenceFeatures.
		if (hasReferenceFeatures){
			serializer.attribute(DAS_XML_NAMESPACE, "reference", "yes");
			serializer.attribute(DAS_XML_NAMESPACE, "superparts", (hasSuperParts) ? "yes" : "no");
			serializer.attribute(DAS_XML_NAMESPACE, "subparts", (hasSubParts) ? "yes" : "no");
		}
		//if (categorize){
			if (this.getCategory() != null && this.getCategory().length() > 0){
				serializer.attribute(DAS_XML_NAMESPACE, "category", this.getCategory());
			} //else {
				// To prevent the DAS server from dying, if no category has been set, but
				// a category is required, spit out the type ID again as the category.
				//serializer.attribute(DAS_XML_NAMESPACE, "category", this.getId());
			//}
		//}

        if (featuresCommand) {
            //Tag content should be the label
            if (this.getLabel() != null && this.getLabel().length() > 0){
			    serializer.text(this.getLabel());
            }
        } else {
            //Tag content should be the count
            if (count != null){
                serializer.text(Integer.toString(count));
            }
        }
		serializer.endTag(DAS_XML_NAMESPACE, "TYPE");

	}

}
