package br.ufc.quixada.reserva.app;

import java.io.*;
import java.net.*;

public class ServidorEspacoFisico {

    // Servidor do exercicio 4
    public static void main(String[] args) throws Exception {

        ServerSocket server = new ServerSocket(12345);
        System.out.println("Servidor funcionando...");

        while (true) {
            Socket cliente = server.accept();

            try {
                InputStream in = cliente.getInputStream();
                OutputStream out = cliente.getOutputStream();

                System.out.println("Receber dados do cliente...");

                int tamanhoNome = in.read();
                byte[] nomeBytes = new byte[tamanhoNome];

                int total = 0;
                while (total < tamanhoNome) {
                    int lido = in.read(nomeBytes, total, tamanhoNome - total);
                    total += lido;
                }

                String nome = new String(nomeBytes);

                int capacidade = in.read();

                System.out.println("Reservado: " + nome + " - Capacidade: " + capacidade);

                String resposta = "Reserva concluída para " + nome;

                byte[] respBytes = resposta.getBytes();
                out.write(respBytes.length);
                out.write(respBytes);
                out.flush();

                cliente.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}