package com.cnct.genericparadigm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO java泛型示例
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // TODO 初始泛型
    private static void firstLook(){
        // 理解泛型先从我们最熟悉的List集合开始
        List list1 = new ArrayList();
        // 上面的集合下面有黄线，提示未指定泛型
        // 加上泛型后的样子, 表示集合对象只能为String类型
        List<String> list2 = new ArrayList<String>();
        list2.add("item1");
        list2.add("item2");
        // 添加整形则会报错
        // list2.add(3);

        for (int i = 0; i < list2.size(); i++) {
            System.out.println(list2.get(i));
        }
    }

    /**
     * TODO 使用和不使用泛型
     */
    private static void fxClass(){
        UseFx<String> u1 = new UseFx<String>("Test");
        u1.showType();
        System.out.println(u1.getT());
        UseFx<Integer> u2 = new UseFx<Integer>(1000);
        u2.showType();
        System.out.println(u2.getT());
        System.out.println("****************************************");

        NoUseFx nu1 = new NoUseFx(1000);
        nu1.showType();
        System.out.println(nu1.getT());
        NoUseFx nu2= new NoUseFx("Test");
        nu2.showType();
        System.out.println(nu2.getT());
    }


    /**
     * TODO 泛型通配符
     * TODO 特指“？”
     */
    private void fxSymbol(){
        UseFx<String> u1 = new UseFx<String>("Test");
        getClassData(u1);
    }

    private void getClassData(UseFx<?> u){
        System.out.println("class type :" + u.getClass());
    }


    // TODO 泛型方法
    private void fxMethod(){
        fxMethod(1000);
        fxMethod("fgfd");
    }

    /**
     * TODO 泛型方法,当方法操作的引用数据类型不确定的时候，可以将泛型定义在方法上
     * TODO <T>是为了规范参数,T为返回值，(T t)为传参
     */
    private <T> T fxMethod(T t){
        System.out.println(t.getClass().getSimpleName());
        return t;
    }

    /**
     * TODO 泛型接口
     */
    private void fxJKou(){
        FxTClass ft = new  FxTClass();
        ft.get();
    }
}
