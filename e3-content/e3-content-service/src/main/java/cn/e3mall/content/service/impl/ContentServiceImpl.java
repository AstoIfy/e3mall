package cn.e3mall.content.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.e3mall.common.pojo.DataGridResult;
import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.content.service.ContentService;
import cn.e3mall.mapper.TbContentMapper;
import cn.e3mall.pojo.TbContent;
import cn.e3mall.pojo.TbContentExample;
import cn.e3mall.pojo.TbContentExample.Criteria;
@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private TbContentMapper tbContentMapper;
	
	public DataGridResult findContentList(long categoryId, int page, int rows) {
		//1.创建查询条件
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		if(categoryId != 0){
			criteria.andCategoryIdEqualTo(categoryId);
		}
		//2.设置分页
		PageHelper.startPage(page, rows);
		
		//3.执行查询
		List<TbContent> list = tbContentMapper.selectByExample(example);
		
		//4.获取分页结果,并封装DataGridResult
		PageInfo pageInfo = new PageInfo<>(list);
		DataGridResult result = new DataGridResult();
		result.setTotal(pageInfo.getTotal());
		result.setRows(list);
		return result;
	}

	@Override
	public E3Result addContent(TbContent tbContent) {
		//1.完善对象信息
		tbContent.setCreated(new Date());
		tbContent.setUpdated(new Date());
		//2.保存对象
		tbContentMapper.insertSelective(tbContent);
		return E3Result.ok();
	}

	@Override
	public E3Result updateContent(TbContent tbContent) {
		//1.完善对象信息
		tbContent.setUpdated(new Date());
		//2.更新对象
		tbContentMapper.updateByPrimaryKeySelective(tbContent);
		return E3Result.ok();
	}

	@Override
	public E3Result deleteContent(String ids) {
		//1.分割ids
		String[] split = ids.split(",");
		for (String id : split) {
			tbContentMapper.deleteByPrimaryKey(Long.valueOf(id));
		}
		return E3Result.ok();
	}

}
