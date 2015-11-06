package com.hm.sample.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;
import org.springframework.util.Assert;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BeanUtils extends org.springframework.beans.BeanUtils{
	public static void copyPropertiesIgnoreNullValue(Object source, Object target,String... ignoreProperties)
			throws BeansException {
		Assert.notNull(source, "Source must not be null");
		Assert.notNull(target, "Target must not be null");
		Class<?> actualEditable = target.getClass();
		PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);
		List<String> ignoreList = (ignoreProperties != null ? Arrays.asList(ignoreProperties) : null);
		for (PropertyDescriptor targetPd : targetPds) {
			Method writeMethod = targetPd.getWriteMethod();
			if (writeMethod != null && (ignoreList == null || !ignoreList.contains(targetPd.getName()))) {
				PropertyDescriptor sourcePd = getPropertyDescriptor(
						source.getClass(), targetPd.getName());
				if (sourcePd != null && sourcePd.getReadMethod() != null) {
					try {
						Method readMethod = sourcePd.getReadMethod();
						if (!Modifier.isPublic(readMethod.getDeclaringClass()
								.getModifiers())) {
							readMethod.setAccessible(true);
						}
						Object value = readMethod.invoke(source);
						// 这里判断以下value是否为空 当然这里也能进行一些特殊要求的处理 例如绑定时格式转换等等
						if (value != null) {
							if (!Modifier.isPublic(writeMethod
									.getDeclaringClass().getModifiers())) {
								writeMethod.setAccessible(true);
							}
							writeMethod.invoke(target, value);
						}
					} catch (Throwable ex) {
						throw new FatalBeanException(
								"Could not copy properties from source to target",
								ex);
					}
				}
			}
		}
	}
	public static void copyPropertiesIgnoreNullValue(Object source, Object target)
			throws BeansException {
		copyPropertiesIgnoreNullValue(source, target, (String[]) null);
	}

	public static Map<String, String> getEntityPrimaryKeyAndColumnAnnotationsMappping(Class<?> classz) {
		// 获取所有属性
		Field[] files = classz.getDeclaredFields();
		HashMap<String, String> mapping = new HashMap<String, String>();
		for (int i = 0; i < files.length; i++) {
			// 获取所有方法信息
			Field filed = files[i];
			String field = filed.getName();
			Annotation annotation = filed.getAnnotation(PrimaryKeyColumn.class);
			if (annotation != null) {
				PrimaryKeyColumn primaryKeyColumn = (PrimaryKeyColumn) annotation;
				mapping.put(field, primaryKeyColumn.name());
			} else {
				annotation = filed.getAnnotation(Column.class);
				if (annotation != null) {
					Column column = (Column) annotation;
					mapping.put(field, column.value());
				}
			}
		}
		return mapping;
	}

	public static Map<String, String> getEntityPrimaryKeyAnnotationsMappping(Class<?> classz) {
		// 获取所有属性
		Field[] files = classz.getDeclaredFields();
		HashMap<String, String> primaryKeyMapping = new HashMap<String, String>();
		try{
			for (int i = 0; i < files.length; i++) {
				// 获取所有方法信息
				Field filed = files[i];
				String field = filed.getName();
				Annotation annotation = filed.getAnnotation(PrimaryKeyColumn.class);
				if (annotation != null) {
					PrimaryKeyColumn primaryKeyColumn = (PrimaryKeyColumn) annotation;
					primaryKeyMapping.put(field, primaryKeyColumn.name());
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return primaryKeyMapping;
	}
}
