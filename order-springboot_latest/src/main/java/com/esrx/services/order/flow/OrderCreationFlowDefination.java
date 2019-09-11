package com.esrx.services.order.flow;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.authorization.Authorization;
import org.camunda.bpm.engine.authorization.Groups;
import org.camunda.bpm.engine.authorization.Resource;
import org.camunda.bpm.engine.authorization.Resources;
import org.camunda.bpm.engine.filter.Filter;
import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.spring.ProcessEngineFactoryBean;
import org.camunda.bpm.engine.spring.SpringProcessEngineConfiguration;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.HashMap;
import java.util.Map;


import static org.camunda.bpm.engine.authorization.Authorization.ANY;
import static org.camunda.bpm.engine.authorization.Authorization.AUTH_TYPE_GRANT;
import static org.camunda.bpm.engine.authorization.Permissions.ALL;
import static org.camunda.bpm.engine.authorization.Resources.FILTER;


@Configuration
public class OrderCreationFlowDefination {

   /* @Autowired
    private ProcessEngine engine;*/

    @Autowired
    private ApplicationContext applicationContext;

    @PostConstruct
    public void createFlow() {
    /*
     * Currently the order.bpmn in src/main/resources is used
     * You can either define flows as BPMN 2.0 XML or by Model API:

    engine.getRepositoryService().createDeployment() //
      .addModelInstance("order.bpmn", Bpmn.createProcess("order").executable() //
        .startEvent().message("OrderPlacedEvent")
        .sendTask().name("Fetch goods").camundaDelegateExpression(exp(FetchGoodsAdapter.class))
        .receiveTask().name("Goods fetched").message("GoodsFetchedEvent")
          .camundaOutputParameter("pickId", "#{PAYLOAD_GoodsFetchedEvent.jsonPath('$.pickId').stringValue()}")
        .sendTask("payment").name("Retrieve payment").camundaDelegateExpression(exp(RetrievePaymentAdapter.class))
        .receiveTask().name("Payment received").message("PaymentReceivedEvent")
        .sendTask().name("Ship goods").camundaDelegateExpression(exp(ShipGoodsAdapter.class))
        .receiveTask().name("Goods shipped").message("GoodsShippedEvent")
        .endEvent().camundaExecutionListenerDelegateExpression("end", exp(OrderCompletedAdapter.class))
        .done()
      ).deploy();
      */
    }

    @SuppressWarnings("rawtypes")
    public String exp(Class delegateClass) {
        String[] beanNames = applicationContext.getBeanNamesForType(delegateClass);
        if (beanNames.length>1) {
            throw new RuntimeException("More than one Spring bean found for type " + delegateClass);
        }
        return "#{" + beanNames[0] + "}";
    }


    @Primary
    @Bean
    public ProcessEngine processEngine() throws Exception {
        ProcessEngineFactoryBean factoryBean = new ProcessEngineFactoryBean();
        factoryBean.setProcessEngineConfiguration(engineConfiguration());
        createDefaultUser(factoryBean.getObject());
        return factoryBean.getObject();
    }



    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:process-engine;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
        //dataSource.setUsername("sa");
        //dataSource.setPassword("");
        return dataSource;
    }

    @Value("${camunda.bpm.history-level:FULL}")
    private String historyLevel;

    @Autowired
    private ResourcePatternResolver resourceLoader;


    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    public SpringProcessEngineConfiguration engineConfiguration() throws  Exception {
        SpringProcessEngineConfiguration config = new SpringProcessEngineConfiguration();

        config.setDataSource(dataSource());
        config.setDatabaseSchemaUpdate("true");
        config.setProcessEngineName("default");

        config.setTransactionManager(transactionManager());

        config.setHistory(historyLevel);

        // config.setJobExecutorActivate(false);
        //config.setMetricsEnabled(false);

        // deploy all processes from folder 'processes'
        org.springframework.core.io.Resource[] resources = resourceLoader.getResources("classpath:/processes/*.bpmn");
        config.setDeploymentResources(resources);

        return config;
    }

  /*
        .boundaryEvent().timerWithDuration("PT5S").endEvent().compensateEventDefinition().compensateEventDefinitionDone()
        .moveToNode("payment")
   */

    public static void createDefaultUser(ProcessEngine engine) {
        // and add default user to Camunda to be ready-to-go
        if (engine.getIdentityService().createUserQuery().userId("demo").count() == 0) {
            User user = engine.getIdentityService().newUser("demo");
            user.setFirstName("Demo");
            user.setLastName("Demo");
            user.setPassword("demo");
            user.setEmail("demo@camunda.org");
            engine.getIdentityService().saveUser(user);

            Group group = engine.getIdentityService().newGroup(Groups.CAMUNDA_ADMIN);
            group.setName("Administrators");
            group.setType(Groups.GROUP_TYPE_SYSTEM);
            engine.getIdentityService().saveGroup(group);

            for (Resource resource : Resources.values()) {
                Authorization auth = engine.getAuthorizationService().createNewAuthorization(AUTH_TYPE_GRANT);
                auth.setGroupId(Groups.CAMUNDA_ADMIN);
                auth.addPermission(ALL);
                auth.setResourceId(ANY);
                auth.setResource(resource);
                engine.getAuthorizationService().saveAuthorization(auth);
            }

            engine.getIdentityService().createMembership("demo", Groups.CAMUNDA_ADMIN);
        }

        // create default "all tasks" filter
        if (engine.getFilterService().createFilterQuery().filterName("Alle").count() == 0) {

            Map<String, Object> filterProperties = new HashMap<>();
            filterProperties.put("description", "Alle Aufgaben");
            filterProperties.put("priority", 10);

            Filter filter = engine.getFilterService().newTaskFilter() //
                    .setName("Alle") //
                    .setProperties(filterProperties)//
                    .setOwner("demo")//
                    .setQuery(engine.getTaskService().createTaskQuery());
            engine.getFilterService().saveFilter(filter);

            // and authorize demo user for it
            if (engine.getAuthorizationService().createAuthorizationQuery().resourceType(FILTER).resourceId(filter.getId()) //
                    .userIdIn("demo").count() == 0) {
                Authorization managementGroupFilterRead = engine.getAuthorizationService().createNewAuthorization(Authorization.AUTH_TYPE_GRANT);
                managementGroupFilterRead.setResource(FILTER);
                managementGroupFilterRead.setResourceId(filter.getId());
                managementGroupFilterRead.addPermission(ALL);
                managementGroupFilterRead.setUserId("demo");
                engine.getAuthorizationService().saveAuthorization(managementGroupFilterRead);
            }

        }

    }


}
