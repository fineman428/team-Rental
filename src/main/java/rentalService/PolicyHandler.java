package rentalService;

import org.springframework.beans.BeanUtils;
import rentalService.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PolicyHandler{
    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){

    }

    @Autowired
    ProductRepository ProductRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverProductSaved_Product(@Payload ProductSaved productSaved){

        if(productSaved.isMe()){

            Product product = new Product();
            product.setName( productSaved.getName());

            ProductRepository.save(product);
        }
    }

    @Autowired
    RentalRepository RentalRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverDelivered_DeliveryStatus(@Payload Delivered delivered){

        if(delivered.isMe()){
            Optional<Rental> rentalOptional = RentalRepository.findById(delivered.getRentalId());
            Rental rental = rentalOptional.get();
            rental.setStatus(delivered.getStatus());

            RentalRepository.save(rental);
        }

    }

}
