package jpabook.jpashop.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("M") //싱글테이블이니 데이터 넣을때 구분이 되어야함
@Getter @Setter
public class Movie extends Item {

    private String director;
    private String actor;
}
