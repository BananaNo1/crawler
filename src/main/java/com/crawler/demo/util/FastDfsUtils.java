package com.crawler.demo.util;

import org.csource.common.MyException;
import org.csource.fastdfs.*;

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
            return fileIds[1];
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

    public static void delete(String localFileName) {
        try {
            ClientGlobal.init(conf_filename);
            TrackerClient trackerClient = new TrackerClient();
            TrackerServer trackerServer = trackerClient.getConnection();
            StorageServer storageServer = null;
            StorageClient storageClient = new StorageClient(trackerServer, storageServer);
            int i = storageClient.delete_file("group1", "M00"+localFileName);
            System.out.println(i == 0 ? "删除成功" : "删除失败" + i);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }

    }

    /*@Override
    public void afterPropertiesSet() throws Exception {
        ClientGlobal.init(conf_filename);
        TrackerClient tracker = new TrackerClient();
        trackerServer = tracker.getConnection();
        storageClient = new StorageClient(trackerServer, storageServer);
    }*/
}
