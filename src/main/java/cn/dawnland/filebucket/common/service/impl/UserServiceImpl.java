package cn.dawnland.filebucket.common.service.impl;

import cn.dawnland.filebucket.common.mapper.UserMapper;
import cn.dawnland.filebucket.common.pojo.user.User;
import cn.dawnland.filebucket.common.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findUserByUsernameAndPassword(String username, String password) {
        return userMapper.findUserByUsernameAndPassword(username, password);
    }

    @Override
    @Transactional
    public void insertUser(User user) {
        userMapper.insertUser(user);
    }

    @Override
    @Transactional
    public void updateNotNullUser(User user) {
        userMapper.updateNotNullUser(user);
    }

    @Override
    public User findUserByParams(User user) {
        return userMapper.findUserByParams(user);
    }


}
