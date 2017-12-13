package com.doubledragon.pattern.Decorator;

/**
 * Created by zhushuanglong on 2017/12/13.
 */
public abstract class Decorator extends Component {
    private Component component;
    public Decorator(Component component){
        this.component = component;
    }

    @Override
    public void decorate() {
        this.component.decorate();
    }
}
