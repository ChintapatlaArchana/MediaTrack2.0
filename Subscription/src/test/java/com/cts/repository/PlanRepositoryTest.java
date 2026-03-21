//package com.cts.repository;
//
//import com.cts.model.Plan;
//import com.cts.model.Plan.BillingCycle;
//import com.cts.model.Plan.Status;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.beans.factory.annotation.Autowired;
//import static org.junit.jupiter.api.Assertions.*;
//
//@DataJpaTest
//class PlanRepositoryTest {
//
//    @Autowired
//    private PlanRepository planRepository;
//
//    @Test
//    void testSavePlan() {
//        Plan plan = new Plan();
//        plan.setName("Basic");
//        plan.setPrice(100.0);
//        plan.setBillingCycle(BillingCycle.Monthly);
//        plan.setStatus(Status.Active);
//
//        Plan saved = planRepository.save(plan);
//
//        assertNotNull(saved);
//        assertTrue(saved.getPlanId() > 0);
//        assertEquals("Basic", saved.getName());
//    }
//
//    @Test
//    void testFindById() {
//        Plan plan = new Plan();
//        plan.setName("Pro");
//        plan.setPrice(200.0);
//        plan.setBillingCycle(BillingCycle.Yearly);
//        plan.setStatus(Status.Active);
//
//        Plan saved = planRepository.save(plan);
//
//        Plan found = planRepository.findById(saved.getPlanId()).orElse(null);
//
//        assertNotNull(found);
//        assertEquals("Pro", found.getName());
//    }
//
//    @Test
//    void testDeletePlan() {
//        Plan plan = new Plan();
//        plan.setName("Temp");
//        plan.setPrice(50.0);
//        plan.setBillingCycle(BillingCycle.Monthly);
//        plan.setStatus(Status.Inactive);
//
//        Plan saved = planRepository.save(plan);
//        long id = saved.getPlanId();
//
//        planRepository.delete(saved);
//
//        assertFalse(planRepository.findById(id).isPresent());
//    }
//}