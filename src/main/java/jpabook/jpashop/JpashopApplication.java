package jpabook.jpashop;

import com.fasterxml.jackson.datatype.hibernate5.jakarta.Hibernate5JakartaModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class JpashopApplication {

	public static void main(String[] args) {
		SpringApplication.run(JpashopApplication.class, args);
	}

	@Bean
	Hibernate5JakartaModule hibernate5JakartaModule() {
		Hibernate5JakartaModule hibernate5JakartaModule = new Hibernate5JakartaModule();
//		hibernate5JakartaModule.configure(Hibernate5JakartaModule.Feature.FORCE_LAZY_LOADING, true); //이 옵션을 추가하면 데이터 다 갖고옴.(레이지 로딩 되어있는거 다 찔러서 갖고오는 것) 근데 일케하면 안댐
//		일케해서 엔티티 그대로 노출했다가 엔티티 스펙 다바뀌면 난리남 + 성능 좋지도 않음. 사용하지도 않는거 다가져옴
		return hibernate5JakartaModule;
	}
}
