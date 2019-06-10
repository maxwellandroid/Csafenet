package com.maxwell.csafenet;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

public class StringConstants {

    public static String mainUrl="http://www.csafenet.com/api/";
    public static String loginUrl="csalogin.php";
    public static String newObservation="insertobservation_details.php";
    public static String updateObservationUrl="editobservance.php";
    public static String viewMyObservation="viewmyobservation.php";
    public static String viewObservationForAction="observation_for_action.php";
    public static String projectsListUrl="project.php";
    public static String companyListUrl="company.php";
    public static String locationListUrl="location.php";
    public static String safetyCategoryListUrl="safty_category.php";
    public static String riskListUrl="risk_category.php";
    public static String rootCauseListUrl="rootcause_category.php";
    public static String actionByListUrl ="actionby.php";
    public static String healthandwelfareurl ="occupation_health_and_welfare.php";
    public static String ocupationalsafetyUrl ="occupationalsafety.php";
    public static String enviromentUrl ="environment.php";
    public static String technicalSafetyUrl ="technical_and_processed_safety.php";
    public static String incidentReportingInvestigationUrl ="incident_reporting_and_investigation.php";
    public static String secuirityManagmentUrl ="security_management.php";
    public static String offShoreSafetytUrl ="offshore_safety.php";
    public static String riskManagmentUrl ="hse_risk_management.php";
    public static String emergencyManagmentUrl ="emergency_and_crisis_management.php";
    public static String observationStatisticsUrl="observation_statistics.php";
    public static String topObserverUrl="top_observer.php";
    public static String hseAcheivmentphotosUrl="HSE_Achievements_photos.php";
    public static String hseAcheivmentlettersUrl="HSE_Achievements_letters.php";
    public static String hseOrganisationChartUrl="HSE_Organisation_Chat.php";
    public static String hsemanagementSystemUrl="HSE-Management-System.php";
    public static String hseActionTrackingUrl="action_item_tracking_register.php";
    public static String updateActionByUrl="edit_review_observation.php";
    public static String observationsOpenedUrl="Action_tracking_observations_open.php";
    public static String observationsClosedUrl="Action_tracking_observations_close.php";
    public static String onlineTestResultUrl="online_training_result.php";
    public static String immediateNotificationUrl="initial_incident_notification.php";
    public static String securityIncidentReportUrl="security_incident_report.php";
    public static String correctiveActionReportUrl="corrective_action_report.php";
    public static String incidentNotificationReportUrl="incident_notification_report.php";
    public static String userprofileUrl="view_profile.php?";
    public static String complianceInspectionUrl="hse_compliance_inspection.php";
    public static String auditModuleUrl="audit_module.php";
    public static String onlineTrainingUrl="online_training.php";
    public static String hseComplianceAnswers ="hse_compliance_answered_question.php";
    public static String hseAuditAnswers ="hse_audit_answer_question.php";
    public static String imssUrl="intergrated_images.php";
    public static String goldenRulesUrl="golden_rule_images.php";
    public static String lifesavingUrl="livesaverimages.php";
    public static String viewAnsweredQuestionsUrl="view_hse_onsubmit.php";
    public static String hseBbulletinsUrl="hsc_communication_bulletin.php";
    public static String hseAlertsUrl="hsc_communication_alerts.php";
    public static String changePasswordUrl="change_password.php";
    public static String addInitialIncidentNotificationUrl="initial_incident_add.php";
    public static String specificLocationUrl="specific_location.php";
    public static String hseTrainigPassportUrl="online_training_passport.php?";
    public static String headCountTrackerUrl="view_crisis_emergency_headcount.php?";
    public static String cevsUrl="view_verification_emergency.php?";
    public static String statusListingUrl="status.php";
    public static String cevsStatusUpdateUrl="insert_emergency_verification.php";
    public static String locationWiseQuestionsUrl="hse_compliance_with_location.php?";
    public static String locationWiseQuestionsAuditUrl="hse_audit_with_location.php?";
    public static String emergencyCrisisNotificationUrl="emergency_crisis_notification.php?";
    public static String viewComplianceModuleUrl="view_compliance.php?field_id=";
    public static String viewAuditModuleUrl="view_audit.php?field_id=";
    public static String updateHeadCountUserStatusUrl="insert_headcount.php?";
    public static String checkHeadCountUserStatusUrl="view_crisis_verify_headcount.php?";
    public static String equipmentsAssignedUrl="list_of_equipment.php";

    public static String inputOldPassword ="old_password";
    public static String inputNewPassword="new_password";
    public static String inputUsername="employee_no";
    public static String inputPassword="apassword";
    public static String inputUserID="user_id";
    public static String inputRegCode="reg_code";
    public static String inputEmployeeNumber="employeeno";
    public static String inputDesignation="designation";
    public static String inputDate="enter_date";
    public static String inputTime="enter_time";
    public static String inputRegDate="reg_date";
    public static String inputRegTime="reg_time";
    public static String inputCompany="company";
    public static String inputProject="project";
    public static String inputLocation="location";
    public static String inputSafetyCategory="safety";
    public static String inputObservationType="observation_type";
    public static String inputRisk="risk";
    public static String inputRoot="root";
    public static String inputRootOther="root_others";
    public static String inputRiskOthers="risk_others";
    public static String inputActivityDescription="action_desc";
    public static String inputunsafeCodition="unsafe_condition";
    public static String inputCorrectiveActionTaken="action_taken";
    public static String inputRecommendation="recommend";
    public static String inputActionBy="action_by";
    public static String inputEnterbyId="entered_by_value";
    public static String inputactionbyId="observation_action_by_id";
    public static String inputQuestionId="question_id";
    public static String inputAswer="answer";
    public static String inputobservationCode="observation_code";
    public static String inputEnterBy="enter_by";
    public static String inputFieldId="field_id";
    public static String inputImage="image";
    public static String inputImage2="image2";
    public static String inputPlatform="platform";
    public static String inputFeedback="feedback";
    public static String inputStatus="status";
    public static String inputSafteyOthers="safety_others";
    public static String inputSafteyExplain="safety_explain";
    public static String inputTotalQuestions="total_question";
    public static String inputCorrectAnswers="correct_answers";
    public static String inputAnsweredQuestions ="wrong_question";
    public static String inputTitleId ="title_id";
    public static String inputSafetyCategoryUpdate="SafetyCategory";
    public static String inputHappen="desc";
    public static String inputActionCorrection="action_correction";
    public static String inputActionTaken="action_taken";
    public static String inputPostBy="post_by";
    public static String inputFromDate="from_date";
    public static String inputToDate="to_date";
    public static String inputQuestionsId="ques_id";
    public static String getInputActivityDescriptionUpdate="action_descption";
    public static String getInputCorrectiveActionUpdate="immediate_corrective";
    public static String getInputRecommendationUpdate="futher_recommend";
    public static String prefEmployeeName="EmployeeName";
    public static String prefEmployeeId="EmployeeID";
    public static String prefEmployeeNo="EmployeeNo";
    public static String prefEmployeeDesignation="EmployeeDesignation";
    public static String prefRegCode="Regcode";
    public static String inputLocationId="location_id";
    public static String inputSpecificLocationId="specific_location_id";
    public static String inputIncidentImage="incident_image";

    public static String inputuserStatus="status";
    public static String inputDependentStatus="dept_status";
    public static String inputusercomments="additional_comments";
    public static String inputdependentComment="additional_comments1";
    public static String inputAssistanceRequired="assistance";
    public static String inputHowcanHelp="help";
    public static String inputHowToReach="reach";
    public static String inputEmergencyCaseId="verify_id";
    public static String inputHeadCountId="head_count_id";
    public static String inputHeadUserId="head_user_id";
    public static String inputEmployeeId="employee_id";
    public static String inputHeadCountIdNew="headcount_id";

    public static String ErrorMessage(VolleyError volleyError) {
        String message = null;
        if (volleyError instanceof NetworkError) {
            message = "Cannot connect to Internet...Please check your connection!";
// Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        } else if (volleyError instanceof ServerError) {
            message = "The server could not be found. Please try again after some time!!";
// Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        } else if (volleyError instanceof AuthFailureError) {
            message = "Cannot connect to Internet...Please check your connection!";
// Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        } else if (volleyError instanceof ParseError) {
            message = "Parsing error! Please try again after some time!!";
// Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        } else if (volleyError instanceof NoConnectionError) {
            message = "Cannot connect to Internet...Please check your connection!";
//Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        } else if (volleyError instanceof TimeoutError) {
            message = "Connection TimeOut! Please check your internet connection.";
//Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        }

        //   Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

        return message;
    }

}
