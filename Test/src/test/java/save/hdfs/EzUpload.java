//package save.hdfs;
//
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.multipart.MultipartHttpServletRequest;
//import org.springframework.web.servlet.ModelAndView;
//import save.save2.DateUtil;
//
//import java.io.InputStream;
//import java.util.Map;
//
//public class EzUpload {
//
//    /**
//     * 上传文件
//     *
//     * @param request
//     * @param httpSession
//     * @param dir
//     * @return
//     * @throws IOException
//     */
//    @RequestMapping("/uploadFile.do")
//    public ModelAndView uploadFile(HttpServletRequest request, HttpSession httpSession, @RequestParam(value = "dir") String dir, @RequestParam(value = "originalDir") String originalDir, @RequestParam(value = "parentid") long parentid) throws IOException {
//        User user = (User) httpSession.getAttribute(Constants.currentUserSessionKey);
//        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
//        if (multipartResolver.isMultipart(request)) {
//            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
//            multipartRequest.setCharacterEncoding("UTF-8");
//            Map<String, MultipartFile> fms = multipartRequest.getFileMap();
//            for (Map.Entry<String, MultipartFile> entity : fms.entrySet()) {
//                MultipartFile multipartFile = entity.getValue();
//                InputStream inputStream = multipartFile.getInputStream();
//
//                int splitIndex = multipartFile.getOriginalFilename().lastIndexOf(".");
//                String name = System.nanoTime() + "." + multipartFile.getOriginalFilename().substring(splitIndex + 1);
//
//                File file = new File();
//                file.setDir(false);
//                file.setFile(true);
//                file.setSize(FilesUtil.FormetFileSize(multipartFile.getSize()));
//                file.setOriginalName(multipartFile.getOriginalFilename());
//                file.setName(name);
//                if (dir.equals("/")) {
//                    file.setPath(dir + name);
//                    file.setOriginalPath(originalDir);
//                } else {
//                    file.setPath(dir + "/" + name);
//                    file.setOriginalPath(originalDir + "/");
//                }
//                file.setViewflag("N");
//                String nameSufix = FilesUtil.getFileSufix(name);
//                for (int i = 0; i < Constants.sufix.length; i++) {
//                    if (nameSufix.equals(Constants.sufix[i])) {
//                        file.setViewflag("Y");
//                        break;
//                    }
//                }
//                file.setDate(DateUtil.DateToString("yyyy-MM-dd HH:mm:ss", new Date()));
//                fileService.uploadFile(inputStream, file, user, parentid);
//                inputStream.close();
//            }
//        }
//        return null;
//    }
//
//}
