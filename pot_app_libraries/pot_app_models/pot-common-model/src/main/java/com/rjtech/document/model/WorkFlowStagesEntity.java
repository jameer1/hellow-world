
package com.rjtech.document.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import com.rjtech.document.model.TemplateCategoriesEntity;
import com.rjtech.document.model.SampleTemplatesEntity;
import com.rjtech.document.model.CentralTemplatesEntity;
import com.rjtech.document.model.ProjectTemplatesEntity;
import com.rjtech.document.model.CategoriesMstrEntity;
import com.rjtech.common.model.UserMstrEntity;

/**
 * The persistent class for the templates_workflow_stages database table.
 * 
 */
@Entity
@Table(name = "templates_workflow_stages")
public class WorkFlowStagesEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WORKFLOW_ID")
    private Long workflowId;

    /*@OneToOne
    @JoinColumn(name = "SAMPLE_TEMPLATE_ID", updatable = false)
    private SampleTemplatesEntity sampleTemplateId;

    @OneToOne
    @JoinColumn(name = "CENTRAL_TEMPLATE_ID", updatable = false)
    private CentralTemplatesEntity centralTemplateId;
    
    @OneToOne
    @JoinColumn(name = "PROJECT_TEMPLATE_ID", updatable = false)
    private ProjectTemplatesEntity projectTemplateId;
    
    @OneToOne
    @JoinColumn(name = "TEMPLATE_CATEGORY_ID", updatable = false)
    private TemplateCategoriesEntity templateCategoryId;
    
    @ManyToOne
    @JoinColumn(name = "CATEGORY_MSTR_ID", updatable = false)
    private CategoriesMstrEntity categoryMstrId;*/
    
    @Column(name = "STAGES_COUNT")
    private Integer stagesCount;
    
    @Column(name = "STAGE_1_FIELD")
    private String stage1Field;
    
    @Column(name = "STAGE_1_NAME")
    private String stage1Name;
    
    @Column(name = "STAGE_1_STATUS")
    private String stage1Status;
    
    @Column(name = "STAGE_2_FIELD")
    private String stage2Field;
    
    @Column(name = "STAGE_2_NAME")
    private String stage2Name;
    
    @Column(name = "STAGE_2_STATUS")
    private String stage2Status;
    
    @Column(name = "STAGE_3_FIELD")
    private String stage3Field;
    
    @Column(name = "STAGE_3_NAME")
    private String stage3Name;
    
    @Column(name = "STAGE_3_STATUS")
    private String stage3Status;
    
    @Column(name = "STAGE_4_FIELD")
    private String stage4Field;
    
    @Column(name = "STAGE_4_NAME")
    private String stage4Name;
    
    @Column(name = "STAGE_4_STATUS")
    private String stage4Status;
    
    @Column(name = "STAGE_5_FIELD")
    private String stage5Field;
    
    @Column(name = "STAGE_5_NAME")
    private String stage5Name;
    
    @Column(name = "STAGE_5_STATUS")
    private String stage5Status;
    
    @Column(name = "STAGE_6_FIELD")
    private String stage6Field;
    
    @Column(name = "STAGE_6_NAME")
    private String stage6Name;
    
    @Column(name = "STAGE_6_STATUS")
    private String stage6Status;
    
    @Column(name = "STAGE_1_APPROVER_COMMENTS")
    private String stage1ApproverComments;
    
    @Column(name = "STAGE_2_APPROVER_COMMENTS")
    private String stage2ApproverComments;
    
    @Column(name = "STAGE_3_APPROVER_COMMENTS")
    private String stage3ApproverComments;
    
    @Column(name = "STAGE_4_APPROVER_COMMENTS")
    private String stage4ApproverComments;
    
    @Column(name = "STAGE_5_APPROVER_COMMENTS")
    private String stage5ApproverComments;
    
    @Column(name = "STAGE_6_APPROVER_COMMENTS")
    private String stage6ApproverComments;
    
    @ManyToOne
    @JoinColumn(name = "STAGE_1_APPROVER_ID")
    private UserMstrEntity stage1ApproverId;
    
    @ManyToOne
    @JoinColumn(name = "STAGE_2_APPROVER_ID")
    private UserMstrEntity stage2ApproverId;
    
    @ManyToOne
    @JoinColumn(name = "STAGE_3_APPROVER_ID")
    private UserMstrEntity stage3ApproverId;
    
    @ManyToOne
    @JoinColumn(name = "STAGE_4_APPROVER_ID")
    private UserMstrEntity stage4ApproverId;
    
    @ManyToOne
    @JoinColumn(name = "STAGE_5_APPROVER_ID")
    private UserMstrEntity stage5ApproverId;
    
    @ManyToOne
    @JoinColumn(name = "STAGE_6_APPROVER_ID")
    private UserMstrEntity stage6ApproverId;
    
    @Column(name = "STATUS")
    private String status;
    
    public Long getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId( Long workflowId ) {
        this.workflowId = workflowId;
    }
    
    /*public SampleTemplatesEntity getSampleTemplateId() {
    	return sampleTemplateId;
    }
    
    public void setSampleTemplateId( SampleTemplatesEntity sampleTemplateId ) {
    	this.sampleTemplateId = sampleTemplateId;
    }
    
    public CentralTemplatesEntity getCentralTemplateId() {
    	return centralTemplateId;
    }
    
    public void setCentralTemplateId( CentralTemplatesEntity centralTemplateId ) {
    	this.centralTemplateId = centralTemplateId;
    }
    
    public ProjectTemplatesEntity getProjectTemplateId() {
    	return projectTemplateId;
    }
    
    public void setProjectTemplateId( ProjectTemplatesEntity projectTemplateId ) {
    	this.projectTemplateId = projectTemplateId;
    }
    
    public void setTemplateCategoryId( TemplateCategoriesEntity templateCategoryId ) {
        this.templateCategoryId = templateCategoryId;
    }

    public TemplateCategoriesEntity getTemplateCategoryId() {
        return templateCategoryId;
    }    
    
    public CategoriesMstrEntity getCategoryMstrId() {
    	return categoryMstrId;
    }
    
    public void setCategoryMstrId( CategoriesMstrEntity categoryMstrId ) {
    	this.categoryMstrId = categoryMstrId;
    }*/
    
    public String getStage1Field() {
        return stage1Field;
    }

    public void setStage1Field(String stage1Field) {
        this.stage1Field = stage1Field;
    }
    
    public String getStage1Name() {
        return stage1Name;
    }

    public void setStage1Name(String stage1Name) {
        this.stage1Name = stage1Name;
    }
    
    public String getStage1Status() {
        return stage1Status;
    }

    public void setStage1Status(String stage1Status) {
        this.stage1Status = stage1Status;
    }
    
    public String getStage2Field() {
        return stage2Field;
    }

    public void setStage2Field(String stage2Field) {
        this.stage2Field = stage2Field;
    }
    
    public String getStage2Name() {
        return stage2Name;
    }

    public void setStage2Name(String stage2Name) {
        this.stage2Name = stage2Name;
    }
    
    public String getStage2Status() {
        return stage2Status;
    }

    public void setStage2Status(String stage2Status) {
        this.stage2Status = stage2Status;
    }
    
    public String getStage3Field() {
        return stage3Field;
    }

    public void setStage3Field(String stage3Field) {
        this.stage3Field = stage3Field;
    }
    
    public String getStage3Name() {
        return stage3Name;
    }

    public void setStage3Name(String stage3Name) {
        this.stage3Name = stage3Name;
    }
    
    public String getStage3Status() {
        return stage3Status;
    }

    public void setStage3Status(String stage3Status) {
        this.stage3Status = stage3Status;
    }
    
    public String getStage4Field() {
        return stage4Field;
    }

    public void setStage4Field(String stage4Field) {
        this.stage4Field = stage4Field;
    }
    
    public String getStage4Name() {
        return stage4Name;
    }

    public void setStage4Name(String stage4Name) {
        this.stage4Name = stage4Name;
    }
    
    public String getStage4Status() {
        return stage4Status;
    }

    public void setStage4Status(String stage4Status) {
        this.stage4Status = stage4Status;
    }
    
    public String getStage5Field() {
        return stage5Field;
    }

    public void setStage5Field(String stage5Field) {
        this.stage5Field = stage5Field;
    }
    
    public String getStage5Name() {
        return stage5Name;
    }

    public void setStage5Name(String stage5Name) {
        this.stage5Name = stage5Name;
    }
    
    public String getStage5Status() {
        return stage5Status;
    }

    public void setStage5Status(String stage5Status) {
        this.stage5Status = stage5Status;
    }
    
    public String getStage6Field() {
        return stage6Field;
    }

    public void setStage6Field(String stage6Field) {
        this.stage6Field = stage6Field;
    }
    
    public String getStage6Name() {
        return stage6Name;
    }

    public void setStage6Name(String stage6Name) {
        this.stage6Name = stage6Name;
    }
    
    public String getStage6Status() {
        return stage6Status;
    }

    public void setStage6Status(String stage6Status) {
        this.stage6Status = stage6Status;
    }
    
    public Integer getStagesCount() {
        return stagesCount;
    }

    public void setStagesCount(Integer stagesCount) {
        this.stagesCount = stagesCount;
    }
    
    public String getStage1ApproverComments() {
        return stage1ApproverComments;
    }

    public void setStage1ApproverComments(String stage1ApproverComments) {
        this.stage1ApproverComments = stage1ApproverComments;
    }
    
    public String getStage2ApproverComments() {
        return stage2ApproverComments;
    }

    public void setStage2ApproverComments(String stage2ApproverComments) {
        this.stage2ApproverComments = stage2ApproverComments;
    }
    
    public String getStage3ApproverComments() {
        return stage3ApproverComments;
    }

    public void setStage3ApproverComments(String stage3ApproverComments) {
        this.stage3ApproverComments = stage3ApproverComments;
    }
    
    public String getStage4ApproverComments() {
        return stage4ApproverComments;
    }

    public void setStage4ApproverComments(String stage4ApproverComments) {
        this.stage4ApproverComments = stage4ApproverComments;
    }
    
    public String getStage5ApproverComments() {
        return stage5ApproverComments;
    }

    public void setStage5ApproverComments( String stage5ApproverComments ) {
        this.stage5ApproverComments = stage5ApproverComments;
    }
    
    public String getStage6ApproverComments() {
        return stage6ApproverComments;
    }

    public void setStage6ApproverComments( String stage6ApproverComments ) {
        this.stage6ApproverComments = stage6ApproverComments;
    }
    
    public UserMstrEntity getStage1ApproverId() {
        return stage1ApproverId;
    }

    public void setStage1ApproverId( UserMstrEntity stage1ApproverId ) {
        this.stage1ApproverId = stage1ApproverId;
    }
    
    public UserMstrEntity getStage2ApproverId() {
        return stage2ApproverId;
    }

    public void setStage2ApproverId( UserMstrEntity stage2ApproverId ) {
        this.stage2ApproverId = stage2ApproverId;
    }
    
    public UserMstrEntity getStage3ApproverId() {
        return stage3ApproverId;
    }

    public void setStage3ApproverId( UserMstrEntity stage3ApproverId ) {
        this.stage3ApproverId = stage3ApproverId;
    }
    
    public UserMstrEntity getStage4ApproverId() {
        return stage4ApproverId;
    }

    public void setStage4ApproverId( UserMstrEntity stage4ApproverId ) {
        this.stage4ApproverId = stage4ApproverId;
    }
    
    public UserMstrEntity getStage5ApproverId() {
        return stage5ApproverId;
    }

    public void setStage5ApproverId( UserMstrEntity stage5ApproverId ) {
        this.stage5ApproverId = stage5ApproverId;
    }
    
    public UserMstrEntity getStage6ApproverId() {
        return stage6ApproverId;
    }

    public void setStage6ApproverId( UserMstrEntity stage6ApproverId ) {
        this.stage6ApproverId = stage6ApproverId;
    }
    
    public String getStatus() {
        return status;
    }

    public void setStatus( String status ) {
        this.status = status;
    }
    
    public String toString()
    {
    	return "From WorkFlowStagesEntity Template Id: "+" stage1Field:"+stage1Field+" stage1Status:"+stage1Status+" stage2Field:"+stage2Field+" stage2Status:"+stage2Status+" stage3Field:"+stage3Field+" stage3Status:"+stage3Status+" stage4Field:"+stage4Field+" stage4Status:"+stage4Status+" stage5Field:"+stage5Field+" stage5Status:"+stage5Status+" stage6Field:"+stage6Field+" stage6Status:"+stage6Status+" status:"+status;
    }    
}