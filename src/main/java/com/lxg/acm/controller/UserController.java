package com.lxg.acm.controller;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lxg.acm.entity.User;
import com.lxg.acm.exception.UserException;
import com.lxg.acm.mapper.UserMapper;


/**
 * 用户管理
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserMapper userMapper;

	/**
	 * 添加用户
	 * @param user
	 * @return
	 */
	@RequestMapping("/userAdd")
	public String addUser(User user){
		userMapper.save(user);
		return "redirect:/problemlist/1/50";
	}
	
	/**
	 * 用户列表
	 * @param page
	 * @param offset
	 * @return
	 */
	@RequestMapping("/userlist/{page}/{offset}")
	public String findList(@PathVariable Long page, @PathVariable Long offset) {
		return "userlist";
	}
	
	/**
	 * 用户信息
	 * @param uid
	 * @param model
	 * @return
	 */
	@RequestMapping("/userinfo/{uid}")
	public String find(@PathVariable Long uid, Model model) {
		
		List<Integer> problemSolvedList=userMapper.queryByUidSolved(uid);
		
		List<Integer> problemNotSolvedList=userMapper.queryByUidNoSolved(uid);

		model.addAttribute("problemSolvedList", problemSolvedList);
		model.addAttribute("problemNotSolvedList", problemNotSolvedList);

		User user = userMapper.query(uid);
		if (user == null) {
			throw new UserException("用户不存在");
		}
		model.addAttribute("user", user);
		return "userinfo";
	}

	/**
	 * 跳转用户注册页面
	 * @param user
	 * @return
	 */
	@RequestMapping("/regist")
	public String regist(User user) {

		return "regist";
	}

	/**
	 * 跳转修改用户页面
	 * @param uid
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/update/{uid}", method = RequestMethod.GET)
	public String updatePage(@PathVariable Long uid, Model model) {
		User user = userMapper.query(uid);
		model.addAttribute("user", user);
		return "update";
	}

	/**
	 * 修改用户
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(User user) {
		userMapper.update(user);
		return "redirect:/problemlist/1/50";
	}

	// 跳转登陆页面
	@RequestMapping(value = "/login")
	public String login(User user) {
		return "login";
	}

	// 退出
	@RequestMapping(value = "/logout")
	public String logout() {
		return "index";
	}

	// 删除用户
	@RequestMapping("/remove")
	@ResponseBody
	public String remove(Long user) {

		return "";
	}

}