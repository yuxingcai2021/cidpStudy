package com.bmw.seed.service.impl;

public class Test {
    public static void main(String[] args) {
        Test1 t=new Test3();
        t.dosome();
        System.out.println("������");
    }


}
class Test1{
    public Test1(){
        System.out.println("����=====test1");
    }

    public void dosome(){
        System.out.println("do=======test1");
    }
}

class Test2 extends Test1{
    public  Test2(){
        System.out.println("����=====test2");
    }

    public void dosome(){
        System.out.println("do==========test2");
    }
}

class Test3 extends Test2{
    public Test3(){
        System.out.println("����=====test3");
    }
    public void dosome(){
        System.out.println("do==========test3");
    }

}
