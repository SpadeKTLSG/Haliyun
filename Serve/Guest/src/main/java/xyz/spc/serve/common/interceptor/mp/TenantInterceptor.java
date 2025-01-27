package xyz.spc.serve.common.interceptor.mp;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import xyz.spc.serve.common.context.CurrentTenantContext;

public class TenantInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 从请求中获取租户ID（例如从请求头中获取）
        Long tenantId = Long.valueOf(request.getHeader("Tenant-Id"));
        // 设置当前租户上下文
        CurrentTenantContext.setTenantId(tenantId);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 方法执行后清除当前租户上下文
        CurrentTenantContext.clear();
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 请求完成后清除当前租户上下文
        CurrentTenantContext.clear();
    }
}
