package xyz.spc.common.util.beanUtil;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 反射工具类合集. 提供调用getter/setter方法, 访问私有变量, 调用私有方法, 获取泛型类型Class, 被AOP过的真实类等工具函数.
 */
@Slf4j
@SuppressWarnings("rawtypes,unchecked")
public final class ReflectUtil {

    private static final String SETTER_PREFIX = "set";

    private static final String GETTER_PREFIX = "get";

    /**
     * CGLIB 的类名分隔符
     */
    private static final String CGLIB_CLASS_SEPARATOR = "$$";


    /**
     * 转换为Map
     */
    public static Map<String, Object> beanToMap(Object bean) {
        PropertyDescriptor[] propertyDescriptorArray = getPropertyDescriptorArray(bean);
        Map<String, Object> parameterMap = new HashMap<String, Object>();
        for (PropertyDescriptor propertyDescriptor : propertyDescriptorArray) {
            Object value = getPropertyDescriptorValue(bean, propertyDescriptor);
            parameterMap.put(propertyDescriptor.getName(), value);
        }
        return parameterMap;
    }

    /**
     * 通过反射设置属性
     */
    public static void setPropertie(Object bean, String key, Object value) {
        if (null != bean && StringUtils.isNotEmpty(key)) {
            PropertyDescriptor[] descriptor = getPropertyDescriptorArray(bean);
            PropertyDescriptor propertyDescriptor = getPropertyDescriptor(descriptor, key);
            setPropertyDescriptorValue(bean, propertyDescriptor, value);
        }
    }

    /**
     * 通过反射设置属性
     *
     * @param skipExist 是否跳过已存在的属性
     */
    public static void setPropertie(Object bean, String key, Object value, boolean skipExist) {
        if (null != bean && StringUtils.isNotEmpty(key)) {
            if (skipExist) {
                Object propValue = getPropertie(bean, key);
                if (null == propValue) {
                    setPropertie(bean, key, value);
                }
            } else {
                setPropertie(bean, key, value);
            }
        }
    }


    /**
     * 通过反射将map的key value 映射到实体类中
     *
     * @param skipExist 是否跳过已存在的属性
     */
    public static void setPropertie(Object bean, Map<String, Object> parameterMap, boolean skipExist) {
        if (null != bean && null != parameterMap && !parameterMap.isEmpty()) {
            for (Map.Entry<String, Object> entry : parameterMap.entrySet()) {
                setPropertie(bean, entry.getKey(), entry.getValue());
            }
        }

    }


    /**
     * 获取属性的值
     */
    public static Object getPropertie(Object bean, String key) {
        Object value = null;
        if (null != bean && StringUtils.isNotEmpty(key)) {
            PropertyDescriptor[] descriptor = getPropertyDescriptorArray(bean);
            PropertyDescriptor propertyDescriptor = getPropertyDescriptor(descriptor, key);
            value = getPropertyDescriptorValue(bean, propertyDescriptor);
        }
        return value;
    }


    public static Object getPropertyDescriptorValue(Object bean, PropertyDescriptor propertyDescriptor) {
        Object value = null;
        if (null != propertyDescriptor) {
            Method readMethod = propertyDescriptor.getReadMethod();
            value = invok(readMethod, bean, propertyDescriptor.getPropertyType(), null);
        }
        return value;
    }


    public static void setPropertyDescriptorValue(Object bean, PropertyDescriptor propertyDescriptor, Object value) {
        if (null != propertyDescriptor) {
            Method writeMethod = propertyDescriptor.getWriteMethod();
            invok(writeMethod, bean, propertyDescriptor.getPropertyType(), value);
        }
    }

    /**
     * 获取 PropertyDescriptor 属性
     */
    public static PropertyDescriptor getPropertyDescriptor(PropertyDescriptor[] propertyDescriptorArray, String key) {
        PropertyDescriptor propertyDescriptor = null;
        for (PropertyDescriptor descriptor : propertyDescriptorArray) {
            String fieldName = descriptor.getName();
            if (fieldName.equals(key)) {
                propertyDescriptor = descriptor;
                break;
            }
        }
        return propertyDescriptor;
    }


    /**
     * 获取 PropertyDescriptor 属性
     */
    public static PropertyDescriptor getPropertyDescriptor(Object bean, String key) {
        PropertyDescriptor[] propertyDescriptorArray = getPropertyDescriptorArray(bean);
        return getPropertyDescriptor(propertyDescriptorArray, key);
    }


    /**
     * invok 调用方法
     */
    public static Object invok(String methodName, Object bean, Class<?> targetType, Object value) {
        Object resultValue = null;
        if (StringUtils.isNotEmpty(methodName) && null != bean) {
            Method method = getMethod(bean.getClass(), methodName);
            if (null != method) {
                resultValue = invok(method, bean, targetType, value);
            }
        }
        return resultValue;
    }

    /**
     * 调用 invok 方法
     */
    public static Object invok(Method method, Object bean, Class<?> targetType, Object value) {
        //  System.out.println("method:" + method.getName() + "   bean:" + bean.getClass().getName() + "     " + value);
        Object resultValue = null;
        if (null != method && null != bean) {
            try {
                int count = method.getParameterCount();
                if (count >= 1) {
                    if (null != value) {
                        value = ConvertUtils.convert(value, targetType);
                    }
                    resultValue = method.invoke(bean, value);
                } else {
                    resultValue = method.invoke(bean);
                }

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return resultValue;
    }

    /**
     * 获取内省的属性
     */
    public static PropertyDescriptor[] getPropertyDescriptorArray(Object bean) {
        BeanInfo beanInfo = null;
        PropertyDescriptor[] propertyDescriptors = null;
        try {
            beanInfo = Introspector.getBeanInfo(bean.getClass());
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        if (null != beanInfo) {
            propertyDescriptors = beanInfo.getPropertyDescriptors();
        }
        return propertyDescriptors;
    }

    /**
     * 获取method 方法
     */
    private static Method getMethod(Class clazz, String methodName) {
        Method method = null;
        if (null != clazz) {
            try {
                method = clazz.getDeclaredMethod(methodName);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        return method;
    }

    private static Object getBean(Class clazz) {
        Object bean = null;
        if (null != clazz) {
            try {
                bean = clazz.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return bean;
    }

    /**
     * 同步 bean 中的数据
     */
    public static <T> void syncBeanData(T oldBean, T newBean) {
        PropertyDescriptor[] descriptorArray = getPropertyDescriptorArray(newBean);
        for (PropertyDescriptor propertyDescriptor : descriptorArray) {
            Object newValue = getPropertyDescriptorValue(newBean, propertyDescriptor);
            Object oldValue = getPropertyDescriptorValue(oldBean, propertyDescriptor);
            if (null == newValue && oldValue != null) {
                setPropertyDescriptorValue(newBean, propertyDescriptor, oldValue);
            }
        }
    }


    /**
     * 通过反射获取class字节码文件
     */
    public static Class getClassForName(String className) {
        Class clazz = null;
        if (StringUtils.isNotEmpty(className)) {
            try {
                clazz = Class.forName(className);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return clazz;
    }


    /**
     * 通过反射获取对象
     */
    public static Object getClassForBean(String className) {
        Object bean = null;
        Class clazz = getClassForName(className);
        if (null != clazz) {
            try {
                bean = clazz.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return bean;
    }

    /**
     * 获取属性字段的注解属性
     */
    public static Annotation[] getFieldAnnotations(Object bean, PropertyDescriptor propertyDescriptor) {
        List<Field> fieldList = Arrays.asList(bean.getClass().getDeclaredFields()).stream().filter(f -> f.getName().equals(propertyDescriptor.getName())).collect(Collectors.toList());
        if (null != fieldList && fieldList.size() > 0) {
            return fieldList.get(0).getDeclaredAnnotations();
        }
        return null;
    }

    /**
     * 获取属性字段的注解属性
     */
    public static Annotation[] getFieldAnnotations(Object bean, String key) {
        PropertyDescriptor propertyDescriptor = getPropertyDescriptor(bean, key);
        return getFieldAnnotations(bean, propertyDescriptor);
    }

    /**
     * 调用Getter方法.
     * 支持多级，如：对象名.对象名.方法
     */
    @SuppressWarnings("unchecked")
    public static <E> E invokeGetter(Object obj, String propertyName) {
        Object object = obj;
        for (String name : StringUtils.split(propertyName, ".")) {
            String getterMethodName = GETTER_PREFIX + StringUtils.capitalize(name);
            object = invokeMethod(object, getterMethodName, new Class[]{}, new Object[]{});
        }
        return (E) object;
    }

    /**
     * 调用Setter方法, 仅匹配方法名。
     * 支持多级，如：对象名.对象名.方法
     */
    public static <E> void invokeSetter(Object obj, String propertyName, E value) {
        Object object = obj;
        String[] names = StringUtils.split(propertyName, ".");
        for (int i = 0; i < names.length; i++) {
            if (i < names.length - 1) {
                String getterMethodName = GETTER_PREFIX + StringUtils.capitalize(names[i]);
                object = invokeMethod(object, getterMethodName, new Class[]{}, new Object[]{});
            } else {
                String setterMethodName = SETTER_PREFIX + StringUtils.capitalize(names[i]);
                invokeMethodByName(object, setterMethodName, new Object[]{value});
            }
        }
    }

    /**
     * 直接读取对象属性值, 无视private/protected修饰符, 不经过getter函数.
     */
    @SuppressWarnings("unchecked")
    public static <E> E getFieldValue(final Object obj, final String fieldName) {
        Field field = getAccessibleField(obj, fieldName);
        if (field == null) {
            log.debug("在 [" + obj.getClass() + "] 中，没有找到 [" + fieldName + "] 字段 ");
            return null;
        }
        E result = null;
        try {
            result = (E) field.get(obj);
        } catch (IllegalAccessException e) {
            log.error("不可能抛出的异常{}", e.getMessage());
        }
        return result;
    }

    /**
     * 直接设置对象属性值, 无视private/protected修饰符, 不经过setter函数.
     */
    public static <E> void setFieldValue(final Object obj, final String fieldName, final E value) {
        Field field = getAccessibleField(obj, fieldName);
        if (field == null) {
            // throw new IllegalArgumentException("在 [" + obj.getClass() + "] 中，没有找到 [" + fieldName + "] 字段 ");
            log.debug("在 [" + obj.getClass() + "] 中，没有找到 [" + fieldName + "] 字段 ");
            return;
        }
        try {
            field.set(obj, value);
        } catch (IllegalAccessException e) {
            log.error("不可能抛出的异常: {}", e.getMessage());
        }
    }

    /**
     * 直接调用对象方法, 无视private/protected修饰符.
     * 用于一次性调用的情况，否则应使用getAccessibleMethod()函数获得Method后反复调用.
     * 同时匹配方法名+参数类型，
     */
    public static <E> E invokeMethod(final Object obj, final String methodName, final Class<?>[] parameterTypes, final Object[] args) {
        if (obj == null || methodName == null) {
            return null;
        }
        Method method = getAccessibleMethod(obj, methodName, parameterTypes);
        if (method == null) {
            log.debug("在 [" + obj.getClass() + "] 中，没有找到 [" + methodName + "] 方法 ");
            return null;
        }
        try {
            return (E) method.invoke(obj, args);
        } catch (Exception e) {
            String msg = "method: " + method + ", obj: " + obj + ", args: " + args;
            throw convertReflectionExceptionToUnchecked(msg, e);
        }
    }

    /**
     * 直接调用对象方法, 无视private/protected修饰符，
     * 用于一次性调用的情况，否则应使用getAccessibleMethodByName()函数获得Method后反复调用.
     * 只匹配函数名，如果有多个同名函数调用第一个。
     */
    public static <E> E invokeMethodByName(final Object obj, final String methodName, final Object[] args) {
        Method method = getAccessibleMethodByName(obj, methodName, args.length);
        if (method == null) {
            // 如果为空不报错，直接返回空。
            log.debug("在 [" + obj.getClass() + "] 中，没有找到 [" + methodName + "] 方法 ");
            return null;
        }
        try {
            // 类型转换（将参数数据类型转换为目标方法参数类型）
            Class<?>[] cs = method.getParameterTypes();
            for (int i = 0; i < cs.length; i++) {
                if (args[i] != null && !args[i].getClass().equals(cs[i])) {
                    if (cs[i] == String.class) {
                        args[i] = ConvertUtil.toStr(args[i]);
                        if (StringUtils.endsWith((String) args[i], ".0")) {
                            args[i] = StringUtils.substringBefore((String) args[i], ".0");
                        }
                    } else if (cs[i] == Integer.class) {
                        args[i] = ConvertUtil.toInt(args[i]);
                    } else if (cs[i] == Long.class) {
                        args[i] = ConvertUtil.toLong(args[i]);
                    } else if (cs[i] == Double.class) {
                        args[i] = ConvertUtil.toDouble(args[i]);
                    } else if (cs[i] == Float.class) {
                        args[i] = ConvertUtil.toFloat(args[i]);
                    } else if (cs[i] == Date.class) {
                        args[i] = DateUtil.parseDate((CharSequence) args[i]);
                    } else if (cs[i] == boolean.class || cs[i] == Boolean.class) {
                        args[i] = ConvertUtil.toBool(args[i]);
                    }
                }
            }
            return (E) method.invoke(obj, args);
        } catch (Exception e) {
            String msg = "method: " + method + ", obj: " + obj + ", args: " + args;
            throw convertReflectionExceptionToUnchecked(msg, e);
        }
    }

    /**
     * 循环向上转型, 获取对象的DeclaredField, 并强制设置为可访问.
     * 如向上转型到Object仍无法找到, 返回null.
     */
    public static Field getAccessibleField(final Object obj, final String fieldName) {
        // 为空不报错。直接返回 null
        if (obj == null) {
            return null;
        }
        Validate.notBlank(fieldName, "fieldName can't be blank");
        for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
            try {
                Field field = superClass.getDeclaredField(fieldName);
                makeAccessible(field);
                return field;
            } catch (NoSuchFieldException e) {
                continue;
            }
        }
        return null;
    }

    /**
     * 循环向上转型, 获取对象的DeclaredMethod,并强制设置为可访问.
     * 如向上转型到Object仍无法找到, 返回null.
     * 匹配函数名+参数类型。
     * 用于方法需要被多次调用的情况. 先使用本函数先取得Method,然后调用Method.invoke(Object obj, Object... args)
     */
    public static Method getAccessibleMethod(final Object obj, final String methodName, final Class<?>... parameterTypes) {
        // 为空不报错。直接返回 null
        if (obj == null) {
            return null;
        }
        Validate.notBlank(methodName, "methodName can't be blank");
        for (Class<?> searchType = obj.getClass(); searchType != Object.class; searchType = searchType.getSuperclass()) {
            try {
                Method method = searchType.getDeclaredMethod(methodName, parameterTypes);
                makeAccessible(method);
                return method;
            } catch (NoSuchMethodException e) {
                continue;
            }
        }
        return null;
    }

    /**
     * 循环向上转型, 获取对象的DeclaredMethod,并强制设置为可访问.
     * 如向上转型到Object仍无法找到, 返回null.
     * 只匹配函数名。
     * 用于方法需要被多次调用的情况. 先使用本函数先取得Method,然后调用Method.invoke(Object obj, Object... args)
     */
    public static Method getAccessibleMethodByName(final Object obj, final String methodName, int argsNum) {
        // 为空不报错。直接返回 null
        if (obj == null) {
            return null;
        }
        Validate.notBlank(methodName, "methodName can't be blank");
        for (Class<?> searchType = obj.getClass(); searchType != Object.class; searchType = searchType.getSuperclass()) {
            Method[] methods = searchType.getDeclaredMethods();
            for (Method method : methods) {
                if (method.getName().equals(methodName) && method.getParameterTypes().length == argsNum) {
                    makeAccessible(method);
                    return method;
                }
            }
        }
        return null;
    }

    /**
     * 改变private/protected的方法为public，尽量不调用实际改动的语句，避免JDK的SecurityManager抱怨。
     */
    public static void makeAccessible(Method method) {
        if ((!Modifier.isPublic(method.getModifiers()) || !Modifier.isPublic(method.getDeclaringClass().getModifiers())) && !method.isAccessible()) {
            method.setAccessible(true);
        }
    }

    /**
     * 改变private/protected的成员变量为public，尽量不调用实际改动的语句，避免JDK的SecurityManager抱怨。
     */
    public static void makeAccessible(Field field) {
        if ((!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers()) || Modifier.isFinal(field.getModifiers())) && !field.isAccessible()) {
            field.setAccessible(true);
        }
    }

    /**
     * 通过反射, 获得Class定义中声明的泛型参数的类型, 注意泛型必须定义在父类处
     * 如无法找到, 返回Object.class.
     */
    @SuppressWarnings("unchecked")
    public static <T> Class<T> getClassGenricType(final Class clazz) {
        return getClassGenricType(clazz, 0);
    }

    /**
     * 通过反射, 获得Class定义中声明的父类的泛型参数的类型.
     * 如无法找到, 返回Object.class.
     */
    public static Class getClassGenricType(final Class clazz, final int index) {
        Type genType = clazz.getGenericSuperclass();

        if (!(genType instanceof ParameterizedType)) {
            log.debug(clazz.getSimpleName() + "'s superclass not ParameterizedType");
            return Object.class;
        }

        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

        if (index >= params.length || index < 0) {
            log.debug("Index: " + index + ", Size of " + clazz.getSimpleName() + "'s Parameterized Type: " + params.length);
            return Object.class;
        }
        if (!(params[index] instanceof Class)) {
            log.debug(clazz.getSimpleName() + " not set the actual class on superclass generic parameter");
            return Object.class;
        }

        return (Class) params[index];
    }

    public static Class<?> getUserClass(Object instance) {
        if (instance == null) {
            throw new RuntimeException("Instance must not be null");
        }
        Class clazz = instance.getClass();
        if (clazz != null && clazz.getName().contains(CGLIB_CLASS_SEPARATOR)) {
            Class<?> superClass = clazz.getSuperclass();
            if (superClass != null && !Object.class.equals(superClass)) {
                return superClass;
            }
        }
        return clazz;

    }

    /**
     * 将反射时的checked exception转换为unchecked exception.
     */
    public static RuntimeException convertReflectionExceptionToUnchecked(String msg, Exception e) {
        if (e instanceof IllegalAccessException || e instanceof IllegalArgumentException || e instanceof NoSuchMethodException) {
            return new IllegalArgumentException(msg, e);
        } else if (e instanceof InvocationTargetException) {
            return new RuntimeException(msg, ((InvocationTargetException) e).getTargetException());
        }
        return new RuntimeException(msg, e);
    }
}
