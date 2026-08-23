package com.github.rhafaelcosta.commerce.order.infrastructure.persistence;

import com.github.rhafaelcosta.commerce.order.utils.TestcontainerPostgreSQLConfig;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({TestcontainerPostgreSQLConfig.class, SpringDataAuditingConfig.class})
public abstract class AbstractPersistenceIT {

}