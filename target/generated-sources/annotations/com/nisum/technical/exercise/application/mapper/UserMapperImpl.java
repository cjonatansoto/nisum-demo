package com.nisum.technical.exercise.application.mapper;

import com.nisum.technical.exercise.application.dto.request.PhoneRequest;
import com.nisum.technical.exercise.application.dto.request.UserRequest;
import com.nisum.technical.exercise.application.dto.response.PhoneResponse;
import com.nisum.technical.exercise.application.dto.response.UserResponse;
import com.nisum.technical.exercise.domain.entity.Phone;
import com.nisum.technical.exercise.domain.entity.User;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-10T11:21:34-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 23.0.1 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User mapRequestToEntity(UserRequest userRequest) {
        if ( userRequest == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.name( userRequest.getName() );
        user.email( userRequest.getEmail() );
        user.password( userRequest.getPassword() );

        return user.build();
    }

    @Override
    public UserResponse entityToResponse(User user) {
        if ( user == null ) {
            return null;
        }

        List<PhoneResponse> phones = null;
        UUID id = null;
        String name = null;
        String email = null;
        OffsetDateTime created = null;
        OffsetDateTime modified = null;
        OffsetDateTime lastLogin = null;
        String token = null;

        phones = phoneListToPhoneResponseList( user.getPhones() );
        id = user.getId();
        name = user.getName();
        email = user.getEmail();
        created = user.getCreated();
        modified = user.getModified();
        lastLogin = user.getLastLogin();
        token = user.getToken();

        boolean isActive = false;

        UserResponse userResponse = new UserResponse( id, name, email, phones, created, modified, lastLogin, token, isActive );

        return userResponse;
    }

    @Override
    public Phone phoneRequestToPhone(PhoneRequest request) {
        if ( request == null ) {
            return null;
        }

        Phone.PhoneBuilder phone = Phone.builder();

        phone.number( request.getNumber() );
        phone.cityCode( request.getCityCode() );
        phone.countryCode( request.getCountryCode() );

        return phone.build();
    }

    @Override
    public PhoneResponse phoneToPhoneResponse(Phone phone) {
        if ( phone == null ) {
            return null;
        }

        String number = null;
        String cityCode = null;
        String countryCode = null;

        number = phone.getNumber();
        cityCode = phone.getCityCode();
        countryCode = phone.getCountryCode();

        PhoneResponse phoneResponse = new PhoneResponse( number, cityCode, countryCode );

        return phoneResponse;
    }

    protected List<PhoneResponse> phoneListToPhoneResponseList(List<Phone> list) {
        if ( list == null ) {
            return null;
        }

        List<PhoneResponse> list1 = new ArrayList<PhoneResponse>( list.size() );
        for ( Phone phone : list ) {
            list1.add( phoneToPhoneResponse( phone ) );
        }

        return list1;
    }
}
