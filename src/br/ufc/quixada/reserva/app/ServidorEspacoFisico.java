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
                System.out.println("Aguardando requisições...");
                
                DatagramPacket pacoteRecebido = middleware.getRequest();
                MensagemRPC msgRequisicao = MensagemRPC.fromBytes(pacoteRecebido.getData());

                System.out.println("Método invocado: " + msgRequisicao.methodId);
                
                String resposta;
                
                if (msgRequisicao.methodId.equals("solicitarReserva")) {
                    System.out.println("Argumentos recebidos: " + msgRequisicao.arguments);
                    resposta = "Reserva confirmada com sucesso no servidor!";
                } else {
                    resposta = "Método não encontrado.";
                }

                middleware.sendReply(resposta.getBytes(), pacoteRecebido.getAddress(), pacoteRecebido.getPort());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}