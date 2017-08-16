package com.cnct.genericparadigm;


/**
 * ������, ʹ�÷��͵����
 * @author yangdi
 *
 */
public class UseFx<T> {

	private T t;

	public UseFx(T t) {
		this.t = t;
	}
	
	public T getT(){
		return this.t;
	}
	
	public void showType(){
		System.out.println("T���� �� "+t.getClass().getSimpleName());
	}
	
	
}
