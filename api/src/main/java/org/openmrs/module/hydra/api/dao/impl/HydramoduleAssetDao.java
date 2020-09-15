package org.openmrs.module.hydra.api.dao.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.openmrs.module.hydra.api.dao.HydraDao;
import org.openmrs.module.hydra.api.dao.IHydramoduleAssetDao;
import org.openmrs.module.hydra.model.HydramoduleAsset;
import org.openmrs.module.hydra.model.HydramoduleAssetCategory;
import org.openmrs.module.hydra.model.HydramoduleAssetType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Transactional
public class HydramoduleAssetDao implements IHydramoduleAssetDao {

	@Autowired
	public SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public HydramoduleAssetType saveAssetType(HydramoduleAssetType serviceType) {
		HydramoduleAssetCategory assetCategory = serviceType.getAssetCategory();
		if (assetCategory != null) {
			assetCategory = getAssetCategory(assetCategory.getUuid());
			serviceType.setAssetCategory(assetCategory);
		}
		getSession().saveOrUpdate(serviceType);
		getSession().flush();
		return serviceType;
	}

	@Override
	public HydramoduleAssetType getAssetType(String uuid) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleAssetType.class);
		criteria.add(Restrictions.eq("uuid", uuid));
		criteria.add(Restrictions.eq("retired", false));
		return (HydramoduleAssetType) criteria.uniqueResult();
	}

	@Override
	public List<HydramoduleAssetType> getAllAssetTypes(boolean retired) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleAssetType.class);
		criteria.addOrder(Order.asc("assetTypeId"));
		criteria.add(Restrictions.eq("retired", retired));
		return criteria.list();
	}

	// AssetCategory
	@Override
	public HydramoduleAssetCategory saveAssetCategory(HydramoduleAssetCategory serviceType) {
		getSession().saveOrUpdate(serviceType);
		getSession().flush();
		return serviceType;
	}

	@Override
	public HydramoduleAssetCategory getAssetCategory(String uuid) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleAssetCategory.class);
		criteria.add(Restrictions.eq("uuid", uuid));
		criteria.add(Restrictions.eq("retired", false));
		return (HydramoduleAssetCategory) criteria.uniqueResult();
	}

	@Override
	public List<HydramoduleAssetCategory> getAllAssetCategories(boolean retired) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleAssetCategory.class);
		criteria.addOrder(Order.asc("assetCategoryId"));
		criteria.add(Restrictions.eq("retired", retired));
		return criteria.list();
	}

	// Asset
	@Override
	public HydramoduleAsset saveAsset(HydramoduleAsset serviceType) {
		HydramoduleAssetType assetType = serviceType.getAssetType();
		if (assetType != null) {
			assetType = getAssetType(assetType.getUuid());
			serviceType.setAssetType(assetType);
		}

		// System.out.println(serviceType.getUuid());
		getSession().saveOrUpdate(serviceType);
		getSession().flush();
		return serviceType;
	}

	@Override
	public HydramoduleAsset getAsset(String uuid) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleAsset.class);
		criteria.add(Restrictions.eq("uuid", uuid));
		criteria.add(Restrictions.eq("retired", false));
		return (HydramoduleAsset) criteria.uniqueResult();
	}

	@Override
	public List<HydramoduleAsset> getAllAssets(boolean retired) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleAsset.class);
		criteria.addOrder(Order.asc("assetId"));
		criteria.add(Restrictions.eq("retired", retired));
		return criteria.list();
	}

}
