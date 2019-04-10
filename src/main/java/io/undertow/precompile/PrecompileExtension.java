package io.undertow.precompile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.AsyncContext;
import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpUpgradeHandler;
import javax.servlet.http.Part;

import io.undertow.server.handlers.resource.Resource;
import io.undertow.servlet.ServletExtension;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.spec.RequestDispatcherImpl;
import io.undertow.servlet.util.ImmediateInstanceFactory;

public class PrecompileExtension implements ServletExtension {
    public void handleDeployment(DeploymentInfo deploymentInfo, ServletContext servletContext) {
        try {
            Resource root = deploymentInfo.getResourceManager().getResource("");
            Set<String> paths = new HashSet<String>();
            handlePaths(root, paths);

            deploymentInfo.addListener(Servlets.listener(PreCompileListener.class,
            new ImmediateInstanceFactory<PreCompileListener>(new PreCompileListener(paths))));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void handlePaths(Resource root, Set<String> paths) {
        if (root.isDirectory()) {
            for (Resource i : root.list()) {
                handlePaths(i, paths);
            }
        } else {
            if (root.getName().endsWith(".jsp")) {
                paths.add(root.getPath());
            }
        }
    }

    public static class PreCompileListener implements ServletContextListener {

        private final Set<String> paths;

        public PreCompileListener(Set<String> paths) {
            this.paths = paths;
        }

        public void contextInitialized(ServletContextEvent sce) {
            try {
                for (String i : paths) {
                    RequestDispatcherImpl dispatcher = (RequestDispatcherImpl) sce.getServletContext().getRequestDispatcher(i + "?preCompile=true");
                    dispatcher.mock(new HttpServletRequest() {
                        public String getAuthType() {
                            return null;
                        }

                        public Cookie[] getCookies() {
                            return new Cookie[0];
                        }

                        public long getDateHeader(String name) {
                            return 0;
                        }

                        public String getHeader(String name) {
                            return null;
                        }

                        public Enumeration<String> getHeaders(String name) {
                            return null;
                        }

                        public Enumeration<String> getHeaderNames() {
                            return null;
                        }

                        public int getIntHeader(String name) {
                            return 0;
                        }

                        public String getMethod() {
                            return null;
                        }

                        public String getPathInfo() {
                            return null;
                        }

                        public String getPathTranslated() {
                            return null;
                        }

                        public String getContextPath() {
                            return null;
                        }

                        public String getQueryString() {
                            return null;
                        }

                        public String getRemoteUser() {
                            return null;
                        }

                        public boolean isUserInRole(String role) {
                            return false;
                        }

                        public Principal getUserPrincipal() {
                            return null;
                        }

                        public String getRequestedSessionId() {
                            return null;
                        }

                        public String getRequestURI() {
                            return null;
                        }

                        public StringBuffer getRequestURL() {
                            return null;
                        }

                        public String getServletPath() {
                            return null;
                        }

                        public HttpSession getSession(boolean create) {
                            return null;
                        }

                        public HttpSession getSession() {
                            return null;
                        }

                        public String changeSessionId() {
                            return null;
                        }

                        public boolean isRequestedSessionIdValid() {
                            return false;
                        }

                        public boolean isRequestedSessionIdFromCookie() {
                            return false;
                        }

                        public boolean isRequestedSessionIdFromURL() {
                            return false;
                        }

                        public boolean isRequestedSessionIdFromUrl() {
                            return false;
                        }

                        public boolean authenticate(HttpServletResponse response) throws IOException, ServletException {
                            return false;
                        }

                        public void login(String username, String password) throws ServletException {

                        }

                        public void logout() throws ServletException {

                        }

                        public Collection<Part> getParts() throws IOException, ServletException {
                            return null;
                        }

                        public Part getPart(String name) throws IOException, ServletException {
                            return null;
                        }

                        public <T extends HttpUpgradeHandler> T upgrade(Class<T> handlerClass) throws IOException, ServletException {
                            return null;
                        }

                        public Object getAttribute(String name) {
                            return null;
                        }

                        public Enumeration<String> getAttributeNames() {
                            return null;
                        }

                        public String getCharacterEncoding() {
                            return null;
                        }

                        public void setCharacterEncoding(String env) throws UnsupportedEncodingException {

                        }

                        public int getContentLength() {
                            return 0;
                        }

                        public long getContentLengthLong() {
                            return 0;
                        }

                        public String getContentType() {
                            return null;
                        }

                        public ServletInputStream getInputStream() throws IOException {
                            return null;
                        }

                        public String getParameter(String name) {
                            return null;
                        }

                        public Enumeration<String> getParameterNames() {
                            return null;
                        }

                        public String[] getParameterValues(String name) {
                            return new String[0];
                        }

                        public Map<String, String[]> getParameterMap() {
                            return null;
                        }

                        public String getProtocol() {
                            return null;
                        }

                        public String getScheme() {
                            return null;
                        }

                        public String getServerName() {
                            return null;
                        }

                        public int getServerPort() {
                            return 0;
                        }

                        public BufferedReader getReader() throws IOException {
                            return null;
                        }

                        public String getRemoteAddr() {
                            return null;
                        }

                        public String getRemoteHost() {
                            return null;
                        }

                        public void setAttribute(String name, Object o) {

                        }

                        public void removeAttribute(String name) {

                        }

                        public Locale getLocale() {
                            return null;
                        }

                        public Enumeration<Locale> getLocales() {
                            return null;
                        }

                        public boolean isSecure() {
                            return false;
                        }

                        public RequestDispatcher getRequestDispatcher(String path) {
                            return null;
                        }

                        public String getRealPath(String path) {
                            return null;
                        }

                        public int getRemotePort() {
                            return 0;
                        }

                        public String getLocalName() {
                            return null;
                        }

                        public String getLocalAddr() {
                            return null;
                        }

                        public int getLocalPort() {
                            return 0;
                        }

                        public ServletContext getServletContext() {
                            return null;
                        }

                        public AsyncContext startAsync() throws IllegalStateException {
                            return null;
                        }

                        public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse) throws IllegalStateException {
                            return null;
                        }

                        public boolean isAsyncStarted() {
                            return false;
                        }

                        public boolean isAsyncSupported() {
                            return false;
                        }

                        public AsyncContext getAsyncContext() {
                            return null;
                        }

                        public DispatcherType getDispatcherType() {
                            return null;
                        }
                    }, new HttpServletResponse() {
                        public void addCookie(Cookie cookie) {

                        }

                        public boolean containsHeader(String name) {
                            return false;
                        }

                        public String encodeURL(String url) {
                            return null;
                        }

                        public String encodeRedirectURL(String url) {
                            return null;
                        }

                        public String encodeUrl(String url) {
                            return null;
                        }

                        public String encodeRedirectUrl(String url) {
                            return null;
                        }

                        public void sendError(int sc, String msg) throws IOException {

                        }

                        public void sendError(int sc) throws IOException {

                        }

                        public void sendRedirect(String location) throws IOException {

                        }

                        public void setDateHeader(String name, long date) {

                        }

                        public void addDateHeader(String name, long date) {

                        }

                        public void setHeader(String name, String value) {

                        }

                        public void addHeader(String name, String value) {

                        }

                        public void setIntHeader(String name, int value) {

                        }

                        public void addIntHeader(String name, int value) {

                        }

                        public void setStatus(int sc) {

                        }

                        public void setStatus(int sc, String sm) {

                        }

                        public int getStatus() {
                            return 0;
                        }

                        public String getHeader(String name) {
                            return null;
                        }

                        public Collection<String> getHeaders(String name) {
                            return null;
                        }

                        public Collection<String> getHeaderNames() {
                            return null;
                        }

                        public String getCharacterEncoding() {
                            return null;
                        }

                        public String getContentType() {
                            return null;
                        }

                        public ServletOutputStream getOutputStream() throws IOException {
                            return null;
                        }

                        public PrintWriter getWriter() throws IOException {
                            return null;
                        }

                        public void setCharacterEncoding(String charset) {

                        }

                        public void setContentLength(int len) {

                        }

                        public void setContentLengthLong(long len) {

                        }

                        public void setContentType(String type) {

                        }

                        public void setBufferSize(int size) {

                        }

                        public int getBufferSize() {
                            return 0;
                        }

                        public void flushBuffer() throws IOException {

                        }

                        public void resetBuffer() {

                        }

                        public boolean isCommitted() {
                            return false;
                        }

                        public void reset() {

                        }

                        public void setLocale(Locale loc) {

                        }

                        public Locale getLocale() {
                            return null;
                        }
                    });
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
