package br.ufc.quixada.reserva.stream;

import br.ufc.quixada.reserva.model.EspacoFisico;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class EspacoFisicoInputStream extends InputStream {
    private DataInputStream origem;

    public EspacoFisicoInputStream(InputStream origem) {
        this.origem = new DataInputStream(origem);
    }

    @Override
    public int read() throws IOException {
        return origem.read();
    }

    public EspacoFisico[] ler() throws IOException {
        int quantidade = origem.readInt();
        EspacoFisico[] espacos = new EspacoFisico[quantidade];

        for (int i = 0; i < quantidade; i++) {
            int id = origem.readInt();
            int nomeLength = origem.readInt();

             if (nomeLength < 0 || nomeLength > 1024 * 1024) {
                throw new IOException("Tamanho inválido: " + nomeLength);
            }

            byte[] nomeBytes = new byte[nomeLength];
            origem.readFully(nomeBytes);
            String nome = new String(nomeBytes, java.nio.charset.StandardCharsets.UTF_8);

            int capacidade = origem.readInt();

            espacos[i] = new EspacoFisico(id, nome, capacidade) { };
        }

        return espacos;
    }
}
