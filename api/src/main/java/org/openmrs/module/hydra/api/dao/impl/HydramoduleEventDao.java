package org.openmrs.module.hydra.api.dao.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.openmrs.api.db.hibernate.DbSession;
import org.openmrs.api.db.hibernate.DbSessionFactory;
import org.openmrs.module.hydra.api.dao.HydraDao;
import org.openmrs.module.hydra.api.dao.IHydramoduleEventDao;
import org.openmrs.module.hydra.model.HydramoduleEvent;
import org.openmrs.module.hydra.model.HydramoduleEventAsset;
import org.openmrs.module.hydra.model.HydramoduleEventParticipants;
import org.openmrs.module.hydra.model.HydramoduleEventSchedule;
import org.openmrs.module.hydra.model.HydramoduleEventService;
import org.openmrs.module.hydra.model.HydramoduleEventType;
import org.openmrs.module.hydra.model.HydramoduleFieldAnswer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("eventDao")
@Transactional
public class HydramoduleEventDao extends HydraDao implements IHydramoduleEventDao {

	@Override
	public HydramoduleEvent saveHydramoduleEvent(HydramoduleEvent event) {
		Integer eventId = event.getEventId();

		HydramoduleEventSchedule schedule = event.getSchedule();
		schedule = saveHydramoduleEventScedule(schedule);
		event.setSchedule(schedule);
		getSession().saveOrUpdate(event);
		getSession().flush();

		// TODO #BadPractice, code below block should be in service layer not
		// there in
		// data
		// access layer!
		/* if (eventId == null) */ {
			// saving EventAssets
			List<HydramoduleEventAsset> assets = event.getEventAssets();
			if (assets != null) {
				if (assets.size() > 0) {
					for (HydramoduleEventAsset asset : assets) {
						asset.setEvent(event);
						/* persistantAssets.add */saveHydramoduleEventAsset(asset);
					}
				}
			}

			// saving EventServices
			List<HydramoduleEventService> services = event.getEventServices();
			if (services != null) {
				if (services.size() > 0) {
					for (HydramoduleEventService service : services) {
						service.setEvent(event);
						saveHydramoduleEventService(service);
					}
				}
			}

			// saving EventParticipants
			List<HydramoduleEventParticipants> participants = event.getEventParticipants();
			if (participants != null) {
				if (participants.size() > 0) {
					for (HydramoduleEventParticipants eventParticipants : participants) {
						eventParticipants.setEvent(event);
						/* persistantParticipants.add */saveHydramoduleEventParticipant(eventParticipants);
					}
				}
			}
		}
		return event;
	}

	@Override
	public HydramoduleEvent getHydramoduleEvent(String uuid) {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleEvent.class);
		criteria.add(Restrictions.eq("uuid", uuid));
		criteria.add(Restrictions.eq("voided", false));
		return (HydramoduleEvent) criteria.uniqueResult();
	}

	@Override
	public HydramoduleEvent getHydramoduleEvent(Integer id) {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleEvent.class);
		criteria.add(Restrictions.eq("eventId", id));
		criteria.add(Restrictions.eq("voided", false));
		return (HydramoduleEvent) criteria.uniqueResult();
	}

	@Override
	public List<HydramoduleEvent> getAllHydramoduleEvents(boolean retired) {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleEvent.class);
		criteria.addOrder(Order.asc("eventId"));
		criteria.add(Restrictions.eq("voided", retired));
		return criteria.list();
	}

	// EventScedule
	@Override
	public HydramoduleEventSchedule saveHydramoduleEventScedule(HydramoduleEventSchedule serviceType) {
		getSession().saveOrUpdate(serviceType);
		getSession().flush();
		return serviceType;
	}

	@Override
	public HydramoduleEventSchedule getHydramoduleEventScedule(String uuid) {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleEventSchedule.class);
		criteria.add(Restrictions.eq("uuid", uuid));
		criteria.add(Restrictions.eq("voided", false));
		return (HydramoduleEventSchedule) criteria.uniqueResult();
	}

	@Override
	public List<HydramoduleEventSchedule> getAllHydramoduleEventScedules(boolean retired) {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleEventSchedule.class);
		criteria.addOrder(Order.asc("scheduleId"));
		criteria.add(Restrictions.eq("voided", retired));
		return criteria.list();
	}

	// EventType
	@Override
	public HydramoduleEventType saveHydramoduleEventType(HydramoduleEventType serviceType) {
		// System.out.println(serviceType.getUuid());
		getSession().saveOrUpdate(serviceType);
		getSession().flush();
		return serviceType;
	}

	@Override
	public HydramoduleEventType getHydramoduleEventType(String uuid) {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleEventType.class);
		criteria.add(Restrictions.eq("uuid", uuid));
		criteria.add(Restrictions.eq("retired", false));
		return (HydramoduleEventType) criteria.uniqueResult();
	}

	@Override
	public List<HydramoduleEventType> getAllHydramoduleEventTypes(boolean retired) {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleEventType.class);
		criteria.addOrder(Order.asc("eventTypeId"));
		criteria.add(Restrictions.eq("retired", retired));
		return criteria.list();
	}

	// EventAsset
	@Override
	public HydramoduleEventAsset saveHydramoduleEventAsset(HydramoduleEventAsset eventAsset) {
		/*
		 * HydramoduleAsset asset = eventAsset.getAsset(); asset =
		 * getAsset(asset.getUuid()); eventAsset.setAsset(asset);
		 * 
		 * HydramoduleEvent event = eventAsset.getEvent(); event =
		 * getHydramoduleEvent(event.getUuid()); eventAsset.setEvent(event);
		 */

		getSession().saveOrUpdate(eventAsset);
		getSession().flush();
		return eventAsset;
	}

	@Override
	public HydramoduleEventAsset getHydramoduleEventAsset(String uuid) {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleEventAsset.class);
		criteria.add(Restrictions.eq("uuid", uuid));
		criteria.add(Restrictions.eq("voided", false));
		return (HydramoduleEventAsset) criteria.uniqueResult();
	}

	@Override
	public List<HydramoduleEventAsset> getAllHydramoduleEventAssets(boolean retired) {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleEventAsset.class);
		criteria.addOrder(Order.asc("eventAssetId"));
		criteria.add(Restrictions.eq("voided", retired));
		return criteria.list();
	}

	// EventService
	@Override
	public HydramoduleEventService saveHydramoduleEventService(HydramoduleEventService serviceType) {
		// System.out.println(serviceType.getUuid());
		getSession().saveOrUpdate(serviceType);
		getSession().flush();
		return serviceType;
	}

	@Override
	public HydramoduleEventService getHydramoduleEventService(String uuid) {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleEventService.class);
		criteria.add(Restrictions.eq("uuid", uuid));
		criteria.add(Restrictions.eq("voided", false));
		return (HydramoduleEventService) criteria.uniqueResult();
	}

	@Override
	public List<HydramoduleEventService> getAllHydramoduleEventServices(boolean retired) {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleEventService.class);
		criteria.addOrder(Order.asc("eventServiceId"));
		criteria.add(Restrictions.eq("voided", retired));
		return criteria.list();
	}

	// EventParticipant
	@Override
	public HydramoduleEventParticipants saveHydramoduleEventParticipant(HydramoduleEventParticipants eventParticipants) {
		// System.out.println(serviceType.getUuid());
		if (eventParticipants.getParticipant() == null)
			return null;
		getSession().saveOrUpdate(eventParticipants);

		getSession().flush();
		return eventParticipants;
	}

	@Override
	public HydramoduleEventParticipants getHydramoduleEventParticipant(String uuid) {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleEventParticipants.class);
		criteria.add(Restrictions.eq("uuid", uuid));
		criteria.add(Restrictions.eq("voided", false));
		return (HydramoduleEventParticipants) criteria.uniqueResult();
	}

	@Override
	public List<HydramoduleEventParticipants> getAllHydramoduleEventParticipants(boolean retired) {
		DbSession session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HydramoduleEventParticipants.class);
		criteria.addOrder(Order.asc("eventParticipantId"));
		criteria.add(Restrictions.eq("voided", retired));
		return criteria.list();
	}

	// EventParticipant

}
