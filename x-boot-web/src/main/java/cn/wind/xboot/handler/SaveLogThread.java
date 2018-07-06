package cn.wind.xboot.handler;

import cn.wind.db.sys.entity.SysLog;
import cn.wind.db.sys.service.ISysLogService;

/**
 * <p>Title: SaveLogThread</p>
 * <p>Description: TODO</p>
 *
 * @author xukk
 * @version 1.0
 * @date 2018/6/29
 */
public class SaveLogThread implements Runnable{

    private SysLog sysLog;
    private ISysLogService sysLogService;

    public SaveLogThread(SysLog sysLog, ISysLogService sysLogService) {
        this.sysLog=sysLog;
        this.sysLogService=sysLogService;
    }

    @Override
    public void run() {
            sysLogService.insert(sysLog);
    }
}
