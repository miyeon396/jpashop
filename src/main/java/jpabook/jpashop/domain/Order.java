package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.aspectj.weaver.ast.Or;
import org.hibernate.annotations.BatchSize;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id") //매핑을 뭘로 할거냐. foreignKey 이름
    //양방향 연관관계이기 떄문에 연관관계의 주인을 정해줘야함. 둘다 바꿔버리면 JPA는 뭘 보고 값이 바뀌면 Foregin키를 바꿔야하지 혼란
    //주인은 그냥 냅두면됨.
    private Member member;

    @BatchSize(size = 10) //요런 방법도 있음.
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery; //주인

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING) //ORDINAL 기본인데 절대 쓰지마세요
    private OrderStatus status; //주문 상태



    //==연관관계 편의 메서드==//
    //연관관계를 setting 할 때 편한게 하는 것.
    //이 메서드는 양쪽에서 컨트롤 하는쪽에서 들고 있는게 좋음.

    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }
    //원래대로라면 아래와 같이 해줘야하지만 휴만에러로 놓칠 수 있따. 아래코드를 줄여주는거
    //Member member = new Member();
    //Order order = new Order();
    //member.getOrders().add(order);
    //order.setMember(member);


    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    //생성 메서드
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;

    }

    //비지니스 로직
    /**
     * 주문 취소
     */
    public void cancel() {
        if (delivery.getStatus() == DeliveryStatus.COMP) {
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능 합니다.");
        }

        this.setStatus(OrderStatus.CANCEL);
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }

    //조회로직
    /**
     * 전체 주문 가격 조회
     */
    public int getTotalPrice() {
//        int totalPrice = 0;
//        for (OrderItem orderItem : orderItems) {
//            totalPrice += orderItem.getTotalPrice();
//        }
//        아래와 동일코드
        return orderItems.stream()
                .mapToInt(OrderItem::getTotalPrice)
                .sum();

    }
}
