package service;

import com.example.demo.user.domain.User;
import com.example.demo.user.service.UserService2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest //해당 클래스를 테스트 클래스로 사용하기 위해 해당 어노테이션 사
public class UserServiceTest {

    //UserService2의 구현체인 UserService2Logic를 주입하기 위해서는 @Autowired 사용
    // * 단위테스트에서는, 생성자를 통한 주입 이나, @RequiredArgsConstructor 어노테이션을 이용한 주입이 불가함
    @Autowired
    private UserService2 userService2;

    //@BeforeEach
    //public void registerTest

    @Test
    public void resigterTest(){
        User sample = User.sample();

        assertThat(this.userService2.register(sample)).isEqualTo(sample.getUserid());
        assertThat(this.userService2.findAll().size()).isEqualTo(2);
    }
}
