package executor.service.collection.queue;

import executor.service.model.Scenario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ThreadSafeQueueHandlerTest {
    private ThreadSafeQueueHandler<Scenario> handler;
    private static final int THREAD_COUNT = 8;
    private static final int ELEMENT_COUNT = 200;
    private CountDownLatch countDownLatch;
    private ExecutorService executorService;

    @BeforeEach
    public void setUp() {
        handler = new ThreadSafeQueueHandler<>();
        countDownLatch = new CountDownLatch(THREAD_COUNT);
        executorService = Executors.newFixedThreadPool(THREAD_COUNT);
    }

    @Test
    public void testAdd() throws InterruptedException {
        Runnable addRunnableTask = () -> {
            handler.add(new Scenario(UUID.randomUUID(), "name", "sice", List.of()));
            countDownLatch.countDown();
        };
        for (int i = 0; i < THREAD_COUNT; i++) executorService.submit(addRunnableTask);
        countDownLatch.await();
        assertEquals(THREAD_COUNT, handler.removeAll().size());
    }

    @Test
    public void testAddAll() throws InterruptedException {
        List<Scenario> elements = IntStream.range(0, ELEMENT_COUNT).boxed().map(v ->
                new Scenario(UUID.randomUUID(), "name", "sice", List.of()))
                .toList();
        Runnable addAllRunnableTask = () -> {
            handler.addAll(elements);
            countDownLatch.countDown();
        };
        for (int i = 0; i < THREAD_COUNT; i++) executorService.submit(addAllRunnableTask);
        countDownLatch.await();
        assertEquals(THREAD_COUNT * ELEMENT_COUNT, handler.removeAll().size());
    }

    @Test
    public void testPoll() throws InterruptedException {
        for(int i = 0; i < ELEMENT_COUNT; i++) handler.add(new Scenario(UUID.randomUUID(), "name", "sice", List.of()));
        Runnable pollRunnableTask = () -> {
            handler.poll();
            countDownLatch.countDown();
        };
        for (int i = 0; i < THREAD_COUNT; i++) executorService.submit(pollRunnableTask);
        countDownLatch.await();
        assertEquals(ELEMENT_COUNT - THREAD_COUNT, handler.removeAll().size());
    }

    @Test
    public void testRemoveAll() throws InterruptedException {
        for(int i = 0; i < ELEMENT_COUNT; i++) handler.add(new Scenario(UUID.randomUUID(), "name", "sice", List.of()));
        AtomicInteger resultSize = new AtomicInteger(0);
        Runnable removeAllRunnableTask = () -> {
            resultSize.addAndGet(handler.removeAll().size());
            countDownLatch.countDown();
        };
        for (int i = 0; i < THREAD_COUNT; i++) executorService.submit(removeAllRunnableTask);
        countDownLatch.await();
        assertEquals(ELEMENT_COUNT, resultSize.get());
        assertEquals(0, handler.removeAll().size());
    }

    @Test
    public void testGetSize() {
        for(int i = 0; i < ELEMENT_COUNT; i++) handler.add(new Scenario(UUID.randomUUID(), "name", "sice", List.of()));
        assertThat(handler.getSize()).isEqualTo(ELEMENT_COUNT);
    }
}