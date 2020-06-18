package org.openmrs.module.hydra.api.dao;

import org.openmrs.api.db.hibernate.DbSession;
import org.openmrs.api.db.hibernate.DbSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class HydraDao {

	@Autowired
	protected DbSessionFactory sessionFactory;

	protected DbSession getSession() {
		return sessionFactory.getCurrentSession();
	}

}
