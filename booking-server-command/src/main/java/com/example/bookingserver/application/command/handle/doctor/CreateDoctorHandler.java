package com.example.bookingserver.application.command.handle.doctor;

import com.example.bookingserver.application.command.command.doctor.CreateDoctorCommand;
import com.example.bookingserver.application.command.handle.HandlerDTO;
import com.example.bookingserver.application.command.reponse.DoctorResponse;
import com.example.bookingserver.domain.*;
import com.example.bookingserver.domain.repository.DoctorRepository;
import com.example.bookingserver.domain.repository.UserRepository;
import com.example.bookingserver.infrastructure.mapper.DoctorMapper;
import com.example.bookingserver.infrastructure.mapper.UserMapper;
import com.example.bookingserver.infrastructure.message.MessageProducer;
import document.constant.TopicConstant;
import document.event.doctor.CreateDoctorEvent;
import document.event.user.CreateUserEvent;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class CreateDoctorHandler implements HandlerDTO<CreateDoctorCommand, DoctorResponse> {

    final DoctorRepository doctorRepository;
    final MessageProducer messageProducer;
    final DoctorMapper doctorMapper;
    final String TOPIC= TopicConstant.DoctorTopic.CREATE;
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Override
    @SneakyThrows
    public DoctorResponse execute(CreateDoctorCommand command) {

        Set<Role> roles= new HashSet<>();
        roles.add(new Role(ERole.USER)); // Cần suy nghĩ lại, nếu doctor cũng là user, vậy doctor sẽ có nhưng khả năng của user
        roles.add(new Role(ERole.DOCTOR));

        User user= userMapper.toUserFromCreateCommand(command.getUser());
        user.setRoles(roles);
        user= userRepository.save(user);

        Doctor doctor= doctorMapper.toDoctorFromCreateCommand(command);
        doctor.setUser(user);
        doctorRepository.save(doctor);

        CreateDoctorEvent mainEvent= doctorMapper.fromDoctorToCreateDoctorEvent(doctor);
        CreateUserEvent userEvent= userMapper.fromUserToCreateUserEvent(user);
        mainEvent.setUser(userEvent);

        messageProducer.sendMessage(TOPIC, "CREATE", mainEvent, mainEvent.getId(), "Doctor");
        return doctorMapper.toResponse(doctor);
    }
}
