package com.multithreading;

public class OddEvenPrinterUsingWaitNotify {

	private volatile boolean isOdd;

	synchronized void printEven(int number) {
		while (!isOdd) {
			try {
				wait();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
		System.out.println(Thread.currentThread().getName() + ":" + number);
		isOdd = false;
		notify();
	}

	synchronized void printOdd(int number) {
		while (isOdd) {
			try {
				wait();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
		System.out.println(Thread.currentThread().getName() + ":" + number);
		isOdd = true;
		notify();
	}

	public static void main(String... args) {
		OddEvenPrinterUsingWaitNotify print = new OddEvenPrinterUsingWaitNotify();
		Thread t1 = new Thread(new TaskEvenOdd(print, 10, false), "Odd");
		Thread t2 = new Thread(new TaskEvenOdd(print, 10, true), "Even");
		t1.start();
		t2.start();
	}
}

class TaskEvenOdd implements Runnable {
	private int max;
	private OddEvenPrinterUsingWaitNotify print;
	private boolean isEvenNumber;

	public TaskEvenOdd(OddEvenPrinterUsingWaitNotify printer, int max, boolean isEven) {
		this.print = printer;
		this.max = max;
		this.isEvenNumber = isEven;
	}

	@Override
	public void run() {
		int number = isEvenNumber ? 2 : 1;
		
		while (number <= max) {
			System.out.println("Starting: " + Thread.currentThread().getName() + ":" +"number is: *** :: " + number);
			if (isEvenNumber) {
				print.printEven(number);
			} else {
				print.printOdd(number);
			}
			number += 2;
			System.out.println("Ending: " + Thread.currentThread().getName() + ":" +"number is: *** :: " + number);
		}
	}
}