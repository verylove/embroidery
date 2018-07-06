package cn.wind.common.res;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author xukk
 * @date 2018/5/4
 */
@Data
@Builder
@AllArgsConstructor
public class ApiRes<T> implements Serializable {
    /**
     * 900000成功，非900000失败
     **/
    private int status;
    /**
     * 详情
     */
    private String message;

    /**
     * 错误类型
     */
    private String error;

    @JsonProperty("error_description")
    @SerializedName("error_description")
    private String errorDescription;

    private String path;

    /**
     * 内容
     */
    protected T data;
    /**
     * 时间戳
     */
    private long timestamp;

    public ApiRes() {
        this.timestamp = System.currentTimeMillis();
    }

    public ApiRes failure() {
        this.status = ApiStatus.FAIL.code();
        this.message = ApiStatus.FAIL.desc();
        this.error = ApiStatus.FAIL.name();
        this.errorDescription = this.message;
        this.timestamp = System.currentTimeMillis();
        return this;
    }

    public ApiRes failure(ApiStatus ApiStatus) {
        this.status = ApiStatus.code();
        this.message = ApiStatus.desc();
        this.error = ApiStatus.name();
        this.errorDescription = ApiStatus.desc();
        this.timestamp = System.currentTimeMillis();
        return this;
    }

    public ApiRes failure(ApiStatus ApiStatus, String message) {
        if(message==null){
            message="";
        }
        this.status = ApiStatus.code();
        this.message = message;
        this.error = ApiStatus.name();
        this.errorDescription = message;
        this.timestamp = System.currentTimeMillis();
        return this;
    }

    public ApiRes failure(Integer status, String message) {
        if(message==null){
            message="";
        }
        this.status = status;
        this.message = message;
        this.error = ApiStatus.FAIL.name();
        this.errorDescription = this.message;
        this.timestamp = System.currentTimeMillis();
        return this;
    }

    public ApiRes failure(String message) {
        if(message==null){
            message="";
        }
        this.status = ApiStatus.FAIL.code();
        this.message = message;
        this.error = ApiStatus.FAIL.name();
        this.errorDescription = this.message;
        this.timestamp = System.currentTimeMillis();
        return this;
    }

    public ApiRes failure(String error, String message) {
        if(message==null){
            message="";
        }
        if(error==null){
            error="";
        }
        this.status = ApiStatus.FAIL.code();
        this.message = message;
        this.error = error;
        this.errorDescription = this.message;
        this.timestamp = System.currentTimeMillis();
        return this;
    }

    public ApiRes path(String path) {
        this.path = path;
        return this;
    }

    public ApiRes failure(Integer status, String error, String message) {
        if(message==null){
            message="";
        }
        if(error==null){
            error="";
        }
        this.status = status;
        this.message = message;
        this.error = error;
        this.errorDescription = this.message;
        this.timestamp = System.currentTimeMillis();
        return this;
    }

    public ApiRes success() {
        this.status = ApiStatus.SUCCESS.code();
        this.message = ApiStatus.SUCCESS.desc();
        this.error = "";
        this.errorDescription = ApiStatus.SUCCESS.desc();
        this.timestamp = System.currentTimeMillis();
        return this;
    }

    public ApiRes success(String message) {
        this.status = ApiStatus.SUCCESS.code();
        this.message = message;
        this.error = "";
        this.errorDescription = "";
        this.timestamp = System.currentTimeMillis();
        return this;
    }

    public Boolean valid() {
        if (status == ApiStatus.SUCCESS.code()) {
            return true;
        } else {
            return false;
        }
    }

    public static ApiRes Custom() {
        ApiRes apiRes  = new ApiRes();
        return apiRes ;
    }

    public static ApiRes Str() {
        ApiRes<String> apiRes = new ApiRes<>();
        if (apiRes .getData() == null) {
            apiRes .setData("");
        }
        return apiRes ;
    }

    public static ApiRes List() {
        ApiRes<List> apiRes  = new ApiRes<List>();
        if (apiRes.getData() == null) {
            apiRes.setData(Lists.newArrayList());
        }
        return apiRes;
    }

    public static ApiRes Map() {
        ApiRes<Map> apiRes = new ApiRes<Map>();
        if (apiRes.getData() == null) {
            apiRes.setData(Maps.newHashMap());
        }
        return apiRes;
    }

    public ApiRes add(Object value) {
        if (this.getData() == null) {
            List list = Lists.newArrayList();
            list.add(value);
            this.data = (T) list;
        } else {
            List list = (List) this.getData();
            list.add(value);
        }
        return this;
    }

    public ApiRes put(String key, Object value) {
        if (this.getData() == null) {
            Map map = Maps.newHashMap();
            map.put(key, value);
            this.data = (T) map;
        } else {
            Map map = (Map) this.getData();
            map.put(key, value);
        }
        return this;
    }
    public static ApiRes Custom(boolean flag){
        ApiRes apiRes = new ApiRes();
        if(flag){
            apiRes.success();
        }else {
            apiRes.failure();
        }
        return apiRes;
    }
    public static ApiRes Custom(boolean flag,String succuess,String failure){
        ApiRes apiRes = new ApiRes();
        if(flag){
            apiRes.success(succuess);
        }else {
            apiRes.failure(failure);
        }
        return apiRes;
    }
    public ApiRes putAll(Map map) {
        if (this.getData() == null) {
            this.data = (T) map;
        } else {
            Map map1 = (Map) this.getData();
            map1.putAll(map);
        }
        return this;
    }

    @SuppressWarnings("unchecked")
    public ApiRes addData(T data) {
        this.data = data;
        return this;
    }
}