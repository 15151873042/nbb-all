package org.hp.thymeleaf.controller;

import org.hp.thymeleaf.domain.TableDataInfo;
import org.hp.thymeleaf.domain.User;
import org.hp.thymeleaf.vo.ResultVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * 用户 CRUD
 * 
 * @author wonders
 * @date 2023-03-13
 */
@Controller
@RequestMapping("/sysUser")
public class SysUserController {

	private String prefix = "user";
	

	
	@GetMapping()
	public String index() {
        return prefix + "/index";
	}

	private static final Map<String, User> userId2User = Collections.synchronizedMap(new LinkedHashMap<String, User>() {
		{
			put("1", new User("1", "张三", "10"));
			put("2", new User("2", "李四", "20"));
			put("3", new User("3", "王五", "30"));
			put("4", new User("4", "赵六", "40"));
			put("5", new User("5", "小张", "10"));
			put("6", new User("6", "小李", "20"));
			put("7", new User("7", "小王", "30"));
			put("8", new User("8", "小赵", "40"));
			put("9", new User("5", "老张", "10"));
			put("10", new User("6", "老李", "20"));
			put("11", new User("7", "老王", "30"));
			put("12", new User("8", "老赵", "40"));
		}
	});

	/**
	 * 查询用户列表
	 */
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(Integer pageNum, Integer pageSize) {
		Integer start = (pageNum -1) * pageSize;
		TableDataInfo rspData = new TableDataInfo();
		rspData.setCode(0);
		List<User> allUsers = userId2User.values().stream().collect(Collectors.toList());
		Integer end = start + pageSize >= allUsers.size() ? allUsers.size() : start + pageSize;
		List<User> pageUsers = allUsers.subList(start, end);
		rspData.setRows(pageUsers);
		rspData.setTotal(allUsers.size());
		return rspData;
	}

    /**
     * 详细用户
     */
    @GetMapping("/detail/{userId}")
    public String detail(@PathVariable("userId") String id, ModelMap mmap) {
		User user = userId2User.get(id);
		mmap.put("sysUser", user);
        return prefix + "/detail";
    }


	/**
	 * 新增用户
	 */
	@GetMapping("/add")
	public String add(ModelMap mmap) {
        mmap.put("sysUser",new User());
        mmap.put("type","add");
        return prefix + "/edit";
	}
	
	/**
	 * 新增保存用户
	 */
	@PostMapping("/add")
	@ResponseBody
	public ResultVo addSave(User user) {
		user.setId(String.valueOf(System.currentTimeMillis()));
		userId2User.put(user.getId(), user);
		return new ResultVo();
	}

	/**
	 * 修改用户
	 */
	@GetMapping("/edit/{userId}")
	public String edit(@PathVariable("userId") String userId, ModelMap mmap) {
		User user = userId2User.get(userId);
		mmap.put("sysUser", user);
        mmap.put("type","edit");
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存用户
	 */
	@PostMapping("/edit")
	@ResponseBody
	public ResultVo editSave( User user) {
		userId2User.put(user.getId(), user);
		return new ResultVo();
	}

	/**
	 * 删除用户
	 */
	@PostMapping("/remove")
	@ResponseBody
	public ResultVo remove(String ids) {
		userId2User.remove(ids);
		return new ResultVo();
	}
}
