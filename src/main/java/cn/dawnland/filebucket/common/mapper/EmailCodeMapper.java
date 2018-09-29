package cn.dawnland.filebucket.common.mapper;

import cn.dawnland.filebucket.common.pojo.emailcode.EmailCode;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmailCodeMapper {

    void insertCode(EmailCode emailCode);

    EmailCode findOneById(Long id);

    List<EmailCode> findListByParams(EmailCode emailCode);

    void updateNotNull(EmailCode emailCode);

    void deleteEmailCode(Long id);

    void deleteEmailCodeByEmail(@Param("email") String email);

    EmailCode findCodeInfoByEmailAndEmailCode(@Param("email") String email, @Param("emailCode") String emailCode);

    Integer updateCodeStatusByEmailAndEmailCode(@Param("email") String email, @Param("emailCode") String emailCode);
}
