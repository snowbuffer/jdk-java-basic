
/**
 * AOP创建代理主流程：
 * <p>
 * AbstractAutoProxyCreator
 * // 定义创建bean proxy流程(在哪个阶段拦截，是否需要代理)
 * 抽象方法：getAdvicesAndAdvisorsForBean
 * <p>
 * AbstractAdvisorAutoProxyCreator
 * // 委托BeanFactoryAdvisorRetrievalHelper 搜索所有的Advisor bean 并提供 isEligibleBean 方法用于过滤符合条件的Advisor bean, 默认实现 全部
 * <p>
 * 子类：DefaultAdvisorAutoProxyCreator
 * // 重写 isEligibleBean 方法 ，只查找出符合固定格式的beanName 的 Advisor bean
 * <p>
 * 子类：AspectJAwareAdvisorAutoProxyCreator
 * // 重写 shouldSkip 方法(原理跟重写 isEligibleBean 方法类似)， 只查找出非AspectJPointcutAdvisor类型(用户可能自己配置，无需另外代理) 的 Advisor bean
 * <p>
 * 子类：
 * AnnotationAwareAspectJAutoProxyCreator
 * // 重写 findCandidateAdvisors 方法， 在原有的基础上，追加@Apsect管理的Advisor
 * <p>
 * // 以上只是搜索出各个子类感兴趣的Advisor bean， 但不代表就一定能够应用到当前的bean
 * // 因此，紧接着需要确定Advisor bean 中哪些可以应用到bean上
 * AbstractAdvisorAutoProxyCreator#findEligibleAdvisors：
 * protected List<Advisor> findEligibleAdvisors(Class<?> beanClass, String beanName) {
 * List<Advisor> candidateAdvisors = findCandidateAdvisors(); // 查出所有感兴趣的advisor bean
 * List<Advisor> eligibleAdvisors = findAdvisorsThatCanApply(candidateAdvisors, beanClass, beanName); // 筛选可以应用到bean 的 Advisor
 * extendAdvisors(eligibleAdvisors);
 * if (!eligibleAdvisors.isEmpty()) {
 * eligibleAdvisors = sortAdvisors(eligibleAdvisors);
 * }
 * }
 * 最终：
 * AopUtils#canApply：
 * public static boolean canApply(Pointcut pc, Class<?> targetClass, boolean hasIntroductions) {
 * Assert.notNull(pc, "Pointcut must not be null");
 * if (!pc.getClassFilter().matches(targetClass)) {
 * return false;
 * }
 * <p>
 * MethodMatcher methodMatcher = pc.getMethodMatcher();
 * if (methodMatcher == MethodMatcher.TRUE) {
 * // No need to iterate the methods if we're matching any method anyway...
 * return true;
 * }
 * <p>
 * IntroductionAwareMethodMatcher introductionAwareMethodMatcher = null;
 * if (methodMatcher instanceof IntroductionAwareMethodMatcher) {
 * introductionAwareMethodMatcher = (IntroductionAwareMethodMatcher) methodMatcher;
 * }
 * <p>
 * Set<Class<?>> classes = new LinkedHashSet<Class<?>>(ClassUtils.getAllInterfacesForClassAsSet(targetClass));
 * classes.add(targetClass);
 * for (Class<?> clazz : classes) {
 * Method[] methods = ReflectionUtils.getAllDeclaredMethods(clazz);
 * for (Method method : methods) {
 * if ((introductionAwareMethodMatcher != null &&
 * introductionAwareMethodMatcher.matches(method, targetClass, hasIntroductions)) ||
 * methodMatcher.matches(method, targetClass)) {
 * // 只有bean 中有一个方法匹配了PointcutAdvisor,那么就为这个bean 生成 aop proxy
 * return true;
 * }
 * }
 * }
 * <p>
 * return false;
 * }
 * 执行目标方法 首次动态确定是否需要应用拦截器chain
 * JdkDynamicAopProxy 执行目标方法：
 * JdkDynamicAopProxy#invoke
 * 首先查询目标方法上是否存在interceptor(可以理解成advice)
 * List<Object> chain = this.advised.getInterceptorsAndDynamicInterceptionAdvice(method, targetClass);
 * -> DefaultAdvisorChainFactory#getInterceptorsAndDynamicInterceptionAdvice
 * // 以下代码有删减
 * for (Advisor advisor : config.getAdvisors()) {
 * if (advisor instanceof PointcutAdvisor) {
 * PointcutAdvisor pointcutAdvisor = (PointcutAdvisor) advisor;
 * if (config.isPreFiltered() || pointcutAdvisor.getPointcut().getClassFilter().matches(actualClass)) {
 * MethodMatcher mm = pointcutAdvisor.getPointcut().getMethodMatcher();
 * if (MethodMatchers.matches(mm, method, actualClass, hasIntroductions)) {
 * MethodInterceptor[] interceptors = registry.getInterceptors(advisor);
 * if (mm.isRuntime()) {
 * for (MethodInterceptor interceptor : interceptors) {
 * interceptorList.add(new InterceptorAndDynamicMethodMatcher(interceptor, mm));
 * }
 * }
 * else {
 * interceptorList.addAll(Arrays.asList(interceptors));
 * }
 * }
 * }
 * }
 * else {
 * Interceptor[] interceptors = registry.getInterceptors(advisor);
 * interceptorList.addAll(Arrays.asList(interceptors));
 * }
 * }
 * 其次将List<Object> chain(这个chain会被缓存，下次调用同一个方法时，直接从缓存中取) 委托给ReflectiveMethodInvocation执行
 * ReflectiveMethodInvocation#proceed
 * <p>
 * <p>
 * Note: CglibAopProxy 原理类似，详见：org.springframework.aop.framework.CglibAopProxy.DynamicAdvisedInterceptor.intercept
 */

/**  执行目标方法 首次动态确定是否需要应用拦截器chain
 JdkDynamicAopProxy 执行目标方法：
 JdkDynamicAopProxy#invoke
 首先查询目标方法上是否存在interceptor(可以理解成advice)
 List<Object> chain = this.advised.getInterceptorsAndDynamicInterceptionAdvice(method, targetClass);
 -> DefaultAdvisorChainFactory#getInterceptorsAndDynamicInterceptionAdvice
 // 以下代码有删减
 for (Advisor advisor : config.getAdvisors()) {
 if (advisor instanceof PointcutAdvisor) {
 PointcutAdvisor pointcutAdvisor = (PointcutAdvisor) advisor;
 if (config.isPreFiltered() || pointcutAdvisor.getPointcut().getClassFilter().matches(actualClass)) {
 MethodMatcher mm = pointcutAdvisor.getPointcut().getMethodMatcher();
 if (MethodMatchers.matches(mm, method, actualClass, hasIntroductions)) {
 MethodInterceptor[] interceptors = registry.getInterceptors(advisor);
 if (mm.isRuntime()) {
 for (MethodInterceptor interceptor : interceptors) {
 interceptorList.add(new InterceptorAndDynamicMethodMatcher(interceptor, mm));
 }
 }
 else {
 interceptorList.addAll(Arrays.asList(interceptors));
 }
 }
 }
 }
 else {
 Interceptor[] interceptors = registry.getInterceptors(advisor);
 interceptorList.addAll(Arrays.asList(interceptors));
 }
 }
 其次将List<Object> chain(这个chain会被缓存，下次调用同一个方法时，直接从缓存中取) 委托给ReflectiveMethodInvocation执行
 ReflectiveMethodInvocation#proceed


 Note: CglibAopProxy 原理类似，详见：org.springframework.aop.framework.CglibAopProxy.DynamicAdvisedInterceptor.intercept
 */