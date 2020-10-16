package rentalService;

import javax.persistence.*;

import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
@Entity
@Table(name="Product_table")
public class Product {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private Long productId;
    private String name;
    private int qty ;

    @PostPersist
    public void onPostPersist(){

        ProductRegisted productRegisted = new ProductRegisted();
        BeanUtils.copyProperties(this, productRegisted);
        productRegisted.publishAfterCommit();

        /*
        ProductSaved productSaved = new ProductSaved();
        BeanUtils.copyProperties(this, productSaved);
        productSaved.publishAfterCommit();
        */
    }

    /*
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }*/
}
