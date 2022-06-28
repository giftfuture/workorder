package com.xbhy.workorder.test;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class Test {


    private static Unsafe unsafe = null;
    private static Field getUnsafe = null;

    static {
        try {
            getUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            getUnsafe.setAccessible(true);
            unsafe = (Unsafe) getUnsafe.get(null);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    private final static int BYTE = 1;

    private long size;
    private long address;

    public Test(long size) {
        this.size = size;
        address = unsafe.allocateMemory(size * BYTE);
    }

    public void set(long i, byte value) {
        unsafe.putByte(address + i * BYTE, value);
    }

    public int get(long idx) {
        return unsafe.getByte(address + idx * BYTE);
    }

    public long size() {
        return size;
    }


    public static void main(String[] args) {
        long SUPER_SIZE = (long)Integer.MAX_VALUE * 2;
        Test array = new Test(SUPER_SIZE);
        System.out.println("Array size:" + array.size()); // 4294967294
        int sum=0;
        for (int i = 0; i < 100; i++) {
            array.set((long)Integer.MAX_VALUE + i, (byte)3);
            sum += array.get((long)Integer.MAX_VALUE + i);
        }
        System.out.println(sum);
    }



  //  public static void main(String[] args) {
    //    System.out.println(Integer.MAX_VALUE);
//        ServiceLoader<Search> s = ServiceLoader.load(Search.class);
//        Iterator<Search> iterator = s.iterator();
//        while (iterator.hasNext()) {
//            Search search =  iterator.next();
//            search.searchDoc("hello world");
//        }


 //   }

}
