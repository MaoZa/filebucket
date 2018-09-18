package cn.dawnland.filebucket.common.mapper;

import cn.dawnland.filebucket.common.pojo.files.Files;

import java.util.List;

public interface FilesMapper {

    /**
     * 插入文件记录
     * @param files
     * @return
     */
    Integer insertFileInfo(Files files);

    /**
     * 查询用户文件列表
     * @param files
     * @return
     */
    List<Files> findFileInfoByParams(Files files);



}
