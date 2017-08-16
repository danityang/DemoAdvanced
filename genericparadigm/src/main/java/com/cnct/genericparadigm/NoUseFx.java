package com.cnct.genericparadigm;


/**
 * ��ʹ�÷��͵����
 * @author yangdi
 *
 */
public class NoUseFx {

	private Object t;

	public NoUseFx(Object t) {
		this.t = t;
	}

	public Object getT(){
		return this.t;
	}

	public void showType(){
		System.out.println("T���� �� "+t.getClass().getSimpleName());
	}

}
