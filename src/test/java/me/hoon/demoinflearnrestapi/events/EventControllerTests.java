package me.hoon.demoinflearnrestapi.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
public class EventControllerTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void createEvent() throws Exception{

        Event event = Event.builder()
                .name("Spring")
                .description("REST API Development with Spring")
                .beginEnrollmentDateTime(LocalDateTime.of(2018,11, 23, 14, 21))
                .closeEnrollmentDateTime(LocalDateTime.of(2018,11, 24, 14, 21))
                .beginEventDateTime(LocalDateTime.of(2018,11, 25, 14, 21))
                .endEventDateTime(LocalDateTime.of(2018,11, 26, 14, 21))
                .basePrice(100)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("강남역 D2 스타트업 팩토리")
                .build()
                ;

        mockMvc.perform(post("/api/events/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaTypes.HAL_JSON)
                    .content(objectMapper.writeValueAsString(event)))   //content를 json으로 전달
                .andDo(print()) //요청과 응답 정보를 출력
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").exists()) //id가 있는지 확인
        ;
    }

}
