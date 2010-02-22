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

package uk.ac.ebi.mydas.controller;

import uk.ac.ebi.mydas.model.DasAnnotatedSegment;
import uk.ac.ebi.mydas.model.DasComponentFeature;
import uk.ac.ebi.mydas.model.DasFeature;
import uk.ac.ebi.mydas.model.DasGroup;
import uk.ac.ebi.mydas.exceptions.DataSourceException;
import uk.ac.ebi.mydas.extendedmodel.*;

import java.io.IOException;
import java.util.Collection;

import org.xmlpull.v1.XmlSerializer;

/**
 * Created using IntelliJ IDEA.
 * Date: 12-May-2007
 * Time: 11:49:57
 *
 * @author Phil Jones, EMBL-EBI, pjones@ebi.ac.uk
 *
 * Wraps a DasAnnotatedSegment object with details of the request encapsulated, i.e. the requested
 * start and stop coordinates.
 */
public class FoundFeaturesReporter implements SegmentReporter {

    private final boolean restricted;

    private Integer requestedStart;

    private Integer requestedStop;

    private final DasAnnotatedSegment annotatedSegment;

    FoundFeaturesReporter(DasAnnotatedSegment annotatedSegment, SegmentQuery segmentQuery){
        this.restricted = segmentQuery.getStartCoordinate() != null;
        if (restricted){
            this.requestedStart = segmentQuery.getStartCoordinate();
            this.requestedStop = segmentQuery.getStopCoordinate();
        }
        this.annotatedSegment = annotatedSegment;
    }

    FoundFeaturesReporter(DasAnnotatedSegment annotatedSegment){
        this.restricted = false;
        this.annotatedSegment = annotatedSegment;
    }

    Collection<DasFeature> getFeatures(boolean strictlyEnclosed) {
        return (restricted)
                ? annotatedSegment.getFeatures(requestedStart, requestedStop, strictlyEnclosed)
                : annotatedSegment.getFeatures();
    }

    public Integer getStart(){
        return (restricted)
                ? requestedStart
                : annotatedSegment.getStartCoordinate();
    }

    public Integer getStop(){
        return (restricted)
                ? requestedStop
                : annotatedSegment.getStopCoordinate();
    }

    public String getSegmentId(){
        return annotatedSegment.getSegmentId();
    }

    String getSegmentLabel(){
        return annotatedSegment.getSegmentLabel();
    }

    String getType(){
        return annotatedSegment.getType();
    }

    String getVersion(){
        return annotatedSegment.getVersion();
    }
    
    void serialize(String DAS_XML_NAMESPACE,XmlSerializer serializer,DasFeatureRequestFilter filter,boolean categorize,boolean isFeaturesStrictlyEnclosed, boolean isUseFeatureIdForFeatureLabel) throws IllegalArgumentException, IllegalStateException, IOException, DataSourceException{
        serializer.startTag(DAS_XML_NAMESPACE, "SEGMENT");
        serializer.attribute(DAS_XML_NAMESPACE, "id", this.getSegmentId());
        serializer.attribute(DAS_XML_NAMESPACE, "start", (this.getStart() == null)
                ? ""
                : Integer.toString(this.getStart()));
        serializer.attribute(DAS_XML_NAMESPACE, "stop", (this.getStop() == null)
                ? ""
                : Integer.toString(this.getStop()));
        if (this.getType() != null && this.getType().length() > 0){
            serializer.attribute(DAS_XML_NAMESPACE, "type", this.getType());
        }
        serializer.attribute(DAS_XML_NAMESPACE, "version", this.getVersion());
        if (this.getSegmentLabel() != null && this.getSegmentLabel().length() > 0){
            serializer.attribute(DAS_XML_NAMESPACE, "label", this.getSegmentLabel());
        }
        for (DasFeature feature : this.getFeatures(isFeaturesStrictlyEnclosed)){
        	(new DasFeatureE(feature)).serialize(DAS_XML_NAMESPACE, serializer, filter, categorize, isFeaturesStrictlyEnclosed, isUseFeatureIdForFeatureLabel);
        }
        serializer.endTag(DAS_XML_NAMESPACE, "SEGMENT");
    	
    }

}
