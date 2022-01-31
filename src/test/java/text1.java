import com.gxt.dao.UserMapper;
import com.gxt.pojo.User;
import com.gxt.service.UserService;
import org.junit.Test;

public class text1 {

    private UserService userService;
    private UserMapper userMapper;
    @Test
    public void text1(){
        User user = new User();
        user = userService.login("admin","1234567");
        System.out.println(user.toString());
    }
    @Test
    public void text2(){
        User user = userMapper.getLoginUser("admin");
        System.out.println(user.toString());
    }
}
