package br.ufc.quixada.reserva.app;

import br.ufc.quixada.reserva.model.EspacoFisico;
import br.ufc.quixada.reserva.stream.EspacoFisicoOutputStream;
import br.ufc.quixada.reserva.stream.EspacoFisicoInputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TesteEspacoFisicoInputStream {
    // Testes para o item (i), (ii) e (iii) do exercício 3 - InputStream
    public static void main(String[] args) throws Exception {
        EspacoFisico[] espacos = {
            new EspacoFisico(1, "Espaco A", 30) { },
            new EspacoFisico(2, "Espaco B", 20) { },
            new EspacoFisico(3, "Espaco C", 50) { }
        };

        System.out.println("--- Teste (i) com System.in ---");
        InputStream antigo = System.in;
        try {
            ByteArrayOutputStream saidaMemoria = new ByteArrayOutputStream();
            new EspacoFisicoOutputStream(espacos, espacos.length, saidaMemoria).transmitirDados();
            System.setIn(new ByteArrayInputStream(saidaMemoria.toByteArray()));

            EspacoFisico[] lidosSystemIn = new EspacoFisicoInputStream(System.in).ler();
            for (EspacoFisico espaco : lidosSystemIn) {
                System.out.println(espaco.getId() + " - " + espaco.getNome() + " - " + espaco.getCapacidade());
            }
        } finally {
            System.setIn(antigo);
        }

        System.out.println();
        System.out.println("--- Teste (ii) com arquivo ---");
        try (FileInputStream entrada = new FileInputStream("dados_espacos.dat")) {
            EspacoFisico[] lidosArquivo = new EspacoFisicoInputStream(entrada).ler();
            for (EspacoFisico espaco : lidosArquivo) {
                System.out.println(espaco.getId() + " - " + espaco.getNome() + " - " + espaco.getCapacidade());
            }
        }

        System.out.println();
        System.out.println("--- Teste (iii) com TCP ---");
        Thread servidor = new Thread(() -> {
            try (ServerSocket server = new ServerSocket(12345);
                 Socket cliente = server.accept()) {
                EspacoFisico[] lidosTcp = new EspacoFisicoInputStream(cliente.getInputStream()).ler();
                for (EspacoFisico espaco : lidosTcp) {
                    System.out.println(espaco.getId() + " - " + espaco.getNome() + " - " + espaco.getCapacidade());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        servidor.start();
        Thread.sleep(300);

        try (Socket socket = new Socket("localhost", 12345)) {
            new EspacoFisicoOutputStream(espacos, espacos.length, socket.getOutputStream()).transmitirDados();
        }

        servidor.join();
    }
}