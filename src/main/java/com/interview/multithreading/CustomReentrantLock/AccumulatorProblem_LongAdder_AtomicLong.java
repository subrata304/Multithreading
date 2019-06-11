package com.interview.multithreading.CustomReentrantLock;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;

/*
 * https://blog.overops.com/java-8-longadders-the-fastest-way-to-add-numbers-concurrently/
 */
public class AccumulatorProblem_LongAdder_AtomicLong {

	private static final String[] NAMES = { "A", "B" };
	private static final int NB_THREADS = 1000;
	//private final Map<String, AtomicInteger> countsMap = new ConcurrentHashMap<>();
	
	private final Map<String, LongAdder> countsMap = new ConcurrentHashMap<>();

	public void testIt(){
		
		long startTime = System.currentTimeMillis();
		System.out.println("Start Time: "+ startTime);
		
		ExecutorService executor = Executors.newFixedThreadPool(NB_THREADS);
		for (int i = 0; i < NB_THREADS; i++) {
			Runnable task = new WorkerThread();
			executor.submit(task);
		}
		executor.shutdown();
		try {
			executor.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		System.out.println(countsMap);
		
		long endTime = System.currentTimeMillis();
		System.out.println("Start Time: "+ endTime);
		
		System.out.println("Total Time: "+ (endTime - startTime));
	}

	private void accumulate(String name) {
	//	countsMap.computeIfAbsent(name, k -> new AtomicInteger()).incrementAndGet();
		countsMap.computeIfAbsent(name, k -> new LongAdder()).increment();
	}

	private class WorkerThread implements Runnable {
		public void run() {
			//System.out.println(ThreadLocalRandom.current().nextInt(0, NAMES.length));
			accumulate(NAMES[ThreadLocalRandom.current().nextInt(0, NAMES.length)]);
		}
	}
	
	public static void main(String[] args) {
		AccumulatorProblem_LongAdder_AtomicLong accumulatorThree = new AccumulatorProblem_LongAdder_AtomicLong();
		accumulatorThree.testIt();
	}
}
