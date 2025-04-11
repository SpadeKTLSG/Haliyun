package xyz.spc.common.constant;

/**
 * 上传下载常量
 */
public interface UploadDownloadCT {

    /**
     * 判断重复请求的时间间隔 (s)
     */
    int SAME_URL_DATA_TIME = 1;

    /**
     * 默认上传文件最大大小 500M
     */
    long DEFAULT_MAX_SIZE = 99999L * 1024 * 1024 * 5;

    /**
     * 默认的文件名最大长度 1000
     */
    int DEFAULT_FILE_NAME_LENGTH = 1000;

    /**
     * 默认用户上传的文件路径
     */
    String UPLOAD_DEFAULT_PATH = "D:/CODE/HaliyunAll/Data/upload";

    /**
     * 默认用户下载的文件路径
     */
    String DOWNLOAD_DEFAULT_PATH = "D:/CODE/HaliyunAll/Data/download";

    /**
     * 文件上传失败重试次数
     */
    Integer UPDATE_FAIL_RETRY = 3;
}
