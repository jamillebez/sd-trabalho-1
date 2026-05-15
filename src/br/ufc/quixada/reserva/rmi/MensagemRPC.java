package br.ufc.quixada.reserva.rmi;
import com.google.gson.Gson;

public class MensagemRPC {
    public int messageType;        
    public int requestId;          
    public String objectReference; 
    public String methodId;        
    public String arguments;       

    public MensagemRPC() {} 

    public MensagemRPC(int messageType, int requestId, String objectReference, String methodId, String arguments) {
        this.messageType = messageType;
        this.requestId = requestId;
        this.objectReference = objectReference;
        this.methodId = methodId;
        this.arguments = arguments;
    }

    public byte[] toBytes() {
        return new Gson().toJson(this).getBytes();
    }

    public static MensagemRPC fromBytes(byte[] data) {
        String json = new String(data).trim();
        return new Gson().fromJson(json, MensagemRPC.class);
    }
}