package br.ufc.quixada.reserva.stream;

import br.ufc.quixada.reserva.model.EspacoFisico;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class EspacoFisicoOutputStream extends OutputStream {
    private EspacoFisico[] dados;
    private int numObjetos;
    private OutputStream destino;

    public EspacoFisicoOutputStream(EspacoFisico[] dados, int numObjetos, OutputStream destino) {
        this.dados = dados;
        this.numObjetos = numObjetos;
        this.destino = destino;
    }

    @Override
    public void write(int b) throws IOException {
        destino.write(b);
    }

    public void transmitirDados() throws IOException {
        for (int i = 0; i < numObjetos; i++) {
            EspacoFisico espaco = dados[i];

            byte[] idBytes = ByteBuffer.allocate(4).putInt(espaco.getId()).array();
            byte[] nomeBytes = espaco.getNome().getBytes(StandardCharsets.UTF_8);
            byte[] capBytes = ByteBuffer.allocate(4).putInt(espaco.getCapacidade()).array();

            destino.write(idBytes.length);
            destino.write(idBytes);

            destino.write(nomeBytes.length);
            destino.write(nomeBytes);

            destino.write(capBytes.length);
            destino.write(capBytes);
        }
        destino.flush();
    }
}