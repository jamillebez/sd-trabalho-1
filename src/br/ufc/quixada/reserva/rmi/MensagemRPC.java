package br.ufc.quixada.reserva.rmi;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        String json = "{" 
                + "\"messageType\":" + messageType + ","
                + "\"requestId\":" + requestId + ","
                + "\"objectReference\":\"" + encode(objectReference) + "\"," 
                + "\"methodId\":\"" + encode(methodId) + "\"," 
                + "\"arguments\":\"" + encode(arguments) + "\"" 
                + "}";
        return json.getBytes(StandardCharsets.UTF_8);
    }

    public static MensagemRPC fromBytes(byte[] data) {
        String json = new String(data, StandardCharsets.UTF_8).trim();
        int messageType = extractInt(json, "messageType");
        int requestId = extractInt(json, "requestId");
        String objectReference = extractString(json, "objectReference");
        String methodId = extractString(json, "methodId");
        String arguments = extractString(json, "arguments");
        return new MensagemRPC(messageType, requestId, objectReference, methodId, arguments);
    }

    private static int extractInt(String json, String key) {
        Pattern pattern = Pattern.compile("\\\"" + key + "\\\"\\s*:\\s*(\\d+)");
        Matcher matcher = pattern.matcher(json);
        return matcher.find() ? Integer.parseInt(matcher.group(1)) : 0;
    }

    private static String extractString(String json, String key) {
        Pattern pattern = Pattern.compile("\\\"" + key + "\\\"\\s*:\\s*\\\"(.*?)\\\"");
        Matcher matcher = pattern.matcher(json);
        return matcher.find() ? decode(matcher.group(1)) : "";
    }

    private static String encode(String value) {
        if (value == null) {
            return "";
        }
        return Base64.getEncoder().encodeToString(value.getBytes(StandardCharsets.UTF_8));
    }

    private static String decode(String value) {
        if (value == null || value.isEmpty()) {
            return "";
        }
        return new String(Base64.getDecoder().decode(value), StandardCharsets.UTF_8);
    }
}