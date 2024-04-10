package jpabook.jpashop;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepositoryBk {

    @PersistenceContext //spring boot가 이 어노테이션 있으면 EntityManager 주입해줌
    private EntityManager em;

    public Long save(MemberBk member) {
        em.persist(member);
        return member.getId();
    }

    public MemberBk find(Long id) {
        return em.find(MemberBk.class, id);
    }

}
