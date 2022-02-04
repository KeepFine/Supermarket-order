import com.gxt.dao.UserMapper;
import com.gxt.pojo.User;
import com.gxt.service.UserService;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

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
    public void text2() throws ParseException {
        String birthday = "2020-12-01";
        System.out.println(new SimpleDateFormat("yyyy-MM-dd").parse(birthday));
//        new SimpleDateFormat("yyyy-MM-dd").parse(birthday);
    }


    //and productName like "%"?"%"
    //WHERE proCode = like "%"?"%"
}
