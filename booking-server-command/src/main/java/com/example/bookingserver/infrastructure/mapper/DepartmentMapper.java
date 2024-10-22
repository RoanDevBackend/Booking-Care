package com.example.bookingserver.infrastructure.mapper;

import com.example.bookingserver.application.command.command.department.CreateDepartmentCommand;
import com.example.bookingserver.application.command.command.department.UpdateInfoDepartmentCommand;
import com.example.bookingserver.application.command.event.department.CreateDepartmentEvent;
import com.example.bookingserver.application.command.event.department.UpdateInfoDepartmentEvent;
import com.example.bookingserver.application.command.reponse.DepartmentResponse;
import com.example.bookingserver.domain.Department;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {
    Department toDepartment(CreateDepartmentCommand command);
    void updateInfo(@MappingTarget Department department, UpdateInfoDepartmentCommand command);
    DepartmentResponse toResponse(Department department);
    CreateDepartmentEvent toCreateDepartmentEvent(Department department);
    UpdateInfoDepartmentEvent toUpdateDepartmentEvent(Department department);
}