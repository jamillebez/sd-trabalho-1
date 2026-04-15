package br.ufc.quixada.reserva.app;

import br.ufc.quixada.reserva.model.*;
import br.ufc.quixada.reserva.stream.EspacoFisicoOutputStream;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class TesteEspacoFisicoOutputStream {
    public static void main(String[] args) {
        // Testes para o item (i), (ii) e (iii) do exercício 2 - OutputStream
        try {

            EspacoFisico[] espacos = new EspacoFisico[3];
            espacos[0] = new Sala(1, "Sala Bloco 1", 40, true);
            espacos[1] = new Laboratorio(2, "Lab de Redes - Bloco 2", 30, 30);
            espacos[2] = new Auditorio(3, "Auditorio Principal Quixadá", 150, true);

            System.out.println("--- Teste (i): Saída Padrão (System.out) ---");
            EspacoFisicoOutputStream streamConsole = new EspacoFisicoOutputStream(espacos, 3, System.out);
            streamConsole.transmitirDados();
            System.out.println("\n[Dados enviados para console com sucesso]\n");

            System.out.println("--- Teste (ii): Arquivo (FileOutputStream) ---");
            File arquivoSaida = new File("dados_espacos.dat");
            try (FileOutputStream fos = new FileOutputStream(arquivoSaida)) {
                EspacoFisicoOutputStream streamArquivo = new EspacoFisicoOutputStream(espacos, 3, fos);
                streamArquivo.transmitirDados();
                System.out.println("[Dados salvos no arquivo: " + arquivoSaida.getAbsolutePath() + "]\n");
            }

            System.out.println("--- Teste (iii): Servidor Remoto (TCP) ---");
            while (true) {
                try (Socket socket = new Socket("localhost", 9090)) {
                    OutputStream socketOut = socket.getOutputStream();
                    EspacoFisicoOutputStream streamTcp = new EspacoFisicoOutputStream(espacos, 3, socketOut);
                    streamTcp.transmitirDados();
                    System.out.println("[Cliente] Dados transmitidos via TCP.");
                }

                Thread.sleep(1000);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}