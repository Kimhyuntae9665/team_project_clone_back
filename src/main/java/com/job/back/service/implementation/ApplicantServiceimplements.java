package com.job.back.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;

import com.job.back.common.constant.ResponseMessage;
import com.job.back.dto.Applicant_Content_Dto;
import com.job.back.dto.Carrer_Dto;
import com.job.back.dto.License_Dto;
import com.job.back.dto.University_Grade_Dto;
import com.job.back.dto.response.ResponseDto;
import com.job.back.dto.response.applicant.ApplicantScoreResponseDto;
import com.job.back.entity.CompanySelectComponent_Carrer_Entity;
import com.job.back.entity.CompanySelectComponent_License_Entity;
import com.job.back.entity.CompanySelectComponent_University_Entity;
import com.job.back.repository.CompanySelectComponent_Carrer_Repository;
import com.job.back.repository.CompanySelectComponent_License_Repository;
import com.job.back.repository.CompanySelectComponent_University_Repository;
import com.job.back.service.ApplicantService;


public class ApplicantServiceimplements implements ApplicantService{

    @Autowired
    CompanySelectComponent_University_Repository companySelectComponent_University_Repository;
    @Autowired
    CompanySelectComponent_Carrer_Repository companySelectComponent_Carrer_Repository;
    @Autowired
    CompanySelectComponent_License_Repository companySelectComponent_License_Repository;
    



    // ! 지원자의 점수를 계산하고 보여주는 ServiceImplemnts
    public ResponseDto<ApplicantScoreResponseDto> show_Applicant_Score(String company_Tel_Number,
                                                                       Applicant_Content_Dto applicantContentDto){

        ApplicantScoreResponseDto data = null;


        try{

            // ! 지원자가 접속한 company의 Select_component와 지원자의 Select_Component를 비교한다 ==> Applicant에게 점수를 확정하여 보여준다 
            // ? Repository는 매개변수로 받은 전화번호에 해당하는 instance를 반환한다 
            CompanySelectComponent_University_Entity company_university_info = companySelectComponent_University_Repository.findByCompanyTelNumber(company_Tel_Number);
            if(company_university_info==null) return ResponseDto.setFailed(ResponseMessage.NOT_EXIST_COMPANY);


            CompanySelectComponent_Carrer_Entity company_carrer_info = companySelectComponent_Carrer_Repository.findByCompanyTelNumber(company_Tel_Number);
            if(company_carrer_info==null) return ResponseDto.setFailed(ResponseMessage.NOT_EXIST_COMPANY);

            CompanySelectComponent_License_Entity company_license_info = companySelectComponent_License_Repository.findByCompanyTelNumber(company_Tel_Number);
            if(company_license_info==null) return ResponseDto.setFailed(ResponseMessage.NOT_EXIST_COMPANY);

            


        }catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed(ResponseMessage.DATABASE_ERROR);
        }

        return ResponseDto.setSuccess(ResponseMessage.SUCCESS, data);




    }


    
}
