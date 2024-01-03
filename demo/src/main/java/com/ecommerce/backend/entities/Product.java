package com.ecommerce.backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products")
@JsonIgnoreProperties({"category", "orderItems"})
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Size(max = 30)
    @NotNull
    @Column(name = "name", nullable = false, length = 30)
    private String name;

    @Size(max = 255)
    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @NotNull
    @Column(name = "stock_quantity", nullable = false)
    private Integer stockQuantity;

    @NotNull
    @Column(name = "volume", nullable = false)
    private Integer volume;

    @Column(name = "image_url", length = Integer.MAX_VALUE)
    private String imageUrl;

    @OneToMany(mappedBy = "product")
    private Set<OrderItem> orderItems = new LinkedHashSet<>();

}