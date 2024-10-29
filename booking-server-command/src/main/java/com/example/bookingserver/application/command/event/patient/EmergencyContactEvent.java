package com.example.bookingserver.application.command.event.patient;

import com.example.bookingserver.application.command.event.EventBase;
import com.example.bookingserver.domain.Patient;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmergencyContactEvent extends EventBase {
    Long id;
    String name;
    String phone;
    String address;
    String relationship;
    Patient patient;
}
