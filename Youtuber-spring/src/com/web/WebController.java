package com.web;


import com.bean.Pojo;
import com.service.YoutuberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
@Controller
public class WebController  {
    @Autowired
    private YoutuberService service ;

    @RequestMapping("/webController")
//    public String webController(@RequestParam (value = "typeId" ) String typeId , Model model)  {
    public String webController(String typeId , Model model)  {
        String youtubername = "";
        switch(typeId){//獲取youtuber名子
            case "jaga":
                youtubername += "蔡阿嘎";
                break;
            case "jpeo":
                youtubername += "這群人";
                break;
            case "jgod":
                youtubername += "阿神";
                break;
            case "jadi":
                youtubername += "阿滴英文";
                break;
            case "jrock":
                youtubername += "滾石唱片";
                break;

        }
        model.addAttribute("youtubername",youtubername);

            List<Pojo> list = type(typeId);//常見詞彙及出現次數
        model.addAttribute("list", list);
        List<Pojo> leaderboard = leaderboard(typeId);//排行榜內容
        model.addAttribute("leaderboard",leaderboard);
        return  "view";
    }

    /**
     *  獲取table名,返回常見詞彙及出現次數
     * @param type :table名
     * @return List<Pojo>
     */
    private List<Pojo> type(String type ){
        String sql = "select item , sum from "+ type + " where sum > 20 and LENGTH( item ) > 3 limit 100  ";
        List<Pojo> list = service.queryForItem(sql);
        return list;
    }
    /**
     *  獲取table名,返回排行榜內容
     * @param type :table名
     * @return List<Pojo>
     */
    private  List<Pojo> leaderboard (String type){
        String sql = " SELECT item , sum FROM "+ type + " where length(item)>3 order by sum desc limit 10";
        List<Pojo> list = service.queryForItem(sql);
        return list;
    }


}
