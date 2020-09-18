/**
 * bean创建过程中的生命周期
 * 1. 解析beanClass
 * 2. 实例化bean
 * InstantiationAwareBeanPostProcessor ibp = (InstantiationAwareBeanPostProcessor) bp;
 * Object bean = ibp.postProcessBeforeInstantiation(beanClass, beanName);
 * if (bean != null) {
 * bean = processor.postProcessAfterInitialization(result, beanName);
 * }
 * if (bean != null) {
 * return bean;  // 整个bean生命周期流程结束
 * }
 * <p>
 * // 如果是工厂方法方式
 * if (mbd.getFactoryMethodName() != null) {
 * return instantiateUsingFactoryMethod(beanName, mbd, args);
 * -> BeanWrapperImpl bw = new BeanWrapperImpl();
 * -> this.beanFactory.initBeanWrapper(bw);
 * }
 * <p>
 * SmartInstantiationAwareBeanPostProcessor ibp = (SmartInstantiationAwareBeanPostProcessor) bp;
 * Constructor<?>[] ctors = ibp.determineCandidateConstructors(beanClass, beanName);
 * if (ctors != null) {
 * autowireConstructor
 * BeanWrapperImpl bw = new BeanWrapperImpl();
 * this.beanFactory.initBeanWrapper(bw);
 * -> bw.setConversionService(getConversionService());
 * -> registerCustomEditors(bw);
 * } else {
 * instantiateBean
 * BeanWrapper bw = new BeanWrapperImpl(beanInstance);
 * initBeanWrapper(bw);
 * -> bw.setConversionService(getConversionService());
 * -> registerCustomEditors(bw);
 * }
 * <p>
 * <p>
 * 3. 合并beanDefinition
 * MergedBeanDefinitionPostProcessor bdp = (MergedBeanDefinitionPostProcessor) bp;
 * bdp.postProcessMergedBeanDefinition(mbd, beanType, beanName);
 * <p>
 * 4. 早期实例暴露(解决循环依赖)
 * addSingletonFactory(beanName, new ObjectFactory<Object>() {
 *
 * @Override public Object getObject() throws BeansException {
 * return getEarlyBeanReference(beanName, mbd, bean);
 * }
 * });
 * <p>
 * 5. 填充bean属性
 * InstantiationAwareBeanPostProcessor ibp = (InstantiationAwareBeanPostProcessor) bp;
 * if (!ibp.postProcessAfterInstantiation(bw.getWrappedInstance(), beanName)) {
 * continueWithPropertyPopulation = false;
 * break;
 * }
 * <p>
 * if (!continueWithPropertyPopulation) {
 * return; // 填充bean属性阶段结束，不设置属性值，但bean的生命周期还是会往步骤6走
 * }
 * <p>
 * // <bean class="xxx" autowire="byName/>
 * if (mbd.getResolvedAutowireMode() == RootBeanDefinition.AUTOWIRE_BY_NAME) {
 * autowireByName(beanName, mbd, bw, newPvs);
 * -> for (String propertyName : propertyNames) {
 * if (containsBean(propertyName)) {
 * Object bean = getBean(propertyName);
 * pvs.add(propertyName, bean);
 * registerDependentBean(propertyName, beanName);
 * }
 * }
 * }
 * <p>
 * // <bean class="xxx" autowire="byType/>
 * if (mbd.getResolvedAutowireMode() == RootBeanDefinition.AUTOWIRE_BY_TYPE) {
 * autowireByType(beanName, mbd, bw, newPvs); // 同autowireByName
 * }
 * <p>
 * InstantiationAwareBeanPostProcessor ibp = (InstantiationAwareBeanPostProcessor) bp;
 * pvs = ibp.postProcessPropertyValues(pvs, filteredPds, bw.getWrappedInstance(), beanName);
 * if (pvs == null) {
 * return;  // 填充bean属性阶段结束，不设置属性值，但bean的生命周期还是会往步骤6走
 * }
 * <p>
 * applyPropertyValues(beanName, mbd, bw, pvs);
 * -> bw.setPropertyValues(MutablePropertyValues);
 * ->  for (PropertyValue pv : propertyValues) {
 * setPropertyValue(pv);
 * -> 场景1：processKeyedProperty(tokens, pv);
 * -> 场景1： isArray
 * -> 场景2： List
 * -> 场景3： Map
 * 以上三种场景都会调用：Object valueToApply = this.typeConverterDelegate.convertIfNecessary(propertyName, oldValue, newValue, requiredType, td);
 * -> 场景2：processLocalProperty(tokens, pv);
 * -> PropertyHandler ph = getLocalPropertyHandler(tokens.actualName); // BeanPropertyHandler
 * Object valueToApply = this.typeConverterDelegate.convertIfNecessary(propertyName, oldValue, newValue, requiredType, td);
 * ph.setValue(this.wrappedObject, valueToApply);
 * -> writeMethod.invoke(getWrappedInstance(), value);
 * }
 * <p>
 * 6. 设置aware
 * invokeAwareMethods
 * -> BeanNameAware
 * -> BeanClassLoaderAware
 * -> BeanFactoryAware
 * 7. 初始化bean
 * bean = BeanPostProcessor.postProcessBeforeInitialization(result, beanName);
 * if (bean == null) {
 * 停止执行其他的postProcessBeforeInitialization方法，但不会终止流程继续向下执行
 * }
 * ((InitializingBean) bean).afterPropertiesSet();
 * invokeCustomInitMethod(beanName, bean, mbd);
 * bean = processor.postProcessAfterInitialization(result, beanName);
 * if (bean == null) {
 * 停止执行其他的postProcessAfterInitialization方法，但不会终止流程继续向下执行
 * }
 * 8. 注册销毁
 * if (mbd.isSingleton()) {
 * // Register a DisposableBean implementation that performs all destruction
 * // work for the given bean: DestructionAwareBeanPostProcessors,
 * // DisposableBean interface, custom destroy method.
 * registerDisposableBean(beanName,
 * new DisposableBeanAdapter(bean, beanName, mbd, getBeanPostProcessors(), acc));
 * }
 * else {
 * // A bean with a custom scope...
 * Scope scope = this.scopes.get(mbd.getScope());
 * scope.registerDestructionCallback(beanName,
 * new DisposableBeanAdapter(bean, beanName, mbd, getBeanPostProcessors(), acc));
 * }
 * 9.销毁阶段 详见：DisposableBeanAdapter
 * 先销毁依赖当前bean的其他bean
 * 再销毁自己
 * 最后在销毁自己内部bean
 * for (DestructionAwareBeanPostProcessor processor : this.beanPostProcessors) {
 * processor.postProcessBeforeDestruction(this.bean, this.beanName);
 * }
 * ((DisposableBean) bean).destroy();
 * invokeCustomDestroyMethod(this.destroyMethod);
 */