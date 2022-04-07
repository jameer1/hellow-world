package com.rjtech.projectlib.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.rjtech.projectlib.model.ChangeOrderMstrEntity;
import com.rjtech.projsettings.model.ProjManpowerEntity;
import com.rjtech.projsettings.model.ProjectPlantsDtlEntity;
import com.rjtech.common.model.UserMstrEntity;
//import com.rjtech.projectlib.model.ProjManpowerEntity;

//import com.rjtech.projectlib.model.ProjectPlantsDtlEntityCopy;

/**
 * The persistent class for the change_order_map database table.
 * 
 */

@Entity
@Table(name = "change_order_map")
public class ChangeOrderMapEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CO_MAP_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "CO_MSTR_ID_FK")
    private ChangeOrderMstrEntity coMstrId;
    
    @ManyToOne
    @JoinColumn(name = "MANPOWER_ID_FK")
    private ProjManpowerEntity manpowerId;
    
    @ManyToOne
    @JoinColumn(name = "PLANT_ID_FK")
    private ProjectPlantsDtlEntity plantId;
    
    
    public Long getId() {
        return id;
    }

    public void setId( Long id ) {
        this.id = id;
    }
    
    public ChangeOrderMstrEntity getCoMstrId() {
        return coMstrId;
    }

    public void setCoMstrId( ChangeOrderMstrEntity coMstrId ) {
        this.coMstrId = coMstrId;
    }
   
    public ProjManpowerEntity getManpowerId() {
        return manpowerId;
    }

    public void setManpowerId( ProjManpowerEntity manpowerId ) {
        this.manpowerId = manpowerId;
    }
    
    public ProjectPlantsDtlEntity getPlantId() {
        return plantId;
    }

    public void setPlantId( ProjectPlantsDtlEntity plantId ) {
        this.plantId = plantId;
    }
}