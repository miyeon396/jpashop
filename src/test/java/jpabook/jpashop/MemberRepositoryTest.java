package jpabook.jpashop;

import org.assertj.core.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    MemberRepositoryBk memberRepository;

    @Test
    @Transactional
//    @Rollback(value = false)
    void testMember() throws Exception {
        //given
        MemberBk member = new MemberBk();
        member.setUsername("memberA");


        //when
        Long saveId = memberRepository.save(member);
        MemberBk findMember = memberRepository.find(saveId);

        //then
        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
        Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        Assertions.assertThat(findMember).isEqualTo(member);
        System.out.println("findMember == member : "+(findMember == member));//같은 영속성 컨텍스트에서 아이디가 같으면 같다.
    }

}