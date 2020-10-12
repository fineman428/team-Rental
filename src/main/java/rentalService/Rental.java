package rentalService;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@Entity
@Table(name="Rental_table")
public class Rental {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private Long productId;
    private Integer qty;
    private String status;
    private String productName;

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
        RentalCanceled rentalCanceled = new RentalCanceled();
        BeanUtils.copyProperties(this, rentalCanceled);
        rentalCanceled.setStatus("CANCELED");
        rentalCanceled.publishAfterCommit();

        //Following code causes dependency to external APIs
        // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.

        rentalService.external.Delivery delivery = new rentalService.external.Delivery();
        // mappings goes here
        BeanUtils.copyProperties(this, delivery);
        delivery.setRentalId(this.id);
        delivery.setStatus("CANCELED");
        RentalApplication.applicationContext.getBean(rentalService.external.DeliveryService.class)
                .deliveryCancel(delivery);

    }


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getProductId() {
        return productId;
    }
    public void setProductId(Long productId) {
        this.productId = productId;
    }
    public Integer getQty() {
        return qty;
    }
    public void setQty(Integer qty) {
        this.qty = qty;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getProductName() {
        return this.productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }

}
