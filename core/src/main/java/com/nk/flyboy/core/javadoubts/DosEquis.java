package com.nk.flyboy.core.javadoubts;

/**
 * Created on 2017/5/26.
 *
 *每一个表达式的第二个和第三个操作数的类型都不相同:x 是 char 类型的，而 0 和 i 都是 int 类型的。
 * 就像在谜题 5 的解答中提到的，混合类型的计算会引
 *起混乱，而这一点比在条件表达式中比在其它任何地方都表现得更明显。
 *
 * 操作数类型要保持一致
 */
public class DosEquis {

    public static void main(String[] args) {
        char x='x';
        int i=0;

        System.out.println(true?x:0);
        System.out.println(false?i:x);
    }
}
