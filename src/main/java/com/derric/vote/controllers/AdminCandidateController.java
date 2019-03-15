package com.derric.vote.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
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
		model.addAttribute("candidateForm", candidateForm);
		return PageConstants.ADMIN_CANDIDATE_PAGE;
	}

	@RequestMapping(value = "/" + URLConstants.ADMIN_CANDIDATE, method = RequestMethod.POST)
	public String submitCandidate(HttpServletRequest request,
			@ModelAttribute("candidateForm") @Validated AdminCandidateForm candidateForm, BindingResult result,Errors errors)
			throws IOException, ServletException {
		if (result.hasErrors()) {
			return PageConstants.ADMIN_CANDIDATE_PAGE;
		}
		Part profileImage = request.getPart("profilePhoto");
		System.out.println("Size::"+profileImage.getSize());
		boolean hasErrors=false;
		hasErrors=coreValidator.rejectIfNoUploadFileFound(errors, profileImage, "profilePhoto","file.notfound","Profile photo must be uploaded");
		if(hasErrors) {
			return PageConstants.ADMIN_CANDIDATE_PAGE;
		}
		List<String> permittedExtensions=new ArrayList<>();
		permittedExtensions.add(".jpg");
		permittedExtensions.add(".jpeg");
		permittedExtensions.add(".png");
		permittedExtensions.add(".gif");
		hasErrors=coreValidator.rejectIfNotExpectedFileType(errors, profileImage,permittedExtensions ,"profilePhoto","file.notImage","Only images can be uploaded(jpg/jpeg/png/gif)");
		if(hasErrors) {
			return PageConstants.ADMIN_CANDIDATE_PAGE;
		}
		candidateServices.addCandidate(candidateForm, (User)request.getSession().getAttribute("user"));
		
		System.out.println("filepart::"+profileImage);
		System.out.println("filename::"+profileImage.getSubmittedFileName());
		
		if (profileImage.getSubmittedFileName()!=null && !profileImage.getSubmittedFileName().isEmpty()) {
			OutputStream out = new FileOutputStream(new File("F:\\Softwarez\\"+profileImage.getSubmittedFileName()));
			InputStream fileContent = profileImage.getInputStream();
			int read = 0;
			byte[] bytes = new byte[1024]; // Reading 1 MB from inputStream then writing it to file
			while ((read = fileContent.read(bytes)) != -1) { // -1 means read completed
				out.write(bytes, 0, read);
			}
			System.out.println("File succesffully saved");
		}
		return PageConstants.ADMIN_CANDIDATE_PAGE;
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
