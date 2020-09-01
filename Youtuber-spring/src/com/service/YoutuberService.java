package com.service;

import com.JDBC.JDBCUtils;
import com.bean.Pojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class YoutuberService {
    @Autowired
    private JDBCUtils jdbcUtils ;
    /**
     * 查詢關鍵字
     * @param sql
     * @return list
     */
    public  List<Pojo> queryForItem(String sql){
        List<Pojo> youtuberList = jdbcUtils.getForList(Pojo.class, sql );
        return youtuberList;
    }


}
