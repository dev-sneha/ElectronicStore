package com.lcdw.electronic.store.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

import java.util.ArrayList;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="categories")
public class Category {


    @Id
    private String categoryId;
    @Column(name="category_title" ,length=50,nullable = false)
    private String title;
    @Column(name="category_desc", length=500)
    private String description;
    private String coverImage;

    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL, fetch=FetchType.LAZY)
    private List<Product> products= new ArrayList<>();


}
