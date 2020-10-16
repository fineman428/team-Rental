package rentalService;

import javax.persistence.*;

import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
@Entity
@Table(name="Rental_table")
public class Rental {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private Long productId;
    private int qty;
    private String status;
    private String productName;
    private Long deliveryId;

    /*
    @Autowired
    ProductRepository ProductRepository;
    */

    @PostPersist
    public void onPostPersist(){
/*
        Optional<Product> productOptional = ProductRepository.findById(productId);
        Product product = productOptional.get();
        productName = product.getName();
*/

        Rentaled rentaled = new Rentaled();
        BeanUtils.copyProperties(this, rentaled);
        rentaled.publishAfterCommit();

    }

    @PreRemove
    public void onPreRemove(){
        // RentalCanceled
        RentalCanceled rentalCanceled = new RentalCanceled();
        BeanUtils.copyProperties(this, rentalCanceled);
        rentalCanceled.setStatus("CANCELED");
        rentalCanceled.publishAfterCommit();

        //Following code causes dependency to external APIs
        // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.

        // 동기호출
        rentalService.external.Delivery delivery = new rentalService.external.Delivery();
        // mappings goes here
        BeanUtils.copyProperties(this, delivery);
        delivery.setId(deliveryId);
        delivery.setRentalId(this.id);
        delivery.setStatus("CANCELED");
        RentalApplication.applicationContext.getBean(rentalService.external.DeliveryService.class)
                .deliveryCancel(delivery);

    }

}
