package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded //내장타입을 포함했다. 쓸때는 둘중에 하나만있으면됨. 임베디드나 임베더블
    private Address address;

    @OneToMany(mappedBy = "member") //order table에 있는 member필드레 의해서 매핑된거야
    //반대로 나는 주인이 아니에요. 나는 거울이에요
    //읽기 전용. 나는 저거에 의해서 매핑된거일 뿐이야
    private List<Order> orders = new ArrayList<>();
}
