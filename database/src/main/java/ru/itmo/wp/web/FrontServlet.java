package ru.itmo.wp.web;

import com.google.common.base.Strings;
import freemarker.template.*;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.web.exception.NotFoundException;
import ru.itmo.wp.web.exception.RedirectException;
import ru.itmo.wp.web.page.IndexPage;
import ru.itmo.wp.web.page.NotFoundPage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class FrontServlet extends HttpServlet {
    private static final String BASE_PAGE_PACKAGE = FrontServlet.class.getName().substring(
            0, FrontServlet.class.getName().length() - FrontServlet.class.getSimpleName().length()
    ) + "page";

    private Configuration sourceFreemarkerConfiguration;
    private Configuration targetFreemarkerConfiguration;

    private Configuration newFreemarkerConfiguration(
            String templateDirName, boolean debug) throws ServletException {
        File templateDir = new File(templateDirName);
        if (!templateDir.isDirectory()) {
            return null;
        }

        Configuration configuration = new Configuration(Configuration.VERSION_2_3_29);
        try {
            configuration.setDirectoryForTemplateLoading(templateDir);
        } catch (IOException e) {
            throw new ServletException("Can't create freemarker configuration [templateDir=" + templateDir + "].", e);
        }

        configuration.setDefaultEncoding(StandardCharsets.UTF_8.name());
        configuration.setTemplateExceptionHandler(debug
                ? TemplateExceptionHandler.HTML_DEBUG_HANDLER
                : TemplateExceptionHandler.RETHROW_HANDLER);
        configuration.setLogTemplateExceptions(false);
        configuration.setWrapUncheckedExceptions(true);
        configuration.setFallbackOnNullLoopVariable(false);

        return configuration;
    }

    @Override
    public void init() throws ServletException {
        sourceFreemarkerConfiguration = newFreemarkerConfiguration(getServletContext().getRealPath(".") + "/../../src/main/webapp/WEB-INF/templates", true);
        targetFreemarkerConfiguration = newFreemarkerConfiguration(getServletContext().getRealPath("WEB-INF/templates"), false);
    }

    private Template newTemplate(String name) throws ServletException {
        Template template = null;
        try {
            template = sourceFreemarkerConfiguration.getTemplate(name);
        } catch (TemplateNotFoundException ignored) {
            // No operations.
        } catch (IOException e) {
            throw new ServletException("Can't load template [name=" + name + "].", e);
        }

        if (template == null) {
            try {
                template = targetFreemarkerConfiguration.getTemplate(name);
            } catch (TemplateNotFoundException ignored) {
                // No operations.
            } catch (IOException e) {
                throw new ServletException("Can't load template [name=" + name + "].", e);
            }
        }

        if (template == null) {
            throw new ServletException("Unable to find template [template=" + name + "].");
        }

        return template;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding(StandardCharsets.UTF_8.name());
        process(request, response);
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Route route = Route.newRoute(request);
        try {
            process(route, request, response);
        } catch (NotFoundException e) {
            try {
                process(Route.newNotFoundRoute(), request, response);
            } catch (NotFoundException e1) {
                throw new ServletException(e1);
            }
        }
    }

    private void process(Route route,
                         HttpServletRequest request,
                         HttpServletResponse response) throws NotFoundException, ServletException, IOException {
        Class<?> pageClass;
        try {
            pageClass = Class.forName(route.getClassName());
        } catch (ClassNotFoundException e) {
            throw new NotFoundException();
        }

        Object page;
        try {
            //noinspection deprecation
            page = pageClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new ServletException("Can't create page [pageClass=" + pageClass + "].", e);
        }

        // before open
        Method method = findMethodByName(pageClass, "before");

        if (method == null) {
            throw new NotFoundException();
        }

        Map<String, Object> view = new HashMap<>();

        if (!invokeMethod(page, pageClass, method, request, view, response)) {
            return;
        }
        // before close
        // action open
        method = findMethodByName(pageClass, route.getAction());

        if (method == null) {
            throw new NotFoundException();
        }

        if (!invokeMethod(page, pageClass, method, request, view, response)) {
            return;
        }
        // action close
        // after open
        method = findMethodByName(pageClass, "after");

        if (method == null) {
            throw new NotFoundException();
        }

        if (!invokeMethod(page, pageClass, method, request, view, response)) {
            return;
        }
        // after close

        Template template = newTemplate(pageClass.getSimpleName() + ".ftlh");

        response.setContentType("text/html");
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        try {
            template.process(view, response.getWriter());
        } catch (TemplateException e) {
            throw new ServletException("Can't render template [pageClass=" + pageClass + ", method=" + method + "].", e);
        }
    }

    private Method findMethodByName(Class<?> pageClass, String name) {
        Method method = null;
        for (Class<?> clazz = pageClass; method == null && clazz != null; clazz = clazz.getSuperclass()) {
            Method[] methods = clazz.getDeclaredMethods();
            for (Method m : methods) {
                if (m.getName().equals(name)) {
                    method = m;
                    break;
                }
            }
        }
        return method;
    }

    private boolean invokeMethod(Object page, Class<?> pageClass, Method method, HttpServletRequest request,
                                 Map<String, Object> view, HttpServletResponse response) throws ServletException, IOException {
        try {
            method.setAccessible(true);
            switch (method.getParameterTypes().length) {
                case 0: {
                    method.invoke(page);
                    break;
                }
                case 1: {
                    if (method.getParameterTypes()[0].equals(Map.class)) {
                        method.invoke(page, view);
                    } else if (method.getParameterTypes()[0].equals(HttpServletRequest.class)) {
                        method.invoke(page, request);
                    }
                    break;
                }
                case 2: {
                    if (method.getParameterTypes()[0].equals(Map.class)) {
                        method.invoke(page, view, request);
                    } else {
                        method.invoke(page, request, view);
                    }
                    break;
                }
                default: {
                    throw new ServletException("Too many arguments [pageClass=" + pageClass + ", method=" + method + "]");
                }
            }

        } catch (IllegalAccessException e) {
            throw new ServletException("Unable to run action [pageClass=" + pageClass + ", method=" + method + "].", e);
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof RedirectException) {
                RedirectException redirectException = (RedirectException) cause;
                response.sendRedirect(redirectException.getLocation());
                return false;
            } else if (cause instanceof ValidationException) {
                ValidationException validationException = (ValidationException) cause;

                view.put("error", validationException.getMessage());
                for (Map.Entry<String, String[]> param : request.getParameterMap().entrySet()) {
                    String key = param.getKey();
                    if (param.getValue() != null && param.getValue().length == 1) {
                        String value = param.getValue()[0];
                        view.put(key, value);
                    }
                }
            } else {
                throw new ServletException("Unable to run action [pageClass=" + pageClass + ", method=" + method + "].", e);
            }
        }
        return true;
    }

    private static final class Route {
        private static final String DEFAULT_ACTION = "action";

        private final String className;
        private final String action;

        private Route(String className, String action) {
            this.className = className;
            this.action = action;
        }

        private static Route newNotFoundRoute() {
            return new Route(
                    NotFoundPage.class.getName(),
                    DEFAULT_ACTION
            );
        }

        private static Route newIndexRoute() {
            return new Route(
                    IndexPage.class.getName(),
                    DEFAULT_ACTION
            );
        }

        private String getClassName() {
            return className;
        }

        private String getAction() {
            return action;
        }

        private static Route newRoute(HttpServletRequest request) {
            String uri = request.getRequestURI();

            StringBuilder className = new StringBuilder(BASE_PAGE_PACKAGE);
            Arrays.stream(uri.split("/")).filter(s -> !s.isEmpty()).forEach(s -> {
                className.append('.');
                className.append(s);
            });

            if (className.toString().equals(BASE_PAGE_PACKAGE)) {
                return newIndexRoute();
            }

            int lastPeriodPos = className.lastIndexOf(".");
            className.setCharAt(lastPeriodPos + 1, Character.toUpperCase(className.charAt(lastPeriodPos + 1)));
            className.append("Page");

            String action = request.getParameter("action");
            if (Strings.isNullOrEmpty(action)) {
                action = DEFAULT_ACTION;
            }

            return new Route(className.toString(), action);
        }
    }
}
