package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;

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
        //2保存用户信息
        //2.1s设置激活码
        //2.2设置激活状态
        user.setCode(UuidUtil.getUuid());
        user.setStatus("N");
        userDao.save(user);
        //3激活邮件发送，邮件正文?
        String content="<a href='http://localhost:8080/travel/user/active?code="+user.getCode()+"'>点击激活【黑马旅游网】</a>";

        MailUtils.sendMail(user.getEmail(),content,"激活邮件");


        return true;
    }

    @Override
    public boolean active(String code) {
        //1根据激活码查询用户对象

     User user  =  userDao.findByCode(code);
     if(user !=null){
         //2调用dao的修改激活码状态的方法
         userDao.updateStatus(user);
         return true;
     }
        return false;
    }

    /**
     * 登录方法
     * @param user
     * @return
     */

    @Override
    public User login(User user) {
        return userDao.findByUsernameAndPassword(user.getUsername(),user.getPassword());
    }
}
