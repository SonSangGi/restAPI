package com.dbdr.response;

import com.dbdr.security.exception.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.net.ConnectException;

@RequiredArgsConstructor
@RestControllerAdvice
@Slf4j
public class ExceptionAdvice {
	private final ResponseService responseService;
	private final MessageSource messageSource;

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	protected Result defaultException(HttpServletRequest request, Exception e) {
		e.printStackTrace();
		return responseService.getFailResult(Integer.valueOf(getMessage("unKnown.code")), getMessage("unKnown.msg"));
	}

	@ExceptionHandler({NoHandlerFoundException.class, HttpRequestMethodNotSupportedException.class})
	@ResponseStatus(HttpStatus.NOT_FOUND)
	protected Result notFoundException(HttpServletRequest request, Exception e) {
		return responseService.getFailResult(Integer.valueOf(getMessage("notFound.code")), getMessage("notFound.msg"));
	}

	@ExceptionHandler(SigninFailedException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	protected Result signinFailedException(HttpServletRequest request, SigninFailedException e) {
		return responseService.getFailResult(Integer.valueOf(getMessage("signinFailed.code")), getMessage("signinFailed.msg"));
	}

	@ExceptionHandler(AuthenticationEntryPointException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	protected Result authenticationEntryPointException(HttpServletRequest request, AuthenticationEntryPointException e) {
		return responseService.getFailResult(Integer.valueOf(getMessage("entryPointException.code")), getMessage("entryPointException.msg"));
	}

	@ExceptionHandler(AccessDeniedException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	protected Result accessDeniedException(HttpServletRequest request, AccessDeniedException e) {
		return responseService.getFailResult(Integer.valueOf(getMessage("accessDenied.code")), getMessage("accessDenied.msg"));
	}

	@ExceptionHandler(PersistenceException.class)
	protected Result connectionFailedException(HttpServletRequest request, AccessDeniedException e) {
		return responseService.getFailResult(Integer.valueOf(getMessage("connectionFailed.code")), getMessage("connectionFailed.msg"));
	}

	@ExceptionHandler(DuplicateUsersException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	protected Result duplicateUsersException(HttpServletRequest request, DuplicateUsersException e) {
		return responseService.getFailResult(Integer.valueOf(getMessage("duplicateUsers.code")), getMessage("duplicateUsers.msg"));
	}

	@ExceptionHandler(UserNotFoundException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	protected Result userNotFoundException(HttpServletRequest request,  UserNotFoundException e) {
		return responseService.getFailResult(Integer.valueOf(getMessage("userNotFound.code")), getMessage("userNotFound.msg"));
	}

	// code 정보에 해당하는 메세지 조회
	private String getMessage(String code) {
		return getMessage(code, null);
	}

	// code 정보, 추가 argument로 현재 locale에 맞는 메세지 조회
	private String getMessage(String code, Object[] args) {
		return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
	}
}
