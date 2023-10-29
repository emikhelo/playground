package com.example.playground;

import com.example.playground.equipment.*;
import com.example.playground.kid.ImpatientKid;
import com.example.playground.kid.Kid;
import com.example.playground.kid.KidStatus;
import com.example.playground.kid.PatientKid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class PlaygroundTest {
    private Kid bob;
    Playground playground;
    PlaySite bigSite;
    ApplicationConfig applicationConfig;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        applicationConfig = Mockito.mock(ApplicationConfig.class);
        ApplicationConfig.setInstance(applicationConfig);
        when(applicationConfig.getToday()).thenReturn(LocalDate.of(2023, 1, 1));

        createPlayground();
        bob = new Kid("Bob");
    }

    private void createPlayground() {
        playground = new Playground();
        bigSite = new PlaySite(playground, "Big");

        new DoubleSwing(bigSite);
        new DoubleSwing(bigSite);
        new Slide(bigSite);
        new Carousel(bigSite);
        new BallPit(bigSite);
        Slide smallSlide = new Slide(bigSite);
        smallSlide.setMaxKids(2);
    }

    @Test
    public void testEmptyPlayground() {
        assertEquals(0, bigSite.calcUtilizationPercent());
        assertEquals(0, playground.getVisitorsForToday());
    }

    @Test
    public void testFullPlayground() {
        fillBigSite();
        assertEquals(100, bigSite.calcUtilizationPercent());
        assertEquals(bigSite.getMaxKids(), playground.getVisitorsForToday());
    }

    @Test
    public void testQueueing() {
        fillBigSite();
        
        Kid alice = new PatientKid("Alice", 5);
        bigSite.addOrQueueKid(alice);
        assertEquals(KidStatus.QUEUED, alice.getStatus());
        assertEquals(100, bigSite.calcUtilizationPercent());
        int maxKids = bigSite.getMaxKids();
        assertEquals(maxKids, playground.getVisitorsForToday());

        Kid afterAlice = new PatientKid("After Alice", 5);
        bigSite.addOrQueueKid(afterAlice);

        bigSite.removeKid(bob);
        assertEquals(KidStatus.OUTSIDE, bob.getStatus());
        assertEquals(KidStatus.PLAYING, alice.getStatus());
        assertEquals(100, bigSite.calcUtilizationPercent());
        assertEquals(maxKids + 1, playground.getVisitorsForToday());
    }


    @Test
    public void testImpatientKid() {
        fillBigSite();

        Kid charlie = new ImpatientKid("Charlie", 5);
        bigSite.addOrQueueKid(charlie);
        assertEquals(KidStatus.OUTSIDE, charlie.getStatus());

        bigSite.removeKid(bob);
        assertTrue(bigSite.calcUtilizationPercent() < 100);
    }

    @Test
    public void testKidRemovalNoQueue() {
        bigSite.addOrQueueKid(bob);

        bigSite.removeKid(bob);
        assertEquals(KidStatus.OUTSIDE, bob.getStatus());
        assertTrue(bigSite.calcUtilizationPercent() == 0);
        assertEquals(1, playground.getVisitorsForToday());
    }

    @Test
    public void testDoubleSwing() {
        PlaySite smallSite = createSmallSite();
        smallSite.addOrQueueKid(bob);
        assertEquals(1, playground.getVisitorsForToday());
        assertEquals(0, smallSite.calcUtilizationPercent());

        Kid kid = new Kid("Mr. Smith");
        smallSite.addOrQueueKid(kid);
        assertEquals(100, smallSite.calcUtilizationPercent());
    }

    @Test
    public void testVisitorsMultiplePlaySites() {
        PlaySite smallSite = createSmallSite();
        smallSite.addOrQueueKid(bob);
        smallSite.removeKid(bob);
        bigSite.addOrQueueKid(bob);
        assertEquals(1, playground.getVisitorsForToday());

        Kid kid = new Kid("Mr. Smith");
        smallSite.addOrQueueKid(kid);
        assertEquals(2, playground.getVisitorsForToday());
    }

    @Test
    public void testVisitorsForDate() {
        bigSite.addOrQueueKid(bob);
        assertEquals(1, playground.getVisitorsForToday());

        when(applicationConfig.getToday()).thenReturn(LocalDate.of(2023, 1, 2));
        assertEquals(0, playground.getVisitorsForToday());
    }

    @Test
    public void testKidException() {
        assertThrows(KidException.class, () -> {
            bigSite.removeKid(bob);
        });

        bigSite.addOrQueueKid(bob);
        assertThrows(KidException.class, () -> {
            bigSite.addOrQueueKid(bob);
        });
    }

    private PlaySite createSmallSite() {
        PlaySite smallSite = new PlaySite(playground, "Small");
        new DoubleSwing(smallSite);
        return smallSite;
    }

    private void fillBigSite() {
        bigSite.addOrQueueKid(bob);

        for (int i = 2; i <= bigSite.getMaxKids(); i++) {
            Kid kid = new Kid("Mr. Smith");
            bigSite.addOrQueueKid(kid);
        }
    }
}
