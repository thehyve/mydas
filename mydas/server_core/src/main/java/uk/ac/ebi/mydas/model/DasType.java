/*
 * Copyright 2007 Philip Jones, EMBL-European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 * For further details of the mydas project, including source code,
 * downloads and documentation, please see:
 *
 * http://code.google.com/p/mydas/
 *
 */

package uk.ac.ebi.mydas.model;

import java.io.Serializable;

/**
 * Created Using IntelliJ IDEA.
 * Date: 17-May-2007
 * Time: 14:41:39
 *
 * @author Phil Jones, EMBL-EBI, pjones@ebi.ac.uk
 *
 * This class has two jobs:
 * Loading the types from the server configuration and holding details of the types
 * for a particular request.
 */
@SuppressWarnings("serial")
public class DasType implements Serializable {

    /**
     * the type id
     */
    private String id;

    /**
     * The category of the type (optional).
     */
    private String category;

    /**
     * @deprecated
     * the method of the type (optional).
     */
    private String method;

    /**
     * The cvId attribute (optional, recommended) is the ID of a term from a relevant controlled vocabulary (SO, MOD, BS).
     */
    private String cvId;

    private String label;

    /**
     * Constructor for a DasType object.  The id field is mandatory, however the category and / or the method
     * are optional parameters.
     * @param id <b>Mandatory</b> the id of the type. Will throw an IllegalArgumentException if this is not set
     * to a non-null, non-zero length String.
     * @param category <i>Optional</i> the category of the type.
     * @param cvId <i>Optional, Recommended </i> is the ID of a term from a relevant controlled vocabulary (SO, MOD, BS).
     */
    public DasType(String id, String category, String cvId,String label){
        if (id == null || id.length() == 0){
            throw new IllegalArgumentException("id must not be null or an empty String");
        }
        this.id = id;
        this.category = category;
        this.cvId = cvId;
        this.label=label;
    }

    /**
     * Returns the type id.
     * @return the type id.
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the category, or null if this has not been set.
     * @return the category, or null if this has not been set.
     */
    public String getCategory() {
        return category;
    }

    /**
     * @deprecated
     * Returns the method, or null if this has not been set.
     * @return the method, or null if this has not been set.
     */
    public String getMethod() {
        return method;
    }


    /**
     * Implementation of equals method.
     * @param o object to compare with.
     * @return boolean indicating equality
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DasType dasType = (DasType) o;

        if (category != null ? !category.equals(dasType.category) : dasType.category != null) return false;
        if (!id.equals(dasType.id)) return false;
        if (cvId != null ? !cvId.equals(dasType.cvId) : dasType.cvId != null) return false;

        return true;
    }

    /**
     * Implementation of hashcode method.
     * @return unique integer for each (distinct, by equals method) instance.
     */
    public int hashCode() {
        int result;
        result = id.hashCode();
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (cvId != null ? cvId.hashCode() : 0);
        return result;
    }

    /**
     * To string simple representation of this object.
     * @return simple representation of this object.
     */
    public String toString(){
        StringBuffer buf = new StringBuffer("DasType.  id: '");
        buf .append (id)
            .append ("' category: '")
            .append ((category == null) ? "null" : category)
            .append ("' method: '")
            .append ((cvId == null) ? "null" : cvId)
            .append ("'");
        return buf.toString();
    }

	public String getCvId() {
		return cvId;
	}
	public String getLabel() {
		return label;
	}

    public void setId(String id) {
        this.id = id;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setCvId(String cvId) {
        this.cvId = cvId;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
