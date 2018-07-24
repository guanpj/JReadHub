package com.jeez.guanpj.jreadhub.module.rule;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class MyRule implements TestRule {
    @Override
    public Statement apply(Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                String method = description.getMethodName();
                System.out.print(method + "测试开始");
                base.evaluate();
                System.out.print(method + "测试结束");
            }
        };
    }
}
