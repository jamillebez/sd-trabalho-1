package br.ufc.quixada.reserva.rmi;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class MiddlewareRMI {
    private DatagramSocket socket;

    public MiddlewareRMI(int porta) throws Exception {
        this.socket = new DatagramSocket(porta);
    }

    public MiddlewareRMI() throws Exception {
        this.socket = new DatagramSocket();
    }

    public byte[] doOperation(String objectRef, String methodId, byte[] arguments, String ipServidor, int portaServidor) {
        try {
            MensagemRPC request = new MensagemRPC(0, 1, objectRef, methodId, new String(arguments));
            byte[] dadosRequisicao = request.toBytes();

            InetAddress endereco = InetAddress.getByName(ipServidor);
            DatagramPacket pacoteEnvio = new DatagramPacket(dadosRequisicao, dadosRequisicao.length, endereco, portaServidor);
            socket.send(pacoteEnvio);

            byte[] buffer = new byte[4096];
            DatagramPacket pacoteRecebido = new DatagramPacket(buffer, buffer.length);
            socket.receive(pacoteRecebido);

            MensagemRPC reply = MensagemRPC.fromBytes(pacoteRecebido.getData());
            return reply.arguments.getBytes(); 

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public DatagramPacket getRequest() throws Exception {
        byte[] buffer = new byte[4096];
        DatagramPacket pacoteRecebido = new DatagramPacket(buffer, buffer.length);
        socket.receive(pacoteRecebido); 
        return pacoteRecebido;
    }

    public void sendReply(byte[] replyArgs, InetAddress clientHost, int clientPort) {
        try {
            MensagemRPC replyMsg = new MensagemRPC(1, 1, "Servidor", "resposta", new String(replyArgs));
            byte[] dadosResposta = replyMsg.toBytes();

            DatagramPacket pacoteEnvio = new DatagramPacket(dadosResposta, dadosResposta.length, clientHost, clientPort);
            socket.send(pacoteEnvio);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}