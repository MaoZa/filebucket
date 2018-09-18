package cn.dawnland.filebucket.common.service;

import cn.dawnland.filebucket.common.pojo.user.User;

public interface UserService {

    /** 账号密码查询用户 */
    User findUserByUsernameAndPassword(String username, String password);

    /** insert用户 */
    void insertUser(User user);

    /** update用户 */
    void updateNotNullUser(User user);

}
