package jpabook.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Delivery {

    @Id @GeneratedValue
    @Column(name = "delivert_id")
    private Long id;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "delivery")
    private Order order; //거울

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING) //ORDINAL 기본인데 절대 쓰지마세요
    private DeliveryStatus status; //READY, COMP
}
