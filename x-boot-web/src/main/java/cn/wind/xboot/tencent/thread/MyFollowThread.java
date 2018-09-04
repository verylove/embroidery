package cn.wind.xboot.tencent.thread;

import cn.wind.db.ar.entity.ArUser;
import cn.wind.db.ar.entity.ArUserMessage;
import cn.wind.db.ar.service.impl.ArUserMessageServiceImpl;
import cn.wind.db.ar.service.impl.ArUserServiceImpl;
import cn.wind.xboot.service.PushManage;
import cn.wind.xboot.tencent.utils.SpringContextUtil;
import cn.wind.xboot.vo.PushVo;
import com.google.common.collect.Lists;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/8/31 14:21
 * @Description: 我的关注 消息推送
 */
public class MyFollowThread {
    private ArUserMessageServiceImpl messageService;

    private ArUserServiceImpl userService;

    private boolean completed = false;

    /**
     * @param fromUserId 执行操作的人
     * @param toUserId 收到消息推送的人
     */
    public synchronized void myFollowThread(Long fromUserId, Long toUserId){
        System.out.println("线程任务");
        if(this.completed){
            return;
        }
        try{
            messageService = (ArUserMessageServiceImpl) SpringContextUtil.getBean("arUserMessageServiceImpl");
            userService = (ArUserServiceImpl) SpringContextUtil.getBean("arUserServiceImpl");

            ArUser user = userService.selectById(fromUserId);
            ArUser user2 = userService.selectById(toUserId);

            PushVo vo = new PushVo();
            vo.setALIAS_LIST(Lists.newArrayList(user2.getPhone()));
            vo.setTITLE("系统关注消息");
            vo.setMSG_CONTENT("系统关注消息");
            vo.setALERT(user.getAccount()+"关注了你！");
            vo.setType(1);
//            Map map= Maps.newHashMap();
//            map.put("type","1");
//            vo.setExtra(map);
            PushManage.sendPushWithCallback(vo);

            //2.保存消息
            ArUserMessage userMessage = new ArUserMessage();
            userMessage.setFromUserId(fromUserId);
            userMessage.setToUserId(toUserId);
            userMessage.setType(4);
            userMessage.setTitle("系统关注消息");
            userMessage.setContent(user.getAccount()+"关注了你！");
            messageService.insert(userMessage);
        }catch (Throwable t){

        }
        this.completed = true;
    }

    public boolean isCompleted(){
        return this.completed;
    }
}
