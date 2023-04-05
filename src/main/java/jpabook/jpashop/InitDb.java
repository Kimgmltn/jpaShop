package jpabook.jpashop;

import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init(){
        initService.dbInit1();
        initService.dbInit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService{

        private final EntityManager em;
        public void dbInit1(){
            Member member = createMember("userA", "123", "1", "1111");
            em.persist(member);

            Book book1 = createBook("jpa1", 10000);
            em.persist(book1);

            Book book2 = createBook("jpa2", 20000);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.creatOrderItem(book1, 10000, 1);
            OrderItem orderItem2 = OrderItem.creatOrderItem(book2, 20000, 2);

            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);
        }

        public void dbInit2(){
            Member member = createMember("userB", "234", "234", "234");
            em.persist(member);

            Book book1 = createBook("spring1", 20000);
            em.persist(book1);

            Book book2 = createBook("spring2", 40000);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.creatOrderItem(book1, 20000, 3);
            OrderItem orderItem2 = OrderItem.creatOrderItem(book2, 40000, 4);

            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);
        }

        private Book createBook(String jpa1, int price) {
            Book book1 = new Book();
            book1.setName(jpa1);
            book1.setPrice(price);
            book1.setStockQuantity(100);
            return book1;
        }

        private Member createMember(String name, String city, String street, String zipcode) {
            Member member = new Member();
            member.setName(name);
            member.setAddress(new Address(city, street, zipcode));
            return member;
        }

    }

}
