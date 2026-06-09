package br.ufc.quixada.reserva.rmi;

public class Mensagem implements java.io.Serializable {

    private int messageType;
    private int requestId;
    private String objectReference;
    private String methodId;
    private String arguments;

    public Mensagem() {
    }

    public Mensagem(
            int messageType,
            int requestId,
            String objectReference,
            String methodId,
            String arguments) {

        this.messageType = messageType;
        this.requestId = requestId;
        this.objectReference = objectReference;
        this.methodId = methodId;
        this.arguments = arguments;
    }

    public int getMessageType() {
        return messageType;
    }

    public int getRequestId() {
        return requestId;
    }

    public String getObjectReference() {
        return objectReference;
    }

    public String getMethodId() {
        return methodId;
    }

    public String getArguments() {
        return arguments;
    }
}