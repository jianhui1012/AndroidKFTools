package com.tools.kf.view.anotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
// 表示用在字段上
@Retention(RetentionPolicy.RUNTIME)
// 表示在生命周期是运行时
public @interface ViewInject {
	int value();

	int parentId() default 0;;
}