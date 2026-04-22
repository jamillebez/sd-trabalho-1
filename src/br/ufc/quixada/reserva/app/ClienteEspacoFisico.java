package br.ufc.quixada.reserva.app;

import java.io.*;
import java.net.*;
import br.ufc.quixada.reserva.model.*;

public class ClienteEspacoFisico {

    public static void main(String[] args) throws Exception {

        Socket socket = new Socket("localhost", 12345);

        OutputStream out = socket.getOutputStream();
        InputStream in = socket.getInputStream();

        EspacoFisico espaco = new Sala(1, "Sala ufc", 50, true);

        System.out.println("Enviando dados do espaço físico...");
        System.out.println("Nome: " + espaco.getNome());
        System.out.println("Capacidade: " + espaco.getCapacidade());

        byte[] nomeBytes = espaco.getNome().getBytes();

        out.write(nomeBytes.length);
        out.write(nomeBytes);
        out.write(espaco.getCapacidade());

        out.flush();

        int tamanho = in.read();
        byte[] buffer = new byte[tamanho];

        int total = 0;
        while (total < tamanho) {
            int lido = in.read(buffer, total, tamanho - total);
            total += lido;
        }

        String resposta = new String(buffer);
        System.out.println("Resposta: " + resposta);

        socket.close();
    }
}