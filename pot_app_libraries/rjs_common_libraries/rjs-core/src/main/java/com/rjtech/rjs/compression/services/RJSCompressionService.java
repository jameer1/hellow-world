package com.rjtech.rjs.compression.services;

import com.rjtech.rjs.compression.exception.CompressionServiceException;
import com.rjtech.rjs.compression.dto.RJSZippedObject;

public interface RJSCompressionService<E, R> {

    RJSZippedObject compressObject(E object) throws CompressionServiceException;

    R unCompressObject(RJSZippedObject rjsZippedObject) throws CompressionServiceException;

}
