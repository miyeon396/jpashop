package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id") //매핑을 뭘로 할거냐. foreignKey 이름
    //양방향 연관관계이기 떄문에 연관관계의 주인을 정해줘야함. 둘다 바꿔버리면 JPA는 뭘 보고 값이 바뀌면 Foregin키를 바꿔야하지 혼란
    //주인은 그냥 냅두면됨.
    private Member member;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "delivery_id")
    private Delivery delivery; //주인

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING) //ORDINAL 기본인데 절대 쓰지마세요
    private OrderStatus status; //주문 상태


}
