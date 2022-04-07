

update time_sheet_mstr set TSM_APPR_STATUS = 'Under Preparation' where TSM_APPR_STATUS is null;
commit;