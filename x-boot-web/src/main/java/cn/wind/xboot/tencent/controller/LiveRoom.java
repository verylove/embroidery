package cn.wind.xboot.tencent.controller;

import cn.wind.db.ar.entity.ArUser;
import cn.wind.db.ar.service.IArUserService;
import cn.wind.xboot.service.app.CXArUserManage;
import cn.wind.xboot.tencent.logic.RoomMgr;
import cn.wind.xboot.tencent.pojo.Request.*;
import cn.wind.xboot.tencent.pojo.Response.*;
import cn.wind.xboot.tencent.pojo.Room;
import cn.wind.xboot.tencent.service.RoomService;
import cn.wind.xboot.tencent.service.UtilService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 直播-连麦 房间接口
 */
@RestController
@RequestMapping("/weapp/live_room")
public class LiveRoom {
    @Autowired
    RoomService roomService;

    @Autowired
    UtilService utilService;

    @RequestMapping("login")
    public LoginRsp login(String sdkAppID, String accountType, String userID, String userSig){
        return roomService.login(sdkAppID, accountType, userID, userSig);
    }

    @RequestMapping("logout")
    public BaseRsp logout(String userID, String token){
        return roomService.logout(userID, token);
    }

    @RequestMapping("get_push_url")
    public GetPushUrlRsp get_push_url(String userID, String token){
        return utilService.getPushUrl(userID, token);
    }

    @RequestMapping("get_room_list")
    public GetRoomListRsp get_room_list(String userID, String token, GetRoomListReq req){
        return roomService.getRoomList(userID, token, req, RoomMgr.LIVE_ROOM);
    }

    @RequestMapping("get_pushers")
    public Room get_pushers(String userID, String token, GetPushersReq req){
        return roomService.getPushers(userID, token, req, RoomMgr.LIVE_ROOM);
    }

    @RequestMapping("create_room")
    public CreateRoomRsp create_room(String userID, String token, CreateRoomReq req){
        return roomService.createRoom(userID, token, req, RoomMgr.LIVE_ROOM);
    }

    @RequestMapping("destroy_room")
    public BaseRsp destroy_room(String userID, String token,  DestroyRoomReq req) {
        return roomService.destroyRoom(userID, token, req, RoomMgr.LIVE_ROOM);
    }

    @RequestMapping("add_pusher")
    public BaseRsp add_pusher(String userID, String token,  AddPusherReq req) {
        return roomService.addPusher(userID, token, req, RoomMgr.LIVE_ROOM);
    }

    @RequestMapping("delete_pusher")
    public BaseRsp delete_pusher(String userID, String token,  DeletePusherReq req) {
        return roomService.deletePusher(userID, token, req, RoomMgr.LIVE_ROOM);
    }

    @RequestMapping("pusher_heartbeat")
    public BaseRsp pusher_heartbeat(String userID, String token,  PusherHeartbeatReq req) {
        return roomService.pusherHeartbeat(userID, token, req, RoomMgr.LIVE_ROOM);
    }

    @RequestMapping("merge_stream")
    public MergeStreamRsp merge_stream(String userID, String token,  Map map) {
        return utilService.mergeStream(userID, token, map);
    }

    @RequestMapping("get_custom_info")
    public GetCustomInfoRsp get_custom_info(String userID, String token,  GetCustomInfoReq req) {
        return roomService.getCustomInfo(userID, token, req, RoomMgr.LIVE_ROOM);
    }

    @RequestMapping("set_custom_field")
    public GetCustomInfoRsp set_custom_field(String userID, String token,  SetCustomInfoReq req) {
        return roomService.setCustomInfo(userID, token, req, RoomMgr.LIVE_ROOM);
    }

    @RequestMapping("add_audience")
    public BaseRsp add_audience(String userID, String token,  AddAudienceReq req) {
        return roomService.addAudience(userID, token, req, RoomMgr.LIVE_ROOM);
    }

    @RequestMapping("delete_audience")
    public BaseRsp delete_audience(String userID, String token,  DelAudienceReq req) {
        return roomService.delAudience(userID, token, req, RoomMgr.LIVE_ROOM);
    }

    @RequestMapping("get_audiences")
    public GetAudiencesRsp get_audiences(String userID, String token,  GetAudiencesReq req) {
        return roomService.getAudiences(userID, token, req, RoomMgr.LIVE_ROOM);
    }

    @ApiOperation("获取关注的人的直播")
    @RequestMapping("get_room_list_follows")
    public GetRoomListRsp getRoomListByFollows(String userID, String token, int cnt, int index){
        return roomService.getRoomListByFollows(userID,token,cnt,index, RoomMgr.LIVE_ROOM);
    }

    @ApiOperation("踢人/屏蔽")
    @RequestMapping("shielding")
    public BaseRsp shielding(String userID, String token,List<Long> userIDs){
        return roomService.shielding(userID, token, userIDs, RoomMgr.LIVE_ROOM);
    }

    @ApiOperation("我的屏蔽")
    @RequestMapping("get_user_list_shielding")
    public GetUserListRsp shieldUsers(String userID, String token, int cnt, int index){
        return roomService.shieldUsers(userID,token,cnt,index,RoomMgr.LIVE_ROOM);
    }

    @ApiOperation("取消屏蔽")
    @RequestMapping("cancelShielding")
    public BaseRsp cancelShielding(String userID, String token, Long shieldingId){
        return roomService.cancelShielding(userID,token,shieldingId,RoomMgr.LIVE_ROOM);
    }

    @ApiOperation("获取主播信息")
    @RequestMapping("getHostInfo")
    public GetUserDetailRsp getHostInfo(String userID, String token, String roomID){
        return roomService.getHostInfo(userID,token,roomID,RoomMgr.LIVE_ROOM);
    }

    @ApiOperation("获取礼物信息 分页")
    @RequestMapping("getAllGifts")
    public GetGiftListRsp getAllGifts(String userID, String token, int cnt, int index){
        return roomService.getAllGifts(userID,token,cnt,index);
    }

    @ApiOperation("pk其他动作 开始")
    @RequestMapping("pkActionsBefore")
    public BaseRsp pkActionsBefore(String userID, String token, String inviteUserID, int category){
        return roomService.pkActionsBefore(userID,token,inviteUserID,category);
    }

    @ApiOperation("打赏")
    @RequestMapping("reward")
    public GetRewardRsp reward(String userID, String token, String roomID, Long worth, int pkIng, int category){
        return roomService.reward(userID,token,roomID,worth,pkIng,category);
    }

    @ApiOperation("pk其他动作 结束")
    @RequestMapping("pkActionsAfter")
    public GetResultRsp pkActionsAfter(String inviteUserID,String enInviteUserID, int category){
        return roomService.pkActionsAfter(inviteUserID,enInviteUserID,category);
    }
}
