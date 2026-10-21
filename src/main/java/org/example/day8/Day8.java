package org.example.day8;

import org.example.day7.Rectangle;
import org.example.day7.Shape;

public class Day8 {
    public static void main(String[] args){
        try {
            Shape rectangle=new Rectangle(4,5);
            System.out.println(rectangle.describe());
            Shape invalidRectangle=new Rectangle(0,5);
            System.out.println(invalidRectangle.describe());
        }
        catch (IllegalArgumentException e){
            System.out.println("参数错误："+e.getMessage());
        }finally {
            System.out.println("图形宽高数值校验结束");
        }
        String text = "abc";
        try {
            int number = Integer.parseInt(text);
            System.out.println(number);
        } catch (NumberFormatException e) {
            System.out.println("输入不是合法数字：" + text);
        }
    }
}
//IllegalArgumentException 适合表示什么问题
//它表示：调用方法时传入了不合法、不合理或不可接受的参数。
//NumberFormatException 通常由什么代码触发
//但更具体地说，是由“把字符串转换成数字”的方法触发
//throw：真正抛出异常对象
//        throws：在方法声明上说明可能抛出异常