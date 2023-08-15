package com.project.spring.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cart_item")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Cart cart;
    @ManyToOne
    private Product product;
    private int quantity;
    
	public Product getProduct() {
		// TODO Auto-generated method stub
		return product;
	}
	public int getQuantity() {
		// TODO Auto-generated method stub
		return 0;
	}
}
