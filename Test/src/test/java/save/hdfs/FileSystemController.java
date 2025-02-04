//package save.hdfs;
//
//
//import com.alibaba.fastjson.JSON;
//import org.apache.hadoop.fs.FSDataInputStream;
//import org.apache.hadoop.fs.FSDataOutputStream;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.multipart.MultipartHttpServletRequest;
//
//import java.io.BufferedInputStream;
//import java.io.BufferedOutputStream;
//import java.io.InputStream;
//import java.io.PrintWriter;
//
//@Controller
//public class FileSystemController {
//    @Autowired
//    FileSystemDao fileSystemDao;
//
//    // 获取当前页面所有的文件信息
//    @RequestMapping("/getFiles")
//    public void getFiles(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        String currentPath = request.getParameter("currentPath");
//        JSONObject json = new JSONObject();
//        PrintWriter pw = response.getWriter();
//        response.setCharacterEncoding("utf-8");
//        response.setContentType("text/json; charset=UTF-8");
//
//        System.out.println("getFiles...");
//        json.put("status", "ok");
//        json.put("fileList", fileSystemDao.getFileList(currentPath));
//        pw.write(JSON.toJSONString(json));
//
//    }
//
//    // 通过关键字找到服药要求的文件
//    @RequestMapping("/findFile")
//    public void findFile(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        String key = request.getParameter("key");
//
//    }
//
//    // 文件复制（文件复制和粘贴）
//    @RequestMapping("/copyToElsePlace")
//    public void copyToElsePlace(HttpServletRequest request, HttpServletResponse response) throws IOException {
//
//        String oldPath = request.getParameter("oldPath");
//        String newPath = request.getParameter("newPath");
//
//        JSONObject json = new JSONObject();
//        PrintWriter pw = response.getWriter();
//        response.setCharacterEncoding("UTF-8");
//        response.setContentType("text/json; charset=UTF-8");
//
//        fileSystemDao.copyToElsePlace(oldPath, newPath);
//        json.put("status", "ok");
//        json.put("fileList", fileSystemDao.getFileList(newPath));
//        pw.write(JSON.toJSONString(json));
//    }
//
//    // 文件移动（文件剪切和粘贴）
//    @RequestMapping("/moveToElsePlace")
//    public void moveToElsePlace(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        String oldPath = request.getParameter("oldPath");
//        String newPath = request.getParameter("newPath");
//
//        JSONObject json = new JSONObject();
//        PrintWriter pw = response.getWriter();
//        response.setCharacterEncoding("UTF-8");
//        response.setContentType("text/json; charset=UTF-8");
//
////        System.out.println("moveToElsePlace....");
//        fileSystemDao.moveToElsePlace(oldPath, newPath);
//        json.put("status", "ok");
//        json.put("fileList", fileSystemDao.getFileList(newPath));
//        pw.write(JSON.toJSONString(json));
//    }
//
//    // 文件重命名
//    @RequestMapping("/fileRename")
//    public void fileRename(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        String oldName = request.getParameter("oldName");
//        String newName = request.getParameter("newName");
//
//        JSONObject json = new JSONObject();
//        PrintWriter pw = response.getWriter();
//        response.setCharacterEncoding("UTF-8");
//        response.setContentType("text/json; charset=UTF-8");
//
//        fileSystemDao.fileRename(oldName, newName);
//        json.put("status", "ok");
//        pw.write(JSON.toJSONString(json));
//
//    }
//
//    // 用户登录
//    @RequestMapping("/login")
//    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        String userName = request.getParameter("userName");
//        String password = request.getParameter("password");
//        System.out.println("userName= " + userName + " , password= " + password);
//
//        PrintWriter pw = response.getWriter();
//        JSONObject json = new JSONObject();
//        response.setCharacterEncoding("utf-8");
//        response.setContentType("text/json; charset=UTF-8");
//
//        // 检查用户信息是否正确
//        if (!fileSystemDao.loginCheck(userName, password)) {
//            System.out.println("未找到相关用户信息！");
//            json.put("status", "error");
//            pw.write(JSON.toJSONString(json));
//            return;
//        }
//
//        json.put("status", "ok");
//        json.put("username", userName);
//        json.put("currentPath", "/" + userName);
//        List<HDFSFile> fileList = fileSystemDao.getFileList("/" + userName);
//        json.put("fileList", fileList);
//        System.out.println(fileList);
//
//        pw.write(JSON.toJSONString(json));
//
////        session.setAttribute("LOGIN_STATUE",userName);
////        session.setAttribute("path","/"+userName);
//
////        model.addAttribute("currentpath","/"+userName);
////        model.addAttribute("filelist", fileSystemDao.getFileList("/"+userName));
//
////        return "myfiles";
//    }
//
//    // 用户注册
//    @RequestMapping("/register")
//    public void register(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        System.out.println("register is running.....");
//        String userName = request.getParameter("username");
//        String password = request.getParameter("password");
//        String password_confirm = request.getParameter("confirmPassword");
//
//        PrintWriter pw = response.getWriter();
//
//        response.setCharacterEncoding("utf-8");
//        response.setContentType("text/json; charset=UTF-8");
//
//        JSONObject json = new JSONObject();
//
//        if (!password.equals(password_confirm)) {
//            json.put("status", "notsame");
//            pw.write(JSON.toJSONString(json));
//            return;
//        }
//
//        // 检验用户名是否存在
//        if (fileSystemDao.userExistCheck(userName)) {
//            json.put("status", "userexist");
//            pw.write(JSON.toJSONString(json));
//            return;
//        }
//
//        // 添加用户信息到userinfo.dat 文件中
//        fileSystemDao.insertUserInfoToFile(userName, password);
//
//        json.put("status", "ok");
//        pw.write(JSON.toJSONString(json));
//    }
//
//    // 创建目录
//    @RequestMapping("/makeDir")
//    public void makeDir(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        String currentPath = request.getParameter("currentPath");
//        String filename = request.getParameter("filename");
//        PrintWriter pw = response.getWriter();
//        JSONObject json = new JSONObject();
//        response.setCharacterEncoding("utf-8");
//        response.setContentType("text/json; charset=UTF-8");
//
//        System.out.println("makeDir: " + currentPath + "/" + filename);
//        if (fileSystemDao.checkFileExist(currentPath + "/" + filename)) {
//            json.put("status", "isexist");
//            pw.write(JSON.toJSONString(json));
//            return;
//        } else {
//            fileSystemDao.mdkir(currentPath + "/" + filename);
//            json.put("status", "ok");
//            json.put("fileList", fileSystemDao.getFileList(currentPath));
//            pw.write(JSON.toJSONString(json));
//            return;
//        }
//    }
//
//    // 删除文件
//    @RequestMapping("/deleteFile")
//    public void deleteFile(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        String fileName = request.getParameter("fileName");
//        String path = request.getParameter("path");
//        String newPath = path + "/" + fileName;
//        fileSystemDao.delete(newPath);
//
//        JSONObject json = new JSONObject();
//        response.setCharacterEncoding("UTF-8");
//        response.setContentType("text/json; charset=UTF-8");
//        PrintWriter pw = response.getWriter();
//
//        json.put("status", "ok");
//        json.put("fileList", fileSystemDao.getFileList(path));
//
//        pw.write(JSON.toJSONString(json));
//    }
//
//    // 删除用户
//    @RequestMapping("/deleteUser")
//    public String deleteUser(HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model) throws IOException {
//        String userName = session.getAttribute("path").toString().split("/")[1];
//        if (!fileSystemDao.userExistCheck(userName)) {
//            System.out.println("未找到相关用户信息！");
//            return "deleteUser";
//        } else {
//            fileSystemDao.deleteUserInfoFromFile(userName);
//            fileSystemDao.delete("/" + userName);
//            return "index";
//        }
//    }
//
//    // 返回上级目录
//    @RequestMapping("/back")
//    public void back(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        String oldPath = request.getParameter("path");
//        int i = oldPath.lastIndexOf("/");
//        JSONObject json = new JSONObject();
//        response.setCharacterEncoding("utf-8");
//        response.setContentType("text/json; charset=UTF-8");
//        PrintWriter pw = response.getWriter();
//
//        // 已经是用户根目录，无法再返回上级目录
//        if (i == 0) {
//            json.put("status", "error");
//            pw.write(JSON.toJSONString(json));
//            return;
//        } else {
//            String newPath = oldPath.substring(0, i);
//            json.put("status", "ok");
//            json.put("path", newPath);
//            json.put("fileList", fileSystemDao.getFileList(newPath));
//            pw.write(JSON.toJSONString(json));
//        }
//
//    }
//
//    // 下载文件（GET）
//    @RequestMapping(value = "/download", method = RequestMethod.GET)
//    public void download(@RequestParam String fileName, @RequestParam String currentPath, HttpServletRequest request, HttpServletResponse response) throws IOException {
//        String downloadPath = currentPath + "/" + fileName;
//
//        // 如果是文件，则下载文件
//        response.setCharacterEncoding("utf-8");
//        response.setContentType("application/x-download");
//
//        FileSystem fs = fileSystemDao.getFileSystem();
//        FSDataInputStream in = fs.open(new Path(downloadPath));
////            response.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
//        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
//        BufferedInputStream bis = new BufferedInputStream(in);
//        BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
//
//        byte[] buffer = new byte[1024];
//
//        int len;
//        while ((len = bis.read(buffer, 0, buffer.length)) != -1) {
//            bos.write(buffer, 0, len);
//        }
//
//        response.flushBuffer();
//        bis.close();
//        bos.close();
//        fs.close();
//    }
//
//    //处理文件和目录请求，如果是目录，则跳转到对应目录中去
//    @RequestMapping("/fileHandle")
//    public void fileHandle(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        String fileName = request.getParameter("fileName");
//        String path = request.getParameter("currentPath");
//
//        // 如果是目录，则打开目录
//        PrintWriter pw = response.getWriter();
//        JSONObject json = new JSONObject();
//        response.setCharacterEncoding("utf-8");
//        response.setContentType("text/json;charset=UTF-8");
//        String newPath = path + "/" + fileName;
//        json.put("status", "ok");
//        json.put("fileList", fileSystemDao.getFileList(newPath));
//        json.put("currentPath", newPath);
//        pw.write(JSON.toJSONString(json));
//        return;
//    }
//
//    //文件上传
//    @RequestMapping("/upload")
//    public void upload(HttpServletRequest request, HttpServletResponse response) throws FileUploadException, IOException {
//        // 获取文件
//        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
//        MultipartFile multipartFile = multipartRequest.getFile("file");//file是form-data中二进制字段对应的name
//
//        // 获取的名字 格式需调整
//        String totalPath = request.getParameter("filename");
//        int i = totalPath.lastIndexOf("\\");
//        String filename = totalPath.substring(i + 1, totalPath.length());
//        String path = request.getParameter("path");
//        String newPath = path + "/" + filename;
//
//        response.setCharacterEncoding("utf-8");
//        response.setContentType("text/json; charset=UTF-8");
////        response.setContentType("json/text;charset=UTF-8");
//        JSONObject json = new JSONObject();
//        PrintWriter pw = response.getWriter();
//
//        // 文件已经存在（有同名的文件）
//        boolean fileExist = fileSystemDao.checkFileExist(newPath);
//        if (fileExist) {
//            json.put("status", "isExist");
//            pw.write(JSON.toJSONString(json));
//            return;
//        }
//
//        InputStream is = multipartFile.getInputStream();
//        Path outputPath = new Path(newPath);
//        FileSystem fs = fileSystemDao.getFileSystem();
//        FSDataOutputStream fsdos = fs.create(outputPath);
//        byte[] b = new byte[1024 * 8];
//        int len = 0;
//        while ((len = is.read(b)) != -1) {
//            fsdos.write(b, 0, len);
//        }
//        is.close();
//        fsdos.close();
//
//        json.put("status", "ok");
//        json.put("fileList", fileSystemDao.getFileList(path));
//        pw.write(JSON.toJSONString(json));
//        return;
//        // 获取项目所在的路径
////        String root = System.getProperty("user.dir");
////        System.out.println(root);
//
//    }
//}
//
