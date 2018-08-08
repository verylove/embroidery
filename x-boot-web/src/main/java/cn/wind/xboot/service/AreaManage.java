package cn.wind.xboot.service;

import cn.wind.db.sr.entity.SrArea;
import cn.wind.db.sr.service.ISrAreaService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URL;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/8/8 17:29
 * @Description:
 */
@Service
public class AreaManage {

    @Autowired
    private ISrAreaService areaService;

    public SrArea getNowWhere(Double longitude, Double latitude) {
        String add = getAdd(longitude.toString(), latitude.toString());
        JSONObject jsonObject = JSONObject.fromObject(add);
        JSONArray jsonArray = JSONArray.fromObject(jsonObject.getString("addrList"));
        JSONObject j_2 = JSONObject.fromObject(jsonArray.get(0));
        String allAdd = j_2.getString("admName");
        String arr[] = allAdd.split(",");
        System.out.println("省:"+arr[0]+"\n市:"+arr[1]+"\n区:"+arr[2]);
        SrArea area = areaService.findByName(arr[1]+"%");
        return area;
    }

    public static String getAdd(String log, String lat ){
        //lat 小  log  大
        //参数解释: 纬度,经度 type 001 (100代表道路，010代表POI，001代表门址，111可以同时显示前三项)
        String urlString = "http://gc.ditu.aliyun.com/regeocoding?l="+lat+","+log+"&type=010";
        String res = "";
        try {
            URL url = new URL(urlString);
            java.net.HttpURLConnection conn = (java.net.HttpURLConnection)url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            java.io.BufferedReader in = new java.io.BufferedReader(new java.io.InputStreamReader(conn.getInputStream(),"UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                res += line+"\n";
            }
            in.close();
        } catch (Exception e) {
            System.out.println("error in wapaction,and e is " + e.getMessage());
        }
        System.out.println(res);
        return res;
    }
}
