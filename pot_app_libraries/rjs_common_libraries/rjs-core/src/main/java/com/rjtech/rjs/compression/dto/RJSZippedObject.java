package com.rjtech.rjs.compression.dto;

import java.io.Serializable;
import java.util.Arrays;

import com.rjtech.rjs.compression.exception.CompressionServiceException;

public class RJSZippedObject implements Serializable {
    private static final long serialVersionUID = -3358536005963926858L;

    private byte[] byteArray = null;

    public RJSZippedObject(byte[] byteArray) throws CompressionServiceException {
        this.constructByteArray(byteArray);
    }

    public byte[] getByteArray() {
        return null != byteArray ? byteArray.clone() : null;
    }

    public void setByteArray(byte[] byteArray) {
        this.constructByteArray(byteArray);
    }

    private void constructByteArray(byte[] byteArray) {
        if (null != byteArray) {
            this.byteArray = Arrays.copyOf(byteArray, byteArray.length);
        } else {
            this.byteArray = null;
        }
    }
}
