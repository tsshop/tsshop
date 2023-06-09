package com.shop.tsshop.web.util;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileCopier;
import com.shop.tsshop.common.config.TsshopConfig;
import com.shop.tsshop.common.exception.file.FileNameLengthLimitExceededException;
import com.shop.tsshop.common.exception.file.InvalidExtensionException;
import com.shop.tsshop.common.utils.file.FileUploadUtils;
import com.shop.tsshop.common.utils.file.MimeTypeUtils;
import com.shop.tsshop.common.utils.uuid.Seq;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 文件处理工具类
 *
 * @author : tsai
 */
@Component
public class LocalStorageUtils {

    @Value("${tsshop.profile}")
    private String profile;

    @Value("${tsshop.url}")
    private String url;

    public String uploadFile(MultipartFile file) throws IOException {
        return upload(profile + "/", url + "/files/", file);
    }

    /**
     * 根据文件路径上传
     *
     * @param baseDir 相对应用的基目录
     * @param file    上传的文件
     * @return 文件名称
     * @throws IOException
     */
    public static final String upload(String baseDir, String url, MultipartFile file) throws IOException {
        try {
            return upload(baseDir, file, url, MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION);
        } catch (Exception e) {
            throw new IOException(e.getMessage(), e);
        }
    }

    /**
     * 文件上传
     *
     * @param baseDir          相对应用的基目录
     * @param file             上传的文件
     * @param allowedExtension 上传文件类型
     * @return 返回上传成功的文件名
     * @throws FileSizeLimitExceededException       如果超出最大大小
     * @throws FileNameLengthLimitExceededException 文件名太长
     * @throws IOException                          比如读写文件出错时
     * @throws InvalidExtensionException            文件校验异常
     */
    public static final String upload(String baseDir, MultipartFile file, String url, String[] allowedExtension)
            throws FileSizeLimitExceededException, IOException, FileNameLengthLimitExceededException,
            InvalidExtensionException {
        int fileNameLength = Objects.requireNonNull(file.getOriginalFilename()).length();
        if (fileNameLength > FileUploadUtils.DEFAULT_FILE_NAME_LENGTH) {
            throw new FileNameLengthLimitExceededException(FileUploadUtils.DEFAULT_FILE_NAME_LENGTH);
        }

        assertAllowed(file, allowedExtension);

        String fileName = extractFilename(file);

        String absPath = getAbsoluteFile(baseDir, fileName).getAbsolutePath();
        file.transferTo(Paths.get(absPath));
        return getPathFileName(baseDir, url, fileName);
    }

    public static final String uploadPay(String baseDir, MultipartFile file, String url)
            throws FileSizeLimitExceededException, IOException, FileNameLengthLimitExceededException{
        int fileNameLength = Objects.requireNonNull(file.getOriginalFilename()).length();
        if (fileNameLength > FileUploadUtils.DEFAULT_FILE_NAME_LENGTH) {
            throw new FileNameLengthLimitExceededException(FileUploadUtils.DEFAULT_FILE_NAME_LENGTH);
        }
        String fileName = extractFilename(file);

        String absPath = getAbsolutePayFile(baseDir, fileName).getAbsolutePath();
        file.transferTo(Paths.get(absPath));
        return getPathFileName(baseDir, url, fileName);
    }

    /**
     * 编码文件名
     */
    public static final String extractFilename(MultipartFile file) {
        return FilenameUtils.getBaseName(file.getOriginalFilename()) + Seq.getId(Seq.uploadSeqType) + "." + getExtension(file);
    }

    public static final File getAbsolutePayFile(String uploadDir, String fileName) throws IOException {
        File desc = new File(uploadDir + File.separator);

        if (!desc.exists()) {
            if (!desc.getParentFile().exists()) {
                desc.getParentFile().mkdirs();
            }
        }
        return desc;
    }

    public static final File getAbsoluteFile(String uploadDir, String fileName) throws IOException {
        File desc = new File(uploadDir + File.separator + fileName);

        if (!desc.exists()) {
            if (!desc.getParentFile().exists()) {
                desc.getParentFile().mkdirs();
            }
        }
        return desc;
    }

    public static final String getPathFileName(String uploadDir, String url, String fileName) throws IOException {
        int dirLastIndex = TsshopConfig.getProfile().length() + 1;
        String currentDir = StringUtils.substring(uploadDir, dirLastIndex);
        return url + fileName;
    }

    /**
     * 文件大小校验
     *
     * @param file 上传的文件
     * @return
     * @throws FileSizeLimitExceededException 如果超出最大大小
     * @throws InvalidExtensionException
     */
    public static final void assertAllowed(MultipartFile file, String[] allowedExtension)
            throws FileSizeLimitExceededException, InvalidExtensionException {
        long size = file.getSize();

        String fileName = file.getOriginalFilename();
        String extension = getExtension(file);

    }

    /**
     * 判断MIME类型是否是允许的MIME类型
     *
     * @param extension
     * @param allowedExtension
     * @return
     */
    public static final boolean isAllowedExtension(String extension, String[] allowedExtension) {
        for (String str : allowedExtension) {
            if (str.equalsIgnoreCase(extension)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取文件名的后缀
     *
     * @param file 表单文件
     * @return 后缀名
     */
    public static final String getExtension(MultipartFile file) {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (StringUtils.isEmpty(extension)) {
            extension = MimeTypeUtils.getExtension(Objects.requireNonNull(file.getContentType()));
        }
        return extension;
    }

    /**
     * 批量上传图片
     *
     * @param fileList 图片集合
     * @return 图片路径
     */
    public String uploadFileList(List<MultipartFile> fileList) throws IOException {
        String fileUrl = "";
        String photoUrl = "";
        for (int i = 0; i < fileList.size(); i++) {
            fileUrl = uploadFile(fileList.get(i));
            if (i == 0) {
                photoUrl = fileUrl;
            } else {
                photoUrl += "," + fileUrl;
            }
        }
        return photoUrl.trim();
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
     * 支付文件上传
     *
     * @param file 文件
     * @param path 路径
     * @return result
     */
    public Map<String, String> payFileUpload(MultipartFile file, String path) {
        Map<String, String> result = new HashMap<>();
        String originalFilename = file.getOriginalFilename();
        String name = path + new DateTime().toString("yyyyMMddHHmmss") + "/" + originalFilename;
        String dir = System.getProperty("user.dir");
        try {
            String upload = uploadPay(dir + File.separator + name,file, url);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        result.put("url", "/" + name);
        return result;
    }
    /**
     * 获取文件
     * @param certFilePath            文件相对路径
     * @return                        文件
     */
    public File getCertFile(String certFilePath) {
        String dir = System.getProperty("user.dir");
        File certFile = new File(dir + File.separator + "pay" + certFilePath);
        // 本地存在直接返回
        if (certFile.exists()) {
            return certFile;
        }
        /**
         * @param1 源文件或目录
         * @param2 目标文件或目录，目标不存在会自动创建（目录、文件都创建）
         * @param3 是否覆盖目标文件
         * @return 目标目录或文件
         */
        return FileUtil.copy(profile + File.separator + certFilePath, dir + certFilePath, true);
    }
}