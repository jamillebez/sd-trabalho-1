package br.ufc.quixada.reserva.rmi;

import java.net.InetAddress;
import java.rmi.Naming;

import br.ufc.quixada.reserva.service.ServicoConsultaRemote;
import br.ufc.quixada.reserva.service.ServicoReservaRemote;

public class MiddlewareRMI {
    private MensagemRPC ultimaRequisicao;
    private byte[] ultimaResposta;

    public MiddlewareRMI(int porta) {
    }

    public MiddlewareRMI() {
    }

    public byte[] doOperation(String objectRef, String methodId, byte[] arguments, String ipServidor, int portaServidor) {
        try {
            ultimaRequisicao = new MensagemRPC(0, 1, objectRef, methodId, new String(arguments));
            MensagemRPC request = MensagemRPC.fromBytes(getRequest());

            String resposta = invocarServico(request, ipServidor, portaServidor);
            sendReply(resposta.getBytes(), InetAddress.getLoopbackAddress(), portaServidor);
            return ultimaResposta == null ? null : MensagemRPC.fromBytes(ultimaResposta).arguments.getBytes();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public byte[] getRequest() {
        return ultimaRequisicao == null ? new byte[0] : ultimaRequisicao.toBytes();
    }

    public void sendReply(byte[] replyArgs, InetAddress clientHost, int clientPort) {
        MensagemRPC replyMsg = new MensagemRPC(1, 1, "Servidor", "resposta", new String(replyArgs));
        ultimaResposta = replyMsg.toBytes();
    }

    private String invocarServico(MensagemRPC request, String ipServidor, int portaServidor) throws Exception {
        String endereco = "rmi://" + ipServidor + ":" + portaServidor + "/" + request.objectReference;

        if ("ServicoReserva".equals(request.objectReference)) {
            ServicoReservaRemote servico = (ServicoReservaRemote) Naming.lookup(endereco);
            if ("solicitarReserva".equals(request.methodId)) {
                return servico.solicitarReserva(request.arguments);
            }
            if ("cancelarReserva".equals(request.methodId)) {
                return servico.cancelarReserva(request.arguments);
            }
        }

        if ("ServicoConsulta".equals(request.objectReference)) {
            ServicoConsultaRemote servico = (ServicoConsultaRemote) Naming.lookup(endereco);
            if ("verificarDisponibilidade".equals(request.methodId)) {
                return servico.verificarDisponibilidade();
            }
            if ("consultarDetalhes".equals(request.methodId)) {
                return servico.consultarDetalhes(request.arguments);
            }
        }

        return "Erro: operação remota não suportada.";
    }
}