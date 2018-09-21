package cn.dawnland.filebucket.common.mapper;

import cn.dawnland.filebucket.common.pojo.emailcode.EmailCode;

import java.util.List;

public interface EmailCodeMapper {

    void insertCode(EmailCode emailCode);

    EmailCode findOneById(Long id);

    List<EmailCode> findListByParams(EmailCode emailCode);

    void updateNotNull(EmailCode emailCode);

    void deleteEmailCode(Long id);

}
