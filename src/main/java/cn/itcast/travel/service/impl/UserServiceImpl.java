package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;

public class UserServiceImpl implements UserService {
    private UserDao userDao = new UserDaoImpl();
    /**
     * 注册用户
     * @param user
     * @return
     */
    @Override
    public boolean regist(User user) {
        //1.根据用用户名查询用户对象
        User u = userDao.findByUsername(user.getUsername());
        //存在返回false，不存在调用dao层，保存用户信息
        if(u!=null){
            return false;
        }
        userDao.save(user);
        return true;
    }
}
