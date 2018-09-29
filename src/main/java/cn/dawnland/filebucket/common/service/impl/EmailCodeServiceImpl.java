package cn.dawnland.filebucket.common.service.impl;

import cn.dawnland.filebucket.common.mapper.EmailCodeMapper;
import cn.dawnland.filebucket.common.pojo.emailcode.EmailCode;
import cn.dawnland.filebucket.common.service.EmailCodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmailCodeServiceImpl implements EmailCodeService {

    private Logger logger = LoggerFactory.getLogger(EmailCodeServiceImpl.class);

    @Autowired
    private EmailCodeMapper emailCodeMapper;

    @Override
    @Transactional
    public void insertCode(EmailCode emailCode){
        emailCodeMapper.insertCode(emailCode);
    }

    @Override
    public EmailCode findOneById(Long id) {
        return emailCodeMapper.findOneById(id);
    }

    @Override
    public List<EmailCode> findListByParams(EmailCode emailCode) {
        return emailCodeMapper.findListByParams(emailCode);
    }

    @Override
    @Transactional
    public void updateNotNull(EmailCode emailCode){
        emailCodeMapper.updateNotNull(emailCode);
    }

    @Override
    @Transactional
    public void deleteEmailCode(Long id){
        emailCodeMapper.deleteEmailCode(id);
    }

    @Override
    public void deleteEmailCodeByEmail(String email) throws Exception {
        emailCodeMapper.deleteEmailCodeByEmail(email);
    }

    @Override
    public EmailCode findCodeInfoByEmailAndEmailCode(String email, String emailCode) {
        return emailCodeMapper.findCodeInfoByEmailAndEmailCode(email, emailCode);
    }

    @Override
    public Integer updateCodeStatusByEmailAndEmailCode(String email, String emailCode) {
        return emailCodeMapper.updateCodeStatusByEmailAndEmailCode(email, emailCode);
    }
}
