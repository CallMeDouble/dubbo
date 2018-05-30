package com.dragon.pattern.Decorator;

/**
 * Created by zhushuanglong on 2017/12/13.
 */
public class ConcreteComponent extends Component {
    @Override
    public void decorate() {
        System.out.println("要开始刷墙了");
    }
}
