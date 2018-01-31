package cn.e3mall.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 首页
 * @author AstoIfy
 *
 */
@Controller
public class IndexController {

	@RequestMapping("/index.html")
	public String toIndex(){
		return "index";
	}
}
