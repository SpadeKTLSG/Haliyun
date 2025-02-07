package xyz.spc.common.util.fileUtil;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import lombok.extern.slf4j.Slf4j;
import xyz.spc.common.util.webUtil.HttpsUtil;

import java.util.List;

@Slf4j
public final class DownloadUtil {

    /**
     * 获取 RSA Hex 格式密钥
     *
     * @return RSA Hex 格式密钥
     */
    public static synchronized String getRsaHexKeyOrGenerate() {
        String rsaHexKey = "";
//        SystemConfigDTO systemConfigDTO = systemConfigService.getSystemConfig();
//        String rsaHexKey = systemConfigDTO.getRsaHexKey();
//        if (StrUtil.isEmpty(rsaHexKey)) {
//            byte[] key = SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue()).getEncoded();
//            rsaHexKey = HexUtil.encodeHexStr(key);
//
//            SystemConfig loginVerifyModeConfig = systemConfigMapper.findByName(SystemConfigConstant.RSA_HEX_KEY);
//            loginVerifyModeConfig.setValue(rsaHexKey);
//            systemConfigMapper.updateById(loginVerifyModeConfig);
//            systemConfigDTO.setRsaHexKey(rsaHexKey);
//            //    @ApiModelProperty(value = "RAS Hex Key", example = "r2HKbzc1DfvOs5uHhLn7pA==")
//            //    private String rsaHexKey;
//            Cache cache = cacheManager.getCache(CACHE_NAME);
//            Optional.ofNullable(cache).ifPresent(cache1 -> cache1.put("dto", systemConfigDTO));
//        }
        return rsaHexKey;
    }

    private static final String PROXY_DOWNLOAD_LINK_DELIMITER = ":";

    /**
     * 服务器代理下载 URL 有效期 (分钟).
     */
    public static final Integer PROXY_DOWNLOAD_LINK_EFFECTIVE_SECOND = 1800;


    /**
     * 生成签名
     *
     * @param storageId       存储源 ID
     * @param pathAndName     文件路径及名称
     * @param effectiveSecond 有效时间, 单位秒
     * @return 签名
     */
    public static String generatorSignature(Integer storageId, String pathAndName, Integer effectiveSecond) {

        // 如果有效时间为空, 则设置 30 分钟过期
        if (effectiveSecond == null || effectiveSecond < 1) {
            effectiveSecond = PROXY_DOWNLOAD_LINK_EFFECTIVE_SECOND;
        }

        // 过期时间的秒数
        long second = DateUtil.offsetSecond(DateUtil.date(), effectiveSecond).getTime();
        String content = storageId + PROXY_DOWNLOAD_LINK_DELIMITER + pathAndName + PROXY_DOWNLOAD_LINK_DELIMITER + second;

        String rsaHexKey = getRsaHexKeyOrGenerate();
        byte[] key = HexUtil.decodeHex(rsaHexKey);
        //构建
        SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, key);

        //加密
        return aes.encryptHex(content);
    }


    /**
     * 校验签名是否过期
     *
     * @param expectedStorageId   期望的存储源 ID
     * @param expectedPathAndName 期望的文件路径及名称
     * @param signature           签名
     * @return 是否过期
     */
    public static boolean validSignatureExpired(Integer expectedStorageId, String expectedPathAndName, String signature) {


        String rsaHexKey = getRsaHexKeyOrGenerate();
        byte[] key = HexUtil.decodeHex(rsaHexKey);
        SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, key);

        long currentTimeMillis = System.currentTimeMillis();

        String storageId = null;
        String pathAndName = null;
        String expiredSecond = null;

        try {
            //解密
            String decryptStr = aes.decryptStr(signature);
            List<String> split = StrUtil.split(decryptStr, PROXY_DOWNLOAD_LINK_DELIMITER);
            storageId = split.get(0);
            pathAndName = split.get(1);
            expiredSecond = split.get(2);

            // 校验存储源 ID 和文件路径及是否过期.
            if (StrUtil.equals(storageId, Convert.toStr(expectedStorageId)) && StrUtil.equals(HttpsUtil.concat(pathAndName), HttpsUtil.concat(expectedPathAndName)) && currentTimeMillis < Convert.toLong(expiredSecond)) {
                return true;
            }

            log.warn("校验链接已过期或不匹配, signature: {}, storageId={}, pathAndName={}, expiredSecond={}, now:={}", signature, storageId, pathAndName, expiredSecond, currentTimeMillis);
        } catch (Exception e) {
            log.error("校验签名链接异常, signature: {}, storageId={}, pathAndName={}, expiredSecond={}, now:={}", signature, storageId, pathAndName, expiredSecond, currentTimeMillis);
            return false;
        }

        return false;
    }

}
