package by.markov.checkrunnerspringboot.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    private long id;

    @Column(name = "product_name")
    private String productName;
    private double price;
    @Column(name="is_discount")
    private boolean isDiscount;
}
