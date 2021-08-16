package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService{

    private final MemberRepository memberRepository; // final 쓰면 생성자에서만 값을 넣어줄 수 있음!!!
    // 생성자에서 memberRepository를 누락한 것도 알 수 있게 자바 컴파일에서 오류메시지를 알려준다
    private final DiscountPolicy discountPolicy;

//    @Autowired -> 필드 주입
//    private MemberRepository memberRepository;

//    @Autowired(required = false) // 선택적으로 동작 -> 수정자 주입
//    public void setMemberRepository(MemberRepository memberRepository){
//        this.memberRepository = memberRepository;
//    }

    //@Autowired 생성자가 1개면 생략 가능 -> 생성자 주입6
    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    // 테스트 용도
    public MemberRepository getMemberRepository(){
        return memberRepository;
    }
}
