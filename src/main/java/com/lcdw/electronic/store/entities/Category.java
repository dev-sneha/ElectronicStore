package com.lcdw.electronic.store.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;


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


}
