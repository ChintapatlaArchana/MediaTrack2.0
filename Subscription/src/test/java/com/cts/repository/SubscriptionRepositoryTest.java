//package com.cts.repository;
//
//import com.cts.model.Plan;
//import com.cts.model.Subscription;
//import com.cts.model.Plan.BillingCycle;
//import com.cts.model.Plan.Status;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//
//import java.time.LocalDate;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@DataJpaTest
//class SubscriptionRepositoryTest {
//
//    @Autowired
//    private SubscriptionRepository subscriptionRepository;
//
//    @Autowired
//    private PlanRepository planRepository;
//
//    private Plan createPlan(String name) {
//        Plan p = new Plan();
//        p.setName(name);
//        p.setPrice(99.0);
//        p.setBillingCycle(BillingCycle.Monthly);
//        p.setStatus(Status.Active); // Plan.Status
//        return planRepository.save(p);
//    }
//
//    @Test
//    void testSaveSubscription() {
//        Plan plan = createPlan("BASIC");
//
//        Subscription s = new Subscription();
//        s.setUserId(123L);
//        s.setPlan(plan); // required because @JoinColumn nullable=false
//        s.setStartDate(LocalDate.of(2026, 1, 1));
//        s.setEndDate(LocalDate.of(2026, 12, 31));
//        s.setStatus(Subscription.Status.Active); // fully qualify Subscription.Status
//
//        Subscription saved = subscriptionRepository.save(s);
//
//        assertTrue(saved.getSubscriptionId() > 0);
//        assertEquals(123L, saved.getUserId());
//        assertNotNull(saved.getPlan());
//        assertEquals("BASIC", saved.getPlan().getName());
//        assertEquals(Subscription.Status.Active, saved.getStatus());
//    }
//
//    @Test
//    void testFindById() {
//        Plan plan = createPlan("PRO");
//
//        Subscription s = new Subscription();
//        s.setUserId(999L);
//        s.setPlan(plan);
//        s.setStartDate(LocalDate.now());
//        s.setStatus(Subscription.Status.Active);
//
//        Subscription saved = subscriptionRepository.save(s);
//
//        Subscription found = subscriptionRepository.findById(saved.getSubscriptionId()).orElse(null);
//
//        assertNotNull(found);
//        assertEquals(999L, found.getUserId());
//        assertNotNull(found.getPlan());
//        assertEquals("PRO", found.getPlan().getName());
//    }
//
//    @Test
//    void testDeleteSubscription() {
//        Plan plan = createPlan("DEL");
//        Subscription s = new Subscription();
//        s.setUserId(77L);
//        s.setPlan(plan);
//        s.setStatus(Subscription.Status.Active);
//
//        Subscription saved = subscriptionRepository.save(s);
//        long id = saved.getSubscriptionId();
//
//        subscriptionRepository.delete(saved);
//
//        assertFalse(subscriptionRepository.findById(id).isPresent());
//    }
//}