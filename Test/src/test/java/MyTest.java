import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.spc.serve.guest.GuestAPP;

@SpringBootTest(classes = GuestAPP.class)
public class MyTest {

    @Test
    public void test() throws Exception {
        System.out.println("test: ");
//
//        HdfsIOUtil.downFile("TEST/测试文件.txt", HdfsContext.PC_WORK_DIR + "TEST/测试文件.txt");
//        HdfsFuncUtil.deleteF("TEST/测试文件2.txt");
//        System.out.println(HdfsQueryUtil.chechMD5("TEST/测试文件.txt"));


    }


    //业务参考
/*    public Result hdfsUpload(HttpServletRequest request, MultipartFile file, String path) throws IOException {
        if (path == null) {
            path = "/";
        }
        if (file.isEmpty()) {
            return Result.fail("请选择要上传的文件！");
        }
        // 获取用户名
        String userName = "null";
        // 上传文件
        boolean b = false;
        // 服务器上传的文件所在路径
        String saveFilePath = userName + "/" + path;
        // 判断文件夹是否存在-建立文件夹
        File filePathDir = new File(saveFilePath);
        if (!filePathDir.exists()) {
            filePathDir.mkdir();
        }

        // 获取上传文件的原名
        String saveFileName = file.getOriginalFilename();
        // 上传文件到-磁盘

        OutputStream outputStream = HdfsConn.getFileSystem().create(new Path(saveFilePath, saveFileName), new Progressable() {
            @Override
            public void progress() {
            }
        });
        IOUtils.copyBytes(file.getInputStream(), outputStream, 2048, true);

        return Result.success("上传成功");

    }*/

/*    public Result hdfsDownload(String fileName, String path, HttpServletRequest request) throws FileNotFoundException {
        Map<String, Object> map = new HashMap<>(2, 1);
        if (path == null) {
            path = "/";
        }
        if (fileName.isEmpty()) {
            return Result.fail("文件名为空！");
        }
        // 获取用户名
        String userName = UserHolder.getUserNameByRequest(request);
        // 下载文件，获取下载路径,这个是 个映射的路径
        // 服务器下载的文件所在的本地路径的文件夹
        String saveFilePath = fileRootPath + userName + "/" + path;
        // 判断文件夹是否存在-建立文件夹
        File filePathDir = new File(saveFilePath);
        if (!filePathDir.exists()) {
            filePathDir.mkdir();
        }

        // 本地路径
        String saveFilePath1 = saveFilePath + "/" + fileName;
        String link = saveFilePath1.replace(fileRootPath, "/data/");
        link = stringSlashToOne(link);


        if (HdfsConn.getFileSystem().exists(new Path(saveFilePath, fileName))) {
            FSDataInputStream inputStream = HdfsConn.getFileSystem().open(new Path(saveFilePath));
            OutputStream outputStream = new FileOutputStream(link);
            IOUtils.copyBytes(inputStream, outputStream, 4096, true);

        }
        return Result.success();

    }*/
}
