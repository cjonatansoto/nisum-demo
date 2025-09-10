package com.nisum.technical.exercise.application.mapper;

import com.nisum.technical.exercise.application.dto.request.UserRequest;
import com.nisum.technical.exercise.application.dto.request.PhoneRequest;
import com.nisum.technical.exercise.application.dto.response.PhoneResponse;
import com.nisum.technical.exercise.application.dto.response.UserResponse;
import com.nisum.technical.exercise.domain.entity.Phone;
import com.nisum.technical.exercise.domain.entity.User;
import org.mapstruct.*;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mapping(target = "phones", ignore = true)
    User mapRequestToEntity(UserRequest userRequest);

    @Mapping(target = "phones", source = "phones")
    UserResponse entityToResponse(User user);

    Phone phoneRequestToPhone(PhoneRequest request);

    PhoneResponse phoneToPhoneResponse(Phone phone);

    default User requestToEntity(UserRequest userRequest) {
        var user = mapRequestToEntity(userRequest);

        var phones = Optional.ofNullable(userRequest.getPhones())
                .orElse(List.of())
                .stream()
                .map(this::phoneRequestToPhone)
                .toList();

        phones.forEach(phone -> phone.setUser(user));
        user.setPhones(List.copyOf(phones));

        return user;
    }
}


