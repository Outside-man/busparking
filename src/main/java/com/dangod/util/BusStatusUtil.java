package com.dangod.util;

import com.dangod.model.entity.pi.BusActionLog;
import com.dangod.model.entity.pi.BusScore;
import com.dangod.model.vo.BusVO;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Yxm on 2017/11/30.
 */
public class BusStatusUtil {
    public static List<BusScore> calStatus(List<BusActionLog> actionLogs, Map<String, BusVO> busVoMap, java.util.Date now, String deptId, List<BusScore> preBusScore){
        //把内存中的分数清理
        for(BusVO b : busVoMap.values())b.setActstatus(0);
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
                case "8"://出场安检
                    busVoMap.get(log.getBusid()).addActStatus();
                    break;
            }
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long nowTime = now.getTime();
        for(BusScore s : preBusScore){
            //从数据库验证score 最后一次更新与now是否未超过24h
            if(busVoMap.containsKey(s.getBusid())) {
                //如果未超过24小时，且保存的时间比当前计算高(即抓取数据不再是同一天)
                if (nowTime - s.getLasttime().getTime() < 86400000 && s.getScore()>busVoMap.get(s.getBusid()).getActstatus()) {
                    if (busVoMap.get(s.getBusid()).getActstatus() > 0) {
                        //需要更新存在数据库的时间
                        busVoMap.get(s.getBusid()).addActStatus(s.getScore());
                    } else if(busVoMap.get(s.getBusid()).getActstatus() == 0){
                        //只是继承原来数据库的时间
                        busVoMap.get(s.getBusid()).setLasttime(s.getLasttime());
                        busVoMap.get(s.getBusid()).setActstatus(s.getScore());
                    }
                }
            }
        }

        List<BusScore> scoreList = new ArrayList<>();
        for(BusVO v : busVoMap.values()){
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
