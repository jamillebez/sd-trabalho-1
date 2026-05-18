package br.ufc.quixada.reserva.rmi;

import java.net.InetAddress;
import java.rmi.Naming;
import java.rmi.RemoteException;

import br.ufc.quixada.reserva.service.ServicoConsultaRemote;
import br.ufc.quixada.reserva.service.ServicoReservaRemote;

public class MiddlewareRMI implements MiddlewareRemoto {
    private MensagemRPC ultimaRequisicao;
    private byte[] ultimaResposta;
    private final boolean proxyCliente;

    public MiddlewareRMI(int porta) {
        this.proxyCliente = true;
    }

    public MiddlewareRMI() {
        this.proxyCliente = true;
    }

    public MiddlewareRMI(boolean proxyCliente) {
        this.proxyCliente = proxyCliente;
    }

    @Override
    public byte[] doOperation(String objectRef, String methodId, byte[] arguments, String ipServidor, int portaServidor) throws RemoteException {
        try {
            if (proxyCliente) {
                MiddlewareRemoto middlewareRemoto = (MiddlewareRemoto) Naming.lookup("rmi://" + ipServidor + ":" + portaServidor + "/MiddlewareRMI");
                return middlewareRemoto.doOperation(objectRef, methodId, arguments, ipServidor, portaServidor);
            }

            System.out.println("[Servidor] Requisição recebida: objeto=" + objectRef + ", método=" + methodId + ", argumentos=" + new String(arguments));
            MensagemRPC request = new MensagemRPC(0, 1, objectRef, methodId, new String(arguments));
            ultimaRequisicao = request;
            MensagemRPC recebido = MensagemRPC.fromBytes(getRequest());

            String resposta = invocarServico(recebido, ipServidor, portaServidor);
            sendReply(resposta.getBytes(), InetAddress.getLoopbackAddress(), portaServidor);
            return MensagemRPC.fromBytes(ultimaResposta).arguments.getBytes();

        } catch (Exception e) {
            throw new RemoteException("Erro ao executar operação remota", e);
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