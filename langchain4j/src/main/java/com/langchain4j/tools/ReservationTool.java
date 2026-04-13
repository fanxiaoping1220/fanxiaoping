package com.langchain4j.tools;

import com.langchain4j.entity.Reservation;
import com.langchain4j.service.ReservationService;
import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class ReservationTool {

    public final ReservationService reservationService;

    /**
     * 预约资源填报服务
     * @param name
     * @param gender
     * @param phone
     * @param communicationTime
     * @param province
     * @param estimatedScore
     * @return
     */
    @Tool("预约资源填报服务")
    public Reservation insert(@P("考生姓名") String name,
                              @P("考生性别") String gender,
                              @P("考生手机号") String phone,
                              @P("沟通时间,格式为 yyyy-MM-dd HH:mm") String communicationTime,
                              @P("省份") String province,
                              @P("考生预估分数") Integer estimatedScore) {
        Reservation reservation = new Reservation(null, name, gender, phone,
                LocalDateTime.parse(communicationTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), province, estimatedScore);
        return reservationService.insert(reservation);
    }

    /**
     * 根据手机号查询预约信息
     * @param phone
     * @return
     */
    @Tool("根据手机号查询预约信息")
    public Reservation findByPhone(@P("手机号") String phone) {
        return reservationService.findByPhone(phone);
    }

    /**
     * 更新预约信息
     * @param name
     * @param gender
     * @param phone
     * @param communicationTime
     * @param province
     * @param estimatedScore
     * @return
     */
    @Tool("更新预约信息")
    public Reservation update(@P("考生姓名") String name,
                              @P("考生性别") String gender,
                              @P("考生手机号") String phone,
                              @P("沟通时间,格式为 yyyy-MM-dd HH:mm") String communicationTime,
                              @P("省份") String province,
                              @P("考生预估分数") Integer estimatedScore) {
        Reservation reservation = reservationService.findByPhone(phone);
        if (reservation == null) {
            return reservationService.insert(new Reservation(null, name, gender, phone,
                    LocalDateTime.parse(communicationTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), province, estimatedScore));
        }
        return reservationService.update(new Reservation(reservation.getId(), name, gender, phone,
                LocalDateTime.parse(communicationTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), province, estimatedScore));
    }
}
