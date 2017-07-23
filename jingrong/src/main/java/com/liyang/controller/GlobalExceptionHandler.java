package com.liyang.controller;

import java.sql.SQLException;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.liyang.domain.exception.Exception;
import com.liyang.domain.exception.ExceptionRepository;
import com.liyang.util.FailReturnObject;
import com.liyang.util.ReturnObject;
import com.liyang.util.ReturnObjectImpl;

@ControllerAdvice
public class GlobalExceptionHandler {

	@Autowired
	ExceptionRepository exceptionRepository;

	@Value("${spring.profiles.active}")
	private String active;

//	@ExceptionHandler(java.lang.Exception.class)
	@ResponseBody
	public ReturnObject handleBizExp(java.lang.Exception ex) throws java.lang.Exception {

		if (ex instanceof FailReturnObject) {
			if (((FailReturnObject) ex).getLevel().equals(FailReturnObject.Level.LOG)) {
				Exception exception = new Exception();
				exception.setActionStatus(((FailReturnObject) ex).getActionStatus());
				exception.setErrorCode(((FailReturnObject) ex).getErrorCode());
				exception.setErrorInfo(((FailReturnObject) ex).getErrorInfo());
				exceptionRepository.save(exception);
			}
			ReturnObjectImpl returnObjectImpl = new ReturnObjectImpl();
			returnObjectImpl.setActionStatus(((FailReturnObject) ex).getActionStatus());
			returnObjectImpl.setErrorCode(((FailReturnObject) ex).getErrorCode());
			returnObjectImpl.setErrorInfo(((FailReturnObject) ex).getErrorInfo());
			return returnObjectImpl;
		} else {

			Exception exception = new Exception();
			exception.setActionStatus("FAIL");
			exception.setErrorCode(500);
			exception.setErrorInfo(ex.getClass().toString() + "\n" + ex.getMessage() + "\n" + ex.getCause() + "\n"
					+ Arrays.toString(ex.getStackTrace()));
			exceptionRepository.save(exception);

			ReturnObjectImpl returnObjectImpl = new ReturnObjectImpl();
			returnObjectImpl.setActionStatus("FAIL");
			returnObjectImpl.setErrorCode(500);
			returnObjectImpl.setErrorInfo(ex.getMessage());
			return returnObjectImpl;
		}

//		 return null;
	}
}