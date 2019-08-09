package transaction;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import transaction.dao.FromMapper;
import transaction.dao.UserMapper;
import transaction.model.From;
import transaction.model.User;

import javax.annotation.Resource;

/**
 * Created by zouziwen on 2017/3/30.
 */
@Service
public class TransactionalAnnotaton {

    @Resource
    private UserMapper userMapper;

    @Resource
    private FromMapper fromMapper;

    @Transactional(rollbackFor = RuntimeException.class)
    public void addUser(User user) {
        userMapper.addUser(user);
        addCity(user.getFrom());
    }

    @Transactional
    private void addCity(From from) {
        fromMapper.addFrom(from);
        throw new RuntimeException();
    }

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath*:applicationContext.xml");
        TransactionalAnnotaton ta = context.getBean(TransactionalAnnotaton.class);
        User user = new User();
        user.setUsername("123");
        user.setPassword("123");
        From from = new From();
        from.setProvince("北京");
        from.setCity("北京");
        user.setFrom(from);
        ta.addUser(user);
    }

}
