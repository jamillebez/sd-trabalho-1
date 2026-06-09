package br.ufc.quixada.reserva.rmi;

import java.io.Serializable;

public class RemoteObjectRef implements Serializable {

    private String objectReference;

    public RemoteObjectRef(String objectReference) {
        this.objectReference = objectReference;
    }

    public String getObjectReference() {
        return objectReference;
    }
}