package com.rjtech.rjs.persistence.idgenerator;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.rjtech.rjs.persistence.sequence.idgenerator.impl.SequenceNotSupportedIDGenerator;
import com.rjtech.rjs.persistence.sequence.idgenerator.impl.SequenceSupportedIDGenerator;
import com.rjtech.rjs.persistence.sequence.idgenerator.IDGenerator;

public final class RJSIDGeneratorFactory {

    private RJSIDGeneratorFactory() {

    }

    private static Map<String, IDGenerator> idGeneratorMap = Collections
            .synchronizedMap(new HashMap<String, IDGenerator>());
    private static final String SEQUENCE_SUPPORTED_KEY = "SEQUENCE_SUPPORTED";
    private static final String SEQUENCE_NOT_SUPPORTED_KEY = "SEQUENCE_NOT_SUPPORTED";

    public static IDGenerator getSequenceGenerator(boolean isSequenceSupported) {

        if (isSequenceSupported) {
            if (!idGeneratorMap.containsKey(SEQUENCE_SUPPORTED_KEY)) {
                idGeneratorMap.put(SEQUENCE_SUPPORTED_KEY, new SequenceSupportedIDGenerator());
            }
        } else {
            if (!idGeneratorMap.containsKey(SEQUENCE_NOT_SUPPORTED_KEY)) {
                idGeneratorMap.put(SEQUENCE_NOT_SUPPORTED_KEY, new SequenceNotSupportedIDGenerator());
            }
        }
        return idGeneratorMap.get(isSequenceSupported ? SEQUENCE_SUPPORTED_KEY : SEQUENCE_NOT_SUPPORTED_KEY);
    }

}
