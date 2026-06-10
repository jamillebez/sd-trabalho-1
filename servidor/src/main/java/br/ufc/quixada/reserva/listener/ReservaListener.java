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

        try {
            reservaService.criar(reserva);
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            channel.basicNack(deliveryTag, false, true);
        }
    }
}
