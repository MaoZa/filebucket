package cn.dawnland.filebucket.common.mapper;

import cn.dawnland.filebucket.common.pojo.user.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {

    /** 账号密码查询用户 */
    User findUserByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    /** insert用户 */
    void insertUser(User user);

    /** update用户 */
    void updateNotNullUser(User user);

    User findUserByParams(User user);
}
