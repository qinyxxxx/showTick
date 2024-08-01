package com.qyx.showtick;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = ShowTickPortalApplication.class)
class ShowTickPortalApplicationTests {

    @Autowired
    private ObjectMapper objectMapper;

//    @Test
//    public void testLocalDateTimeSerialization() throws Exception {
//        LocalDateTime now = LocalDateTime.now();
//        String json = objectMapper.writeValueAsString(now);
//        LocalDateTime parsed = objectMapper.readValue(json, LocalDateTime.class);
//        assertEquals(now, parsed);
//    }

}
