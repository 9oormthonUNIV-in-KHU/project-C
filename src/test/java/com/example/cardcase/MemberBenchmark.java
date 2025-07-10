//package com.example.cardcase;
//
//import com.example.cardcase.oauth.domain.entity.Member;
//import com.example.cardcase.oauth.repository.MemberRepository;
//import jakarta.transaction.Transactional;
//import org.springframework.util.StopWatch;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.List;
//import java.util.stream.Collectors;
//import java.util.stream.IntStream;
//
//@SpringBootTest
//@Transactional
//public class MemberBenchmark {
//private final MemberRepository memberRepository;
//
//    @Autowired
//    public MemberBenchmark(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }
//    // 멤버 기초 생성
//    @BeforeEach
//    void setUp() {
//        List<Member> members = IntStream.rangeClosed(1, 1000)
//                .mapToObj(i -> Member.builder()
//                        .email("member" + i)
//                        .password("pass")
//                        .name("member" + i)
//                        .build())
//                .collect(Collectors.toList());
//        memberRepository.saveAll(members);
//    }
//    @Test
//    void compareProjection() {
//        // 워밍업
//        for (int i = 0; i < 5; i++) {
//            memberRepository.findAll();
//            memberRepository.findAllMember();
//        }
//
//        StopWatch sw = new StopWatch();
//        sw.start("findAll");
//        for (int i = 0; i < 50; i++) {
//            memberRepository.findAll();
//        }
//        sw.stop();
//
//        sw.start("findAllMemberDtos");
//        for (int i = 0; i < 50; i++) {
//            memberRepository.findAllMember();
//        }
//        sw.stop();
//
//        System.out.println(sw.prettyPrint());
//    }
//}
