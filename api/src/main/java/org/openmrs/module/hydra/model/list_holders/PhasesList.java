package org.openmrs.module.hydra.model.list_holders;

import java.util.List;

import org.openmrs.BaseOpenmrsData;
import org.openmrs.module.hydra.model.workflow.HydramodulePhase;

public class PhasesList extends BaseOpenmrsData {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4032570248987587995L;

	private int PhaseId;

	private List<HydramodulePhase> listAttributes;

	@Override
	public Integer getId() {
		return PhaseId;
	}

	@Override
	public void setId(Integer id) {
		this.PhaseId = id;
	}

	public List<HydramodulePhase> getListAttributes() {
		return listAttributes;
	}

	public void setListAttributes(List<HydramodulePhase> listAttributes) {
		this.listAttributes = listAttributes;
	}

	public static PhasesList createFromPhases(List<HydramodulePhase> phases) {
		PhasesList list = new PhasesList();
		list.setListAttributes(phases);
		return list;
	}

}
