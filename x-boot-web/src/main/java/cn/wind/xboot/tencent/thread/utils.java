package cn.wind.xboot.tencent.thread;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/8/31 11:55
 * @Description:
 */
public class utils {

    public static String getTattooContent(Integer category){
        String tattooContent = "";
        switch (category){
            case 1:
                tattooContent = "特价纹身";
                break;
            case 2:
                tattooContent = "图库找图";
                break;
            case 3:
                tattooContent = "贴吧话题";
                break;
            case 4:
                tattooContent = "纹纹达人";
                break;
            case 5:
                tattooContent = "动态";
                break;
            case 6:
                tattooContent = "作品";
                break;
            case 7:
                tattooContent = "特价纹身评论";
                break;
            case 8:
                tattooContent = "图库找图评论";
                break;
            case 9:
                tattooContent = "贴吧话题评论";
                break;
            case 10:
                tattooContent = "纹纹达人评论";
                break;
            case 11:
                tattooContent = "动态评论";
                break;
            case 12:
                tattooContent = "作品评论";
                break;
        }

        return tattooContent;
    }
}
