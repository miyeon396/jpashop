package jpabook.jpashop.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("A") //싱글테이블이니 데이터 넣을때 구분이 되어야함
@Getter @Setter
public class Album extends Item {

    private String artist;
    private String etc;
}
