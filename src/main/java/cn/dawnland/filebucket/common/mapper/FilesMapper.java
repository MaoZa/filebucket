package cn.dawnland.filebucket.common.mapper;

import cn.dawnland.filebucket.common.pojo.files.Files;
import org.apache.ibatis.annotations.Param;

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

    /**
     * 非空更新
     * @param files
     */
    void updateFileNotNull(Files files);

    /**
     * id查记录
     * @param id
     * @return
     */
    Files findFilesByFilesId(@Param("id") Long id);

    /**
     * 逻辑删除
     * @param id
     */
    void deleteFiles(@Param("id") Long id);

    /**
     * 查询用户已存文件大小
     * @param userId
     * @return
     */
    Long findSumSize(@Param("userId") Long userId);
}
