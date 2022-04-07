package com.rjtech.register.fixedassets.model;

import java.io.Serializable;

public interface Identifiable<T extends Serializable> {
    T getId();
}
