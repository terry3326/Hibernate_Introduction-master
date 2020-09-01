package com.Gson;

import com.JDBC.JDBCUtils;
import com.alibaba.druid.pool.DruidDataSource;
import com.bean.Youtuber;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.huaban.analysis.jieba.JiebaSegmenter;
import org.junit.Test;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class GsonTest {
    private DruidDataSource dataSource;
    private JDBCUtils jdbcUtils = new JDBCUtils();
    private final Gson gson = new Gson();
    private final JiebaSegmenter jieba = new JiebaSegmenter();
    private final StringBuilder god = new StringBuilder();
    private final StringBuilder adi = new StringBuilder();
    private final StringBuilder peo = new StringBuilder();
    private final StringBuilder rock = new StringBuilder();
    private final StringBuilder aga = new StringBuilder();

    @Test
    public void select()  {
        String sqlGod = "select comment from 阿神 ";
        String sqlAdi = "select comment from 阿滴英文 ";
        String sqlPeople = "select comment from 這群人 ";
        String sqlRock = "select comment from 滾石唱片 ";
        String sqlAga = "select comment from 蔡阿嘎 ";
        List<String> cg = getAllComment(sqlGod);
        List<String> cdi = getAllComment(sqlAdi);
        List<String> cp = getAllComment(sqlPeople);
        List<String> cr = getAllComment(sqlRock);
        List<String> cga = getAllComment(sqlAga);

        for (String s : cg) {
            String filter = StringFilter(s);
            List<String> strings = jieba.sentenceProcess(filter);
            for (String string : strings) {
                god.append(string).append(" ");
            }
        }
        for (String s : cdi) {
            String filter = StringFilter(s);
            List<String> strings = jieba.sentenceProcess(filter);
            for (String string : strings) {
                adi.append(string).append(" ");
            }
        }
        for (String s : cp) {
            String filter = StringFilter(s);
            List<String> strings = jieba.sentenceProcess(filter);
            for (String string : strings) {
                peo.append(string).append(" ");
            }
        }
        for (String s : cr) {
            String filter = StringFilter(s);
            List<String> strings = jieba.sentenceProcess(filter);
            for (String string : strings) {
                rock.append(string).append(" ");
            }
        }
        for (String s : cga) {
            String filter = StringFilter(s);
            List<String> strings = jieba.sentenceProcess(filter);
            for (String string : strings) {
                aga.append(string).append(" ");
            }
        }
        String sgod = new String(god);
        writeFile("sgod.txt", sgod);
//        String saga = new String(aga);
//        writeFile("saga.txt", saga);
//        String speo = new String(peo);
//        writeFile("speo.txt", speo);
        String srock = new String(rock);
        writeFile("srock.txt", srock);
//        String sadi = new String(adi);
//        writeFile("sadi.txt", sadi);
//
//        String sql = "SET NAMES utf8mb4;INSERT INTO test.jieba (god,adi,people,rock,aga) VALUES (?,?,?,?,?)";
//        JDBCUtils.update(sql , rgod,radi,rpeo,rrock,radi);
////        updateIO(sql, igod,iadi,ipeo,irock,iaga);


    }

    private void writeFile(String filename, String file) {
        BufferedWriter bw = null;
        try {
            FileWriter writer = new FileWriter(new File(filename));
            bw = new BufferedWriter(writer);
            bw.write(file);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }

    private String StringFilter(String str) {
//        String pattern="[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]";
//        String reg = "[\\u4e00-\\u9fa5 ]";
        String regEx = "[` !@#$%^&*()+=|《{}':;',//[//].￣<o╥﹏╥o>/?！@#￥%……&*~～｡∀｡》（）・゜・ノД・゜・∠ ͡° ͜ʖ ͡° ᐛ 」∠＿——+|{}【】σ°∀°σ‘⊙▽⊙；≧∇≦ヾ〃∇ﾉヽ´∀｀ノ：ω”“’。，、ヽ●´∀●ﾉ？ヾ´∀｀ﾉ[\\s][0-9][\\uD83E\uDD70\\uD83E\uDD2A\\uD83E\uDD23\\uD83E\uDD22\\uD83E\uDD2E\\uD83E\uDDD0\\uD83E\uDD11\\uD83E\uDD2A\\uD83E\uDD23\\uD83E\uDD2A\\uD83E\uDD74\\ud83c\\udc00-\\ud83c\\udfff]|[\\ud83d\\udc00-\\ud83d\\udfff]|[\\u2600-\\u27ff]]";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(str);
        String trim = matcher.replaceAll("").trim();
        return trim;
    }

    private void updateIO(String sql, Object... args) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            // 1.获取数据库的连接
            conn = dataSource.getConnection();
            // 2.预编译sql语句，返回PreparedStatement的实例
            ps = conn.prepareStatement(sql);
            // 3.填充占位符
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);// 小心参数声明错误！！
            }
            // 4.执行
            int executeUpdate = ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 5.资源的关闭
            jdbcUtils.closeResource(conn, ps);
        }
    }

    @Test
    public void insertGod() throws Exception {

        for (int i = 1; i < 21; i++) {
            FileReader reader = new FileReader("C:\\Users\\a8001\\Downloads\\0523\\阿神\\" + i + ".json");
            List<Youtuber> userList = gson.fromJson(reader, new TypeToken<List<Youtuber>>() {
            }.getType());
            for (Youtuber y : userList) {
                String sql = "SET NAMES utf8mb4;INSERT INTO test.阿神 (id,title,name,comment,time) VALUES (?,?,?,?,?)";
                jdbcUtils.update(sql, y.getId(), y.getTitle(), y.getName(), y.getComment(), y.getTime());
            }
        }


//
        /*
            方法一
         */
//        List<Youtuber> userList = gson.fromJson(reader, new TypeToken<List<Youtuber>>(){}.getType());



        /*
            方法二
         */
//        JsonParser parser = new JsonParser();
//        JsonArray array = parser.parse(reader).getAsJsonArray();
//
//        for (JsonElement j : array) {
//            Youtuber youtuber = gson.fromJson(j, Youtuber.class);
//            System.out.println(youtuber);
//        }

//        Youtuber youtuber = gson.fromJson(s, Youtuber.class);
//        System.out.println(youtuber);
//        List<Youtuber> list = gson.fromJson(s, new TypeToken<List<Youtuber>>(){}.getType());
//        System.out.println(list);
    }

    /**
     * 讀取jason檔,寫入資料庫
     * @throws Exception
     */
    @Test
    public void insertADI() throws Exception {
        for (int i = 1; i < 21; i++) {
            FileReader reader = new FileReader("C:\\Users\\a8001\\Downloads\\0523\\阿滴英文\\" + i + ".json");
            List<Youtuber> userList = gson.fromJson(reader, new TypeToken<List<Youtuber>>() {
            }.getType());
            for (Youtuber y : userList) {
                String sql = "SET NAMES utf8mb4;INSERT INTO test.阿滴英文 (id,title,name,comment,time) VALUES (?,?,?,?,?)";
                jdbcUtils.update(sql, y.getId(), y.getTitle(), y.getName(), y.getComment(), y.getTime());
            }
        }
    }

    /**
     * 讀取jason檔,寫入資料庫
     * @throws Exception
     */
    @Test
    public void insertPeople() throws Exception {
        for (int i = 1; i < 21; i++) {
            FileReader reader = new FileReader("C:\\Users\\a8001\\Downloads\\0523\\這群人\\" + i + ".json");
            List<Youtuber> userList = gson.fromJson(reader, new TypeToken<List<Youtuber>>() {
            }.getType());
            for (Youtuber y : userList) {
                String sql = "SET NAMES utf8mb4;INSERT INTO test.這群人 (id,title,name,comment,time) VALUES (?,?,?,?,?)";
                jdbcUtils.update(sql, y.getId(), y.getTitle(), y.getName(), y.getComment(), y.getTime());
            }
        }
    }

    /**
     * 讀取jason檔,寫入資料庫
     * @throws Exception
     */
    @Test
    public void insertROCK() throws Exception {
        for (int i = 1; i < 21; i++) {
            FileReader reader = new FileReader("C:\\Users\\a8001\\Downloads\\0523\\ROCK\\" + i + ".json");
            List<Youtuber> userList = gson.fromJson(reader, new TypeToken<List<Youtuber>>() {
            }.getType());
            for (Youtuber y : userList) {
                String sql = "SET NAMES utf8mb4;INSERT INTO test.滾石唱片 (id,title,name,comment,time) VALUES (?,?,?,?,?)";
                jdbcUtils.update(sql, y.getId(), y.getTitle(), y.getName(), y.getComment(), y.getTime());
            }
        }
    }
    /**
     * 讀取jason檔,寫入資料庫
     * @throws Exception
     */
    @Test
    public void insertGAGA() throws Exception {
        for (int i = 1; i < 21; i++) {
            FileReader reader = new FileReader("C:\\Users\\a8001\\Downloads\\0523\\蔡阿嘎\\" + i + ".json");
            List<Youtuber> userList = gson.fromJson(reader, new TypeToken<List<Youtuber>>() {
            }.getType());
            for (Youtuber y : userList) {
                String sql = "SET NAMES utf8mb4;INSERT INTO test.蔡阿嘎 (id,title,name,comment,time) VALUES (?,?,?,?,?)";
                jdbcUtils.update(sql, y.getId(), y.getTitle(), y.getName(), y.getComment(), y.getTime());
            }
        }
    }

    /**
     * 返回所有留言內容
     * @param sql
     * @return list
     */
    public List<String> getAllComment(String sql) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = dataSource.getConnection();

            ps = conn.prepareStatement(sql);


            rs = ps.executeQuery();

            ArrayList<String> list = new ArrayList<String>();
            while (rs.next()) {
                String comment = rs.getString("comment");

                list.add(comment);
            }

            return list;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jdbcUtils.closeResource(conn, ps, rs);

        }

        return null;
    }
}


