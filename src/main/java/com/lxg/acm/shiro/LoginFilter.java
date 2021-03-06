package com.lxg.acm.shiro;

import com.alibaba.fastjson.JSON;
import com.lxg.acm.entity.User;
import com.lxg.acm.mapper.UserMapper;
import com.lxg.acm.support.OnlineUserSupport;
import com.lxg.acm.util.SpringUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 登陆过滤
 * @author Administrator
 *
 */
public class LoginFilter extends FormAuthenticationFilter {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	UserMapper userMapper = SpringUtil.getBean(UserMapper.class);

	// 用户登陆成功
	protected boolean onLoginSuccess(AuthenticationToken token,
			Subject subject, ServletRequest request, ServletResponse response)
			throws Exception {
		User user = userMapper.queryByUsername((String) subject.getPrincipal());
		subject.getSession().setAttribute("user", user);
		logger.info("用户user={}登陆成功", JSON.toJSON(user));
		// OnlineUser
		OnlineUserSupport.add(user);
		return super.onLoginSuccess(token, subject, request, response);
	}

	// 错误过滤
	protected void setFailureAttribute(ServletRequest request,
			AuthenticationException ae) {
		if (ae instanceof UnknownAccountException) {
			request.setAttribute(getFailureKeyAttribute(), "账户不存在");
		} else if (ae instanceof IncorrectCredentialsException) {
			request.setAttribute(getFailureKeyAttribute(), "密码不正确");
		} else {
			logger.info("其它错误");
		}
	}

}
