package com.tools.kf.view.anotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
// 表示用在类,接口或者枚举上
@Retention(RetentionPolicy.RUNTIME)
public @interface ContentView {
	int value();
}
