package com.ts.shop.util;


import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.common.utils.DateUtil;
import com.aliyun.oss.model.*;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ts.shop.domain.TsshopConfigStorage;
import com.ts.shop.domain.param.AliOssConfigParams;
import com.ts.shop.mapper.TsshopConfigStorageMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URL;
import java.net.URLDecoder;
import java.text.ParseException;
import java.util.*;

import static com.aliyun.oss.internal.OSSConstants.URL_ENCODING;

/**
 * @Description: 阿里云OSS服务器工具类
 * @author: 孙亚威
 * @date: 2020/12/24 16:26
 */
@Component
public class OssUtil {

    @Autowired
    TsshopConfigStorageMapper tsshopConfigStorageMapper;

    @Value("${oss.endpoint}")
    private String endpoint;
    @Value("${oss.accessKeyId}")
    private String accessKeyId;
    @Value("${oss.accessKeySecret}")
    private String accessKeySecret;
    @Value("${oss.endpointECS}")
    private String endpointECS;
    @Value("${oss.bucketName}")
    private String bucketName;
    @Value("${oss.domain}")
    private String domain;

    private static final Logger log = LoggerFactory.getLogger(OssUtil.class);

    /**
     * 多图片上传
     *
     * @param fileList
     * @return
     */
    public String checkList(TsshopConfigStorage tsshopConfigStorage,List<MultipartFile> fileList) {
        AliOssConfigParams aliOssConfigParams = JSONUtil.toBean(tsshopConfigStorage.getConfig(), AliOssConfigParams.class);
        String fileUrl = "";
        String str = "";
        String photoUrl = "";
        for (int i = 0; i < fileList.size(); i++) {
            fileUrl = uploadImg2Oss(aliOssConfigParams,fileList.get(i));
            str = getImgUrl(aliOssConfigParams,fileUrl);
            if (i == 0) {
                photoUrl = str;
            } else {
                photoUrl += "," + str;
            }
        }
        return photoUrl.trim();
    }

    /**
     * 单个图片上传
     *
     * @param file
     * @return
     */
    public String checkImage(TsshopConfigStorage tsshopConfigStorage,MultipartFile file) {
        AliOssConfigParams aliOssConfigParams = JSONUtil.toBean(tsshopConfigStorage.getConfig(), AliOssConfigParams.class);
        String fileUrl = uploadImg2Oss(aliOssConfigParams,file);
        String str = getImgUrl(aliOssConfigParams,fileUrl);
        return str.trim();
    }

    /**
     * 上传图片
     *
     * @param file
     * @return
     */
    public String uploadImg2Oss(AliOssConfigParams aliOssConfigParams,MultipartFile file) {
        if (file.getSize() > 1024 * 1024 * 20) {
            return "图片太大";//RestResultGenerator.createErrorResult(ResponseEnum.PHOTO_TOO_MAX);
        }
        String originalFilename = file.getOriginalFilename();
        String substring = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
        Random random = new Random();
        String name = random.nextInt(10000) + System.currentTimeMillis() + substring;
        try {
            InputStream inputStream = file.getInputStream();
            this.uploadFile2OSS(aliOssConfigParams,inputStream, name);
            return name;//RestResultGenerator.createSuccessResult(name);
        } catch (Exception e) {
            return "上传失败";//RestResultGenerator.createErrorResult(ResponseEnum.PHOTO_UPLOAD);
        }
    }

    /**
     * 上传图片获取fileUrl
     *
     * @param instream
     * @param fileName
     * @return
     */

    private String uploadFile2OSS(AliOssConfigParams aliParams,InputStream instream, String fileName) {
        String ret = "";
        try {
            //创建上传Object的Metadata
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(instream.available());
            objectMetadata.setCacheControl("no-cache");
            objectMetadata.setHeader("Pragma", "no-cache");
            objectMetadata.setContentType(getcontentType(fileName.substring(fileName.lastIndexOf("."))));
            objectMetadata.setContentDisposition("inline;filename=" + fileName);
            OSS ossClient = new OSSClientBuilder().build(aliParams.getEndpoint(), aliParams.getAccessKeyId(), aliParams.getAccessKeySecret());
            PutObjectResult putResult = ossClient.putObject(aliParams.getBucketName(), aliParams.getFiledir() + fileName, instream, objectMetadata);
            ret = putResult.getETag();
            ossClient.shutdown();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } finally {
            try {
                if (instream != null) {
                    instream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    public static String getcontentType(String FilenameExtension) {
        if (FilenameExtension.equalsIgnoreCase(".bmp")) {
            return "image/bmp";
        }
        if (FilenameExtension.equalsIgnoreCase(".gif")) {
            return "image/gif";
        }
        if (FilenameExtension.equalsIgnoreCase(".jpeg") ||
                FilenameExtension.equalsIgnoreCase(".jpg") ||
                FilenameExtension.equalsIgnoreCase(".png")) {
            return "image/jpeg";
        }
        if (FilenameExtension.equalsIgnoreCase(".html")) {
            return "text/html";
        }
        if (FilenameExtension.equalsIgnoreCase(".txt")) {
            return "text/plain";
        }
        if (FilenameExtension.equalsIgnoreCase(".vsd")) {
            return "application/vnd.visio";
        }
        if (FilenameExtension.equalsIgnoreCase(".pptx") ||
                FilenameExtension.equalsIgnoreCase(".ppt")) {
            return "application/vnd.ms-powerpoint";
        }
        if (FilenameExtension.equalsIgnoreCase(".docx") ||
                FilenameExtension.equalsIgnoreCase(".doc")) {
            return "application/msword";
        }
        if (FilenameExtension.equalsIgnoreCase(".xml")) {
            return "text/xml";
        }
        return "image/jpeg";
    }

    /**
     * 获取图片路径
     *
     * @param fileUrl
     * @return
     */
    public String getImgUrl(AliOssConfigParams aliOssConfigParams,String fileUrl) {
        if (!StringUtils.isEmpty(fileUrl)) {
            String[] split = fileUrl.split("/");
            String url = this.getUrl(aliOssConfigParams,aliOssConfigParams.getFiledir() + split[split.length - 1]);
            return url;
        }
        return null;
    }

    /**
     * 获得url链接
     *
     * @param key
     * @return
     */
    public String getUrl(AliOssConfigParams aliParams,String key) {
        // 设置URL过期时间为10年  3600l* 1000*24*365*10
        Date expiration = new Date(System.currentTimeMillis() + 3600L * 1000 * 24 * 365 * 10);
        // 生成URL
        OSSClient ossClient = new OSSClient(aliParams.getEndpoint(), aliParams.getAccessKeyId(), aliParams.getAccessKeySecret());
        URL url = ossClient.generatePresignedUrl(aliParams.getBucketName(), key, expiration);
        if (url != null) {
            return url.toString();
        }
        return null;
    }


    public Map<String, Object> fileUploadVoucher() {
        AliOssConfigParams aliParams = getAliParams();
        try {
            //国企时间
            long expireEndTime = System.currentTimeMillis() + aliParams.getExpireTime() * 1000L;
            Date expiration = new Date(expireEndTime);
            PolicyConditions policyConds = new PolicyConditions();
            policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, aliParams.getMaxFileSize());
            policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, aliParams.getFiledir());
            OSSClient client = new OSSClient(aliParams.getEndpoint(), aliParams.getAccessKeyId(), aliParams.getAccessKeySecret());
            String postPolicy = client.generatePostPolicy(expiration, policyConds);
            byte[] binaryData = postPolicy.getBytes("utf-8");
            String encodedPolicy = BinaryUtil.toBase64String(binaryData);
            String postSignature = client.calculatePostSignature(postPolicy);

            Map<String, Object> respMap = new LinkedHashMap<>();
            respMap.put("accessId", aliParams.getAccessKeyId());
            respMap.put("policy", encodedPolicy);
            respMap.put("signature", postSignature);
            respMap.put("dir", aliParams.getFiledir());
            respMap.put("host", aliParams.getHost());
            respMap.put("expire", expireEndTime);
            return respMap;
        } catch (Exception e) {
            throw new RuntimeException("获取失败！");
        }

    }

    public Map<String, String> payFileUpload(TsshopConfigStorage tsshopConfigStorage,MultipartFile file, String path) {
        AliOssConfigParams aliParams = JSONUtil.toBean(tsshopConfigStorage.getConfig(), AliOssConfigParams.class);

        Map<String, String> result = new HashMap<>();
        String originalFilename = file.getOriginalFilename();
        String name = path + new DateTime().toString("yyyyMMddHHmmss") + "/" + originalFilename;
        result.put("url", "/" + name);
        String ret = "";
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
            //创建上传Object的Metadata
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(inputStream.available());
            objectMetadata.setCacheControl("no-cache");
            objectMetadata.setHeader("Pragma", "no-cache");
            objectMetadata.setContentType(getcontentType(name.substring(name.lastIndexOf("."))));
            objectMetadata.setContentDisposition("inline;filename=" + name);
            //上传文件
            OSS ossClient = new OSSClientBuilder().build(aliParams.getEndpoint(), aliParams.getAccessKeyId(), aliParams.getAccessKeySecret());
            PutObjectResult putResult = ossClient.putObject(aliParams.getBucketName(), name, inputStream);
            ret = putResult.getETag();
            ossClient.shutdown();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public File getCertFile(TsshopConfigStorage tsshopConfigStorage,String certFilePath) {
        AliOssConfigParams aliOssConfigParams = JSONUtil.toBean(tsshopConfigStorage.getConfig(), AliOssConfigParams.class);
        //项目运行目录
        String dir = System.getProperty("user.dir");

        File certFile = new File(dir + File.separator + "pay" + certFilePath);

        // 本地存在直接返回
        if (certFile.exists()) {
            return certFile;
        }

        // 已经向oss请求并且返回了空文件时
        if (new File(certFile.getAbsolutePath() + ".notexists").exists()) {
            return certFile;
        }

        // 当文件夹不存在时， 需要创建。
        if (!certFile.getParentFile().exists()) {
            certFile.getParentFile().mkdirs();
        }

        // 请求下载并返回 新File
        return downloadFile(aliOssConfigParams,certFilePath, certFile);
    }

    private File downloadFile(AliOssConfigParams aliOssConfigParams,String certFilePath, File certFile) {
        //请求文件并写入
        boolean isSuccess;

        try {
            String url = aliOssConfigParams.getDomain().substring(0, aliOssConfigParams.getDomain().length() - 1) + certFilePath;
            System.out.println("url:" + url);
            HttpUtil.downloadFile(url, new File(certFile.getAbsolutePath()));
            isSuccess = true;
        } catch (Exception e) {
            System.err.println("下载错误");
            e.printStackTrace();
            isSuccess = false;
        }

        // 下载成功 返回新的File对象
        if (isSuccess) {
            return new File(certFile.getAbsolutePath());
        }

        // 下载失败，写入.notexists文件，避免那下次再次下载影响效率。
        try {
            new File(certFile.getAbsolutePath() + ".notexists").createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return certFile;
    }

    public AliOssConfigParams getAliParams() {
        TsshopConfigStorage tsshopConfigStorage = tsshopConfigStorageMapper.selectOne(new LambdaQueryWrapper<TsshopConfigStorage>().eq(TsshopConfigStorage::getOpenStatus, true));
        if (ObjectUtil.isNull(tsshopConfigStorage)) {
            throw new RuntimeException("OSS 参数异常，请联系管理员");
        }
        return JSONUtil.toBean(tsshopConfigStorage.getConfig(), AliOssConfigParams.class);
    }

    public String uploadFile(TsshopConfigStorage tsshopConfigStorage,MultipartFile file, String src, int time) throws IOException, ParseException {
        AliOssConfigParams aliOssConfigParams = JSONUtil.toBean(tsshopConfigStorage.getConfig(), AliOssConfigParams.class);
        String originalFileName = file.getOriginalFilename();
        assert originalFileName != null;
        String h = originalFileName.substring(originalFileName.lastIndexOf("."));
        if (h.length() == 0) {
            h = ".jpg";
        }
        String t = new Date().getTime() + (new Random().nextInt(10000) + "");
        src = src + t + h;
        byte[] byteArr = file.getBytes();
        InputStream input = new ByteArrayInputStream(byteArr);
        ObjectMetadata meta = new ObjectMetadata();
        if (time != 0) {
            Calendar curr = Calendar.getInstance();
            curr.set(Calendar.MONTH, curr.get(Calendar.MONTH) + time); //增加几月
            Date date = curr.getTime();
            meta.setExpirationTime(date);//一个月后消失
        }
        uploadFile(aliOssConfigParams,src, input, meta);
        return aliOssConfigParams.getDomain() + src;
    }

    public void deleteFile(String src) {
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        ossClient.deleteObject(bucketName, src);
        ossClient.shutdown();
    }

    public void deleteFileList(List<String> keys) {
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        DeleteObjectsResult deleteObjectsResult = ossClient.deleteObjects(new DeleteObjectsRequest(bucketName).withKeys(keys).withEncodingType(URL_ENCODING));
        List<String> deletedObjects = deleteObjectsResult.getDeletedObjects();
        try {
            for (String obj : deletedObjects) {
                String deleteObj = URLDecoder.decode(obj, "UTF-8");
                System.out.println(deleteObj);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
// 关闭OSSClient。
        ossClient.shutdown();
    }

    public String uploadFile(String address, InputStream inputStream) {
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        ossClient.putObject(bucketName, address, inputStream);
        ossClient.shutdown();
        return address;
    }

    public void uploadFile(AliOssConfigParams aliOssConfigParams,String address, InputStream inputStream, ObjectMetadata mate) {
        OSS ossClient = new OSSClientBuilder().build(aliOssConfigParams.getEndpoint(),aliOssConfigParams.getAccessKeyId(),aliOssConfigParams.getAccessKeySecret());
        ossClient.putObject(aliOssConfigParams.getBucketName(), address, inputStream, mate);
        ossClient.shutdown();
    }

    public void copyFile(String sourceKey, String destinationKey, int time) throws ParseException {
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        CopyObjectRequest request = new CopyObjectRequest(bucketName, sourceKey, bucketName, destinationKey);
        ObjectMetadata meta = new ObjectMetadata();
        if (time == 0) {
            meta.setExpirationTime(DateUtil.parseIso8601Date("2100-01-01T00:00:00.000Z"));
        } else {
            Calendar curr = Calendar.getInstance();
            curr.set(Calendar.MONTH, curr.get(Calendar.MONTH) + time); //增加一月
            Date date = curr.getTime();
            meta.setExpirationTime(date);//一个月后消失
        }
        request.setNewObjectMetadata(meta);
        ossClient.copyObject(request);
        ossClient.shutdown();
    }

    public Boolean doesObjectExist(String remoteFileName) {
        // endpoint以杭州为例，其它region请按实际情况填写
        String endpoint = "";
        // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录
        String accessKeyId = "";
        String accessKeySecret = "";
        // 创建OSSClient实例
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        Boolean result = ossClient.doesObjectExist(bucketName, remoteFileName);
        // 关闭OSSClient。
        ossClient.shutdown();
        return result;
    }

    public boolean hasFile(String src) {
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        boolean found = ossClient.doesObjectExist(bucketName, src);
        ossClient.shutdown();
        return found;
    }

    public  String uploadFile(TsshopConfigStorage tsshopConfigStorage, MultipartFile file , int time) throws IOException {
        AliOssConfigParams aliOssConfigParams = JSONUtil.toBean(tsshopConfigStorage.getConfig(), AliOssConfigParams.class);
        String originalFileName = file.getOriginalFilename();
        assert originalFileName != null;
        String h=originalFileName.substring(originalFileName.lastIndexOf("."));
        if(h.length()==0){
            h=".jpg";
        }
        String t=new Date().getTime()+(new Random().nextInt(10000)+"");
        String url= t+h;
        byte [] byteArr= file.getBytes();
        InputStream input = new ByteArrayInputStream(byteArr);
        ObjectMetadata meta = new ObjectMetadata();
        if(time!=0)
        {
            Calendar curr = Calendar.getInstance();
            curr.set(Calendar.MONTH,curr.get(Calendar.MONTH)+time); //增加几月
            Date date=curr.getTime();
            meta.setExpirationTime(date);//一个月后消失
        }
        uploadFile(aliOssConfigParams,url,input,meta);
        return aliOssConfigParams.getDomain() + url;
    }
}
