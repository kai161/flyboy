package com.nk.flyboy.core.design_pattern.interpreter;

/**
 * Created on 2017/3/29.
 *
 * 解释器模式
 * 优点：
 *  1、可扩展性比较好，灵活。 2、增加了新的解释表达式的方式。 3、易于实现简单文法。
 * 缺点：
 *  1、可利用场景比较少。 2、对于复杂的文法比较难维护。 3、解释器模式会引起类膨胀。 4、解释器模式采用递归调用方法。
 * 使用场景：
 *  1、可以将一个需要解释执行的语言中的句子表示为一个抽象语法树。 2、一些重复出现的问题可以用一种简单的语言来进行表达。 3、一个简单语法需要解释的场景。
 */
public class Test {

    public static Expression getMaleExpression(){
        Expression robot=new TerminalExpression("robot");
        Expression john=new TerminalExpression("john");

        return new OrExpression(robot,john);
    }

    public static Expression getMarriedWomanExpression(){
        Expression julie=new TerminalExpression("julie");
        Expression married=new TerminalExpression("married");

        return new AndExpression(julie,married);
    }

    public static void main(String[] args) {
        Expression isMale=getMaleExpression();
        Expression isMarriedWeman=getMarriedWomanExpression();

        System.out.println("john is male :"+isMale.interpret("john"));

        System.out.println("julie is a married woman :"+isMarriedWeman.interpret("married julie"));
    }
}
