package br.ufc.quixada.reserva.stream;

import br.ufc.quixada.reserva.model.EspacoFisico;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class EspacoFisicoOutputStream extends OutputStream {
    private final EspacoFisico[] dados;
    private final int numObjetos;
    private final DataOutputStream destino;

    public EspacoFisicoOutputStream(EspacoFisico[] dados, int numObjetos, OutputStream destino) {
        this.dados = dados;
        this.numObjetos = numObjetos;
        this.destino = new DataOutputStream(destino);
    }

    @Override
    public void write(int b) throws IOException {
        destino.write(b);
    }

    public void transmitirDados() throws IOException {
        destino.writeInt(numObjetos);

        for (int i = 0; i < numObjetos; i++) {
            EspacoFisico espaco = dados[i];
            byte[] nomeBytes = espaco.getNome().getBytes(StandardCharsets.UTF_8);

            destino.writeInt(espaco.getId());
            destino.writeInt(nomeBytes.length);
            destino.write(nomeBytes);
            destino.writeInt(espaco.getCapacidade());
        }

        destino.flush();
    }
}