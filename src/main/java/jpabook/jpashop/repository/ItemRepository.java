package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item) {
        if (item.getId() == null) {
            //새객체
            em.persist(item);
        } else {
            //merge는 ItemService에서 작성한 updateItem 한땀한땀을 아래처람 한줄로 해주는 것.
            //준영속 상태의 엔티티를 영속 상태로 변경할 때 사용하는 기능
            //책 3.6.5 확인
            // merge는 병합해서 변경한 내용을 return함 item은 그냥 값 자체임 관리되지 않음. merge후 리턴값이 관리되는애라 그 이후에 뭔가를 사용하려면 item이 아닌 새 값을 사용해야함
            // 주의 : 변경 감지 기능을 사용하면 원하는 속성만 선택해서 변경할 수 있찌만, 병합을 사용하면 모든 속성이 변경된다.
            // 병합 시 값이 없으면 null로 업데이트 할 위험도 있다. 병합은 모든 필드를 교체한다.
            // -> 가급적이면 귀찮더라도 변경감지를 써라.
            em.merge(item); //like update 실무에선 거의 쓸 일이 없음
        }
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }
}
