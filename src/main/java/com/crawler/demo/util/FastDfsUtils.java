package com.crawler.demo.util;

import org.csource.common.MyException;
import org.csource.fastdfs.*;
import org.springframework.beans.factory.InitializingBean;

import java.io.IOException;

/**
 * @ClassName FastDfsUtils
 * @Description TODO
 * @Author leis
 * @Date 2018/12/29 15:53
 * @Version 1.0
 **/
public class FastDfsUtils {

    public static String conf_filename = "src/main/resources/fds_client.conf";
    private static TrackerServer trackerServer = null;
    private static StorageServer storageServer = null;
    private static StorageClient storageClient = null;

    public static String upload(String localFileName) throws IOException {
        try {
            ClientGlobal.init(conf_filename);
            TrackerClient tracker = new TrackerClient();
            trackerServer = tracker.getConnection();
            storageClient = new StorageClient(trackerServer, storageServer);
            String[] fileIds = storageClient.upload_file(localFileName, "jpg", null);
            return fileIds[0] + "/" + fileIds[1];
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("上传失败");
        } finally {
            if (trackerServer != null) {
                trackerServer.close();
            }
        }
        return null;
    }

    /*@Override
    public void afterPropertiesSet() throws Exception {
        ClientGlobal.init(conf_filename);
        TrackerClient tracker = new TrackerClient();
        trackerServer = tracker.getConnection();
        storageClient = new StorageClient(trackerServer, storageServer);
    }*/
}
