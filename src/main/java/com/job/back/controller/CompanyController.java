package com.job.back.controller;

import java.util.List;

import javax.validation.Valid;

import org.aspectj.apache.bcel.classfile.Module.Require;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.job.back.common.constant.ApiPattern;
import com.job.back.dto.request.company.CompanyAdditionalInfoDto;
import com.job.back.dto.request.company.GetCompanyListMainDto;
import com.job.back.dto.request.company.PatchCompanyProfileDto;
import com.job.back.dto.request.company.ValidateCompanyEmailDto;
import com.job.back.dto.request.company.ValidateCompanyTelNumberDto;
import com.job.back.dto.response.ResponseDto;
import com.job.back.dto.response.company.CompanyInfoResponseDto;
import com.job.back.dto.response.company.GetCompanyListMainResponseDto;
import com.job.back.dto.response.company.GetCompanyPageResponseDto;
import com.job.back.dto.response.company.GetCompanyResponseDto;
import com.job.back.dto.response.company.GetRelatedSearchWordResponseDto;
import com.job.back.dto.response.company.GetSearchListResponseDto;
import com.job.back.dto.response.company.GetCompanyTop3ListResponseDto;
import com.job.back.dto.response.company.GetMyApplyCompanyResponseDto;
import com.job.back.dto.response.company.PatchCompanyProfileResponseDto;
import com.job.back.dto.response.company.ValidateCompanyEmailResponseDto;
import com.job.back.dto.response.company.ValidateCompanyTelNumberResponseDto;
import com.job.back.service.CompanyService;
import com.job.back.service.ApplicantService;
import com.job.back.service.CompanyFileService;
import com.job.back.service.implementation.CompanyServiceImplements;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(description = "회사 모듈")
@RestController
@RequestMapping(ApiPattern.COMPANY)
public class CompanyController {
    @Autowired private CompanyService companyService;
    @Autowired private CompanyFileService companyFileService;
    @Autowired private ApplicantService applicantService;

    private final String GET_COMPANY = "/";
    private final String VALIDATE_COMPANY_EMAIL = "/validate/companyEmail";
    private final String VALIDATE_COMPANY_TEL_NUMBER = "/validate/companyTelNumber";
    private final String PATCH_COMPANY_PROFILE = "/companyProfile";
    private final String Main_list_company_info = "/list";
    private final String MAIN_HEAD_TOP3_LIST = "/top3-company-list";
    private final String INSERT_COMPANY_INFO = "/insertCompanyInfo";
    private final String UPDATE_COMPANY_INFO = "/updateCompanyInfo";
    private final String GET_COMPANY_INFO = "/getCompanyInfo";
    private final String GET_COMPANY_PAGE = "/{companyTelNumber}";

    @ApiOperation(value = "회사 정보 불러오기")
    @GetMapping(GET_COMPANY)
    public ResponseDto<GetCompanyResponseDto> getCompany(@AuthenticationPrincipal String companyEmail){
        ResponseDto<GetCompanyResponseDto> response = companyService.getCompany(companyEmail);
        return response;
    }

    @ApiOperation(value = "회사이메일로 중복 체크하기")
    @PostMapping(VALIDATE_COMPANY_EMAIL)
    public ResponseDto<ValidateCompanyEmailResponseDto> validateCompanyEmail(@Valid @RequestBody ValidateCompanyEmailDto requestBody){
        ResponseDto<ValidateCompanyEmailResponseDto> response = companyService.validateCompanyEmail(requestBody);
        return response;
    }

    @ApiOperation(value = "회사 전화번호로 중복 체크하기")
    @PostMapping(VALIDATE_COMPANY_TEL_NUMBER)
    public ResponseDto<ValidateCompanyTelNumberResponseDto> validateCompanyTelNumber(@Valid @RequestBody ValidateCompanyTelNumberDto requestBody){
        ResponseDto<ValidateCompanyTelNumberResponseDto> response = companyService.validateCompanyTelNumber(requestBody);
        return response;
    }

    @ApiOperation(value = "회사 프로필 사진 URL 수정")
    @PatchMapping(PATCH_COMPANY_PROFILE)
    public ResponseDto<PatchCompanyProfileResponseDto> patchCompanyProfile(
        @ApiParam(hidden = true)
        @AuthenticationPrincipal String companyEmail,
        @Valid @RequestBody PatchCompanyProfileDto requestBody
    ){
        ResponseDto<PatchCompanyProfileResponseDto> response = companyService.patchCompanyProfile(companyEmail,requestBody);
        return response;

    }

    @ApiOperation(value = "회사 리스트 가져오기",notes = "회사정보를 response에 담아서 뿌려준다 만약 실패시 실패 메세지를 전달한다.")
    @GetMapping(Main_list_company_info)
    public ResponseDto<GetCompanyListMainResponseDto[]> getCompanyListMain(@AuthenticationPrincipal String companyEmail){
        ResponseDto<GetCompanyListMainResponseDto[]> response  = companyService.getCompanyListMain(companyEmail);
        return response;

    }

    @ApiOperation(value = "회사 top3 리스트 가져오기")
    @GetMapping(MAIN_HEAD_TOP3_LIST)
    public ResponseDto<GetCompanyTop3ListResponseDto[]> getCompanyListTop3(@AuthenticationPrincipal String companyEmail){
        ResponseDto<GetCompanyTop3ListResponseDto[]> response = companyService.getTop3CompanyList(companyEmail);
        return response;
    
    }

    @ApiOperation(value = "회사 추가 정보 저장")
    @PostMapping(INSERT_COMPANY_INFO)
    public ResponseDto<CompanyInfoResponseDto>insertCompanyInfo(@Valid @RequestBody CompanyAdditionalInfoDto requestBody){
        ResponseDto<CompanyInfoResponseDto> response = companyService.insertCompanyAdditionalInfo(requestBody);
        return response;
    }
    // @ApiOperation(value = "회사 추가 정보 수정")
    // @PostMapping(UPDATE_COMPANY_INFO)
    // public ResponseDto<CompanyInfoResponseDto>updataCompanyInfo(@Valid @RequestBody CompanyAdditionalInfoDto requestBody) {
    //     ResponseDto<CompanyInfoResponseDto> response = companyService.updateCompanyInfo(requestBody);
    //     return response;
    // }

    // @ApiOperation(value = "회사 추가정보 조회")
    // @GetMapping(GET_COMPANY_INFO)
    // public ResponseDto<GetCompanyInfoResponseDto>getComapnyInfo(@Valid @RequestBody String companyTelNumber) {
    //     ResponseDto<GetCompanyInfoResponseDto> response = companyService.getComapnyInfo(companyTelNumber);
    //     return response;
    // }

    @ApiOperation(value = "회사 페이지 조회")
    @GetMapping(GET_COMPANY_PAGE)
    public ResponseDto<GetCompanyPageResponseDto> getCompanyPage(@PathVariable("companyTelNumber") String companyTelNumber){
        ResponseDto<GetCompanyPageResponseDto> response = companyService.getCompanyPage(companyTelNumber);
        return response;
    }


    


    



    

}
