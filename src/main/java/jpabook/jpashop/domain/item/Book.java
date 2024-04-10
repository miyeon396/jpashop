package jpabook.jpashop.domain.item;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("B") //싱글테이블이니 데이터 넣을때 구분이 되어야함
@Getter @Setter
public class Book extends Item {

    private String author;
    private String isbn;
}
