package cn.e3mall.content.service.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.common.pojo.TreeNode;
import cn.e3mall.content.service.ContentCatService;
import cn.e3mall.mapper.TbContentCategoryMapper;
import cn.e3mall.pojo.TbContentCategory;
import cn.e3mall.pojo.TbContentCategoryExample;
import cn.e3mall.pojo.TbContentCategoryExample.Criteria;
@Service
public class ContentCatServiceImpl implements ContentCatService {

	@Autowired
	private TbContentCategoryMapper tbContentCategoryMapper;
	
	public List<TreeNode> getContentCatList(long parentId) {
		//1.封装查询条件
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		//2.查询
		List<TbContentCategory> list = tbContentCategoryMapper.selectByExample(example );
		//3.封装List<TreeNode>
		List<TreeNode> nodes = new ArrayList<>();
		for (TbContentCategory tbContentCategory : list) {
			TreeNode treeNode = new TreeNode();
			treeNode.setId(tbContentCategory.getId());
			treeNode.setText(tbContentCategory.getName());
			treeNode.setState(tbContentCategory.getIsParent()?"closed":"open");
			nodes.add(treeNode);
		}
		return nodes;
	}

	@Override
	public E3Result addContentCat(long parentId, String name) {
		//1.设置tbContentCategory对象属性
		TbContentCategory tbContentCategory = new TbContentCategory();
		tbContentCategory.setName(name);
		tbContentCategory.setParentId(parentId);
		tbContentCategory.setCreated(new Date());
		tbContentCategory.setUpdated(new Date());
		tbContentCategory.setSortOrder(1);
		tbContentCategory.setIsParent(false);
		//2.插入数据
		tbContentCategoryMapper.insertSelective(tbContentCategory);
		//3.判断其父节点是否是叶子节点,是的话将其IsParent属性设为true
		TbContentCategory parent = tbContentCategoryMapper.selectByPrimaryKey(parentId);
		if(!parent.getIsParent()){
			parent.setIsParent(true);
			//跟新至数据库
			tbContentCategoryMapper.updateByPrimaryKeySelective(parent);
		}
		//4.将生成的主键返回
		return E3Result.ok(tbContentCategory);
	}

	@Override
	public E3Result updateContentCat(long id, String name) {
		TbContentCategory record = new TbContentCategory();
		record.setId(id);
		record.setName(name);
		tbContentCategoryMapper.updateByPrimaryKeySelective(record );
		return E3Result.ok();
	}

	@Override
	public E3Result deleteContentCat(long id) {
		//0.根据id查询parentId信息
		TbContentCategory category = tbContentCategoryMapper.selectByPrimaryKey(id);
		Long parentId = category.getParentId();
		//1.判断其是否有子类,有则提示先将叶子节点删掉,再删除它
		if(category.getIsParent()){
			return null;
		}
		//2.根据id从数据库删除分类信息
		tbContentCategoryMapper.deleteByPrimaryKey(id);
		//3.判断其父节点是否还有叶子节点
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		int count = tbContentCategoryMapper.countByExample(example );
		if(count == 0){
			TbContentCategory parent = tbContentCategoryMapper.selectByPrimaryKey(parentId);
			parent.setIsParent(false);
			//更新至数据库
			tbContentCategoryMapper.updateByPrimaryKeySelective(parent);
		}
		return E3Result.ok();
	}
	
}
