package com.multithreading;

import java.util.concurrent.Semaphore;

public class OddEvenPrinterUsingSemaphore {
	private Semaphore semEven = new Semaphore(0);
	private Semaphore semOdd = new Semaphore(1);

	void printEvenNum(int num) {
		try {
			semEven.acquire();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		System.out.println(Thread.currentThread().getName() +" :: " + num);
		semOdd.release();
	}

	void printOddNum(int num) {
		try {
			semOdd.acquire();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		System.out.println(Thread.currentThread().getName() +" :: " + num);
		semEven.release();

	}

	public static void main(String... args) {
		OddEvenPrinterUsingSemaphore print = new OddEvenPrinterUsingSemaphore();
		Thread odd = new Thread(new Odd(print, 10), "Odd");
		Thread even = new Thread(new Even(print, 10), "Even");
		odd.start();
		even.start();
	}
}

class Even implements Runnable {
	private OddEvenPrinterUsingSemaphore sp;
	private int max;

	public Even(OddEvenPrinterUsingSemaphore print, int max) {
		this.sp = print;
		this.max = max;
	}

	@Override
	public void run() {
		for (int i = 2; i <= max; i = i + 2) {
			sp.printEvenNum(i);
		}
	}
}

class Odd implements Runnable {
	private OddEvenPrinterUsingSemaphore sp;
	private int max;

	public Odd(OddEvenPrinterUsingSemaphore print, int max) {
		this.sp = print;
		this.max = max;
	}

	@Override
	public void run() {
		for (int i = 1; i <= max; i = i + 2) {
			sp.printOddNum(i);
		}
	}
}