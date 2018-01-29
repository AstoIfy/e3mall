package cn.e3mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.common.pojo.DataGridResult;
import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.service.ItemService;

@Controller
public class ItemController {

	@Autowired
	private ItemService itemService;
	
	@RequestMapping("/item/list")
	@ResponseBody
	public DataGridResult getItemList(int page,int rows){
		DataGridResult result = itemService.getItemList(page,rows);
		return result;
	}
	
	@RequestMapping("/item/save")
	@ResponseBody
	public E3Result addItem(TbItem item,String desc){
		E3Result result = itemService.addItem(item,desc);
		return result;
	}
	
	@RequestMapping("/item/update")
	@ResponseBody
	public E3Result updateItem(TbItem item,String desc){
		E3Result result = itemService.updateItem(item,desc);
		return result;
	}
	@RequestMapping("/item/delete")
	@ResponseBody
	public E3Result deleteItem(String ids){
		E3Result result = itemService.deleteItem(ids);
		return result;
	}
	
	
}
