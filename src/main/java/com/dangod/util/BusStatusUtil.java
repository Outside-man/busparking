package com.dangod.util;

import com.dangod.model.bo.BusBO;
import com.dangod.model.entity.pi.BusActionLog;
import com.dangod.model.entity.pi.BusScore;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * Created by Yxm on 2017/11/30.
 */
public class BusStatusUtil {
    public static List<BusScore> calStatus(List<BusActionLog> actionLogs, Map<String, BusBO> busVoMap, java.util.Date now, String deptId, List<BusScore> preBusScore){
        for(BusActionLog log : actionLogs) {
            switch (log.getActiontype()) {
                case "1"://进场安检
                    busVoMap.get(log.getBusid()).addActStatus();
                    break;
                case "2"://收银
                    busVoMap.get(log.getBusid()).addActStatus();
                    break;
                case "4"://加油结束
                    busVoMap.get(log.getBusid()).addActStatus();
                    break;
                case "6"://洗车结束
                    busVoMap.get(log.getBusid()).addActStatus();
                    break;
                case "8"://出场安检 分数清零
                    busVoMap.get(log.getBusid()).setActstatus(0);
                    break;
            }
            busVoMap.get(log.getBusid()).setLasttime(new java.sql.Timestamp(log.getActiontime().getTime()));
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar nowTime = Calendar.getInstance();
        nowTime.setTime(now);
        nowTime.set(Calendar.HOUR, 1);
        nowTime.set(Calendar.MINUTE, 0);
        nowTime.set(Calendar.SECOND, 0);
        long reZero = nowTime.getTime().getTime();//工作日的开始 1:00AM
        //从数据库验证score
        for(BusScore s : preBusScore){
            //验证0分车 是否因为跨天未出厂为0
            if(busVoMap.containsKey(s.getBusid()) && busVoMap.get(s.getBusid()).getActstatus()==0) {
                // 最后一次时间 是今天1:00前
                if(s.getLasttime().getTime() < reZero) {
                    //因为跨天无记录 停留在场内 维持原有分数
                    busVoMap.get(s.getBusid()).setActstatus(s.getScore());
                }
                //如果未超过24小时，且保存的时间比当前计算高(即抓取数据不再是同一天)
//                if (nowTime - s.getLasttime().getTime() < 86400000 && s.getScore()>busVoMap.get(s.getBusid()).getActstatus()) {
//                    if (busVoMap.get(s.getBusid()).getActstatus() > 0) {
//                        //需要更新存在数据库的时间
//                        busVoMap.get(s.getBusid()).addActStatus(s.getScore());
//                    } else if(busVoMap.get(s.getBusid()).getActstatus() == 0){
//                        //只是继承原来数据库的时间
//                        busVoMap.get(s.getBusid()).setLasttime(s.getLasttime());
//                        busVoMap.get(s.getBusid()).setActstatus(s.getScore());
//                    }
//                }
            }
        }

        List<BusScore> scoreList = new ArrayList<>();
        for(BusBO v : busVoMap.values()){
            BusScore score;
            if(v.getLasttime()!=null) {
                score = new BusScore(v.getBusid(), v.getActstatus(), v.getLasttime(), deptId);
            }else{
                score = new BusScore(v.getBusid(), v.getActstatus(), new Timestamp(now.getTime()), deptId);
            }
            scoreList.add(score);
        }
        return scoreList;
    }
}
