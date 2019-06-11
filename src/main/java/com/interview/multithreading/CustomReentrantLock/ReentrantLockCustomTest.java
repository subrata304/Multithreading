package com.interview.multithreading.CustomReentrantLock;

public class ReentrantLockCustomTest {

	public static void main(String[] args) {
		 
  		LockCustom LockCustom=new ReentrantLockCustom();
  		MyRunnable myRunnable=new MyRunnable(LockCustom);
  		new Thread(myRunnable,"Thread-1").start();
  		new Thread(myRunnable,"Thread-2").start();
  		
	}
}
class MyRunnable implements Runnable{
   	
   	LockCustom lockCustom;
   	public MyRunnable(LockCustom LockCustom) {  	
      		this.lockCustom=LockCustom;
   	}
   	
   	public void run(){
      		
      		System.out.println(Thread.currentThread().getName()
                   		+" is Waiting to acquire LockCustom");
      		
      		lockCustom.lock();
 
      		System.out.println(Thread.currentThread().getName()
                   		+" has acquired LockCustom.");
      		
      		try {
             		Thread.sleep(5000);
             		System.out.println(Thread.currentThread().getName()
                          		+" is sleeping.");
      		} catch (InterruptedException e) {
             		e.printStackTrace();
      		}
 
      		System.out.println(Thread.currentThread().getName()
                   		+" has released LockCustom.");
      		
      		
      		lockCustom.unlock();
   	}
}