package br.ufc.quixada.reserva.trabalho2;

import java.rmi.Naming;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import br.ufc.quixada.reserva.rmi.RemoteObjectRef;
import br.ufc.quixada.reserva.service.ServicoEspaco;
import br.ufc.quixada.reserva.service.ServicoReserva;
import com.google.gson.Gson;

import br.ufc.quixada.reserva.model.*;

public class ClienteEspacoFisico {
    
    private static String ipServidor = "localhost";
    private static int portaServidor = 1099;

    public static void main(String[] args) throws Exception {
        ServicoReserva servico =
                (ServicoReserva)
                        Naming.lookup(
                                "rmi://" + ipServidor + ":" + portaServidor + "/ServicoReserva");

        ServicoEspaco servicoEspaco =
        (ServicoEspaco) Naming.lookup(
                "rmi://" + ipServidor + ":" + portaServidor + "/ServicoEspaco");

        Gson gson = new Gson();
        Scanner scanner = new Scanner(System.in);

        Usuario usuario = new Usuario(
                1,
                "Vitor");

        while (true) {

            System.out.println("\n===== MENU =====");
            System.out.println("1 - Cadastrar reserva");
            System.out.println("2 - Listar reservas");
            System.out.println("3 - Buscar reserva por data");
            System.out.println("4 - Remover reserva por data");
            System.out.println("5 - Listar espaços físicos");
            System.out.println("6 - Buscar espaço físico por ID");
            System.out.println("0 - Sair");
            System.out.print("Opção: ");

            int opcao = Integer.parseInt(scanner.nextLine());

            switch (opcao) {
                case 1:
                    System.out.print("Data (dd/MM/yyyy): ");
                    String data = scanner.nextLine();

                    System.out.print("Id do laboratório: ");
                    int idLab = Integer.parseInt(scanner.nextLine());

                    System.out.print("Nome do laboratório: ");
                    String nomeLab = scanner.nextLine();

                    System.out.print("Capacidade: ");
                    int capacidade = Integer.parseInt(scanner.nextLine());

                    System.out.print("Quantidade de computadores: ");
                    int computadores = Integer.parseInt(scanner.nextLine());

                    EspacoFisico espaco =
                            new Laboratorio(
                                    idLab,
                                    nomeLab,
                                    capacidade,
                                    computadores);

                    ReservaAgendada reserva =
                            new ReservaAgendada(
                                    data,
                                    usuario,
                                    espaco);

                    byte[] respostaCadastro =
                            servico.doOperation(
                                    new RemoteObjectRef("ServicoReserva"),
                                    1,
                                    gson.toJson(reserva)
                                            .getBytes(StandardCharsets.UTF_8));

                    System.out.println(new String(respostaCadastro));

                    break;
                case 2:
                    byte[] respostaListagem =
                            servico.doOperation(
                                    new RemoteObjectRef("ServicoReserva"),
                                    2,
                                    new byte[0]);

                    System.out.println("\nReservas:");
                    System.out.println(new String(respostaListagem));

                    break;
                case 3:
                        System.out.print("Data da reserva: ");
                        String dataBusca = scanner.nextLine();

                        byte[] respostaBusca =
                                servico.doOperation(
                                        new RemoteObjectRef("ServicoReserva"),
                                        3,
                                        dataBusca.getBytes(StandardCharsets.UTF_8));

                        System.out.println(new String(respostaBusca));

                        break;
                case 4:
                        System.out.print("Data da reserva a remover: ");
                        String dataRemocao = scanner.nextLine();

                        byte[] respostaRemocao =
                                servico.doOperation(
                                        new RemoteObjectRef("ServicoReserva"),
                                        4,
                                        dataRemocao.getBytes(StandardCharsets.UTF_8));

                        System.out.println(new String(respostaRemocao));

                        break;
                case 5:
                        String lista = servicoEspaco.listarEspacos();

                        System.out.println(lista);

                        break;
                case 6:
                        System.out.print("ID: ");
                        int id = Integer.parseInt(scanner.nextLine());

                        System.out.println(
                                servicoEspaco.buscarPorId(id));

                        break;
                case 0:

                    System.out.println("Encerrando...");
                    scanner.close();
                    return;

                default:

                    System.out.println("Opção inválida.");
            }
        }
    }
}