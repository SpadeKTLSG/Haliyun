//package save.save2;
//
//import cn.hutool.core.util.IdUtil;
//import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
//import com.example.springboot.base.Result;
//import com.example.springboot.mapper.FileInfoMapper;
//import com.example.springboot.mapper.FolderFileInfoMapper;
//import com.example.springboot.mapper.FolderInfoMapper;
//import com.example.springboot.pojo.FileInfo;
//import com.example.springboot.pojo.FolderFileInfo;
//import com.example.springboot.pojo.FolderInfo;
//import com.example.springboot.service.FileInfoService;
//import com.example.springboot.utils.constant.Constants;
//import com.example.springboot.utils.file.FileTypeJudgeUtil;
//import com.example.springboot.utils.hdfs.HdfsUtil;
//import com.github.yulichang.wrapper.MPJLambdaWrapper;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
///**
// * <p>
// * 服务实现类
// * </p>
// *
// * @author 赵久燚
// * @since 2022-07-11 19:45:42
// */
//@Service
//public class FileInfoServiceImpl extends ServiceImpl<FileInfoMapper, FileInfo> implements FileInfoService {
//
//    @Resource
//    FolderFileInfoMapper folderFileInfoMapper;
//
//    @Resource
//    FolderInfoMapper folderInfoMapper;
//    @Resource
//    FileInfoMapper fileInfoMapper;
//    // 实现接口
//
//    @Override
//    public Result uploadFile(MultipartFile file, String folderId) throws IOException {
//        // 根据文件夹id查询文件夹路径和文件夹所属用户
//        FolderInfo folderInfo = folderInfoMapper.selectOne(new MPJLambdaWrapper<FolderInfo>()
//                .select(FolderInfo::getFolderUrl, FolderInfo::getUserId)
//                .eq(FolderInfo::getFolderId, folderId));
//        String userId = folderInfo.getUserId();
//        // 获取文件名
//        String filename = file.getOriginalFilename();
//        // 拼接文件路径
//        String url = folderInfo.getFolderUrl() + "/" + filename;
//        // 上传文件
//        HdfsUtil.upload(file, url);
//
//        // 保存文件信息（hdfs路径、文件名、文件类型）
//        FileInfo fileInfo = new FileInfo();
//        String fileId = IdUtil.simpleUUID();
//        fileInfo.setFileId(fileId);
//        fileInfo.setFilePath(url);
//        fileInfo.setFileName(filename);
//        //判断文件类型
//        // 裁剪文件名获取文件后缀
//        String suffix = filename.substring(filename.lastIndexOf(".") + 1);
//        // 根据文件后缀获取文件类型
//        Integer integer = FileTypeJudgeUtil.FILE_TYPE.get(suffix);
//        fileInfo.setFileType(integer);
//        fileInfo.setFileUploadId(userId);
//        fileInfo.setFileDel(1);
//        fileInfoMapper.insert(fileInfo);
//
//        // 保存文件所属（父文件夹,用户）
//        FolderFileInfo folderFileInfo = new FolderFileInfo();
//        String folderFileInfoId = IdUtil.simpleUUID();
//        folderFileInfo.setFolderFileInfoId(folderFileInfoId);
//        folderFileInfo.setFolderFileId(fileId);
//        folderFileInfo.setFolderFileType(2);
//        folderFileInfo.setFolderPd(folderId);
//        folderFileInfoMapper.insert(folderFileInfo);
//        return Result.success("上传成功");
//    }
//
//    @Override
//    public void downLoadFile(String fileId, HttpServletResponse response) {
//        FileInfo fileInfo = fileInfoMapper.selectOne(new MPJLambdaWrapper<FileInfo>()
//                .select(FileInfo::getFilePath)
//                .eq(FileInfo::getFileId, fileId));
//        String hdfsUrl = fileInfo.getFilePath();
//        HdfsUtil.download(hdfsUrl, response);
//    }
//
//    @Override
//    public Result deleteFile(String fileId) {
//        // 删除文件夹
//        FileInfo fileInfo = new FileInfo();
//        fileInfo.setFileId(fileId);
//        fileInfo.setFileDel(Constants.FILE.IS_DEL.getValue());
//        fileInfoMapper.updateById(fileInfo);
//        return Result.success("删除成功");
//    }
//}
