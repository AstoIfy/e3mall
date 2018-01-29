package cn.e3mall.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.e3mall.common.pojo.DataGridResult;
import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.common.utils.IDUtils;
import cn.e3mall.mapper.TbItemDescMapper;
import cn.e3mall.mapper.TbItemMapper;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;
import cn.e3mall.pojo.TbItemExample;
import cn.e3mall.service.ItemService;
@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private TbItemMapper tbItemMapper;
	@Autowired
	private TbItemDescMapper tbItemDescMapper;
	@Override
	public TbItem findItemById(Long itemid) {
		
		return tbItemMapper.selectByPrimaryKey(itemid);
	}
	@Override
	public DataGridResult getItemList(int page, int rows) {
		//1.设置分页信息
		PageHelper.startPage(page,rows);
		//2.执行查询									
		TbItemExample example = new TbItemExample();
		List<TbItem> list = tbItemMapper.selectByExample(example);
		//3.获取总记录数
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		long total = pageInfo.getTotal();
		//4.封装dataGridResult
		DataGridResult result = new DataGridResult();
		result.setTotal(total);
		result.setRows(list);
		return result;
	}
	@Override
	public E3Result addItem(TbItem item, String desc) {
		//1.补充商品信息
		item.setId(IDUtils.genItemId());
		item.setStatus((byte) 1);//商品状态，1-正常，2-下架，3-删除
		item.setCreated(new Date());
		item.setUpdated(new Date());
		//2.存储商品
		tbItemMapper.insert(item);
		//3.创建TbItemDesc对象并封装
		TbItemDesc tbItemDesc = new TbItemDesc();
		tbItemDesc.setItemDesc(desc);
		tbItemDesc.setCreated(new Date());
		tbItemDesc.setUpdated(new Date());
		tbItemDesc.setItemId(item.getId());
		tbItemDescMapper.insert(tbItemDesc);
		return E3Result.ok();
	}
	//更新商品
	@Override
	public E3Result updateItem(TbItem item, String desc) {
		//1.为item设置更新时间
		item.setUpdated(new Date());
		//2.根据id更新item
		tbItemMapper.updateByPrimaryKeySelective(item);
		//3.创建TbItemDesc对象并封装
		TbItemDesc tbItemDesc = new TbItemDesc();
		tbItemDesc.setItemDesc(desc);
		tbItemDesc.setUpdated(new Date());
		tbItemDesc.setItemId(item.getId());
		tbItemDescMapper.updateByPrimaryKeySelective(tbItemDesc);
		return E3Result.ok();
	}
	
	//删除商品
	@Override
	public E3Result deleteItem(String ids) {
		//1.分割ids
		String[] split = ids.split(",");
		//2.循环遍历删除 item 和itemDesc
		for (String id : split) {
			tbItemMapper.deleteByPrimaryKey(Long.valueOf(id));
			tbItemDescMapper.deleteByPrimaryKey(Long.valueOf(id));
		}	
		return E3Result.ok();
	}

}
