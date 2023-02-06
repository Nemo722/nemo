package com.nemo.spring.v1.init;

import com.nemo.spring.annotation.Autowired;
import com.nemo.spring.annotation.Controller;
import com.nemo.spring.annotation.RequestMapping;
import com.nemo.spring.annotation.Service;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author mingLong.zhao
 * 容器初始化
 */
public class DispatcherServlet extends HttpServlet {

    private Map<String, Object> mapping = new HashMap<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            doDispatch(req, resp);
        } catch (Exception e) {
            resp.getWriter().write("500 Exception " + Arrays.toString(e.getStackTrace()));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            doDispatch(req, resp);
        } catch (Exception e) {
            resp.getWriter().write("500 Exception " + Arrays.toString(e.getStackTrace()));
        }
    }

    private void doDispatch(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String url = req.getRequestURI();
        String contextPath = req.getContextPath();
        url = url.replace(contextPath, "").replaceAll("/+", "/");
        if (!this.mapping.containsKey(url)) {
            resp.getWriter().write("404 Not Found!!");
            return;
        }
        Method method = (Method)this.mapping.get(url);
        // 入参
        Map<String, String[]> params = req.getParameterMap();
        // 反射调用
        method.invoke(this.mapping.get(method.getDeclaringClass().getName()),
            new Object[] {req, resp, params.get("name")[0]});
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        // 读取配置文件
        // 扫描包
        doScanner("");
        // 遍历依赖注入
        try {
            // IOC
            for (String className : mapping.keySet()) {
                Class<?> clazz = Class.forName(className);
                // 如果被Controller修饰
                if (clazz.isAnnotationPresent(Controller.class)) {
                    RequestMapping requestMapping = clazz.getAnnotation(RequestMapping.class);
                    String baseUrl = requestMapping.value();
                    // Controller下的方法
                    Method[] methods = clazz.getMethods();
                    for (Method method : methods) {
                        // 方法如果被RequestMapping修饰
                        if (!method.isAnnotationPresent(RequestMapping.class)) {
                            continue;
                        }
                        RequestMapping methodRequestMapping = method.getAnnotation(RequestMapping.class);
                        // 拼接出url
                        String url = baseUrl + "/" + methodRequestMapping.value();
                        mapping.put(url, method);
                    }
                } else if (clazz.isAnnotationPresent(Service.class)) {
                    Service service = clazz.getAnnotation(Service.class);
                    String beanName = service.value();
                    if ("".equals(beanName)) {
                        beanName = clazz.getName();
                    }
                    Object instance = clazz.newInstance();
                    mapping.put(beanName, instance);
                    for (Class<?> i : clazz.getInterfaces()) {
                        mapping.put(i.getName(), instance);
                    }
                }
            }

            // 依赖注入
            for (Object object : mapping.values()) {
                if (object == null) {
                    continue;
                }
                Class clazz = object.getClass();
                if (clazz.isAnnotationPresent(Controller.class)) {
                    Field[] fields = clazz.getDeclaredFields();
                    // 属性注入
                    for (Field field : fields) {
                        if (!field.isAnnotationPresent(Autowired.class)) {
                            continue;
                        }
                        // 如果被Autowired修饰
                        Autowired autowired = field.getAnnotation(Autowired.class);
                        String beanName = autowired.value();
                        if ("".equals(beanName)) {
                            beanName = field.getType().getName();
                        }
                        field.setAccessible(true);
                        try {
                            field.set(mapping.get(clazz.getName()), mapping.get(beanName));
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 扫描包
     *
     * @param scanPackage 包名
     */
    private void doScanner(String scanPackage) {
        URL url = this.getClass().getClassLoader().getResource(scanPackage);
        File classDir = new File(url.getFile());
        for (File file : classDir.listFiles()) {
            if (file.isDirectory()) {
                doScanner(scanPackage + "/" + file.getName());
            } else {
                if (!file.getName().endsWith(".class")) {
                    continue;
                }
                String clazzName = scanPackage + "." + file.getName().replace(".class", "");
                mapping.put(clazzName, null);
            }
        }
    }
}
