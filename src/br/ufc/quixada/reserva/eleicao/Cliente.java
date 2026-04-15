package br.ufc.quixada.reserva.eleicao;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Cliente {

    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);

        new Thread(() -> executarMulticast()).start();

        Socket socket = new Socket("localhost", 12345);
        OutputStream out = socket.getOutputStream();
        InputStream in = socket.getInputStream();

        System.out.print("Digite seu nome: ");
        String nome = scanner.nextLine();

        System.out.print("Você é admin (s/n): ");
        String isAdmin = scanner.nextLine();
        boolean isAdminBool = "s".equals(isAdmin);

        enviar(out, "{\"tipo\":\"login\",\"usuario\":\"" + nome + "\", \"admin\":" + isAdminBool + "}");

        String lista = receber(in);
        System.out.println("Lista: " + lista);

        socket.close();

        while (true) {
            Socket votoSocket = new Socket("localhost", 12345);
            OutputStream out2 = votoSocket.getOutputStream();
            InputStream in2 = votoSocket.getInputStream();

            if (isAdminBool) {
                System.out.print("Deseja adicionar candidato (a), remover candidato (r) ou emitir nota(e): ");
                String opcao = scanner.nextLine();

                if ("a".equals(opcao)) {
                    System.out.print("Digite o nome do candidato: ");
                    String nomeCandidato = scanner.nextLine();
                    enviar(out2, "{\"tipo\":\"comandoAdicionarCandidato\",\"nome\":\"" + nomeCandidato + "\"}");
                    System.out.println("Resposta adicionar candidato: " + receber(in2));
                }
                if ("r".equals(opcao)) {
                    System.out.print("Digite o ID do candidato a remover: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    enviar(out2, "{\"tipo\":\"comandoRemoverCandidato\",\"id\":" + id + "}");
                    System.out.println("Resposta remover candidato: " + receber(in2));
                }
                if ("e".equals(opcao)) {
                    enviar(out2, "{\"tipo\":\"comandoEmitirNota\"}");
                    System.out.println("Emitindo nota...");
                }
                votoSocket.close();
            }
            else{
                System.out.print("Qual candidato deseja votar: ");
                int id = scanner.nextInt();

                enviar(out2, "{\"tipo\":\"voto\",\"candidatoId\":" + id + "}");

                System.out.println("Resposta voto: " + receber(in2));
                votoSocket.close();
                break;
            }
        }
    }

    static void enviar(OutputStream out, String msg) throws Exception {
        byte[] b = msg.getBytes();
        out.write(b.length);
        out.write(b);
        out.flush();
    }

    static String receber(InputStream in) throws Exception {
        int tam = in.read();
        byte[] buffer = new byte[tam];
        in.read(buffer);
        return new String(buffer);
    }

    static void executarMulticast() {
        try {
            MulticastSocket socket = new MulticastSocket(4446);
            InetAddress grupo = InetAddress.getByName("230.0.0.0");
            socket.joinGroup(grupo);

            byte[] buffer = new byte[256];

            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                String msg = new String(packet.getData(), 0, packet.getLength());
                System.out.println("AVISO: " + msg);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}