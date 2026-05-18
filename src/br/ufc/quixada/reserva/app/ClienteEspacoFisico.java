package br.ufc.quixada.reserva.app;

import br.ufc.quixada.reserva.model.*;
import br.ufc.quixada.reserva.rmi.MiddlewareRMI;

public class ClienteEspacoFisico {
    
    private static MiddlewareRMI middleware;
    private static String ipServidor = "localhost";
    private static int portaServidor = 1099;

    public static void main(String[] args) {
        try {
            middleware = new MiddlewareRMI(); 

            EspacoFisico espaco = new Sala(1, "Sala UFC Quixadá", 50, true);
            String argumentosJSON = "{\"id\":" + espaco.getId()
                    + ",\"nome\":\"" + espaco.getNome() + "\""
                    + ",\"capacidade\":" + espaco.getCapacidade()
                    + ",\"possuiProjetor\":true}";
            
            System.out.println("--- TESTANDO INVOCACAO RMI (4 MÉTODOS) ---\n");

            invocarMetodo("ServicoReserva", "solicitarReserva", argumentosJSON);

            invocarMetodo("ServicoReserva", "cancelarReserva", "{\"idReserva\": 1}");

            invocarMetodo("ServicoConsulta", "verificarDisponibilidade", argumentosJSON);

            invocarMetodo("ServicoConsulta", "consultarDetalhes", argumentosJSON);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void invocarMetodo(String objectRef, String methodId, String argumentos) {
        System.out.println(">> Invocando remotamente: " + methodId);
        byte[] respostaBytes = middleware.doOperation(objectRef, methodId, argumentos.getBytes(), ipServidor, portaServidor);

        if (respostaBytes != null) {
            String resposta = new String(respostaBytes).trim();
            System.out.println("   Resposta do Servidor: " + resposta + "\n");
        } else {
            System.out.println("   Erro: Nenhuma resposta.\n");
        }
    }
}