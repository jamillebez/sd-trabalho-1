package br.ufc.quixada.reserva.app;

import br.ufc.quixada.reserva.rmi.MensagemRPC;
import br.ufc.quixada.reserva.rmi.MiddlewareRMI;
import java.net.DatagramPacket;

public class ServidorEspacoFisico {
    public static void main(String[] args) {
        try {
            MiddlewareRMI middleware = new MiddlewareRMI(12345);
            System.out.println("Servidor RMI funcionando na porta 12345...");

            while (true) {
                System.out.println("\nAguardando requisições...");
                
                DatagramPacket pacoteRecebido = middleware.getRequest();
                MensagemRPC msgRequisicao = MensagemRPC.fromBytes(pacoteRecebido.getData());

                System.out.println("Método invocado: " + msgRequisicao.methodId);
                System.out.println("Argumentos recebidos: " + msgRequisicao.arguments);
                
                String resposta;
                
                switch (msgRequisicao.methodId) {
                    case "solicitarReserva":
                        resposta = "Sucesso: Reserva confirmada no servidor!";
                        break;
                    case "cancelarReserva":
                        resposta = "Sucesso: Reserva cancelada no servidor!";
                        break;
                    case "verificarDisponibilidade":
                        resposta = "Info: O espaço encontra-se disponível para a data solicitada.";
                        break;
                    case "consultarDetalhes":
                        resposta = "Detalhes: " + msgRequisicao.arguments; 
                        break;
                    default:
                        resposta = "Erro: Método remoto não encontrado.";
                        break;
                }

                middleware.sendReply(resposta.getBytes(), pacoteRecebido.getAddress(), pacoteRecebido.getPort());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}