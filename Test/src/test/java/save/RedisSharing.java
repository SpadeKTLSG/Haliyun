//package save;
//
//public class RedisSharing {
//
//
//    /**
//     * 向 Redis 中加入分享
//     *
//     * @param shareName 分享人名称。
//     * @param shareData 分享时间
//     * @param filePath  被分享文件的路径
//     * @param isEncrypt 是否加密
//     */
//    public String addShare(String shareName, Date shareData, String filePath, boolean isEncrypt) {
//        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
//        String fileKey = "Path:" + uuid;
//        String ShareKey = "Share:" + uuid;
//        String meShareKey = "Name:" + shareName + ":" + uuid;
//        String pwd = null;
//        if (isEncrypt) {
//            pwd = RedisUtil.getStringRandom(4);
//            fileKey += ":" + pwd;
//        }
//        //此时存储的是完整的地址
//        filePath = OSSClient.basePath + filePath;
//        putShare(fileKey, filePath);
//        putShare(meShareKey, new Share_me(uuid, pwd, filePath, shareData));
//        putShare(ShareKey, new Share(uuid, shareName, shareData, isEncrypt));
//
//        return new Msg(0, new Share_ret(uuid, pwd).toJson(), null).toJson();
//
//    }
//
//    /**
//     * 获得分享的文件
//     *
//     * @param key 秘钥
//     * @param pwd 提取码
//     * @return
//     */
//    public String getShare(String key, String pwd) {
//        key = "Path:" + key;
//        if (!pwd.equals("0")) key += ":" + pwd;
//        try {
//            System.out.println(key);
//            return redisTemplate.opsForValue().get(key).toString();
//        } catch (NullPointerException e) {
//            return "0";
//        }
//
//    }
//
//}
