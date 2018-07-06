package cn.wind.common.support.fileupload.progress;

import lombok.Data;

/**
 * @author xukk
 */
@Data
public class UploadStatus {
        /**
         * 已读数据
         */
        private long bytesRead;
        /**
         * 文件总数据
         */
        private long contentLength;
        /**
         * 第几个文件
         */
        private long items;
        /**
         * 开始时间
         */
        private long startTime = System.currentTimeMillis();
        /**
         * 已用时间
         */
        private long useTime = System.currentTimeMillis();
        /**
         * 完成百分比
         */
        private int percent;
}
