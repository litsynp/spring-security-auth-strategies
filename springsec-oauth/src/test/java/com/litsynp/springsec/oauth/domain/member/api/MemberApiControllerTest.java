package com.litsynp.springsec.oauth.domain.member.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.litsynp.springsec.oauth.config.SpringSecurityWebAuxTestConfig;
import com.litsynp.springsec.oauth.domain.member.domain.Member;
import com.litsynp.springsec.oauth.domain.member.dto.MemberCreateRequestDto;
import com.litsynp.springsec.oauth.domain.member.dto.MemberMapper;
import com.litsynp.springsec.oauth.domain.member.service.MemberService;
import com.litsynp.springsec.oauth.template.ApiMockControllerTest;
import com.litsynp.springsec.oauth.util.FieldUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

@WebMvcTest(MemberApiController.class)
@Import({MemberMapper.class})
class MemberApiControllerTest extends ApiMockControllerTest {

    @Autowired
    private MemberMapper memberMapper;

    @MockBean
    private MemberService memberService;

    @Test
    @DisplayName("Register member - Ok")
    void register_ok() throws Exception {
        // given
        MemberCreateRequestDto requestDto = new MemberCreateRequestDto("testuser1@example.com",
                "12345678");

        Member member = Member.builder()
                .email("testuser1@example.com")
                .password("encrypted")
                .build();

        Member created = Member.builder()
                .email("testuser1@example.com")
                .password("encrypted")
                .build();
        FieldUtil.writeField(created, "id", 1L);

        given(memberService.register(any()))
                .willReturn(created);

        // when & then
        mockMvc.perform(post("/v1/members")
                        .content(objectMapper.writeValueAsString(requestDto))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().string(
                        objectMapper.writeValueAsString(memberMapper.toResponseDto(created))));
    }

    @Test
    @DisplayName("Get one member - Ok")
    void getOne_ok() throws Exception {
        // given
        Member member = Member.builder()
                .email("testuser1@example.com")
                .build();
        FieldUtil.writeField(member, "id", 3L);

        given(memberService.findById(member.getId()))
                .willReturn(member);

        // when & then
        mockMvc.perform(get("/v1/members/{id}", member.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().string(
                        objectMapper.writeValueAsString(memberMapper.toResponseDto(member))))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Delete member - Ok")
    @WithMockUser("testuser@example.com")
    void deleteMember_ok() throws Exception {
        // given
        given(memberService.findById(SpringSecurityWebAuxTestConfig.getBasicMember().getId()))
                .willReturn(SpringSecurityWebAuxTestConfig.getBasicMember());

        // when & then
        mockMvc.perform(
                        delete("/v1/members/{id}", SpringSecurityWebAuxTestConfig.getBasicMember().getId())
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}
