package com.derric.vote.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.derric.vote.beans.Candidate;
import com.derric.vote.beans.CandidateDetail;
import com.derric.vote.beans.User;
import com.derric.vote.constants.PageConstants;
import com.derric.vote.constants.URLConstants;
import com.derric.vote.forms.AdminCandidateForm;
import com.derric.vote.services.CandidateServices;
import com.derric.vote.validators.AdminCandidateValidator;
import com.derric.vote.validators.CoreValidator;

@Controller
@SessionAttributes(value = { "candidateForm" })
public class AdminCandidateController {

	@Autowired
	private AdminCandidateValidator candidateValidator;
	@Autowired
	private CandidateServices candidateServices;
	@Autowired
	private CoreValidator coreValidator;

	@RequestMapping(value = "/" + URLConstants.ADMIN_CANDIDATE, method = RequestMethod.GET)
	public String showAddCandidate(Model model) {
		AdminCandidateForm candidateForm = new AdminCandidateForm();
		List<Candidate> candidates=candidateServices.getAllCandidates();
		candidates.stream().parallel().forEach(action ->convertToBase64(action));
		model.addAttribute("candidateForm", candidateForm);
		model.addAttribute("candidates",candidates);
		return PageConstants.ADMIN_CANDIDATE_PAGE;
	}
	public void convertToBase64(Candidate candidate) {
		ByteBuffer profilePhotoBuffer=(ByteBuffer)candidate.getDetail(CandidateDetail.PROFILE_PHOTO);
		String encoded= Base64.getEncoder().encodeToString(profilePhotoBuffer.array());
		candidate.setDetail(CandidateDetail.PROFILE_PHOTO, encoded);
		ByteBuffer symbolBuffer=(ByteBuffer)candidate.getDetail(CandidateDetail.SYMBOL);
		String symbolEncoded=Base64.getEncoder().encodeToString(symbolBuffer.array());
		candidate.setDetail(CandidateDetail.SYMBOL, symbolEncoded);
	}
	@RequestMapping(value = "/" + URLConstants.ADMIN_CANDIDATE, method = RequestMethod.POST)
	public String submitCandidate(HttpServletRequest request,
			@ModelAttribute("candidateForm") @Validated AdminCandidateForm candidateForm, BindingResult result,Errors errors)
			throws IOException, ServletException {
		if (result.hasErrors()) {
			return PageConstants.ADMIN_CANDIDATE_PAGE;
		}
		Part profileImage = request.getPart("profilePhoto");
		Part symbolImage=request.getPart("symbol");
		System.out.println("Size::"+profileImage.getSize());
		boolean hasErrors=false;
		hasErrors=coreValidator.rejectIfNoUploadFileFound(errors, profileImage, "profilePhoto","file.notfound","Profile photo must be uploaded");
		if(hasErrors) {
			return PageConstants.ADMIN_CANDIDATE_PAGE;
		}
		hasErrors=coreValidator.rejectIfNotExpectedFileType(errors, profileImage,getFileExtensions() ,"profilePhoto","file.notImage","Only images can be uploaded(jpg/jpeg/png/gif)");
		if(hasErrors) {
			return PageConstants.ADMIN_CANDIDATE_PAGE;
		}
		hasErrors=coreValidator.rejectIfFileSizeIsMore(errors, profileImage, 4194304, "profilePhoto", "file.moreSize", "Image size must be less than 4 MB");
		if(hasErrors) {
			return PageConstants.ADMIN_CANDIDATE_PAGE;
		}
		hasErrors=coreValidator.rejectIfNoUploadFileFound(errors, symbolImage, "symbol","file.notfound","Profile photo must be uploaded");
		if(hasErrors) {
			return PageConstants.ADMIN_CANDIDATE_PAGE;
		}
		hasErrors=coreValidator.rejectIfNotExpectedFileType(errors, symbolImage,getFileExtensions() ,"symbol","file.notImage","Only images can be uploaded(jpg/jpeg/png/gif)");
		if(hasErrors) {
			return PageConstants.ADMIN_CANDIDATE_PAGE;
		}
		hasErrors=coreValidator.rejectIfFileSizeIsMore(errors, symbolImage, 4194304, "symbol", "file.moreSize", "Image size must be less than 4 MB");
		if(hasErrors) {
			return PageConstants.ADMIN_CANDIDATE_PAGE;
		}
		candidateServices.addCandidate(candidateForm, (User)request.getSession().getAttribute("user"),profileImage,symbolImage);
		
		return "redirect:/"+PageConstants.ADMIN_CANDIDATE_PAGE;
	}
	
	private List<String> getFileExtensions(){
		List<String> permittedExtensions=new ArrayList<>();
		permittedExtensions.add(".jpg");
		permittedExtensions.add(".jpeg");
		permittedExtensions.add(".png");
		permittedExtensions.add(".gif");
		permittedExtensions.add(".JPG");
		permittedExtensions.add(".JPEG");
		permittedExtensions.add(".PNG");
		permittedExtensions.add(".GIF");
		return permittedExtensions;
	}
	
	
	@RequestMapping(value = "getImage", method = RequestMethod.GET)
	public String getImage(Model model) throws IOException {
	//	String imgString = candidateServices.getImageBytes();
	//	model.addAttribute("imgBytes", imgString);
		return "showImagejsp";
	}

	@InitBinder("candidateForm")
	public void initBinder(WebDataBinder binder) {
		binder.setValidator(candidateValidator);
	}
}
