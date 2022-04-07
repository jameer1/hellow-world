package com.rjtech.projectlib.service;

import java.util.List;
import java.util.Map;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.projectlib.req.ProjDeleteReq;
import com.rjtech.projectlib.req.ProjFilterReq;
import com.rjtech.projectlib.req.ProjGetReq;
import com.rjtech.projectlib.req.ProjSaveReq;
import com.rjtech.projectlib.resp.ProjectResp;
import com.rjtech.user.req.UserProjGetReq;
import com.rjtech.user.resp.UserProjResp;

public abstract interface EPSProjService {
    ProjectResp getEPSProjects(ProjGetReq projGetReq);

    ProjectResp getEPSProjectsById(ProjGetReq projGetReq);

    ProjectResp getProjectEps(ProjGetReq projGetReq);

    ProjectResp getProjectsById(ProjFilterReq projFilterReq);

    ProjectResp getProjects(ProjGetReq projGetReq);

    void saveProject(ProjSaveReq projSaveReq);

    void deleteProject(ProjDeleteReq projDeleteReq);

    UserProjResp getAllUserProjects(UserProjGetReq userProjGetReq);

    ProjectResp getEPSUserProjects(UserProjGetReq userProjGetReq);

    UserProjResp getProjectsByUserId(UserProjGetReq userProjGetReq);

    void deactivateProjectEps(ProjDeleteReq projDeleteReq);

    ProjectResp getEPSOnly(ProjGetReq projGetReq);

    Map<Long, LabelKeyTO> getUserProjects();

    List<Long> getUserProjIds();

    UserProjResp getAllProjects();

    public void deactivateEps(ProjDeleteReq projDeleteReq);

}
