package jpabook.jpashop.api;

import jpabook.jpashop.domain.Order;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
