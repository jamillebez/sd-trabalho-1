package br.ufc.quixada.reserva.listener;

import br.ufc.quixada.reserva.config.RabbitMQConfig;
import br.ufc.quixada.reserva.dto.ReservaUpsertDTO;
import br.ufc.quixada.reserva.service.ReservaService;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ReservaListener {

    @Autowired
    private ReservaService reservaService;

    @RabbitListener(queues = RabbitMQConfig.RESERVAS_QUEUE)
    public void consumir(ReservaUpsertDTO reserva, Channel channel, Message message) throws IOException {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();

        System.out.println(">>> [RabbitMQ Assíncrono] Mensagem retirada da fila! Simulando processamento pesado de 15 segundos...");

        try {
            Thread.sleep(15000); 

            reservaService.criar(reserva);
            
            System.out.println(">>> [RabbitMQ Assíncrono] Sucesso! Reserva gravada no ficheiro JSON. A enviar ACK para limpar a fila.");
            channel.basicAck(deliveryTag, false);
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println(">>> [RabbitMQ] Thread interrompida.");
        } catch (Exception e) {
            System.out.println(">>> [RabbitMQ Assíncrono] FALHA no processamento: " + e.getMessage());
            channel.basicNack(deliveryTag, false, false);
        }
    }
}