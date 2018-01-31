package cn.e3mall.content.service;

import java.util.List;

import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.common.pojo.TreeNode;

public interface ContentCatService {

	List<TreeNode> getContentCatList(long parentId);

	E3Result addContentCat(long parentId, String name);

	E3Result updateContentCat(long id, String name);

	E3Result deleteContentCat(long id);

}
