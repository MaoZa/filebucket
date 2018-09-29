package cn.dawnland.filebucket.common.service;

import cn.dawnland.filebucket.common.pojo.emailcode.EmailCode;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmailCodeService {
    void insertCode(EmailCode emailCode) throws Exception;

    EmailCode findOneById(Long id);

    List<EmailCode> findListByParams(EmailCode emailCode);

    void updateNotNull(EmailCode emailCode) throws Exception;

    void deleteEmailCode(Long id) throws Exception;

    void deleteEmailCodeByEmail(String email) throws Exception;

    EmailCode findCodeInfoByEmailAndEmailCode(@Param("email") String email, @Param("emailCode") String emailCode);

    Integer updateCodeStatusByEmailAndEmailCode(@Param("email") String email, @Param("emailCode") String emailCode);
}
