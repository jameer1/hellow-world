package com.rjtech.user.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rjtech.admin.repository.ApplicationGlobalSettingRepository;
import com.rjtech.common.constants.CommonConstants;
import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.model.EmailSettingEntity;
import com.rjtech.common.repository.EmailSettingRepository;
import com.rjtech.common.repository.LoginRepository;
import com.rjtech.common.req.UserGetReq;
import com.rjtech.common.resp.UserModulePermissionResp;
import com.rjtech.common.service.handler.EmailSettingHandler;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.UserTypes;
import com.rjtech.document.repository.EmpRegisterRepositoryCpy;
import com.rjtech.document.repository.ProjDocFileRepository;
import com.rjtech.document.repository.ProjDocFolderRepository;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.register.emp.model.EmpRegisterDtlEntity;
//import com.rjtech.register.model.EmpRegisterDtlEntity;
import com.rjtech.rjs.appuser.utils.AppUserUtils;
import com.rjtech.rjs.core.annotations.RJSService;
import com.rjtech.role.model.ApplicationGlobalSettingEntity;
import com.rjtech.role.repository.RolePermissionRepository;
import com.rjtech.user.dto.ClientRegTO;
import com.rjtech.user.dto.EmailSettingTO;
import com.rjtech.user.dto.UserProjDetailsTO;
import com.rjtech.user.dto.UserTO;
import com.rjtech.user.model.ClientRegMstrEntity;
import com.rjtech.user.model.UserEntity;
import com.rjtech.user.repository.ClientRepository;
import com.rjtech.user.repository.UserProjectsRepository;
import com.rjtech.user.repository.UserRepository;
import com.rjtech.user.req.ActionReq;
import com.rjtech.user.req.ClientGetReq;
import com.rjtech.user.req.ClientRegReq;
import com.rjtech.user.req.ClientReq;
import com.rjtech.user.req.EmailSettingDelReq;
import com.rjtech.user.req.EmailSettingGetReq;
import com.rjtech.user.req.EmailSettingSaveReq;
import com.rjtech.user.req.UserDeleteReq;
import com.rjtech.user.req.UserProjSaveReq;
import com.rjtech.user.req.UserSaveReq;
import com.rjtech.user.resp.ClientRegResp;
import com.rjtech.user.resp.EmailSettingResp;
import com.rjtech.user.resp.UserResp;
import com.rjtech.user.resp.UsersByClientResp;
import com.rjtech.user.service.UserService;
import com.rjtech.user.service.handler.UserServiceHandler;
import org.slf4j.Logger.*;
@Service(value = "userService")
@RJSService(modulecode = "userService")
@Transactional
@EnableAsync
public class UserServiceImpl implements UserService {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ApplicationGlobalSettingRepository applicationGlobalSettingRepository;

    @Autowired
    private UserProjectsRepository userProjectsRepository;

    @Autowired
    private EmailSettingRepository emailSettingRepository;

    @Autowired
    private EPSProjRepository epsProjRepository;

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private EmpRegisterRepositoryCpy empRegisterRepository;

    @Autowired
    private RolePermissionRepository rolePermissionRepository;
    
    @Autowired
    private ProjDocFolderRepository projDocFolderRepository;
    
    @Autowired
    private ProjDocFileRepository projDocFileRepository;

    public UserResp getVendorUsers(ClientReq clientReq) {
        String userName = null;
        String empCode = null;
        if (CommonUtil.isNotBlankStr(clientReq.getUserName())) {
            userName = CommonUtil.appendLikeOperator(clientReq.getUserName());
        }
        if (CommonUtil.isNotBlankStr(clientReq.getEmpCode())) {
            empCode = CommonUtil.appendLikeOperator(clientReq.getEmpCode());
        }

        List<UserEntity> userMstrs = userRepository.findVendorUsers(clientReq.getClientId(), userName, empCode,
                clientReq.getStatus());
        UserResp userResp = new UserResp();
        for (UserEntity userMstr : userMstrs) {
            userResp.getUsers().add(UserServiceHandler.convertUserEntityToPOJO(userMstr));
        }
        return userResp;
    }

    public UserResp getUsers(ClientReq clientReq) {
        String userName = null;
        String empCode = null;
        if (CommonUtil.isNotBlankStr(clientReq.getUserName())) {
            userName = CommonUtil.appendLikeOperator(clientReq.getUserName());
        }
        if (CommonUtil.isNotBlankStr(clientReq.getEmpCode())) {
            empCode = CommonUtil.appendLikeOperator(clientReq.getEmpCode());
        }

        log.info("Client ID {}", AppUserUtils.getClientId());
        log.info("User Name {}", userName);
        log.info("Emp Code {}", empCode);
        log.info("Status {}", clientReq.getStatus());

        List<UserEntity> userMstrs = userRepository.findUsers(AppUserUtils.getClientId(), userName, empCode,
                clientReq.getStatus());
        log.info("User Masters after db call {}", userMstrs.size());
        UserResp userResp = new UserResp();
        for (UserEntity userMstr : userMstrs) {
            userResp.getUsers().add(UserServiceHandler.convertUserEntityToPOJO(userMstr));
        }
        log.info("User TO {}", userResp.getUsers().size());
        return userResp;
    }

    public UserTO findByUserId(Long userId) {
        UserEntity userMstr = userRepository.findOne(userId);
        UserTO userTO = UserServiceHandler.convertUserEntityToPOJO(userMstr);
        if (CommonUtil.objectNotNull(userMstr.getEmpRegId())
                && CommonUtil.objectNotNull(userMstr.getEmpRegId().getProjMstrEntity())) {
            userTO.setProjId(userMstr.getEmpRegId().getProjMstrEntity().getProjectId());
            userTO.setName(userMstr.getEmpRegId().getProjMstrEntity().getProjName());
        }
        return userTO;
    }

    public void saveUser(UserSaveReq saveReq) {
        List<UserEntity> userMstrEntities = UserServiceHandler.converUserPOJOToEntity(saveReq, empRegisterRepository,
                clientRepository);
        List<ApplicationGlobalSettingEntity> appGlobalSetting=null;
        appGlobalSetting= applicationGlobalSettingRepository.findAll();
      
		    for (UserEntity userToSave : userMstrEntities) {
            UserEntity userFromDb = null;
            if (userToSave.getUserId() != null) {
                userFromDb = userRepository.findOne(userToSave.getUserId());
                if (userFromDb != null) {
                    userToSave.setClient(userFromDb.isClient());
                    userToSave.setPassword(userFromDb.getPassword());
                }
            }
            // Send mail on when profile is assigned
            if ((userFromDb == null || userFromDb.getUserRoleMstrs().isEmpty())
                    && !userToSave.getUserRoleMstrs().isEmpty()) {
            	 for (ApplicationGlobalSettingEntity applicationGlobalSettingEntity : appGlobalSetting) {
              
            		 String UrlwithDomain=applicationGlobalSettingEntity.getApplicationDomainName()+'/'+applicationGlobalSettingEntity.getAppClientUrl();
                    if (userToSave.isClient()) {
                    ClientRegMstrEntity clientReg = userToSave.getClientRegMstrEntity();
                   
                    sendEmail(userToSave.getEmail(), userToSave.getUserName(), userToSave.getPassword(),UrlwithDomain,
                            clientReg.getCode(), CommonUtil.convertDateToDDMMYYYYString(clientReg.getLicence()));
                    } else {
                    /*sendUserEmail(saveReq, null, AppUserUtils.getClientId(),
                            userToSave.getClientRegMstrEntity().getCode());*/
                    
                    sendUserEmail(userToSave.getEmail(), userToSave.getUserName(), userToSave.getPassword(),UrlwithDomain,AppUserUtils.getClientId(),
                            userToSave.getClientRegMstrEntity().getCode());
                }
            }
        }}
       
        userRepository.save(userMstrEntities);
    }

    public void saveVendorUser(UserSaveReq saveReq) {
        List<UserEntity> userMstrEntities = UserServiceHandler.converVendorUserPOJOToEntity(saveReq,
                empRegisterRepository);
        userRepository.save(userMstrEntities);
    }

    public void deactivateUsers(UserDeleteReq userDeleteReq) {
        userRepository.deactivateUsers(userDeleteReq.getUserIds(), userDeleteReq.getStatus());
    }

    public ClientRegResp getClients(ClientGetReq clientGetReq) {
        ClientRegResp clientRegResp = new ClientRegResp();
        ClientRegTO clientRegTO = null;
        List<UserEntity> userMstrEntities = userRepository.findClientParentUser(UserTypes.EXTERNAL.getValue(),
                clientGetReq.getStatus());
        for (UserEntity userEntity : userMstrEntities) {
            if (CommonUtil.objectNotNull(userEntity.getClientRegMstrEntity())) {
                clientRegTO = UserServiceHandler.convertClientEntiryToPOJO(userEntity.getClientRegMstrEntity());
                clientRegTO.setUserTO(UserServiceHandler.convertUserEntityToPOJO(userEntity));
                clientRegResp.getClientRegTOs().add(clientRegTO);
            }
        }

        return clientRegResp;
    }

    public ClientRegTO saveClient(ClientRegReq clientRegReq) {
        ClientRegTO clientTo = clientRegReq.getClientRegTO();
        ClientRegMstrEntity clientFromDb = null;
		UserEntity userEntity = null;
        if (clientTo.getId() != null) {
            clientFromDb = clientRepository.findOne(clientTo.getId());
        }
        ClientRegMstrEntity clientRegMstrEntity = clientRepository
                .save(UserServiceHandler.convertClientPOJOToEntity(clientFromDb, clientTo, projDocFolderRepository, projDocFileRepository, clientRegReq.getFolderCategory()));
        if (clientRegReq.getClientRegTO().isSaveFlag()) {
            userEntity = userRepository.save( UserServiceHandler.populateUserEntityFromClientTO(clientRegMstrEntity, clientRegReq.getClientRegTO()) );
        }else{
			userEntity = UserServiceHandler.populateUserEntityFromClientTO(clientRegMstrEntity, clientRegReq.getClientRegTO());
		}
        
        Long proj_document_file_id = clientRegMstrEntity.getProjDocFile().getId();
        projDocFileRepository.updateCreatedUserAndUpdatedUserById( proj_document_file_id, userEntity.getUserId() );
        return UserServiceHandler.convertClientUserEntiryToPOJO(clientRegMstrEntity, UserServiceHandler.convertUserEntityToPOJO(userEntity));
    }

    public void deleteClientUser(Long clientId) {
        clientRepository.delete(clientId);
    }

    public void deactivateClient(ClientReq clientReq) {
        clientRepository.deactivateClient(clientReq.getClientId(), clientReq.getStatus());
        clientRepository.deactivateUsersByClientId(clientReq.getClientId(), clientReq.getStatus());
    }

    @Transactional
    public void saveUserProjects(UserProjSaveReq userProjSaveReq) {
        userProjectsRepository.save(
                UserServiceHandler.populateUserProjectsFromPOJO(userProjSaveReq, loginRepository, epsProjRepository));

        List<Long> projIds = new ArrayList<Long>();
        if (CommonUtil.isListHasData(userProjSaveReq.getUserProjectTOs())) {
            for (UserProjDetailsTO userProjDetailsTO : userProjSaveReq.getUserProjectTOs()) {
                projIds.add(userProjDetailsTO.getProjId());
            }
        }
        if (CommonUtil.isListHasData(projIds)) {
            epsProjRepository.epsAssignedProjectsStatus(projIds, true);
        }
    }

    public EmailSettingResp getEmailSettings(EmailSettingGetReq emailSettingGetReq, Long clientId) {
        EmailSettingResp emailSettingResp = new EmailSettingResp();
        List<EmailSettingEntity> emailSettingEntities = emailSettingRepository.findEmailSettings(clientId,
                emailSettingGetReq.getStatus());

        for (EmailSettingEntity emailSettingEntity : emailSettingEntities) {
            emailSettingResp.getEmailSettingTOs().add(EmailSettingHandler.converEntityToPOJO(emailSettingEntity));
        }
        return emailSettingResp;
    }

    public void saveEmailSettings(EmailSettingSaveReq emailSettingSaveReq) {
        List<EmailSettingEntity> emailSettingEntities = new ArrayList<EmailSettingEntity>();
        for (EmailSettingTO emailSettingTO : emailSettingSaveReq.getEmailSettingTOs()) {
            emailSettingEntities.add(EmailSettingHandler.converPOJOToEntity(emailSettingTO));
        }
        emailSettingRepository.save(emailSettingEntities);
    }

    public void deactivateEmailSettings(EmailSettingDelReq emailSettingDelReq) {
        emailSettingRepository.deactivateEmailSettings(emailSettingDelReq.getEmailSettingIds(),
                emailSettingDelReq.getStatus());
    }

    public UserModulePermissionResp getUsersByModulePermission(UserGetReq userGetReq) {
        UserModulePermissionResp userModulePermissionResp = new UserModulePermissionResp();
        List<Object[]> userMstrEntities = rolePermissionRepository
                .getUsersByModulePermission(userGetReq.getPermission(), AppUserUtils.getClientId());
        LabelKeyTO userLabelKeyTO = null;
        if (CommonUtil.isListHasData(userMstrEntities)) {
            for (Object[] userMstrEntity : userMstrEntities) {
                userLabelKeyTO = new LabelKeyTO();
                userLabelKeyTO.setId(Long.valueOf(userMstrEntity[0].toString()));
                if (userMstrEntity[1] != null && CommonUtil.isNotBlankStr(userMstrEntity[1].toString())) {
                    userLabelKeyTO.setName(userMstrEntity[1].toString());
                }
                if (userMstrEntity[2] != null && CommonUtil.isNotBlankStr(userMstrEntity[2].toString())) {
                    userLabelKeyTO.setEmail(userMstrEntity[2].toString());
                }
                userModulePermissionResp.getUsers().add(userLabelKeyTO);
            }
        }
        return userModulePermissionResp;
    }

    public UsersByClientResp getUsersByClientId(ActionReq actionReq) {
        UsersByClientResp usersByClientResp = new UsersByClientResp();
        List<EmpRegisterDtlEntity> registerDtlEntities = empRegisterRepository
                .findNewEmployees(AppUserUtils.getClientId(), 1);
        List<LabelKeyTO> labelKeyTOs = usersByClientResp.getLabelKeyTOs();
        for (EmpRegisterDtlEntity entity : registerDtlEntities) {
            LabelKeyTO labelKeyTO = new LabelKeyTO();
            labelKeyTO.setId(entity.getId());
            labelKeyTO.setCode(entity.getCode());
            Map<String, String> displayMap = labelKeyTO.getDisplayNamesMap();
            displayMap.put(CommonConstants.FIRST_NAME, entity.getFirstName());
            displayMap.put(CommonConstants.LAST_NAME, entity.getLastName());
            displayMap.put(CommonConstants.GENDER, entity.getGender());
            displayMap.put(CommonConstants.DISPLAY_NAME, entity.getFirstName() + " " + entity.getLastName());
            if (CommonUtil.objectNotNull(entity.getEmpClassMstrEntity()))
                displayMap.put(CommonConstants.DESIGNATION, entity.getEmpClassMstrEntity().getName());
            if (CommonUtil.objectNotNull(entity.getCompanyMstrEntity()))
                displayMap.put(CommonConstants.COMPANY_CATG_NAME, entity.getCompanyMstrEntity().getName());
            labelKeyTO.setDisplayNamesMap(displayMap);
            labelKeyTOs.add(labelKeyTO);
        }
        return usersByClientResp;
    }

    @Async
    private void sendEmail(String email, String userName, String password,String Url, String clientCode, String licence) {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost("mail.rajutech.com");
        sender.setPort(587);
        sender.setUsername("rtadmin@rajutech.com");
        sender.setPassword("RTadmin@2018");

        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.starttls.enable", "true");
        javaMailProperties.put("mail.smtp.auth", "true");
        javaMailProperties.put("mail.transport.protocol", "smtp");
        javaMailProperties.put("mail.debug", "true");

        sender.setJavaMailProperties(javaMailProperties);
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setFrom("rtadmin@rajutech.com");
            helper.setTo(email);
            helper.setText("You are registered for Project On Track:\n\n"
            		+ "Login URL : " + Url
                    + "\n\nUser Name : " + userName
                    + "\n\nPassword : " + password 
                    + "\n\nYour Company Client Code : " + clientCode
                    + "\n\nYour Company Renewal Date : " + licence + "\n\nRegards - Admin");
            helper.setSubject("Client Registration");
            sender.send(message);
        } catch (MailException e) {
            log.error("Mail Exception ", e);
        } catch (MessagingException e) {
            log.error("Messaging Exception ", e);
        }
    }

    @Scheduled(cron = "0 0 0 * * *")
    @Async
    public void LienceExpiredMsgAllClient() {
        System.out.println("LienceExpired Client==========");
        String nextDate = "";

        LocalDate currnetDate = LocalDate.now();
        currnetDate = currnetDate.plusDays(1);
        nextDate = currnetDate.toString();

        Date nextDate1 = CommonUtil.convertStringToDate(nextDate);
        List<ClientRegMstrEntity> clientLicenceMsg = clientRepository.findLienceExpiredMsgAllClient(nextDate1);

        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost("mail.rajutech.com");
        sender.setPort(587);
        sender.setUsername("rtadmin@rajutech.com");
        sender.setPassword("RTadmin@2018");

        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.starttls.enable", "true");
        javaMailProperties.put("mail.smtp.auth", "true");
        javaMailProperties.put("mail.transport.protocol", "smtp");
        javaMailProperties.put("mail.debug", "true");

        sender.setJavaMailProperties(javaMailProperties);
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setFrom("rtadmin@rajutech.com");
            if (clientLicenceMsg != null) {
                for (ClientRegMstrEntity clientRegReq : clientLicenceMsg) {
                    helper.setTo(clientRegReq.getEmail());
                    helper.setText(
                            "Your Licence for Raju Tech India Pvt Ltd will expire on : " + clientRegReq.getLicence());
                    helper.setSubject("Notification For Licence Renwal");
                    sender.send(message);
                }
            }
        } catch (MailException e) {
            log.info("Mail Exception {}", e);

        } catch (MessagingException e) {
            log.info("Messaging Exception {}", e);
        } catch (Exception e) {
            log.info("Exception", e);

        }

    }

    @Async
    private void sendUserEmail(UserSaveReq userSaveReq, UserResp userResp, Long clientId, String clientCode) {

        EmailSettingGetReq emailSettingGetReq = new EmailSettingGetReq();
        EmailSettingResp emailSettingResp = getEmailSettings(emailSettingGetReq, clientId);

        List<EmailSettingTO> emailSettingsTo = emailSettingResp.getEmailSettingTOs();
        for (EmailSettingTO emailSettingTo : emailSettingsTo) {
            JavaMailSenderImpl sender = new JavaMailSenderImpl();
            sender.setHost(emailSettingTo.getHost());
            sender.setPort(emailSettingTo.getPort());
            sender.setUsername(emailSettingTo.getFromEmail());
            sender.setPassword(emailSettingTo.getPassword());

            Properties javaMailProperties = new Properties();
            javaMailProperties.put("mail.smtp.starttls.enable", "true");
            javaMailProperties.put("mail.smtp.auth", "true");
            javaMailProperties.put("mail.transport.protocol", "smtp");
            javaMailProperties.put("mail.debug", "true");

            sender.setJavaMailProperties(javaMailProperties);
            MimeMessage message = sender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            try {
                helper.setFrom(emailSettingTo.getFromEmail());

                List<UserTO> userTos = userSaveReq.getUserTOs();
                for (UserTO userTo : userTos) {
                    String userName = null;
                    String password = null;
                    if (userTo.getUserId() != null) {
                        UserEntity userEntity = userRepository.findOne(userTo.getUserId());
                        userName = userEntity.getUserName();
                        password = userEntity.getPassword();
                    } else {
                        userName = userTo.getUserName();
                        password = userTo.getPassword();
                    }
                    helper.setTo(userTo.getEmail());
                    helper.setSubject("User Registration");
                    helper.setText("You are registered for Raju Tech India Pvt Ltd:\n\n" + "User Name : " + userName
                            + "\n\nPassword : " + password + "\n\nYour Company Client Code : " + clientCode);
                    sender.send(message);
                }

            } catch (MailException e) {
                log.info("Mail Exception {}", e);

            } catch (MessagingException e) {
                log.info("Messaging Exception {}", e);
            }
        }
    }
    
    @Async
    private void sendUserEmail(String email, String userName, String password,String Url, Long clientId, String clientCode) {

        EmailSettingGetReq emailSettingGetReq = new EmailSettingGetReq();
        EmailSettingResp emailSettingResp = getEmailSettings(emailSettingGetReq, clientId);

        List<EmailSettingTO> emailSettingsTo = emailSettingResp.getEmailSettingTOs();
        for (EmailSettingTO emailSettingTo : emailSettingsTo) {
            JavaMailSenderImpl sender = new JavaMailSenderImpl();
            sender.setHost(emailSettingTo.getHost());
            sender.setPort(emailSettingTo.getPort());
            sender.setUsername(emailSettingTo.getFromEmail());
            sender.setPassword(emailSettingTo.getPassword());

            Properties javaMailProperties = new Properties();
            javaMailProperties.put("mail.smtp.starttls.enable", "true");
            javaMailProperties.put("mail.smtp.auth", "true");
            javaMailProperties.put("mail.transport.protocol", "smtp");
            javaMailProperties.put("mail.debug", "true");

            sender.setJavaMailProperties(javaMailProperties);
            MimeMessage message = sender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            try {
                helper.setFrom(emailSettingTo.getFromEmail());
                    helper.setTo(email);
                    helper.setSubject("User Registration");
                    helper.setText("You are registered for Raju Tech India Pvt Ltd:\n\n" 
                    + "Go with Url: " + Url 
                    + "\n\nUser Name : " + userName
                    + "\n\nPassword : " + password 
                    + "\n\nYour Company Client Code : " + clientCode);
                    sender.send(message);
            } catch (MailException e) {
                log.info("Mail Exception {}", e);

            } catch (MessagingException e) {
                log.info("Messaging Exception {}", e);
            }
        }
    }

    public Long getClientByEmail(String email) {
        return clientRepository.findClientByEmail(email);
    }

    public ClientRegTO getClientById(Long clientId) {
        return UserServiceHandler.convertClientEntiryToPOJO(clientRepository.findOne(clientId));
    }

    public void activateUsers(UserDeleteReq userDeleteReq) {
        userRepository.activateUsers(userDeleteReq.getUserIds(), userDeleteReq.getStatus());
    }

}
