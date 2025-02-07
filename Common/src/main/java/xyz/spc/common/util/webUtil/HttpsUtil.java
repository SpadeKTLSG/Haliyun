package xyz.spc.common.util.webUtil;


import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.util.AntPathMatcher;
import xyz.spc.common.constant.HttpStatusCT;
import xyz.spc.common.util.collecUtil.StringUtil;

import javax.net.ssl.*;
import javax.servlet.ServletRequest;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.security.cert.X509Certificate;

import static xyz.spc.common.util.sysUtil.CharsetUtil.UTF_8;

/**
 * http/https 工具类
 */
@Slf4j
public final class HttpsUtil {


    private static final char DELIMITER = '/';

    private static final String DELIMITER_STR = "/";

    private static final String HTTP = "http";

    private static final String HTTP_PROTOCOL = "http://";

    private static final String HTTPS_PROTOCOL = "https://";


    /**
     * 判断url是否与规则配置
     * ? 表示单个字符;
     * * 表示一层路径内的任意字符串，不可跨层级;
     * ** 表示任意层路径;
     *
     * @param pattern 匹配规则
     * @param url     需要匹配的url
     */
    public static boolean isMatch(String pattern, String url) {
        AntPathMatcher matcher = new AntPathMatcher();
        return matcher.match(pattern, url);
    }


    /**
     * 是否为http(s)://开头
     *
     * @param link 链接
     * @return 结果
     */
    public static boolean ishttp(String link) {
        return StringUtils.startsWithAny(link, HttpStatusCT.HTTP, HttpStatusCT.HTTPS);
    }

    /**
     * URL编码
     */
    public static String encodeURL(String url) {
        return URLEncoder.encode(url, StandardCharsets.UTF_8);
    }

    /**
     * URL解码
     */
    public static String decodeURL(String url) {
        return URLDecoder.decode(url, StandardCharsets.UTF_8);
    }


    /**
     * 向指定 URL 发送GET方法的请求
     *
     * @param url 发送请求的 URL
     * @return 所代表远程资源的响应结果
     */
    public static String sendGet(String url) {
        return sendGet(url, StringUtil.EMPTY);
    }

    /**
     * 向指定 URL 发送GET方法的请求
     *
     * @param url   发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        return sendGet(url, param, UTF_8);
    }

    /**
     * 向指定 URL 发送GET方法的请求
     *
     * @param url         发送请求的 URL
     * @param param       请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @param contentType 编码类型
     * @return 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param, String contentType) {
        StringBuilder result = new StringBuilder();
        BufferedReader in = null;
        try {
            String urlNameString = StringUtil.isNotBlank(param) ? url + "?" + param : url;
            log.info("sendGet - {}", urlNameString);
            URL realUrl = new URL(urlNameString);
            URLConnection connection = realUrl.openConnection();
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.connect();
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(), contentType));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            log.info("recv - {}", result);
        } catch (ConnectException e) {
            log.error("调用HttpUtils.sendGet ConnectException, url=" + url + ",param=" + param, e);
        } catch (SocketTimeoutException e) {
            log.error("调用HttpUtils.sendGet SocketTimeoutException, url=" + url + ",param=" + param, e);
        } catch (IOException e) {
            log.error("调用HttpUtils.sendGet IOException, url=" + url + ",param=" + param, e);
        } catch (Exception e) {
            log.error("调用HttpsUtil.sendGet Exception, url=" + url + ",param=" + param, e);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception ex) {
                log.error("调用in.close Exception, url=" + url + ",param=" + param, ex);
            }
        }
        return result.toString();
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url   发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        try {
            log.info("sendPost - {}", url);
            URL realUrl = new URL(url);
            URLConnection conn = realUrl.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Accept-Charset", "utf-8");
            conn.setRequestProperty("contentType", "utf-8");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            out = new PrintWriter(conn.getOutputStream());
            out.print(param);
            out.flush();
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            log.info("recv - {}", result);
        } catch (ConnectException e) {
            log.error("调用HttpUtils.sendPost ConnectException, url=" + url + ",param=" + param, e);
        } catch (SocketTimeoutException e) {
            log.error("调用HttpUtils.sendPost SocketTimeoutException, url=" + url + ",param=" + param, e);
        } catch (IOException e) {
            log.error("调用HttpUtils.sendPost IOException, url=" + url + ",param=" + param, e);
        } catch (Exception e) {
            log.error("调用HttpsUtil.sendPost Exception, url=" + url + ",param=" + param, e);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                log.error("调用in.close Exception, url=" + url + ",param=" + param, ex);
            }
        }
        return result.toString();
    }

    /**
     * 向指定 URL 发送POST方法的请求, 但使用SSL
     *
     * @param url   发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendSSLPost(String url, String param) {
        StringBuilder result = new StringBuilder();
        String urlNameString = url + "?" + param;
        try {
            log.info("sendSSLPost - {}", urlNameString);
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, new TrustManager[]{new TrustAnyTrustManager()}, new java.security.SecureRandom());
            URL console = new URL(urlNameString);
            HttpsURLConnection conn = (HttpsURLConnection) console.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Accept-Charset", "utf-8");
            conn.setRequestProperty("contentType", "utf-8");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            conn.setSSLSocketFactory(sc.getSocketFactory());
            conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String ret = "";
            while ((ret = br.readLine()) != null) {
                if (!ret.trim().isEmpty()) {
                    result.append(new String(ret.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
                }
            }
            log.info("recv - {}", result);
            conn.disconnect();
            br.close();
        } catch (ConnectException e) {
            log.error("调用HttpUtils.sendSSLPost ConnectException, url=" + url + ",param=" + param, e);
        } catch (SocketTimeoutException e) {
            log.error("调用HttpUtils.sendSSLPost SocketTimeoutException, url=" + url + ",param=" + param, e);
        } catch (IOException e) {
            log.error("调用HttpUtils.sendSSLPost IOException, url=" + url + ",param=" + param, e);
        } catch (Exception e) {
            log.error("调用HttpsUtil.sendSSLPost Exception, url=" + url + ",param=" + param, e);
        }
        return result.toString();
    }

    /**
     * 获取请求Body
     */
    public static String getBodyString(ServletRequest request) {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = null;
        try (InputStream inputStream = request.getInputStream()) {
            reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            String line = "";
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            log.warn("getBodyString出现问题！");
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    log.error(ExceptionUtils.getMessage(e));
                }
            }
        }
        return sb.toString();
    }

    /**
     * 信任任何证书
     */
    private static class TrustAnyTrustManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[]{};
        }
    }

    /**
     * 信任任何主机
     */
    private static class TrustAnyHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }


    /**
     * 给 url 拼接参数
     *
     * @param url   原始 URL
     * @param name  参数名称
     * @param value 参数值
     * @return 拼接后的 URL
     */
    public static String concatQueryParam(String url, String name, String value) {
        if (StrUtil.contains(url, "?")) {
            return url + "&" + name + "=" + value;
        } else {
            return url + "?" + name + "=" + value;
        }
    }


    /**
     * 获取远程文件大小
     *
     * @param url 文件 URL
     * @return 文件大小
     */
    public static Long getRemoteFileSize(String url) throws IOException {
        URL urlObject = new URL(url);
        URLConnection conn = urlObject.openConnection();
        return (long) conn.getContentLength();
    }


    /**
     * 移除 URL 中的前后的所有 '/'
     *
     * @param path 路径
     * @return 如 path = '/folder1/file1/', 返回 'folder1/file1'
     * 如 path = '///folder1/file1//', 返回 'folder1/file1'
     */
    public static String trimSlashes(String path) {
        path = trimStartSlashes(path);
        path = trimEndSlashes(path);
        return path;
    }


    /**
     * 移除 URL 中的第一个 '/'
     *
     * @param path 路径
     * @return 如 path = '/folder1/file1', 返回 'folder1/file1'
     * 如 path = '/folder1/file1', 返回 'folder1/file1'
     */
    public static String trimStartSlashes(String path) {
        if (StrUtil.isEmpty(path)) {
            return path;
        }

        while (path.startsWith(DELIMITER_STR)) {
            path = path.substring(1);
        }

        return path;
    }


    /**
     * 移除 URL 中的最后一个 '/'
     *
     * @param path 路径
     * @return 如 path = '/folder1/file1/', 返回 '/folder1/file1'
     * 如 path = '/folder1/file1///', 返回 '/folder1/file1'
     */
    public static String trimEndSlashes(String path) {
        if (StrUtil.isEmpty(path)) {
            return path;
        }

        while (path.endsWith(DELIMITER_STR)) {
            path = path.substring(0, path.length() - 1);
        }

        return path;
    }


    /**
     * 去除路径中所有重复的 '/'
     *
     * @param path 路径
     * @return 如 path = '/folder1//file1/', 返回 '/folder1/file1/'
     * 如 path = '/folder1////file1///', 返回 '/folder1/file1/'
     */
    public static String removeDuplicateSlashes(String path) {
        if (StrUtil.isEmpty(path)) {
            return path;
        }

        StringBuilder sb = new StringBuilder();

        // 是否包含 http 或 https 协议信息
        boolean containProtocol = StrUtil.containsAnyIgnoreCase(path, HTTP_PROTOCOL, HTTPS_PROTOCOL);

        if (containProtocol) {
            path = trimStartSlashes(path);
        }

        // 是否包含 http 协议信息
        boolean startWithHttpProtocol = StrUtil.startWithIgnoreCase(path, HTTP_PROTOCOL);
        // 是否包含 https 协议信息
        boolean startWithHttpsProtocol = StrUtil.startWithIgnoreCase(path, HTTPS_PROTOCOL);

        if (startWithHttpProtocol) {
            sb.append(HTTP_PROTOCOL);
        } else if (startWithHttpsProtocol) {
            sb.append(HTTPS_PROTOCOL);
        }

        for (int i = sb.length(); i < path.length() - 1; i++) {
            char current = path.charAt(i);
            char next = path.charAt(i + 1);
            if (!(current == DELIMITER && next == DELIMITER)) {
                sb.append(current);
            }
        }
        sb.append(path.charAt(path.length() - 1));
        return sb.toString();
    }


    /**
     * 去除路径中所有重复的 '/', 并且去除开头的 '/'
     *
     * @param path 路径
     * @return 如 path = '/folder1//file1/', 返回 'folder1/file1/'
     * 如 path = '///folder1////file1///', 返回 'folder1/file1/'
     */
    public static String removeDuplicateSlashesAndTrimStart(String path) {
        path = removeDuplicateSlashes(path);
        path = trimStartSlashes(path);
        return path;
    }


    /**
     * 去除路径中所有重复的 '/', 并且去除结尾的 '/'
     *
     * @param path 路径
     * @return 如 path = '/folder1//file1/', 返回 '/folder1/file1'
     * 如 path = '///folder1////file1///', 返回 '/folder1/file1'
     */
    public static String removeDuplicateSlashesAndTrimEnd(String path) {
        path = removeDuplicateSlashes(path);
        path = trimEndSlashes(path);
        return path;
    }


    /**
     * 拼接 URL，并去除重复的分隔符 '/'，并去除开头的 '/', 但不会影响 http:// 和 https:// 这种头部.
     *
     * @param strs 拼接的字符数组
     * @return 拼接结果
     */
    public static String concatTrimStartSlashes(String... strs) {
        return trimStartSlashes(concat(strs));
    }


    /**
     * 拼接 URL，并去除重复的分隔符 '/'，并去除结尾的 '/', 但不会影响 http:// 和 https:// 这种头部.
     *
     * @param strs 拼接的字符数组
     * @return 拼接结果
     */
    public static String concatTrimEndSlashes(String... strs) {
        return trimEndSlashes(concat(strs));
    }


    /**
     * 拼接 URL，并去除重复的分隔符 '/'，并去除开头和结尾的 '/', 但不会影响 http:// 和 https:// 这种头部.
     *
     * @param strs 拼接的字符数组
     * @return 拼接结果
     */
    public static String concatTrimSlashes(String... strs) {
        return trimSlashes(concat(strs));
    }


    /**
     * 拼接 URL，并去除重复的分隔符 '/'，但不会影响 http:// 和 https:// 这种头部.
     *
     * @param strs 拼接的字符数组
     * @return 拼接结果
     */
    public static String concat(String... strs) {
        StringBuilder sb = new StringBuilder(DELIMITER_STR);
        for (int i = 0; i < strs.length; i++) {
            String str = strs[i];
            if (StrUtil.isEmpty(str)) {
                continue;
            }
            sb.append(str);
            if (i != strs.length - 1) {
                sb.append(DELIMITER);
            }
        }
        return removeDuplicateSlashes(sb.toString());
    }


}
