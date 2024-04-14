package jpabook.jpashop.api;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.repository.order.simplequery.OrderSimpleQueryDto;
import jpabook.jpashop.repository.order.simplequery.OrderSimpleQueryRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

/**
 * xToOne (ManyToOne, OneToOne)에서의 성능 최적화
 * Order
 * Order -> Member
 * Order -> Delivery
 */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;
    private final OrderSimpleQueryRepository orderSimpleQueryRepository;

    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1() {
        List<Order> all = orderRepository.findAllByCriteria(new OrderSearch()); //일케 하면 무한 루프에 빠져버림
        for (Order order : all) {
            order.getMember().getName(); //Lazy 강제 초기화.
            order.getDelivery().getAddress(); //Lazy 강제 초기화.
            //getMember까지하면 프록시 상태 여기에 getName을 가져오면 강제 초기화.
            //Hibernatejakarta5 FORCE_LAZY_LOAD 끄고(원복) 일케 필요한 항목만 가져와라.
            // 하지만 근본은 엔티티 미노출!
        }
        return all;
    }

    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderDto> ordersV2() { //list반환하면 안되고 result로 한번 감싸야합니다.
        List<Order> orders = orderRepository.findAllByCriteria(new OrderSearch());
        List<SimpleOrderDto> list = orders.stream()
                .map(o -> new SimpleOrderDto(o)) //map은 a를 b로 바꾸는 것.
                .toList();

        return list;

        //1. ORDER -> SQL 1번 -> 결과 주문수 2개
        // 루프 2바퀴
        // 첫바퀴 멤버 쿼리, 딜리버리 쿼리
        // 2바퀴 멤버 쿼리, 딜리버리 쿼리
        // -> 총 5번의 쿼리 -> N+1의 이슈 이것이
    }

    @GetMapping("/api/v3/simple-orders")
    public List<SimpleOrderDto> ordersV3() {
        List<Order> orders = orderRepository.findAllWithMemberDelivery();
        List<SimpleOrderDto> result = orders.stream()
                .map(o -> new SimpleOrderDto(o))
                .toList();

        return result;
    }

    @GetMapping("/api/v4/simple-orders")
    public List<OrderSimpleQueryDto> ordersV4() {
        return orderSimpleQueryRepository.findOrderDtos();
    }


    @Data
    static class SimpleOrderDto {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        public SimpleOrderDto(Order order) {
            this.orderId = order.getId();
            this.name = order.getMember().getName(); //LAZY 초기화. 영속성에서 가져오는데 없다면 디비 쿼리 날림
            this.orderDate = order.getOrderDate();
            this.orderStatus = order.getStatus();
            this.address = order.getDelivery().getAddress(); //LAZY 초기화
        }
    }

}
