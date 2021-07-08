#Spring学习总结

##1.1 Spring框架概述

1. 轻量级开源javaEE框架，为了解决企业复杂性诞生，由两个核心组成：IOC(控制反转)和AOP(切面编程)



##1.2 IOC容器

###IOC底层原理（工厂、反射等）

**原理**：使用Dom4j解析配置文件（xml）使用工厂模式加反射的方式（Class.forName()）创建bean

IOC接口（Beanfactory）

**Spring提供IOC容器实现的两种方式：**

1. Beanfactory ：Spring内部使用的接口

1. ApplicationContext：开发人员使用的接口，提供了更多的功能

**作用**：通过使用Dom4j解析配置文件（xml）使用工厂模式加反射的方式（Class.forName()）创建bean

**区别**：Beanfactory 在程序加载配置文件的时候不会创建里面的对象，在获取（使用对象的时候才去创建）。ApplicationContext在加载配置文件的时候就会创建配置文件中的对象

**实例**：

```java
 public static void main(String[] args) {
        //查询类路径 加载配置文件 ClassPathXmlApplicationContext 读取src下文件夹的相对路径
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-config.xml");
        //读取配置文件所在盘符的绝对路径
        ApplicationContext fileSystemXmlApplicationContext = new FileSystemXmlApplicationContext("D:/resource/springTest/src/spring-config.xml");
        //根据id获取bean
        //Spring就是一个大工厂（容器）专门生成bean bean就是对象
        User user = (User) applicationContext.getBean("user");
        user.setName("大屌哥");
        User user2 = (User) fileSystemXmlApplicationContext.getBean("user");
        user2.setName("小屌哥");
        //输出获取到的对象
        System.out.println("user = " + user+"user2 = " + user2);
    }
```

**输出结果**：

```java
user = Person{name='大屌哥', age=0}user2 = Person{name='小屌哥', age=0}
```



###1.3Bean管理

1. **概念什么是Bean管理**：指的是由Spring创建对象及注入属性两个操作

2. **创建对象**

   在Spring配置文件中使用bean标签创建对象，创建时默认通过无参构造器进行创建

   ~~~java
    //id 该bean唯一标识 class 该bean在src文件夹下的相对路径
       <bean id="user" class="model.User"/>
   ~~~

   

3. **Bean的生命周期**

   3.1通过无参构造器创建Bean的实例

   3.2为Bean的属性设置值和对其他Bean的应用（调用set方法）

   3.3后置处理器中在初始化之前执行的方法

   3.4调用Bean的初始化方法

   3.5后置处理器中在初始化之后执行的方法

   3.6Bean进入IOC容器（通过getBean()方法获取）

   3.7当容器关闭的时候，销毁Bean

   实例：

   Bean 

   ~~~java
   public class BeanLife {
       private String name;
   
       BeanLife() {System.out.println("第一步：调用无参的构造方法创建Bean");}
   
       public void setName(String name) {this.name = name;
           System.out.println("第二步：调用set方法向bean中的属性注值");}
   
       public void initMethod() {System.out.println("第三步：使用初始化方法");}
   
       public void destroyMethod() {System.out.println("第五步：销毁Bean");}
   }
   ~~~

   配置后置处理器

   ~~~java
   public class BeanLifePost implements BeanPostProcessor {
       @Override
       public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
           System.out.println("后置处理器在初始化之前，执行的方法");
           return bean;
       }
       @Override
       public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
           System.out.println("后置处理器在初始化之后，执行的方法");
           return bean;
       }
   }
   ~~~

   

   配置文件中

   ~~~xml
   <bean id="beanLife" class="model.BeanLife" init-method="initMethod" destroy-method="destroyMethod">
       <property name="name" value="123"></property>
       </bean>
   <!--将后置处理器也交给IOC容器管理，后置处理器将会把所有IOC中的Bean的初始化前后都进行操作-->
   <bean id="beanLifePost" class="model.BeanLifePost"></bean>
   ~~~

   调用时

   ~~~java
   public static void main(String[] args) {
           ApplicationContext beanLife = new ClassPathXmlApplicationContext("config.xml");
           BeanLife beanLift = beanLife.getBean("beanLife", BeanLife.class);
           System.out.println("第四步：Bean进入了IOC容器");
           System.out.println(beanLife);
           ((ClassPathXmlApplicationContext) beanLife).close();//关闭容器
       }
   ~~~

   输出结果：

   ~~~java
   第一步：调用无参的构造方法创建Bean
   第二步：调用set方法向bean中的属性注值
   第三步：后置处理器在初始化之前，执行的方法
   第四步：使用初始化方法
   第五步：后置处理器在初始化之后，执行的方法
   第六步：Bean进入了IOC容器
   org.springframework.context.support.ClassPathXmlApplicationContext@26f67b76, started on Thu Oct 22 15:43:47 CST 2020
   第七步：销毁Bean
   ~~~

   

####1.IOC操作Bean管理（基于xml）

**DI：依赖注入，就是注入属性**

######1.1 第一种注入方式：使用set方法进行注入
​	1.1.1 创建类，定义属性及set方法
​	1.1.2 在配置文件中创建对象及注入属性

~~~java
  <bean id="user" class="model.User">
            <!--name:属性名称 value：值-->
            <property name="name" value="哈哈"></property>
        </bean>	
~~~


   **输出结果**：

   ~~~java
       user = Person{name='哈哈', age=0}user2 = Person{name='哈哈', age=0}
   ~~~

  ###### 1.2 第二中注入方式：使用有参构造进行注入
   	1.2.1 创建类，定义属性及有参构造方法

   ~~~java
    public User(String name, int age) {
               this.name = name;
               this.age = age;
           }
   ~~~

   1.2.2 在配置文件中创建对象及注入属性

   ```
   <bean id="user" class="model.User">
           <!--name:属性名称 value：值-->
           <constructor-arg name="name" value="老刁哥"></constructor-arg>
           <constructor-arg name="age" value="1"></constructor-arg>
   </bean>
   ```

   输出结果：

   ~~~java
       user = Person{name='老刁哥', age=1}user2 = Person{name='老刁哥', age=1}
   ~~~

####2 IOC操作Bean管理（FactoryBean）
2.1 Spring 有两种类型的bean，一种普通类型bean，另一种共产bean（FactoryBean）
2.1.1普通bean：在配置文件中定义bean类型就是返回类型
2.1.2工厂bean：实现接口里面的方法，在实现的方法中定义返回的bean类型
	第一步 创建类，让这个类作为工厂bean，实现接口factoryBean
	第二步 实现接口里面的方法，在实现的方法中定义返回的bean类型

~~~java
@Override
        public User getObject() throws Exception {
            User user = new User();
            return user;
}
~~~

####3 IOC操作Bean管理（基于注解）

Spring针对Bean管理中创建对象提供的注解

1.@Component：通用注解，用之在Spring容器中都可以创建对象

2.@Service：一般用于业务逻辑层Service层中

3.@Controller：一般用于web层中

4.@Repository：一般用于dao即持久层中

**注：四个注解作用都是将bean交给IOC容器中，注解名的不同只是用于区分该bean的所处的位置**

基于注解方式实现对象的创建

第一步：引入依赖

spring-aop-5.2.3.RELEASE.jar

第二步：在配置文件中引入context，并开启组件扫描

~~~xml
<!--多个扫描目标路径使用逗号隔开-->
<context:component-scan base-package="com.springTest.Dao,com.springTest.Service"></context:component-scan>
~~~

第三步：在扫描目标文件夹中创建类，并使用注解

~~~java
@Service(value = "userService")//类似于<bean id="userService" class=".."> 该value属性不写也不影响使用
public class UserService {
    private String name;
    public void out(){
         System.out.println("找到你了");
    }
}
~~~

使用junit测试

```java
@Test
public  void userServiceTest(){
    ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
    UserService bean = context.getBean("userService", UserService.class);
    System.out.println(bean);
    bean.out();
}
```

基于注解方式实现对象注入属性

1.@AutoWired ： 根据属性类型进行注入（byType）

通过@AutoWired举例

1.新建接口实现类CatServiceImpl,并使用@Service注解

~~~java
@Service
public class CatServiceImpl implements CatService {
    @Override
    public void add() {
        System.out.println("注入完成");
    }
}
~~~



2.新建controller层使用@Controller注解，并注入CatServiceImpl

~~~java
@Controller
public class UserController {
    @Autowired
    private CatService catService;

    public void test(){
        catService.add();
    }
}
~~~

3.使用Junit进行测试

~~~java
@Test
    public  void userServiceTest(){
        ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
        UserController bean = context.getBean("userController", UserController.class);
        System.out.println(bean);
        bean.test();
    }
~~~

   输出结果：

~~~java
com.springTest.controller.UserController@769a1df5
注入完成
~~~



2.@Qualifier：根据属性名称进行注入(byName),使用的时候要配合@AutoWired注解一起使用

使用场景：当CatService存在多个实现类时，使用@AutoWired会导致IOC容器不知道应该注入那个实现类

使用方式：在实现类中通过设置注解中的value值，在使用@Qualifier进行区分

实现类中：配置value值

~~~java
@Service(value = "catService1")
~~~

在Controller中：配置@Qualifier，由于Qualifier是通过名称进行注入，所以务必保持配置的value值一致

~~~java
@Autowired
    @Qualifier(value = "catService1")
    private CatService catService;
~~~

输出结果：

~~~java
com.springTest.controller.UserController@64b0598
注入完成
~~~

3.@Resource：可以根据属性类型也可以通过属性名称进行注入

@Resource注释默认通过类型进行注入，当使用name属性时就改为通过名称注入

~~~java
@Resource(name = "catService1")
~~~

输出结果：

~~~java
com.springTest.controller.UserController@78452606
注入完成
~~~

4.@value：注入普通类型的属性（多用于字符串）

实例：在实现类中新增属性name并将值注入进name中

```java
@Value(value = "123")
private String name;
```

输出结果：

~~~java
注入完成123
~~~



使用配置类的方式，替换代码中的xml，实现纯注解开发

1.使用@Configuration注解创建配置类，再使用@ComponentScan注解配置扫描目标文件夹

~~~java
@Configuration
@ComponentScan(basePackages = {"com.springTest"})
public class SpringConfig {
}
~~~

2.使用AnnotationConfigApplicationContext接口中的实现类AnnotationConfigApplicationContext实现Bean管理

~~~java
ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        UserController bean = context.getBean("userController", UserController.class);
        System.out.println(bean);
        bean.test();
~~~

输出结果：

~~~java
com.springTest.controller.UserController@2d29b4ee
注入完成123
~~~

1.4 AOP

**概念**：面向切面编程，利用AOP可以对业务逻辑的各个部分进行隔离，从而使业务逻辑各部分之间的耦合度降低，提高程序的可重用性，同时提高了开发的效率。

AOP底层原理（动态代理）：

第一种情况：有接口（JDK动态代理），创建接口实现类代理对象，增强类的方法



  使用Proxy类里面的newProxyInstance()方法创建代理对象

​	方法中三个参数详解

​    1.1第一个参数，类加载器

​	1.2第二个参数，增强方法所在的类，这个类实现的接口，支持多个接口

​	1.3第三个参数，实现接口InvocationHandler，创建代理对象，在该代理对象中编写增强的方法

操作步骤

1.创建接口，定义方法

~~~java
public interface IProxyTest {
    public Integer sum(int a,int b);
    public Integer get(int a);
}
~~~

2.创建接口实现类，实现方法

~~~java
public class ProxyTestImpl implements IProxyTest {
    @Override
    public Integer sum(int a, int b) {return a+b;}

    @Override
    public Integer get(int a) {return a;}
}
~~~

3.实现InvocationHandler接口，创建增强类及方法

~~~java
public class ProxyTestHandler implements InvocationHandler {
    //由于Proxy.newProxyInstance方法中获得的是接口class对象，所以需要指定该接口的实现类，并传入该类
    //通过有参构造进行传递
    private Object o;
    public ProxyTestHandler(Object o){this.o = o;}
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //方法之前
        System.out.println("方法之前执行："+ method.getName()+"    传递的参数："+ Arrays.toString(args));
        //被增强的方法执行
        Object invoke = method.invoke(o,args);
        //方法之后
        System.out.println("方法之后执行："+o);
        return invoke;
    }
}
~~~

4.通过Proxy.newProxyInstance()方法创建被增强的接口并使用

~~~java
	@Test
    public void proxyTest(){
        //待加强的接口数组
        Class[] i = {IProxyTest.class};
        //创建接口实现类的代理对象
        //通过有参构造的方式传递实现类对象
        IProxyTest o = (IProxyTest)Proxy.newProxyInstance(ProxyTestDemo.class.getClassLoader(), i, new ProxyTestHandler(new ProxyTestImpl()));
        o.sum(1,2);
    }
~~~

输出结果：

~~~java
方法之前执行：sum    传递的参数：[1, 2]
方法之后执行：com.springTest.Service.impl.ProxyTestImpl@23e028a9
~~~

第二种情况：没有接口（CGLIB动态代理），创建当前类子类的代理对象



术语：

1.连接点：在一个类中哪些方法可以被增强，这些方法就是连接点

2.切入点：实际被真正增强的方法

3.通知（增强）：a.实际增强的逻辑部分。

b.通知有多种类型（前置、后置、环绕、异常、最终）

4.切面：把通知应用到切入点的这个动作



基于Aspectj实现AOP操作



1、Spring框架中一般基于AspectJ实现AOPcaozuo 

  1.1 什么是AspectJ

 	AspectJ是独立的AOP框架，一般把AspectJ和Spring框架一起使用，进行AOP操作

1.2基于AspectJ实现AOP操作

1.2.1基于xml配置文件

1.2.2基于注解方式实现

准备工作：

1.引入依赖

com.springsource.net.sf.cglib-2.2.0.jar

com.springsource.org.aopalliance-1.0.0.jar

com.springsource.org.aspectj.weaver-1.6.8.RELEASE.jar

spring-aspects-5.2.3.RELEASE.jar

2.切入点表达式

2.1 作用：表达针对哪个类中的哪个方法进行增强

2.2语法结构：

execution（【权限修饰符】【返回类型】【类全路径】（【参数列表】））

举例1：对com.springTest.Service.impl.CatServiceImpl中的add()方法进行增强

execution(* com.springTest.Service.impl.CatServiceImpl.add(..))

上例中的返回类型可以省略，*号作为通用符代表任意权限修饰符



举例2：对com.springTest.Service.impl.CatServiceImpl中的所有方法进行增强

execution(* com.springTest.Service.impl.CatServiceImpl.*(..))

举例3：对com.springTest.Service包中的所有类，类中的所有方法进行增强

~~~java
execution(* com.springTest.Service.*.*(..))
~~~

AOP操作(AspectJ注解方式)

1.使用注解方式将该类作为bean交给spring 创建类，在类里面定义方法

2.使用注解方式将该类作为bean交给spring  创建增强类（编写增强逻辑）

2.1在增强类里面，创建方法，让不同方法代表不同通知类型

3.进行通知的配置

3.1在spring文件文件中，开启注解扫描

~~~xml
<context:component-scan base-package="com.springTest.aop.anno"></context:component-scan>
~~~

3.2使用注解创建AspectJAnnoTest和ProxyAnno对象



```java
@Component
@Aspect //声明该类为增强类
public class ProxyAnno {
    @Before(value = "execution(* com.springTest.aop.anno.AspectJAnnoTest.after(..))")//在切入点之后执行
    public void before(){
        System.out.println("在之前执行...");
    }
}
```



~~~java
@Component(value = "anno")
public class AspectJAnnoTest {
    public void after(){
        System.out.println("之后执行");
    }
}
~~~



3.3在增强类上面添加注解@Aspect

3.4在spring配置文件中开启生成代理对象

~~~xml
<aop:aspectj-autoproxy></aop:aspectj-autoproxy>
~~~

3.5配置不同类型的通知

3.5.1在增强类的里面，在作为通知方法上面添加通知类型注解，使用切入点表达式配置



AOP操作(AspectJ配置文件)



##2 事务管理



###2.1事务管理概念

在JavaEE的开发过程中，service方法用于处理主要的业务逻辑，而业务逻辑的处理往往伴随着对数据库的多个操作。以我们生活中常见的转账为例，service方法要实现将A账户转账到B账户的功能，则该方法内必定要有两个操作：先将A账户的金额减去要转账的数目，然后将B账户加上相应的金额数目。这两个操作必定要全部成功，方才表示本次转账成功；若有任何一方失败，则另一方必须回滚（即全部失败）。事务指的就是这样一组操作：这组操作是不可分割的，要么全部成功，要么全部失败

###2.2重要概念（传播行为和隔离级别）

2.2.1 事务的特性

事务具有ACID四个特性：
**原子性(Atomicity)**：事务是一个不可分割的工作单位，事务中的操作要么都发生，要么都不发生
**一致性(Consistency)**：事务在完成后数据的完整性必须保持一致
**隔离性(Isolation)**：多个用户并发访问数据库时，一个用户的事务不能被其他用户的事务所干扰，多个并发事务之间的数据要相互隔离
**持久性（Durability）**：一个事务一旦被提交，它对数据库中数据的改变应该是永久性的，即使数据库发生故障也不应该对其有任何影响

###2.3基于注解实现声明式事务管理

2.3.1声明式事务底层原理：使用Spring AOP原理实现

2.3.1Spring事务管理API(PlatformTransactionManager事务管理器)

a).提供一个接口，代表事务管理器，这个接口针对不同的框架提供不同的实现类



函数式事务管理（用得少）

声明式事务

1基于注解方式实现事务管理

1.1.在spring配置文件中配置事务管理器（开启事务管理器，注入数据源）

~~~xml
<!-- 组件扫描 -->
    <context:component-scan base-package="com.atguigu"></context:component-scan>
<!-- 数据库连接池 -->
    <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource"
          destroy-method="close">
        <property name="url" value="jdbc:mysql://10.0.0.54:3306/test" />
        <property name="username" value="root" />
        <property name="password" value="root" />
        <property name="driverClassName" value="com.mysql.jdbc.Driver" />
    </bean>
<!-- JdbcTemplate对象 -->
    <bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
        <!--注入dataSource-->
        <property name="dataSource" ref="dataSource"></property>
    </bean>
<!--分隔符-->
    <!--创建事务管理器-->
    <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <!--注入数据源-->
        <property name="dataSource" ref="dataSource"></property>
    </bean>
~~~



1.2.在spring配置文件，开启事务注解

1.2.1在Spring配置文件引入名称空间tx

 1.2.2开启事务注解

~~~xml
<!--开启事务注解-->
    <tx:annotation-driven transaction-manager="transactionManager"></tx:annotation-driven>
~~~

1.2.3 在service实现类（或者实现类的方法上面）添加事务注解@transactional

注意：如果把这个注解添加到类上面，这个类里面所有的方法都添加事务，如果把这个注解添加到方法上面，即为这个方法添加事务

2.基于xml配置文件方式实现事务管理



事务操作（声名式事务管理参数配置）

1.propagation：事务传播行为

1.1多事务方法直接进行调用，这个过程中事务是如何进行管理的

1.2事务的传播机制

- PROPAGATION_REQUIRED
  Spring默认的传播机制，能满足绝大部分业务需求，如果外层有事务，则当前事务加入到外层事务，一块提交，一块回滚。如果外层没有事务，新建一个事务执行
- PROPAGATION_REQUES_NEW
  该事务传播机制是每次都会新开启一个事务，同时把外层事务挂起，当当前事务执行完毕，恢复上层事务的执行。如果外层没有事务，执行当前新开启的事务即可
- PROPAGATION_SUPPORT
  如果外层有事务，则加入外层事务，如果外层没有事务，则直接使用非事务方式执行。完全依赖外层的事务
- PROPAGATION_NOT_SUPPORT
  该传播机制不支持事务，如果外层存在事务则挂起，执行完当前代码，则恢复外层事务，无论是否异常都不会回滚当前的代码
- PROPAGATION_NEVER
  该传播机制不支持外层事务，即如果外层有事务就抛出异常
- PROPAGATION_MANDATORY
  与NEVER相反，如果外层没有事务，则抛出异常
- PROPAGATION_NESTED
  该传播机制的特点是可以保存状态保存点，当前事务回滚到某一个点，从而避免所有的嵌套事务都回滚，即各自回滚各自的，如果子事务没有把异常吃掉，基本还是会引起全部回滚的。

2.isolation：事务隔离级别

2.1事务有特性成为隔离性，多事务操作之间不会产生影响。不考虑隔离性会产生问题

2.1.1脏读：一个未提交事务读取到另一个未提交事务的数据

2.1.2不可重复读:一个未提交事务读取到另一个提交事务的修改数据

2.1.3幻读：一个未提交事务读取到另一个提交事务的添加数据

2.1.4通过设置事务隔离级别，解决读问题

3.timeout：超时时间（事务需要在一定的时间内进行提交，如果不提交进行回滚）

4.readOnly：是否只读

5.rollbackFor：回滚机制（设置出现哪些异常进行事务回滚）

6.noRollbackFor：不回滚机制（设置出现哪些异常不进行事务回滚）

###



##3.Spring5新特性

###3.1整合日志框架

1.Spring5已经移除了Log4JConfigListener，官方建议使用Log4J2

2.整合步骤

2.1第一步 引入依赖

2.2第二步 创建Log4J2配置文件

~~~xml
<?xml version="1.0" encoding="UTF-8"?>
<!--日志级别以及优先级排序: OFF > FATAL > ERROR > WARN > INFO > DEBUG > TRACE > ALL -->
<!--Configuration后面的status用于设置log4j2自身内部的信息输出，可以不设置，当设置成trace时，可以看到log4j2内部各种详细输出-->
<configuration status="INFO">
    <!--先定义所有的appender-->
    <appenders>
        <!--输出日志信息到控制台-->
        <console name="Console" target="SYSTEM_OUT">
            <!--控制日志输出的格式-->
            <PatternLayout pattern="%d{yyyy-MM-dd HH:mm:ss.SSS} [%t] %-5level %logger{36} - %msg%n"/>
    </console>
    </appenders>
    <!--然后定义logger，只有定义了logger并引入的appender，appender才会生效-->
    <!--root：用于指定项目的根日志，如果没有单独指定Logger，则会使用root作为默认的日志输出-->
    <loggers>
        <root level="info">
            <appender-ref ref="Console"/>
        </root>
    </loggers>
</configuration>
~~~

###3.2@Nullable注解

用途@Nullable注解可以使用在方法上面，属性上面，参数上面，标识方法可以为空，属性值可以为空，参数值可以为空

###3.3函数式注册对象Lamda表达式

###3.4整合JUnit5单元测试框架

1.整合junit4

第一步 引入依赖

第二步 创建测试类，通过注释方式完成基本过程

2Spring整合Junit5

第一步：进入Junit5jar包

第二步：创建测试类，通过注释方式完成基本过程

###3.5WebFulx框架









