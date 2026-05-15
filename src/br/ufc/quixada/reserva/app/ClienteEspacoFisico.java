package br.ufc.quixada.reserva.app;

import br.ufc.quixada.reserva.model.*;
import br.ufc.quixada.reserva.rmi.MiddlewareRMI;
import com.google.gson.Gson;

public class ClienteEspacoFisico {
    public static void main(String[] args) {
        try {
            MiddlewareRMI middleware = new MiddlewareRMI();
            Gson gson = new Gson();

            EspacoFisico espaco = new Sala(1, "Sala UFC Quixadá", 50, true);
            
            String argumentosJSON = gson.toJson(espaco);
            
            System.out.println("Invocando método remotamente via RMI...");
            
            byte[] respostaBytes = middleware.doOperation(
                "ServicoReserva",        
                "solicitarReserva",     
                argumentosJSON.getBytes(), 
                "localhost",            
                12345                    
            );

            if (respostaBytes != null) {
                String resposta = new String(respostaBytes).trim();
                System.out.println("Resposta do Servidor RMI: " + resposta);
            } else {
                System.out.println("Erro: Nenhuma resposta do servidor.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}