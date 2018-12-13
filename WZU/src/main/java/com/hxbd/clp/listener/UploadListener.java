package com.hxbd.clp.listener;

import org.apache.commons.fileupload.ProgressListener;

import com.hxbd.clp.to.UploadStatus;

import javax.servlet.http.HttpSession;

/**
 * 文件上传监听类
 */
public class UploadListener implements ProgressListener {

    private HttpSession session;

    public UploadListener(HttpSession session) {
        super();
        this.session = session;
        UploadStatus uploadStatus = new UploadStatus();
        session.setAttribute("upload_status", uploadStatus);
    }

    @Override
    public void update(long bytesRead, long contentLength, int items) {
        UploadStatus uploadStatus = (UploadStatus) session.getAttribute("upload_status");
        uploadStatus.setBytesRead(bytesRead);
        uploadStatus.setContentLength(contentLength);
        uploadStatus.setItems(items);
        uploadStatus.setUseTime(System.currentTimeMillis() - uploadStatus.getStartTime());
        uploadStatus.setPercent((int) (100 * bytesRead / contentLength));
        session.setAttribute("upload_status", uploadStatus);
    }

}
