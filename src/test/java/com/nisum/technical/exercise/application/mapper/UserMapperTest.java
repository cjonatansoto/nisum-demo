package com.nisum.technical.exercise.application.mapper;

import com.nisum.technical.exercise.application.dto.request.PhoneRequest;
import com.nisum.technical.exercise.application.dto.request.UserRequest;
import com.nisum.technical.exercise.application.dto.response.UserResponse;
import com.nisum.technical.exercise.domain.entity.Phone;
import com.nisum.technical.exercise.domain.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        userMapper = new UserMapperImpl();
    }

    @Test
    void testRequestToEntity() {
        var request = new UserRequest(
                "Jonatan",
                "jonatan@example.com",
                "password123",
                List.of(new PhoneRequest("12345678", "1", "56"))
        );
        User user = userMapper.requestToEntity(request);
        assertNotNull(user);
        assertEquals(request.getName(), user.getName());
        assertEquals(request.getEmail(), user.getEmail());
        assertNotNull(user.getPhones());
        assertEquals(1, user.getPhones().size());
        Phone phone = user.getPhones().get(0);
        assertEquals("12345678", phone.getNumber());
        assertEquals("1", phone.getCityCode());
        assertEquals("56", phone.getCountryCode());
        assertSame(user, phone.getUser(), "Phone user debe apuntar al mismo objeto User");
    }

    @Test
    void testEntityToResponse() {
        var user = new User();
        user.setName("Jonatan");
        user.setEmail("jonatan@example.com");
        var phone = new Phone();
        phone.setNumber("12345678");
        phone.setCityCode("1");
        phone.setCountryCode("56");
        phone.setUser(user);
        user.setPhones(List.of(phone));
        UserResponse response = userMapper.entityToResponse(user);
        assertNotNull(response);
        assertEquals(user.getName(), response.name());
        assertEquals(user.getEmail(), response.email());
        assertEquals(1, response.phones().size());
        var phoneResponse = response.phones().get(0);
        assertEquals(phone.getNumber(), phoneResponse.number());
        assertEquals(phone.getCityCode(), phoneResponse.cityCode());
        assertEquals(phone.getCountryCode(), phoneResponse.countryCode());
    }
}
