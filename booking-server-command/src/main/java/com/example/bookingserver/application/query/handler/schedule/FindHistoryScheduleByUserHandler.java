package com.example.bookingserver.application.query.handler.schedule;

import com.example.bookingserver.application.query.QueryBase;
import com.example.bookingserver.application.query.handler.response.PageResponse;
import com.example.bookingserver.application.query.handler.response.FindByUserResponse;
import com.example.bookingserver.domain.Schedule;
import com.example.bookingserver.domain.repository.ScheduleRepository;
import com.example.bookingserver.infrastructure.mapper.ScheduleMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class FindHistoryScheduleByUserHandler {

    ScheduleRepository scheduleRepository;
    ScheduleMapper scheduleMapper;

    public PageResponse<FindByUserResponse> findByUser(String userId, QueryBase<FindByUserResponse> queryBase){
        Page<Schedule> page= scheduleRepository.findByUser(userId, queryBase.getPageable());
        List<FindByUserResponse> scheduleResponses= new ArrayList<>();
        for(Schedule schedule : page.getContent()){
            scheduleResponses.add(scheduleMapper.toFindByUserResponse(schedule));
        }
        return PageResponse.<FindByUserResponse>builder()
                .totalPage(page.getTotalPages())
                .orders(queryBase.getOrders())
                .contentResponse(scheduleResponses)
                .pageIndex(queryBase.getPageIndex())
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .build();
    }
}