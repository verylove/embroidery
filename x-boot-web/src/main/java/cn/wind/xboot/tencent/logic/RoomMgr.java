package cn.wind.xboot.tencent.logic;

import cn.wind.db.ar.entity.ArUser;
import cn.wind.db.ar.entity.ArUserFollows;
import cn.wind.db.ar.entity.ArUserShielding;
import cn.wind.db.ar.service.IArUserFollowsService;
import cn.wind.db.ar.service.IArUserService;
import cn.wind.db.ar.service.IArUserShieldingService;
import cn.wind.db.bc.entity.BcPkTotal;
import cn.wind.db.bc.service.IBcPkTotalService;
import cn.wind.xboot.tencent.common.Config;
import cn.wind.xboot.tencent.pojo.Audience;
import cn.wind.xboot.tencent.pojo.Pusher;
import cn.wind.xboot.tencent.pojo.Response.GetStreamStatusRsp;
import cn.wind.xboot.tencent.pojo.Response.GetUserDetailRsp;
import cn.wind.xboot.tencent.pojo.Room;
import cn.wind.xboot.tencent.utils.Utils;
import com.google.common.collect.Maps;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class RoomMgr implements InitializingBean {
    public static final int MULTI_ROOM = 0;
    public static final int DOUBLE_ROOM = 1;
    public static final int LIVE_ROOM = 2;

    private ConcurrentHashMap<String, Room> liveRoomMap = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, Room> doubleRoomMap = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, Room> multiRoomMap = new ConcurrentHashMap<>();

    private HeartTimer heartTimer = new HeartTimer();
    private Timer timer = null;

    private static Logger log = LoggerFactory.getLogger(RoomMgr.class);

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    RedisTemplate redisTemplate;

    @Resource
    IMMgr imMgr;

    @Autowired
    private IArUserShieldingService shieldingService;
    @Autowired
    private IArUserService userService;
    @Autowired
    private IArUserFollowsService followsService;
    @Autowired
    private IBcPkTotalService bcPkTotalService;

    @Override
    public void afterPropertiesSet() throws Exception {
        // 开启心跳检查定时器
        if (timer  == null) {
            timer = new Timer();
            timer.schedule(heartTimer, 5 * 1000, 5 * 1000);
        }
    }

    /**
     * 获取房间列表
     */
    public ArrayList<Room> getList(int cnt, int index, int category, boolean withPushers, int type, String userId) {
        ConcurrentHashMap<String, Room> orgMap = getActuralMap(type);
        ArrayList<Room> resultList = new ArrayList<>();

        //获取所有房间(排除屏蔽的、在线人数倒序)
        List<Long> shieldingIds = shieldingService.findAllUserIdsByShieldId(Long.parseLong(userId));
        String keyZset = "";
        String keyHash = "";
        if(category == 1){//大咖秀
            keyZset = "bc:show:roomList";
            keyHash = "bc:show:proInfo";
        }else {
            keyZset = "bc:entertainment:roomList";
            keyHash = "bc:entertainment:proInfo";
        }
        Set<ZSetOperations.TypedTuple<Object>> tuples = redisTemplate.opsForZSet().reverseRangeWithScores(keyZset,0,-1);

        List<ArUser> users = userService.findAll();
        Map<Long,ArUser> userMap = users.stream().collect(Collectors.toMap(ArUser::getId, Function.identity()));

        int cursor = 0;
        int roomCnt = 0;
        for(ZSetOperations.TypedTuple<Object> typedTuple:tuples){
            if (roomCnt >= cnt)
                break;
            if (shieldingIds.contains(Long.valueOf(typedTuple.getValue().toString())))
                continue;
            Room value = orgMap.get(typedTuple.getValue().toString());
            if(value==null){
                continue;
            }
            log.info("getRoomList, type:" + type + " , roomID:" + value.getRoomID() + " ,isActived: " + value.isActived() + " ,pushers count: " + value.getPushersCnt());
            if (value.isActived() && value.getPushersCnt() != 0) {
                if (cursor >= index) {
                    value.setCityId(value.getCityId());
                    value.setOnlineNum(typedTuple.getScore().intValue());
                    value.setRoomPic(value.getRoomPic());
                    value.setUser(userMap.get(Long.parseLong(typedTuple.getValue().toString())));
                    resultList.add(value);
                    ++roomCnt;
                } else {
                    ++cursor;
                    continue;
                }

            }
        }

        return resultList;
    }

    /**
     * 获取房间
     */
    public Room getRoom(String roomID, int type) {
        ConcurrentHashMap<String, Room> orgMap = getActuralMap(type);
        if (orgMap.containsKey(roomID)) {
            return orgMap.get(roomID);
        } else {
            return null;
        }
    }

    /**
     * 创建房间
     */
    public void creatRoom(String roomID, String roomInfo, int type, String roomPic, Long cityId, int category) {
        ConcurrentHashMap<String, Room> orgMap = getActuralMap(type);
        Room room = new Room();
        room.setRoomID(roomID);
        room.setActived(false);
        room.setRoomInfo(roomInfo);
        room.setOnlineNum(0);
        room.setRoomPic(roomPic);
        room.setCityId(cityId);
        orgMap.put(roomID, room);
        log.info("creat_room, type: " + type + " , roomID: " + roomID + ", roomInfo: " + roomInfo);

        if(category==1){//大咖秀
            redisTemplate.opsForZSet().add("bc:show:roomList",roomID,0D);
            redisTemplate.opsForHash().put("bc:show:proInfo",roomID,room);
        }else {//娱乐
            redisTemplate.opsForZSet().add("bc:entertainment:roomList",roomID,0D);
            redisTemplate.opsForHash().put("bc:entertainment:proInfo",roomID,room);
        }

        //主播直播信息
        BcPkTotal pkTotal = bcPkTotalService.findOneByUserId(Long.parseLong(roomID));
        if(pkTotal == null){
            pkTotal = new BcPkTotal();
            pkTotal.setUserId(Long.parseLong(roomID));
            pkTotal.setRoomId(Long.parseLong(roomID));
            pkTotal.setCityId(cityId);
            bcPkTotalService.insert(pkTotal);
        }else {
            pkTotal.setCityId(cityId);
            bcPkTotalService.updateById(pkTotal);
        }
    }

    /**
     * 房间是否存在
     */
    public boolean isRoomExist(String roomID, int type) {
        ConcurrentHashMap<String, Room> orgMap = getActuralMap(type);
        return orgMap.containsKey(roomID);
    }

    /**
     * 是否是房间创建者
     */
    public boolean isRoomCreator(String roomID, String userID, int type) {
        ConcurrentHashMap<String, Room> orgMap = getActuralMap(type);
        Room room = orgMap.get(roomID);
        if (room != null && room.getRoomCreator().equals(userID))
            return true;
        return false;
    }

    /**
     * 删除房间
     */
    public void delRoom(String roomID, int type) {
        ConcurrentHashMap<String, Room> orgMap = getActuralMap(type);
        orgMap.remove(roomID);
        log.info("delRoom, type: " + type + " , roomID: " + roomID);

        //删除房间
        redisTemplate.opsForZSet().remove("bc:show:roomList",roomID);
        redisTemplate.opsForZSet().remove("bc:entertainment:roomList",roomID);

        redisTemplate.opsForHash().delete("bc:show:proInfo",roomID);
        redisTemplate.opsForHash().delete("bc:entertainment:proInfo",roomID);
    }

    private ConcurrentHashMap<String, Room> getActuralMap(int type) {
        ConcurrentHashMap<String, Room> actMap;
        switch (type) {
            case MULTI_ROOM:
                actMap = multiRoomMap;
                break;
            case DOUBLE_ROOM:
                actMap = doubleRoomMap;
                break;
            case LIVE_ROOM:
                actMap = liveRoomMap;
                break;
            default:
                actMap = new ConcurrentHashMap<>();
        }
        return actMap;
    }

    /**
     * 新增房间并进房
     */
    public void addRoom(String roomID, String roomInfo, String userID, String mixedPlayUrl, String userName, String userAvatar, String pushURL, String streamID, String acceleratePlayUrl, int type) {
        ConcurrentHashMap<String, Room> orgMap = getActuralMap(type);
        Room room = new Room();
        room.setRoomID(roomID);
        room.setRoomInfo(roomInfo);
        room.setRoomCreator(userID);
        room.setMixedPlayURL(mixedPlayUrl);
        room.setActived(true);
        Pusher pusher = new Pusher();
        pusher.setUserID(userID);
        pusher.setUserName(userName);
        pusher.setUserAvatar(userAvatar);
        pusher.setPushURL(pushURL);
        pusher.setStreamID(streamID);
        pusher.setAccelerateURL(acceleratePlayUrl);
        pusher.setUserID(userID);
        pusher.setTimestamp(System.currentTimeMillis() / 1000);
        room.addPusher(pusher);
        orgMap.put(roomID, room);
        log.info("addRoom, type: " + type + " , roomID: " + roomID + ", roomInfo: " + roomInfo + ", userID: " + userID + ", streamID: " + streamID);
    }

    public boolean isMember(String roomID, String userID, int type) {
        ConcurrentHashMap<String, Room> orgMap = getActuralMap(type);
        Room room = orgMap.get(roomID);
        if (room != null) {
            Pusher pusher = room.getPusher(userID);
            if (pusher != null)
                return true;
        }
        return false;
    }

    public void updateMember(String roomID, String userID, String mixedPlayUrl, String userName, String userAvatar, String pushURL, String streamID, String acceleratePlayUrl, int type) {
        addMember(roomID, userID, mixedPlayUrl, userName, userAvatar, pushURL, streamID, acceleratePlayUrl, type);
        updateMemberTS(roomID, userID, type);
    }

    /**
     * 心跳更新
     */
    public void updateMemberTS(String roomID, String userID, int type) {
        ConcurrentHashMap<String, Room> orgMap = getActuralMap(type);
        Room room = orgMap.get(roomID);
        if (room != null) {
            Pusher pusher = room.getPusher(userID);
            if (pusher != null)
                pusher.setTimestamp(System.currentTimeMillis() / 1000);
        }
    }

    /**
     * 获取房间推流者人数
     */
    public int getMemberCnt(String roomID, int type) {
        int count = 0;
        ConcurrentHashMap<String, Room> orgMap = getActuralMap(type);
        Room room = orgMap.get(roomID);
        if (room != null) {
            count = room.getPushersCnt();
        }
        return count;
    }

    /**
     * 新增推流者 - 进房
     */
    public void addMember(String roomID, String userID, String mixedPlayUrl, String userName, String userAvatar, String pushURL, String streamID, String acceleratePlayUrl, int type) {
        ConcurrentHashMap<String, Room> orgMap = getActuralMap(type);
        Room room = orgMap.get(roomID);
        if (room != null) {
            if (room.isActived() == false) {
                room.setActived(true);
                room.setRoomCreator(userID);
                room.setMixedPlayURL(mixedPlayUrl);
            }

            Pusher pusher = room.getPusher(userID);
            if (pusher != null) {
                pusher.setUserName(userName);
                pusher.setUserAvatar(userAvatar);
                pusher.setPushURL(pushURL);
                pusher.setStreamID(streamID);
                pusher.setAccelerateURL(acceleratePlayUrl);
                pusher.setTimestamp(System.currentTimeMillis() / 1000);
            } else {
                pusher = new Pusher();
                pusher.setUserID(userID);
                pusher.setUserName(userName);
                pusher.setUserAvatar(userAvatar);
                pusher.setPushURL(pushURL);
                pusher.setStreamID(streamID);
                pusher.setAccelerateURL(acceleratePlayUrl);
                pusher.setTimestamp(System.currentTimeMillis() / 1000);
            }
            room.addPusher(pusher);
            log.info("addMember, type: " + type + " , roomID: " + roomID + ", userID: " + userID + ", streamID: " + streamID);
        }
    }

    /**
     * 删除推流者
     */
    public void delPusher(String roomID, String userID, int type) {
        ConcurrentHashMap<String, Room> orgMap = getActuralMap(type);
        Room room = orgMap.get(roomID);
        if (room != null) {
            if (type == LIVE_ROOM && room.getRoomCreator().equals(userID)) {
                orgMap.remove(roomID);
                imMgr.destroyGroup(roomID);
            } else {
                room.delPusher(userID);
                imMgr.notifyPushersChange(roomID);
                if (room.getPushersCnt() == 0) {
                    orgMap.remove(roomID);
                }
            }
        }
    }

    public String getCustomInfo(String roomID, int type) {
        Room room = getRoom(roomID, type);
        if (room != null) {
            return room.getCustomInfo();
        }
        return "";
    }

    public String setCustomInfo(String roomID, String fieldName, String operation, int type) {
        Room room = getRoom(roomID, type);
        if (room != null) {
            room.setCustomInfo(fieldName, operation);
            return room.getCustomInfo();
        }
        return "";
    }

    public ArrayList<Room> getListByFollows(int cnt, int index, int type, String userId) {
        ConcurrentHashMap<String, Room> orgMap = getActuralMap(type);
        ArrayList<Room> resultList = new ArrayList<>();

        //获取所有房间(关注的,排除被屏蔽的)
        List<Long> shieldingIds = shieldingService.findAllUserIdsByShieldId(Long.parseLong(userId));
        Map<String,Object> map = Maps.newHashMap();
        map.put("shieldingIds",shieldingIds);
        map.put("userId",Long.parseLong(userId));
        List<Long> followIds = followsService.findAllFollowIdsByUserIdAndShieldingId(map);
        if(followIds == null || followIds.size()<1){
            return resultList;
        }
        List<ArUser> users = userService.findAllByIdIn(followIds);
        Map<Long,ArUser> userMap = users.stream().collect(Collectors.toMap(ArUser::getId, Function.identity()));

        int cursor = 0;
        int roomCnt = 0;
        for(Long follow:followIds){
            if (roomCnt >= cnt)
                break;
            if(orgMap.containsKey(follow.toString())){
                Room value = orgMap.get(follow.toString());
                log.info("getRoomListByFollow, type:" + type + " , roomID:" + value.getRoomID() + " ,isActived: " + value.isActived() + " ,pushers count: " + value.getPushersCnt());
                if (value != null && value.isActived() && value.getPushersCnt() != 0) {
                    if (cursor >= index) {
                        value.setCityId(value.getCityId());
                        Double online = 0D;
                        Long indexShow = redisTemplate.opsForZSet().rank("bc:show:roomList",follow.toString());
                        if(indexShow != null){
                            online = redisTemplate.opsForZSet().score("bc:show:roomList",follow.toString());
                        }else {
                            online = redisTemplate.opsForZSet().score("bc:entertainment:roomList",follow.toString());
                        }
                        value.setOnlineNum(online.intValue());
                        value.setRoomPic(value.getRoomPic());
                        value.setUser(userMap.get(follow));
                        resultList.add(value);
                        ++roomCnt;
                    } else {
                        ++cursor;
                        continue;
                    }
                }else {
                    if (cursor >= index) {
                        value.setOnlineNum(0);
                        value.setUser(userMap.get(follow));
                        resultList.add(value);
                        ++roomCnt;
                    } else {
                        ++cursor;
                        continue;
                    }
                }
            }else {
                if (cursor >= index) {
                    Room value = new Room();
                    value.setOnlineNum(0);
                    value.setUser(userMap.get(follow));
                    resultList.add(value);
                    ++roomCnt;
                } else {
                    ++cursor;
                    continue;
                }
            }
        }

        return resultList;
    }

    public void shielding(List<Long> userIDs, String userID, int type) {
        for(Long shieldId : userIDs){
            ArUserShielding shielding = shieldingService.findOneByUserIdAndShieldId(Long.parseLong(userID),shieldId);
            if(shielding==null){
                shielding = new ArUserShielding();
                shielding.setUserId(Long.parseLong(userID));
                shielding.setShieldingId(shieldId);
                shielding.setStatus(1);
                shieldingService.insert(shielding);
            }else if(shielding.getStatus()==0){
                shielding.setStatus(1);
                shieldingService.updateById(shielding);
            }
        }
    }

    public ArrayList<ArUser> shieldUsers(int cnt, int index, String userID, int type) {
        ArrayList<ArUser> resultList = new ArrayList<>();

        List<Long> shieldingIds = shieldingService.findAllShieldIdsByUserId(Long.parseLong(userID));
        if(shieldingIds==null ||shieldingIds.size()<1){
            return resultList;
        }
        List<ArUser> users = userService.findAllByIdIn(shieldingIds);

        int cursor = 0;
        int roomCnt = 0;
        for(ArUser user:users){
            if (roomCnt >= cnt)
                break;
            if (cursor >= index) {
                resultList.add(user);
                ++roomCnt;
            } else {
                ++cursor;
                continue;
            }
        }

        return resultList;
    }

    public void cancelShielding(Long shieldingId, String userID, int type) {
        ArUserShielding shielding = shieldingService.findOneByUserIdAndShieldId(Long.parseLong(userID),shieldingId);
        if(shielding!=null){
            shielding.setStatus(0);
            shieldingService.updateById(shielding);
        }
    }

    public GetUserDetailRsp getHostInfo(String userID, String roomID, int type, GetUserDetailRsp rsp) {
        ArUser user = userService.selectById(Long.parseLong(roomID));
        rsp.setId(Long.parseLong(roomID));
        rsp.setAccount(user.getAccount());
        rsp.setIcon(user.getIcon());
        rsp.setPerLevel(user.getPerLevel());
        rsp.setLiveEarnings(user.getLiveEarnings());

        ArUserFollows follows = followsService.findOneByUserIdAndFollowId(Long.parseLong(userID),Long.parseLong(roomID));
        if(follows!=null && follows.getStatus()==1){
            rsp.setIsCollection(1);
        }else {
            rsp.setIsCollection(0);
        }

        BcPkTotal pkTotal = bcPkTotalService.findOneByUserId(Long.parseLong(roomID));
        rsp.setCityId(pkTotal.getCityId());

        Map<String,Object> map = new HashMap<>();
        map.put("userId",Long.parseLong(roomID));
        map.put("cityId",pkTotal.getCityId());
        Long rank = bcPkTotalService.findRankInCity(map);

        rsp.setCityRankNum(rank);
        return rsp;
    }

    public class HeartTimer extends TimerTask {
        @Override
        public void run() {
            onTimer();
        }
    }

    private void onTimer() {
        onTimerCheckHeartBeat(multiRoomMap, Config.MultiRoom.heartBeatTimeout, MULTI_ROOM);
        onTimerCheckHeartBeat(doubleRoomMap, Config.DoubleRoom.heartBeatTimeout, DOUBLE_ROOM);
        onTimerCheckHeartBeat(liveRoomMap, Config.LiveRoom.heartBeatTimeout, LIVE_ROOM);
    }

    /**
     * 心跳超时检查
     * timeout 过期时间，单位秒
     */
    private void onTimerCheckHeartBeat(ConcurrentHashMap<String, Room> map, int timeout, int type) {
        // 遍历房间每个成员，检查pusher的时间戳是否超过timeout
        long currentTS = System.currentTimeMillis()/1000;
        for (Room room : map.values()) {
            if (room.isActived()) {
                for (Pusher pusher : room.getPushersMap().values()) {
                    // 心跳超时
                    if (pusher.getTimestamp() + timeout < currentTS) {
                        if (getStreamStatus(pusher.getStreamID()) == 1) {
                            // 流断了，删除用户
                            delPusher(room.getRoomID(), pusher.getUserID(), type);
                        } else {
                            // 补一个心跳
                            updateMemberTS(room.getRoomID(), pusher.getUserID(), type);
                        }
                    }
                }
            }
        }
    }

    /**
     * 辅助功能函数 - 心跳超时检查流状态
     * 0: 流状态是推流中
     * 1: 流状态是断流
     * 2: 请求错误
     */
    private int getStreamStatus (String streamID) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 5分钟
        long txTime = System.currentTimeMillis() / 1000 + 300;
        String txSecret = Utils.getMD5(Config.Live.APIKEY + txTime);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://fcgi.video.qcloud.com/common_access?appid=" + Config.Live.APP_ID
                + "&interface=Live_Channel_GetStatus&Param.s.channel_id=" + streamID
                + "&t=" + txTime
                + "&sign=" + txSecret);

        HttpEntity entity = new HttpEntity(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                builder.build().encode().toUri(),
                HttpMethod.GET,
                entity,
                String.class);

        log.info("getStreamStatus, streamID:" + streamID + ", response :" + response.getBody());

        // 错误
        if (response.getStatusCode().value() != HttpStatus.OK.value()) {
            log.warn("getStreamStatus出错, streamID: " + streamID + ", HttpCode: " + response.getStatusCode().value());
            return 2;
        }

        ObjectMapper mapper = new ObjectMapper();
        GetStreamStatusRsp rsp = null;

        try {
            rsp = mapper.readValue(response.getBody(), GetStreamStatusRsp.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 推流中
        if (rsp != null && rsp.getRet() == 0 && rsp.getOutput().get(0) != null && rsp.getOutput().get(0).getStatus() == 1) {
            return 0;
        }

        return 1;
    }

    public void addAudience(String roomID, String userID, String userInfo, int type) {
        // 只有直播房间有观众
        if (type != LIVE_ROOM)
            return;

        Room room = liveRoomMap.get(roomID);
        if (room != null) {
            Audience audience = new Audience();
            audience.setUserID(userID);
            audience.setUserInfo(userInfo);
            room.addAudience(audience);
            log.info("addAudience, roomID:" + roomID + ", userID :" + userID + ", userInfo:" + userInfo);

            //添加观众人数
            Long indexShow = redisTemplate.opsForZSet().rank("bc:show:roomList",roomID);
            if(indexShow != null){
                redisTemplate.opsForZSet().incrementScore("bc:show:roomList",roomID,1);
            }
            Long indexEntertainment = redisTemplate.opsForZSet().rank("bc:entertainment:roomList",roomID);
            if(indexEntertainment != null){
                redisTemplate.opsForZSet().incrementScore("bc:entertainment:roomList",roomID,1);
            }
        }
    }

    public void delAudience(String roomID, String userID, int type) {
        // 只有直播房间有观众
        if (type != LIVE_ROOM)
            return;

        Room room = liveRoomMap.get(roomID);
        if (room != null) {
            room.delAudience(userID);
            log.info("delAudience, roomID:" + roomID + ", userID :" + userID);
        }

        //删减观众人数
        Long indexShow = redisTemplate.opsForZSet().rank("bc:show:roomList",roomID);
        if(indexShow != null){
            redisTemplate.opsForZSet().incrementScore("bc:show:roomList",roomID,-1);
        }
        Long indexEntertainment = redisTemplate.opsForZSet().rank("bc:entertainment:roomList",roomID);
        if(indexEntertainment != null){
            redisTemplate.opsForZSet().incrementScore("bc:entertainment:roomList",roomID,-1);
        }
    }

    /**
     * 获取房间观众人数
     */
    public int getAudienceCnt(String roomID, int type) {
        int count = 0;
        // 只有直播房间有观众
        if (type == LIVE_ROOM) {
            Room room = liveRoomMap.get(roomID);
            if (room != null) {
                count = room.getAudiencesCnt();
            }
        }
        log.info("getAudienceCnt, roomID:" + roomID + ", count :" + count);
        return count;
    }

    public ArrayList<Audience> getAudiences(String roomID, int type) {
        // 只有直播房间有观众
        if (type == LIVE_ROOM) {
            Room room = liveRoomMap.get(roomID);
            if (room != null)
                return room.getAudiences();
        }
        return new ArrayList<>();
    }
}
