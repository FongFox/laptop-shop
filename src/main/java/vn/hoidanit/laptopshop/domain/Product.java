package vn.hoidanit.laptopshop.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Size(min = 1, message = "Name can not be blank.")
    private String name;

    @NotNull
//    @Min(value = 1, message = "Price must larger than 0")
    @DecimalMin(value = "0", inclusive = false, message = "Price must larger than 0")
    private double price;

    @NotNull
    @Size(min = 1, message = "Detail Description can not be blank.")
    @Column(columnDefinition = "MEDIUMTEXT")
    private String detailDesc;

    @NotNull
    @Size(min = 1, message = "Short Description can not be blank.")
    private String shortDesc;

    @NotNull
    @Min(value = 1, message = "Quantity must larger than 0")
    private long quantity;

    private long sold;
    private String factory;
    private String target;
    private String image;

    public Product() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDetailDesc() {
        return detailDesc;
    }

    public void setDetailDesc(String detailDesc) {
        this.detailDesc = detailDesc;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public long getSold() {
        return sold;
    }

    public void setSold(long sold) {
        this.sold = sold;
    }

    public String getFactory() {
        return factory;
    }

    public void setFactory(String factory) {
        this.factory = factory;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", image='" + image + '\'' +
                ", detailDesc='" + detailDesc + '\'' +
                ", shortDesc='" + shortDesc + '\'' +
                ", quantity=" + quantity +
                ", sold=" + sold +
                ", factory='" + factory + '\'' +
                ", target='" + target + '\'' +
                '}';
    }
}
