package br.ufc.quixada.reserva.eleicao;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class Servidor {

    static Map<Integer, String> candidatos = new ConcurrentHashMap<>();
    static Map<Integer, Integer> votos = new ConcurrentHashMap<>();

    static long fimVotacao = System.currentTimeMillis() + 60000;

    public static void main(String[] args) throws Exception {

        candidatos.put(1, "João");
        candidatos.put(2, "Rafael");
        candidatos.put(3, "Braga");

        votos.put(1, 0);
        votos.put(2, 0);
        votos.put(3, 0);

        ServerSocket server = new ServerSocket(12345);
        System.out.println("Servidor rodando...");

        while (true) {
            Socket cliente = server.accept();
            new Thread(() -> atender(cliente)).start();
        }
    }

    static void executarMulticast() {
        try {
            MulticastSocket socket = new MulticastSocket(4446);
            InetAddress grupo = InetAddress.getByName("230.0.0.0");
            socket.joinGroup(grupo);

            byte[] buffer = new byte[256];
            String resultado = "Resultado da eleição:";
            for (Map.Entry<Integer, String> entry : candidatos.entrySet()) {
                resultado += "\n" + entry.getValue() + ": " + votos.get(entry.getKey()) + " votos";
            }
            resultado += "\n Porcentagem: ";
            int totalVotos = 0;
            for (Integer voto : votos.values()) {
                totalVotos += voto;
            }
            for (Map.Entry<Integer, String> entry : candidatos.entrySet()) {
                resultado += "\n" + entry.getValue() + ": " + (totalVotos > 0 ? (votos.get(entry.getKey()) * 100 / totalVotos) : 0) + "%";
            }
            String vencedor = candidatos.get(
                Collections.max(votos.entrySet(), Map.Entry.comparingByValue()).getKey()
                );
            resultado += "\n Vencedor: " + vencedor;
            buffer = resultado.getBytes();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, grupo, 4446);
            socket.send(packet);

            socket.leaveGroup(grupo);
            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void atender(Socket cliente) {
        try {
            InputStream in = cliente.getInputStream();
            OutputStream out = cliente.getOutputStream();

            int tam = in.read();
            byte[] buffer = new byte[tam];
            in.read(buffer);
            String msg = new String(buffer);

            System.out.println("Recebido: " + msg);

            if (msg.contains("login")) {
                enviarLista(out);
            }

            if (msg.contains("voto")) {
                if (System.currentTimeMillis() > fimVotacao) {
                    enviar(out, "{\"tipo\":\"erro\",\"msg\":\"Tempo encerrado\"}");
                } else {
                    int id = extrairId(msg);
                    votos.put(id, votos.get(id) + 1);
                    enviar(out, "{\"tipo\":\"ok\"}");
                }
            }

            if (msg.contains("comandoAdicionarCandidato")) {
                String nome = msg.split(":")[2];
                int id = candidatos.size() + 1;
                candidatos.put(id, nome);
                votos.put(id, 0);
                enviar(out, "{\"tipo\":\"ok\",\"msg\":\"Candidato " + nome + " adicionado com id " + id + "\"}");
            }

            if (msg.contains("comandoRemoverCandidato")) {
                int id = extrairId(msg);
                String nome = candidatos.remove(id);
                votos.remove(id);
                enviar(out, "{\"tipo\":\"ok\",\"msg\":\"Candidato " + nome + " removido\"}");
            }

            if (msg.contains("comandoEmitirNota")) {
                if (System.currentTimeMillis() < fimVotacao) {
                    enviar(out, "{\"tipo\":\"erro\",\"msg\":\"Tempo não encerrado\"}");
                } else {
                    enviar(out, "{\"tipo\":\"ok\",\"msg\":\"Emitindo nota...\"}");
                    executarMulticast();
                }
            }

            cliente.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void enviarLista(OutputStream out) throws Exception {
        String json = "{\"tipo\":\"lista\",\"candidatos\":[]}";
        for (Map.Entry<Integer, String> entry : candidatos.entrySet()) {
            json = json.replace("]", ",{\"id\":" + entry.getKey() + ",\"nome\":\"" + entry.getValue() + "\"}]");
        }
        enviar(out, json);
    }

    static void enviar(OutputStream out, String msg) throws Exception {
        byte[] b = msg.getBytes();
        out.write(b.length);
        out.write(b);
        out.flush();
    }

    static int extrairId(String json) {
        return json.contains("1") ? 1 : json.contains("2") ? 2 : 3;
    }
}