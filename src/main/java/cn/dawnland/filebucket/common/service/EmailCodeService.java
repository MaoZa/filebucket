package cn.dawnland.filebucket.common.service;

import cn.dawnland.filebucket.common.pojo.emailcode.EmailCode;

import java.util.List;

public interface EmailCodeService {
    void insertCode(EmailCode emailCode) throws Exception;

    EmailCode findOneById(Long id);

    List<EmailCode> findListByParams(EmailCode emailCode);

    void updateNotNull(EmailCode emailCode) throws Exception;

    void deleteEmailCode(Long id) throws Exception;
}
